// ignore_for_file: invalid_annotation_target
import 'package:freezed_annotation/freezed_annotation.dart';

part 'auth_models.freezed.dart';
part 'auth_models.g.dart';


@freezed
class LoginRequest with _$LoginRequest {
  const factory LoginRequest({
    required String email,
    required String password,
  }) = _LoginRequest;

  factory LoginRequest.fromJson(Map<String, dynamic> json) => _$LoginRequestFromJson(json);
}

@Freezed(genericArgumentFactories: true)
class LoginResponse<T> with _$LoginResponse<T> {
  const factory LoginResponse({
    String? token,
    String? refreshToken,
    String? tokenType,
    String? name,
    T? user,
    @Default(false) bool mfaRequired,
    String? mfaSecret,
    String? mfaQrCode,
  }) = _LoginResponse;

  factory LoginResponse.fromJson(Map<String, dynamic> json, T Function(Object?) fromJsonT) =>
      _$LoginResponseFromJson(json, fromJsonT);
}

@freezed
class ForgetPasswordRequest with _$ForgetPasswordRequest {
  const factory ForgetPasswordRequest({
    required String email,
  }) = _ForgetPasswordRequest;

  factory ForgetPasswordRequest.fromJson(Map<String, dynamic> json) => _$ForgetPasswordRequestFromJson(json);
}

@freezed
class ResetPasswordRequest with _$ResetPasswordRequest {
  const factory ResetPasswordRequest({
    required String token,
    required String newPassword,
  }) = _ResetPasswordRequest;

  factory ResetPasswordRequest.fromJson(Map<String, dynamic> json) => _$ResetPasswordRequestFromJson(json);
}

@freezed
class TokenValidationResponse with _$TokenValidationResponse {
  const factory TokenValidationResponse({
    required bool valid,
    String? email,
    String? role,
    String? purpose,
    int? expiresAt,
    String? message,
  }) = _TokenValidationResponse;

  factory TokenValidationResponse.fromJson(Map<String, dynamic> json) => _$TokenValidationResponseFromJson(json);
}
