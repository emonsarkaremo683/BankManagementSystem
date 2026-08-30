import 'package:dio/dio.dart';
import 'package:ensarkbank_flutter/core/storage/secure_vault.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../providers/auth_provider.dart';

class AuthInterceptor extends Interceptor {
  final SecureVault _vault;
  final Dio _dio;
  final Ref _ref;

  AuthInterceptor(this._vault, this._dio, this._ref);

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) async {
    // Skip auth header for login, register, and refresh
    if (options.path.contains('api/auth/login') || 
        options.path.contains('api/auth/register') ||
        options.path.contains('api/auth/refresh')) {
      return handler.next(options);
    }

    final token = await _vault.getToken();
    if (token != null) {
      options.headers['Authorization'] = 'Bearer $token';
      debugPrint('AuthInterceptor: Token attached to ${options.path}');
    } else {
      debugPrint('AuthInterceptor: No token found for ${options.path}');
    }
    return handler.next(options);
  }

  @override
  void onError(DioException err, ErrorInterceptorHandler handler) async {
    final bool isUnauthorized = err.response?.statusCode == 401;
    final bool isForbidden = err.response?.statusCode == 403;

    if (isUnauthorized || isForbidden) {
      // Don't intercept 401/403 for login, register, or refresh paths
      if (err.requestOptions.path.contains('api/auth/login') || 
          err.requestOptions.path.contains('api/auth/register') ||
          err.requestOptions.path.contains('api/auth/refresh')) {
        return handler.next(err);
      }

      final refreshToken = await _vault.getRefreshToken();
      if (refreshToken != null) {
        try {
          // Attempt refresh - Note: we use the same dio instance, 
          // but onRequest now skips adding the auth header for refresh path.
          final response = await _dio.post(
            'api/auth/refresh',
            data: {'refreshToken': refreshToken},
          );

          if (response.statusCode == 200) {
            final newToken = response.data['token'];
            final newRefreshToken = response.data['refreshToken'];

            await _vault.saveToken(newToken);
            await _vault.saveRefreshToken(newRefreshToken);

            // Retry original request
            final options = err.requestOptions;
            options.headers['Authorization'] = 'Bearer $newToken';
            
            final retryResponse = await _dio.fetch(options);
            return handler.resolve(retryResponse);
          }
        } catch (e) {
          debugPrint('AuthInterceptor: Token refresh failed: $e');
          // If refresh fails, log out properly
          await _ref.read(authProvider.notifier).logout();
        }
      } else {
        // No refresh token available, or it was a hard 403, log out
        await _ref.read(authProvider.notifier).logout();
      }
    }
    return handler.next(err);
  }
}
