import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/auth/auth_models.dart';
import '../models/customer/customer_models.dart';
import '../providers/core_providers.dart';

part 'auth_repository.g.dart';

class AuthRepository {
  final Dio _dio;

  AuthRepository(this._dio);

  Future<LoginResponse<CustomerResponse>> login(LoginRequest request) async {
    final response = await _dio.post('api/auth/login', data: request.toJson());
    return LoginResponse<CustomerResponse>.fromJson(
      response.data,
      (json) => CustomerResponse.fromJson(json as Map<String, dynamic>),
    );
  }

  Future<LoginResponse<CustomerResponse>> verifyMfa(String email, String totpCode) async {
    final response = await _dio.post('api/auth/verify-mfa', data: {
      'email': email,
      'totpCode': totpCode,
    });
    return LoginResponse<CustomerResponse>.fromJson(
      response.data,
      (json) => CustomerResponse.fromJson(json as Map<String, dynamic>),
    );
  }

  Future<LoginResponse<CustomerResponse>> setupMfa(String email) async {
    final response = await _dio.post('api/auth/setup-mfa', data: {'email': email});
    return LoginResponse<CustomerResponse>.fromJson(
      response.data,
      (json) => CustomerResponse.fromJson(json as Map<String, dynamic>),
    );
  }

  Future<Map<String, String>> confirmMfa(String email, String totpCode) async {
    final response = await _dio.post('api/auth/confirm-mfa', data: {
      'email': email,
      'totpCode': totpCode,
    });
    return Map<String, String>.from(response.data);
  }

  Future<Map<String, String>> disableMfa(String email, String totpCode) async {
    final response = await _dio.post('api/auth/disable-mfa', data: {
      'email': email,
      'totpCode': totpCode,
    });
    return Map<String, String>.from(response.data);
  }

  Future<Map<String, String>> logout() async {
    final response = await _dio.post('api/auth/logout');
    return Map<String, String>.from(response.data);
  }

  Future<CustomerResponse> register(CustomerRequest request, Map<String, List<int>> files) async {
    final Map<String, dynamic> data = request.toJson();
    
    // AddressRequest handling for nested policeStation
    data['addresses'] = request.addresses.map((a) => AddressRequestConverter.toJson(a)).toList();

    final formData = FormData.fromMap({
      'data': MultipartFile.fromString(
        jsonEncode(data),
        contentType: DioMediaType.parse('application/json'),
      ),
    });

    // Add files
    for (var entry in files.entries) {
      formData.files.add(MapEntry(
        entry.key,
        MultipartFile.fromBytes(entry.value, filename: '${entry.key}.jpg'),
      ));
    }

    final response = await _dio.post('api/auth/register', data: formData);
    return CustomerResponse.fromJson(response.data);
  }

  Future<Map<String, String>> verifyEmail(String token) async {
    final response = await _dio.get('api/auth/verify-email', queryParameters: {'token': token});
    return Map<String, String>.from(response.data);
  }

  Future<Map<String, String>> sendVerification(String email) async {
    final response = await _dio.post('api/auth/send-verification', data: {'email': email});
    return Map<String, String>.from(response.data);
  }

  Future<Map<String, String>> forgotPassword(String email) async {
    final response = await _dio.post('api/auth/forgot-password', data: {'email': email});
    return Map<String, String>.from(response.data);
  }

  Future<Map<String, String>> resetPassword(ResetPasswordRequest request) async {
    final response = await _dio.post('api/auth/reset-password', data: request.toJson());
    return Map<String, String>.from(response.data);
  }

  Future<TokenValidationResponse> validateToken({String? token}) async {
    final response = await _dio.post('api/auth/validate', data: token != null ? {'token': token} : null);
    return TokenValidationResponse.fromJson(response.data);
  }
}

@riverpod
AuthRepository authRepository(Ref ref) {
  return AuthRepository(ref.watch(dioProvider));
}
