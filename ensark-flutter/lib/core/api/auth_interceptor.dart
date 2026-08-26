import 'package:dio/dio.dart';
import 'package:ensarkbank_flutter/core/storage/secure_vault.dart';
import 'package:flutter/cupertino.dart';

class AuthInterceptor extends Interceptor {
  final SecureVault _vault;
  final Dio _dio; // Used for refresh token call

  AuthInterceptor(this._vault, this._dio);

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) async {
    if (options.path.contains('api/auth/login') || options.path.contains('api/auth/register')) {
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
    if (err.response?.statusCode == 401) {
      final refreshToken = await _vault.getRefreshToken();
      if (refreshToken != null) {
        try {
          // Attempt refresh
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
          // If refresh fails, clear vault and let error bubble up
          await _vault.clearAll();
        }
      }
    }
    return handler.next(err);
  }
}
