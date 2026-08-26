// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'auth_models.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$LoginRequestImpl _$$LoginRequestImplFromJson(Map<String, dynamic> json) =>
    _$LoginRequestImpl(
      email: json['email'] as String,
      password: json['password'] as String,
    );

Map<String, dynamic> _$$LoginRequestImplToJson(_$LoginRequestImpl instance) =>
    <String, dynamic>{'email': instance.email, 'password': instance.password};

_$LoginResponseImpl<T> _$$LoginResponseImplFromJson<T>(
  Map<String, dynamic> json,
  T Function(Object? json) fromJsonT,
) => _$LoginResponseImpl<T>(
  token: json['token'] as String?,
  refreshToken: json['refreshToken'] as String?,
  tokenType: json['tokenType'] as String?,
  name: json['name'] as String?,
  user: _$nullableGenericFromJson(json['user'], fromJsonT),
  mfaRequired: json['mfaRequired'] as bool? ?? false,
  mfaSecret: json['mfaSecret'] as String?,
  mfaQrCode: json['mfaQrCode'] as String?,
);

Map<String, dynamic> _$$LoginResponseImplToJson<T>(
  _$LoginResponseImpl<T> instance,
  Object? Function(T value) toJsonT,
) => <String, dynamic>{
  'token': instance.token,
  'refreshToken': instance.refreshToken,
  'tokenType': instance.tokenType,
  'name': instance.name,
  'user': _$nullableGenericToJson(instance.user, toJsonT),
  'mfaRequired': instance.mfaRequired,
  'mfaSecret': instance.mfaSecret,
  'mfaQrCode': instance.mfaQrCode,
};

T? _$nullableGenericFromJson<T>(
  Object? input,
  T Function(Object? json) fromJson,
) => input == null ? null : fromJson(input);

Object? _$nullableGenericToJson<T>(
  T? input,
  Object? Function(T value) toJson,
) => input == null ? null : toJson(input);

_$ForgetPasswordRequestImpl _$$ForgetPasswordRequestImplFromJson(
  Map<String, dynamic> json,
) => _$ForgetPasswordRequestImpl(email: json['email'] as String);

Map<String, dynamic> _$$ForgetPasswordRequestImplToJson(
  _$ForgetPasswordRequestImpl instance,
) => <String, dynamic>{'email': instance.email};

_$ResetPasswordRequestImpl _$$ResetPasswordRequestImplFromJson(
  Map<String, dynamic> json,
) => _$ResetPasswordRequestImpl(
  token: json['token'] as String,
  newPassword: json['newPassword'] as String,
);

Map<String, dynamic> _$$ResetPasswordRequestImplToJson(
  _$ResetPasswordRequestImpl instance,
) => <String, dynamic>{
  'token': instance.token,
  'newPassword': instance.newPassword,
};

_$TokenValidationResponseImpl _$$TokenValidationResponseImplFromJson(
  Map<String, dynamic> json,
) => _$TokenValidationResponseImpl(
  valid: json['valid'] as bool,
  email: json['email'] as String?,
  role: json['role'] as String?,
  purpose: json['purpose'] as String?,
  expiresAt: (json['expiresAt'] as num?)?.toInt(),
  message: json['message'] as String?,
);

Map<String, dynamic> _$$TokenValidationResponseImplToJson(
  _$TokenValidationResponseImpl instance,
) => <String, dynamic>{
  'valid': instance.valid,
  'email': instance.email,
  'role': instance.role,
  'purpose': instance.purpose,
  'expiresAt': instance.expiresAt,
  'message': instance.message,
};
