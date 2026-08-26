// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'auth_models.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
  'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models',
);

LoginRequest _$LoginRequestFromJson(Map<String, dynamic> json) {
  return _LoginRequest.fromJson(json);
}

/// @nodoc
mixin _$LoginRequest {
  String get email => throw _privateConstructorUsedError;
  String get password => throw _privateConstructorUsedError;

  /// Serializes this LoginRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of LoginRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $LoginRequestCopyWith<LoginRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LoginRequestCopyWith<$Res> {
  factory $LoginRequestCopyWith(
    LoginRequest value,
    $Res Function(LoginRequest) then,
  ) = _$LoginRequestCopyWithImpl<$Res, LoginRequest>;
  @useResult
  $Res call({String email, String password});
}

/// @nodoc
class _$LoginRequestCopyWithImpl<$Res, $Val extends LoginRequest>
    implements $LoginRequestCopyWith<$Res> {
  _$LoginRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of LoginRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? email = null, Object? password = null}) {
    return _then(
      _value.copyWith(
            email: null == email
                ? _value.email
                : email // ignore: cast_nullable_to_non_nullable
                      as String,
            password: null == password
                ? _value.password
                : password // ignore: cast_nullable_to_non_nullable
                      as String,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$LoginRequestImplCopyWith<$Res>
    implements $LoginRequestCopyWith<$Res> {
  factory _$$LoginRequestImplCopyWith(
    _$LoginRequestImpl value,
    $Res Function(_$LoginRequestImpl) then,
  ) = __$$LoginRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String email, String password});
}

/// @nodoc
class __$$LoginRequestImplCopyWithImpl<$Res>
    extends _$LoginRequestCopyWithImpl<$Res, _$LoginRequestImpl>
    implements _$$LoginRequestImplCopyWith<$Res> {
  __$$LoginRequestImplCopyWithImpl(
    _$LoginRequestImpl _value,
    $Res Function(_$LoginRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of LoginRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? email = null, Object? password = null}) {
    return _then(
      _$LoginRequestImpl(
        email: null == email
            ? _value.email
            : email // ignore: cast_nullable_to_non_nullable
                  as String,
        password: null == password
            ? _value.password
            : password // ignore: cast_nullable_to_non_nullable
                  as String,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$LoginRequestImpl implements _LoginRequest {
  const _$LoginRequestImpl({required this.email, required this.password});

  factory _$LoginRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$LoginRequestImplFromJson(json);

  @override
  final String email;
  @override
  final String password;

  @override
  String toString() {
    return 'LoginRequest(email: $email, password: $password)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoginRequestImpl &&
            (identical(other.email, email) || other.email == email) &&
            (identical(other.password, password) ||
                other.password == password));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, email, password);

  /// Create a copy of LoginRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$LoginRequestImplCopyWith<_$LoginRequestImpl> get copyWith =>
      __$$LoginRequestImplCopyWithImpl<_$LoginRequestImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$LoginRequestImplToJson(this);
  }
}

abstract class _LoginRequest implements LoginRequest {
  const factory _LoginRequest({
    required final String email,
    required final String password,
  }) = _$LoginRequestImpl;

  factory _LoginRequest.fromJson(Map<String, dynamic> json) =
      _$LoginRequestImpl.fromJson;

  @override
  String get email;
  @override
  String get password;

  /// Create a copy of LoginRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$LoginRequestImplCopyWith<_$LoginRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

LoginResponse<T> _$LoginResponseFromJson<T>(
  Map<String, dynamic> json,
  T Function(Object?) fromJsonT,
) {
  return _LoginResponse<T>.fromJson(json, fromJsonT);
}

/// @nodoc
mixin _$LoginResponse<T> {
  String? get token => throw _privateConstructorUsedError;
  String? get refreshToken => throw _privateConstructorUsedError;
  String? get tokenType => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  T? get user => throw _privateConstructorUsedError;
  bool get mfaRequired => throw _privateConstructorUsedError;
  String? get mfaSecret => throw _privateConstructorUsedError;
  String? get mfaQrCode => throw _privateConstructorUsedError;

  /// Serializes this LoginResponse to a JSON map.
  Map<String, dynamic> toJson(Object? Function(T) toJsonT) =>
      throw _privateConstructorUsedError;

  /// Create a copy of LoginResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $LoginResponseCopyWith<T, LoginResponse<T>> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LoginResponseCopyWith<T, $Res> {
  factory $LoginResponseCopyWith(
    LoginResponse<T> value,
    $Res Function(LoginResponse<T>) then,
  ) = _$LoginResponseCopyWithImpl<T, $Res, LoginResponse<T>>;
  @useResult
  $Res call({
    String? token,
    String? refreshToken,
    String? tokenType,
    String? name,
    T? user,
    bool mfaRequired,
    String? mfaSecret,
    String? mfaQrCode,
  });
}

/// @nodoc
class _$LoginResponseCopyWithImpl<T, $Res, $Val extends LoginResponse<T>>
    implements $LoginResponseCopyWith<T, $Res> {
  _$LoginResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of LoginResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? token = freezed,
    Object? refreshToken = freezed,
    Object? tokenType = freezed,
    Object? name = freezed,
    Object? user = freezed,
    Object? mfaRequired = null,
    Object? mfaSecret = freezed,
    Object? mfaQrCode = freezed,
  }) {
    return _then(
      _value.copyWith(
            token: freezed == token
                ? _value.token
                : token // ignore: cast_nullable_to_non_nullable
                      as String?,
            refreshToken: freezed == refreshToken
                ? _value.refreshToken
                : refreshToken // ignore: cast_nullable_to_non_nullable
                      as String?,
            tokenType: freezed == tokenType
                ? _value.tokenType
                : tokenType // ignore: cast_nullable_to_non_nullable
                      as String?,
            name: freezed == name
                ? _value.name
                : name // ignore: cast_nullable_to_non_nullable
                      as String?,
            user: freezed == user
                ? _value.user
                : user // ignore: cast_nullable_to_non_nullable
                      as T?,
            mfaRequired: null == mfaRequired
                ? _value.mfaRequired
                : mfaRequired // ignore: cast_nullable_to_non_nullable
                      as bool,
            mfaSecret: freezed == mfaSecret
                ? _value.mfaSecret
                : mfaSecret // ignore: cast_nullable_to_non_nullable
                      as String?,
            mfaQrCode: freezed == mfaQrCode
                ? _value.mfaQrCode
                : mfaQrCode // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$LoginResponseImplCopyWith<T, $Res>
    implements $LoginResponseCopyWith<T, $Res> {
  factory _$$LoginResponseImplCopyWith(
    _$LoginResponseImpl<T> value,
    $Res Function(_$LoginResponseImpl<T>) then,
  ) = __$$LoginResponseImplCopyWithImpl<T, $Res>;
  @override
  @useResult
  $Res call({
    String? token,
    String? refreshToken,
    String? tokenType,
    String? name,
    T? user,
    bool mfaRequired,
    String? mfaSecret,
    String? mfaQrCode,
  });
}

/// @nodoc
class __$$LoginResponseImplCopyWithImpl<T, $Res>
    extends _$LoginResponseCopyWithImpl<T, $Res, _$LoginResponseImpl<T>>
    implements _$$LoginResponseImplCopyWith<T, $Res> {
  __$$LoginResponseImplCopyWithImpl(
    _$LoginResponseImpl<T> _value,
    $Res Function(_$LoginResponseImpl<T>) _then,
  ) : super(_value, _then);

  /// Create a copy of LoginResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? token = freezed,
    Object? refreshToken = freezed,
    Object? tokenType = freezed,
    Object? name = freezed,
    Object? user = freezed,
    Object? mfaRequired = null,
    Object? mfaSecret = freezed,
    Object? mfaQrCode = freezed,
  }) {
    return _then(
      _$LoginResponseImpl<T>(
        token: freezed == token
            ? _value.token
            : token // ignore: cast_nullable_to_non_nullable
                  as String?,
        refreshToken: freezed == refreshToken
            ? _value.refreshToken
            : refreshToken // ignore: cast_nullable_to_non_nullable
                  as String?,
        tokenType: freezed == tokenType
            ? _value.tokenType
            : tokenType // ignore: cast_nullable_to_non_nullable
                  as String?,
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
        user: freezed == user
            ? _value.user
            : user // ignore: cast_nullable_to_non_nullable
                  as T?,
        mfaRequired: null == mfaRequired
            ? _value.mfaRequired
            : mfaRequired // ignore: cast_nullable_to_non_nullable
                  as bool,
        mfaSecret: freezed == mfaSecret
            ? _value.mfaSecret
            : mfaSecret // ignore: cast_nullable_to_non_nullable
                  as String?,
        mfaQrCode: freezed == mfaQrCode
            ? _value.mfaQrCode
            : mfaQrCode // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable(genericArgumentFactories: true)
class _$LoginResponseImpl<T> implements _LoginResponse<T> {
  const _$LoginResponseImpl({
    this.token,
    this.refreshToken,
    this.tokenType,
    this.name,
    this.user,
    this.mfaRequired = false,
    this.mfaSecret,
    this.mfaQrCode,
  });

  factory _$LoginResponseImpl.fromJson(
    Map<String, dynamic> json,
    T Function(Object?) fromJsonT,
  ) => _$$LoginResponseImplFromJson(json, fromJsonT);

  @override
  final String? token;
  @override
  final String? refreshToken;
  @override
  final String? tokenType;
  @override
  final String? name;
  @override
  final T? user;
  @override
  @JsonKey()
  final bool mfaRequired;
  @override
  final String? mfaSecret;
  @override
  final String? mfaQrCode;

  @override
  String toString() {
    return 'LoginResponse<$T>(token: $token, refreshToken: $refreshToken, tokenType: $tokenType, name: $name, user: $user, mfaRequired: $mfaRequired, mfaSecret: $mfaSecret, mfaQrCode: $mfaQrCode)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoginResponseImpl<T> &&
            (identical(other.token, token) || other.token == token) &&
            (identical(other.refreshToken, refreshToken) ||
                other.refreshToken == refreshToken) &&
            (identical(other.tokenType, tokenType) ||
                other.tokenType == tokenType) &&
            (identical(other.name, name) || other.name == name) &&
            const DeepCollectionEquality().equals(other.user, user) &&
            (identical(other.mfaRequired, mfaRequired) ||
                other.mfaRequired == mfaRequired) &&
            (identical(other.mfaSecret, mfaSecret) ||
                other.mfaSecret == mfaSecret) &&
            (identical(other.mfaQrCode, mfaQrCode) ||
                other.mfaQrCode == mfaQrCode));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    token,
    refreshToken,
    tokenType,
    name,
    const DeepCollectionEquality().hash(user),
    mfaRequired,
    mfaSecret,
    mfaQrCode,
  );

  /// Create a copy of LoginResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$LoginResponseImplCopyWith<T, _$LoginResponseImpl<T>> get copyWith =>
      __$$LoginResponseImplCopyWithImpl<T, _$LoginResponseImpl<T>>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson(Object? Function(T) toJsonT) {
    return _$$LoginResponseImplToJson<T>(this, toJsonT);
  }
}

abstract class _LoginResponse<T> implements LoginResponse<T> {
  const factory _LoginResponse({
    final String? token,
    final String? refreshToken,
    final String? tokenType,
    final String? name,
    final T? user,
    final bool mfaRequired,
    final String? mfaSecret,
    final String? mfaQrCode,
  }) = _$LoginResponseImpl<T>;

  factory _LoginResponse.fromJson(
    Map<String, dynamic> json,
    T Function(Object?) fromJsonT,
  ) = _$LoginResponseImpl<T>.fromJson;

  @override
  String? get token;
  @override
  String? get refreshToken;
  @override
  String? get tokenType;
  @override
  String? get name;
  @override
  T? get user;
  @override
  bool get mfaRequired;
  @override
  String? get mfaSecret;
  @override
  String? get mfaQrCode;

  /// Create a copy of LoginResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$LoginResponseImplCopyWith<T, _$LoginResponseImpl<T>> get copyWith =>
      throw _privateConstructorUsedError;
}

ForgetPasswordRequest _$ForgetPasswordRequestFromJson(
  Map<String, dynamic> json,
) {
  return _ForgetPasswordRequest.fromJson(json);
}

/// @nodoc
mixin _$ForgetPasswordRequest {
  String get email => throw _privateConstructorUsedError;

  /// Serializes this ForgetPasswordRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of ForgetPasswordRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $ForgetPasswordRequestCopyWith<ForgetPasswordRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ForgetPasswordRequestCopyWith<$Res> {
  factory $ForgetPasswordRequestCopyWith(
    ForgetPasswordRequest value,
    $Res Function(ForgetPasswordRequest) then,
  ) = _$ForgetPasswordRequestCopyWithImpl<$Res, ForgetPasswordRequest>;
  @useResult
  $Res call({String email});
}

/// @nodoc
class _$ForgetPasswordRequestCopyWithImpl<
  $Res,
  $Val extends ForgetPasswordRequest
>
    implements $ForgetPasswordRequestCopyWith<$Res> {
  _$ForgetPasswordRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of ForgetPasswordRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? email = null}) {
    return _then(
      _value.copyWith(
            email: null == email
                ? _value.email
                : email // ignore: cast_nullable_to_non_nullable
                      as String,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$ForgetPasswordRequestImplCopyWith<$Res>
    implements $ForgetPasswordRequestCopyWith<$Res> {
  factory _$$ForgetPasswordRequestImplCopyWith(
    _$ForgetPasswordRequestImpl value,
    $Res Function(_$ForgetPasswordRequestImpl) then,
  ) = __$$ForgetPasswordRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String email});
}

/// @nodoc
class __$$ForgetPasswordRequestImplCopyWithImpl<$Res>
    extends
        _$ForgetPasswordRequestCopyWithImpl<$Res, _$ForgetPasswordRequestImpl>
    implements _$$ForgetPasswordRequestImplCopyWith<$Res> {
  __$$ForgetPasswordRequestImplCopyWithImpl(
    _$ForgetPasswordRequestImpl _value,
    $Res Function(_$ForgetPasswordRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of ForgetPasswordRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? email = null}) {
    return _then(
      _$ForgetPasswordRequestImpl(
        email: null == email
            ? _value.email
            : email // ignore: cast_nullable_to_non_nullable
                  as String,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$ForgetPasswordRequestImpl implements _ForgetPasswordRequest {
  const _$ForgetPasswordRequestImpl({required this.email});

  factory _$ForgetPasswordRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$ForgetPasswordRequestImplFromJson(json);

  @override
  final String email;

  @override
  String toString() {
    return 'ForgetPasswordRequest(email: $email)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ForgetPasswordRequestImpl &&
            (identical(other.email, email) || other.email == email));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, email);

  /// Create a copy of ForgetPasswordRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$ForgetPasswordRequestImplCopyWith<_$ForgetPasswordRequestImpl>
  get copyWith =>
      __$$ForgetPasswordRequestImplCopyWithImpl<_$ForgetPasswordRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$ForgetPasswordRequestImplToJson(this);
  }
}

abstract class _ForgetPasswordRequest implements ForgetPasswordRequest {
  const factory _ForgetPasswordRequest({required final String email}) =
      _$ForgetPasswordRequestImpl;

  factory _ForgetPasswordRequest.fromJson(Map<String, dynamic> json) =
      _$ForgetPasswordRequestImpl.fromJson;

  @override
  String get email;

  /// Create a copy of ForgetPasswordRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$ForgetPasswordRequestImplCopyWith<_$ForgetPasswordRequestImpl>
  get copyWith => throw _privateConstructorUsedError;
}

ResetPasswordRequest _$ResetPasswordRequestFromJson(Map<String, dynamic> json) {
  return _ResetPasswordRequest.fromJson(json);
}

/// @nodoc
mixin _$ResetPasswordRequest {
  String get token => throw _privateConstructorUsedError;
  String get newPassword => throw _privateConstructorUsedError;

  /// Serializes this ResetPasswordRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of ResetPasswordRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $ResetPasswordRequestCopyWith<ResetPasswordRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ResetPasswordRequestCopyWith<$Res> {
  factory $ResetPasswordRequestCopyWith(
    ResetPasswordRequest value,
    $Res Function(ResetPasswordRequest) then,
  ) = _$ResetPasswordRequestCopyWithImpl<$Res, ResetPasswordRequest>;
  @useResult
  $Res call({String token, String newPassword});
}

/// @nodoc
class _$ResetPasswordRequestCopyWithImpl<
  $Res,
  $Val extends ResetPasswordRequest
>
    implements $ResetPasswordRequestCopyWith<$Res> {
  _$ResetPasswordRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of ResetPasswordRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? token = null, Object? newPassword = null}) {
    return _then(
      _value.copyWith(
            token: null == token
                ? _value.token
                : token // ignore: cast_nullable_to_non_nullable
                      as String,
            newPassword: null == newPassword
                ? _value.newPassword
                : newPassword // ignore: cast_nullable_to_non_nullable
                      as String,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$ResetPasswordRequestImplCopyWith<$Res>
    implements $ResetPasswordRequestCopyWith<$Res> {
  factory _$$ResetPasswordRequestImplCopyWith(
    _$ResetPasswordRequestImpl value,
    $Res Function(_$ResetPasswordRequestImpl) then,
  ) = __$$ResetPasswordRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String token, String newPassword});
}

/// @nodoc
class __$$ResetPasswordRequestImplCopyWithImpl<$Res>
    extends _$ResetPasswordRequestCopyWithImpl<$Res, _$ResetPasswordRequestImpl>
    implements _$$ResetPasswordRequestImplCopyWith<$Res> {
  __$$ResetPasswordRequestImplCopyWithImpl(
    _$ResetPasswordRequestImpl _value,
    $Res Function(_$ResetPasswordRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of ResetPasswordRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? token = null, Object? newPassword = null}) {
    return _then(
      _$ResetPasswordRequestImpl(
        token: null == token
            ? _value.token
            : token // ignore: cast_nullable_to_non_nullable
                  as String,
        newPassword: null == newPassword
            ? _value.newPassword
            : newPassword // ignore: cast_nullable_to_non_nullable
                  as String,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$ResetPasswordRequestImpl implements _ResetPasswordRequest {
  const _$ResetPasswordRequestImpl({
    required this.token,
    required this.newPassword,
  });

  factory _$ResetPasswordRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$ResetPasswordRequestImplFromJson(json);

  @override
  final String token;
  @override
  final String newPassword;

  @override
  String toString() {
    return 'ResetPasswordRequest(token: $token, newPassword: $newPassword)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ResetPasswordRequestImpl &&
            (identical(other.token, token) || other.token == token) &&
            (identical(other.newPassword, newPassword) ||
                other.newPassword == newPassword));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, token, newPassword);

  /// Create a copy of ResetPasswordRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$ResetPasswordRequestImplCopyWith<_$ResetPasswordRequestImpl>
  get copyWith =>
      __$$ResetPasswordRequestImplCopyWithImpl<_$ResetPasswordRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$ResetPasswordRequestImplToJson(this);
  }
}

abstract class _ResetPasswordRequest implements ResetPasswordRequest {
  const factory _ResetPasswordRequest({
    required final String token,
    required final String newPassword,
  }) = _$ResetPasswordRequestImpl;

  factory _ResetPasswordRequest.fromJson(Map<String, dynamic> json) =
      _$ResetPasswordRequestImpl.fromJson;

  @override
  String get token;
  @override
  String get newPassword;

  /// Create a copy of ResetPasswordRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$ResetPasswordRequestImplCopyWith<_$ResetPasswordRequestImpl>
  get copyWith => throw _privateConstructorUsedError;
}

TokenValidationResponse _$TokenValidationResponseFromJson(
  Map<String, dynamic> json,
) {
  return _TokenValidationResponse.fromJson(json);
}

/// @nodoc
mixin _$TokenValidationResponse {
  bool get valid => throw _privateConstructorUsedError;
  String? get email => throw _privateConstructorUsedError;
  String? get role => throw _privateConstructorUsedError;
  String? get purpose => throw _privateConstructorUsedError;
  int? get expiresAt => throw _privateConstructorUsedError;
  String? get message => throw _privateConstructorUsedError;

  /// Serializes this TokenValidationResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of TokenValidationResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $TokenValidationResponseCopyWith<TokenValidationResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $TokenValidationResponseCopyWith<$Res> {
  factory $TokenValidationResponseCopyWith(
    TokenValidationResponse value,
    $Res Function(TokenValidationResponse) then,
  ) = _$TokenValidationResponseCopyWithImpl<$Res, TokenValidationResponse>;
  @useResult
  $Res call({
    bool valid,
    String? email,
    String? role,
    String? purpose,
    int? expiresAt,
    String? message,
  });
}

/// @nodoc
class _$TokenValidationResponseCopyWithImpl<
  $Res,
  $Val extends TokenValidationResponse
>
    implements $TokenValidationResponseCopyWith<$Res> {
  _$TokenValidationResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of TokenValidationResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? valid = null,
    Object? email = freezed,
    Object? role = freezed,
    Object? purpose = freezed,
    Object? expiresAt = freezed,
    Object? message = freezed,
  }) {
    return _then(
      _value.copyWith(
            valid: null == valid
                ? _value.valid
                : valid // ignore: cast_nullable_to_non_nullable
                      as bool,
            email: freezed == email
                ? _value.email
                : email // ignore: cast_nullable_to_non_nullable
                      as String?,
            role: freezed == role
                ? _value.role
                : role // ignore: cast_nullable_to_non_nullable
                      as String?,
            purpose: freezed == purpose
                ? _value.purpose
                : purpose // ignore: cast_nullable_to_non_nullable
                      as String?,
            expiresAt: freezed == expiresAt
                ? _value.expiresAt
                : expiresAt // ignore: cast_nullable_to_non_nullable
                      as int?,
            message: freezed == message
                ? _value.message
                : message // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$TokenValidationResponseImplCopyWith<$Res>
    implements $TokenValidationResponseCopyWith<$Res> {
  factory _$$TokenValidationResponseImplCopyWith(
    _$TokenValidationResponseImpl value,
    $Res Function(_$TokenValidationResponseImpl) then,
  ) = __$$TokenValidationResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    bool valid,
    String? email,
    String? role,
    String? purpose,
    int? expiresAt,
    String? message,
  });
}

/// @nodoc
class __$$TokenValidationResponseImplCopyWithImpl<$Res>
    extends
        _$TokenValidationResponseCopyWithImpl<
          $Res,
          _$TokenValidationResponseImpl
        >
    implements _$$TokenValidationResponseImplCopyWith<$Res> {
  __$$TokenValidationResponseImplCopyWithImpl(
    _$TokenValidationResponseImpl _value,
    $Res Function(_$TokenValidationResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of TokenValidationResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? valid = null,
    Object? email = freezed,
    Object? role = freezed,
    Object? purpose = freezed,
    Object? expiresAt = freezed,
    Object? message = freezed,
  }) {
    return _then(
      _$TokenValidationResponseImpl(
        valid: null == valid
            ? _value.valid
            : valid // ignore: cast_nullable_to_non_nullable
                  as bool,
        email: freezed == email
            ? _value.email
            : email // ignore: cast_nullable_to_non_nullable
                  as String?,
        role: freezed == role
            ? _value.role
            : role // ignore: cast_nullable_to_non_nullable
                  as String?,
        purpose: freezed == purpose
            ? _value.purpose
            : purpose // ignore: cast_nullable_to_non_nullable
                  as String?,
        expiresAt: freezed == expiresAt
            ? _value.expiresAt
            : expiresAt // ignore: cast_nullable_to_non_nullable
                  as int?,
        message: freezed == message
            ? _value.message
            : message // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$TokenValidationResponseImpl implements _TokenValidationResponse {
  const _$TokenValidationResponseImpl({
    required this.valid,
    this.email,
    this.role,
    this.purpose,
    this.expiresAt,
    this.message,
  });

  factory _$TokenValidationResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$TokenValidationResponseImplFromJson(json);

  @override
  final bool valid;
  @override
  final String? email;
  @override
  final String? role;
  @override
  final String? purpose;
  @override
  final int? expiresAt;
  @override
  final String? message;

  @override
  String toString() {
    return 'TokenValidationResponse(valid: $valid, email: $email, role: $role, purpose: $purpose, expiresAt: $expiresAt, message: $message)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TokenValidationResponseImpl &&
            (identical(other.valid, valid) || other.valid == valid) &&
            (identical(other.email, email) || other.email == email) &&
            (identical(other.role, role) || other.role == role) &&
            (identical(other.purpose, purpose) || other.purpose == purpose) &&
            (identical(other.expiresAt, expiresAt) ||
                other.expiresAt == expiresAt) &&
            (identical(other.message, message) || other.message == message));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode =>
      Object.hash(runtimeType, valid, email, role, purpose, expiresAt, message);

  /// Create a copy of TokenValidationResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$TokenValidationResponseImplCopyWith<_$TokenValidationResponseImpl>
  get copyWith =>
      __$$TokenValidationResponseImplCopyWithImpl<
        _$TokenValidationResponseImpl
      >(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$TokenValidationResponseImplToJson(this);
  }
}

abstract class _TokenValidationResponse implements TokenValidationResponse {
  const factory _TokenValidationResponse({
    required final bool valid,
    final String? email,
    final String? role,
    final String? purpose,
    final int? expiresAt,
    final String? message,
  }) = _$TokenValidationResponseImpl;

  factory _TokenValidationResponse.fromJson(Map<String, dynamic> json) =
      _$TokenValidationResponseImpl.fromJson;

  @override
  bool get valid;
  @override
  String? get email;
  @override
  String? get role;
  @override
  String? get purpose;
  @override
  int? get expiresAt;
  @override
  String? get message;

  /// Create a copy of TokenValidationResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$TokenValidationResponseImplCopyWith<_$TokenValidationResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}
