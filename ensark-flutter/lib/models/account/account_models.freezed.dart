// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'account_models.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
  'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models',
);

AccountRequest _$AccountRequestFromJson(Map<String, dynamic> json) {
  return _AccountRequest.fromJson(json);
}

/// @nodoc
mixin _$AccountRequest {
  AccountType? get accountType => throw _privateConstructorUsedError;
  double? get availableBalance => throw _privateConstructorUsedError;
  int? get branchId => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_name')
  String? get nName => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_email')
  String? get nEmail => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_phone')
  String? get nPhone => throw _privateConstructorUsedError;
  NomineeRelation? get relation => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_photo')
  String? get nPhoto => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_nid_front')
  String? get nNidFront => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_nid_back')
  String? get nNidBack => throw _privateConstructorUsedError;
  List<AccountHolderRequest>? get accountHolders =>
      throw _privateConstructorUsedError;

  /// Serializes this AccountRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of AccountRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $AccountRequestCopyWith<AccountRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AccountRequestCopyWith<$Res> {
  factory $AccountRequestCopyWith(
    AccountRequest value,
    $Res Function(AccountRequest) then,
  ) = _$AccountRequestCopyWithImpl<$Res, AccountRequest>;
  @useResult
  $Res call({
    AccountType? accountType,
    double? availableBalance,
    int? branchId,
    @JsonKey(name: 'n_name') String? nName,
    @JsonKey(name: 'n_email') String? nEmail,
    @JsonKey(name: 'n_phone') String? nPhone,
    NomineeRelation? relation,
    @JsonKey(name: 'n_photo') String? nPhoto,
    @JsonKey(name: 'n_nid_front') String? nNidFront,
    @JsonKey(name: 'n_nid_back') String? nNidBack,
    List<AccountHolderRequest>? accountHolders,
  });
}

/// @nodoc
class _$AccountRequestCopyWithImpl<$Res, $Val extends AccountRequest>
    implements $AccountRequestCopyWith<$Res> {
  _$AccountRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of AccountRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? accountType = freezed,
    Object? availableBalance = freezed,
    Object? branchId = freezed,
    Object? nName = freezed,
    Object? nEmail = freezed,
    Object? nPhone = freezed,
    Object? relation = freezed,
    Object? nPhoto = freezed,
    Object? nNidFront = freezed,
    Object? nNidBack = freezed,
    Object? accountHolders = freezed,
  }) {
    return _then(
      _value.copyWith(
            accountType: freezed == accountType
                ? _value.accountType
                : accountType // ignore: cast_nullable_to_non_nullable
                      as AccountType?,
            availableBalance: freezed == availableBalance
                ? _value.availableBalance
                : availableBalance // ignore: cast_nullable_to_non_nullable
                      as double?,
            branchId: freezed == branchId
                ? _value.branchId
                : branchId // ignore: cast_nullable_to_non_nullable
                      as int?,
            nName: freezed == nName
                ? _value.nName
                : nName // ignore: cast_nullable_to_non_nullable
                      as String?,
            nEmail: freezed == nEmail
                ? _value.nEmail
                : nEmail // ignore: cast_nullable_to_non_nullable
                      as String?,
            nPhone: freezed == nPhone
                ? _value.nPhone
                : nPhone // ignore: cast_nullable_to_non_nullable
                      as String?,
            relation: freezed == relation
                ? _value.relation
                : relation // ignore: cast_nullable_to_non_nullable
                      as NomineeRelation?,
            nPhoto: freezed == nPhoto
                ? _value.nPhoto
                : nPhoto // ignore: cast_nullable_to_non_nullable
                      as String?,
            nNidFront: freezed == nNidFront
                ? _value.nNidFront
                : nNidFront // ignore: cast_nullable_to_non_nullable
                      as String?,
            nNidBack: freezed == nNidBack
                ? _value.nNidBack
                : nNidBack // ignore: cast_nullable_to_non_nullable
                      as String?,
            accountHolders: freezed == accountHolders
                ? _value.accountHolders
                : accountHolders // ignore: cast_nullable_to_non_nullable
                      as List<AccountHolderRequest>?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$AccountRequestImplCopyWith<$Res>
    implements $AccountRequestCopyWith<$Res> {
  factory _$$AccountRequestImplCopyWith(
    _$AccountRequestImpl value,
    $Res Function(_$AccountRequestImpl) then,
  ) = __$$AccountRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    AccountType? accountType,
    double? availableBalance,
    int? branchId,
    @JsonKey(name: 'n_name') String? nName,
    @JsonKey(name: 'n_email') String? nEmail,
    @JsonKey(name: 'n_phone') String? nPhone,
    NomineeRelation? relation,
    @JsonKey(name: 'n_photo') String? nPhoto,
    @JsonKey(name: 'n_nid_front') String? nNidFront,
    @JsonKey(name: 'n_nid_back') String? nNidBack,
    List<AccountHolderRequest>? accountHolders,
  });
}

/// @nodoc
class __$$AccountRequestImplCopyWithImpl<$Res>
    extends _$AccountRequestCopyWithImpl<$Res, _$AccountRequestImpl>
    implements _$$AccountRequestImplCopyWith<$Res> {
  __$$AccountRequestImplCopyWithImpl(
    _$AccountRequestImpl _value,
    $Res Function(_$AccountRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of AccountRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? accountType = freezed,
    Object? availableBalance = freezed,
    Object? branchId = freezed,
    Object? nName = freezed,
    Object? nEmail = freezed,
    Object? nPhone = freezed,
    Object? relation = freezed,
    Object? nPhoto = freezed,
    Object? nNidFront = freezed,
    Object? nNidBack = freezed,
    Object? accountHolders = freezed,
  }) {
    return _then(
      _$AccountRequestImpl(
        accountType: freezed == accountType
            ? _value.accountType
            : accountType // ignore: cast_nullable_to_non_nullable
                  as AccountType?,
        availableBalance: freezed == availableBalance
            ? _value.availableBalance
            : availableBalance // ignore: cast_nullable_to_non_nullable
                  as double?,
        branchId: freezed == branchId
            ? _value.branchId
            : branchId // ignore: cast_nullable_to_non_nullable
                  as int?,
        nName: freezed == nName
            ? _value.nName
            : nName // ignore: cast_nullable_to_non_nullable
                  as String?,
        nEmail: freezed == nEmail
            ? _value.nEmail
            : nEmail // ignore: cast_nullable_to_non_nullable
                  as String?,
        nPhone: freezed == nPhone
            ? _value.nPhone
            : nPhone // ignore: cast_nullable_to_non_nullable
                  as String?,
        relation: freezed == relation
            ? _value.relation
            : relation // ignore: cast_nullable_to_non_nullable
                  as NomineeRelation?,
        nPhoto: freezed == nPhoto
            ? _value.nPhoto
            : nPhoto // ignore: cast_nullable_to_non_nullable
                  as String?,
        nNidFront: freezed == nNidFront
            ? _value.nNidFront
            : nNidFront // ignore: cast_nullable_to_non_nullable
                  as String?,
        nNidBack: freezed == nNidBack
            ? _value.nNidBack
            : nNidBack // ignore: cast_nullable_to_non_nullable
                  as String?,
        accountHolders: freezed == accountHolders
            ? _value._accountHolders
            : accountHolders // ignore: cast_nullable_to_non_nullable
                  as List<AccountHolderRequest>?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$AccountRequestImpl implements _AccountRequest {
  const _$AccountRequestImpl({
    this.accountType,
    this.availableBalance,
    this.branchId,
    @JsonKey(name: 'n_name') this.nName,
    @JsonKey(name: 'n_email') this.nEmail,
    @JsonKey(name: 'n_phone') this.nPhone,
    this.relation,
    @JsonKey(name: 'n_photo') this.nPhoto,
    @JsonKey(name: 'n_nid_front') this.nNidFront,
    @JsonKey(name: 'n_nid_back') this.nNidBack,
    final List<AccountHolderRequest>? accountHolders,
  }) : _accountHolders = accountHolders;

  factory _$AccountRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$AccountRequestImplFromJson(json);

  @override
  final AccountType? accountType;
  @override
  final double? availableBalance;
  @override
  final int? branchId;
  @override
  @JsonKey(name: 'n_name')
  final String? nName;
  @override
  @JsonKey(name: 'n_email')
  final String? nEmail;
  @override
  @JsonKey(name: 'n_phone')
  final String? nPhone;
  @override
  final NomineeRelation? relation;
  @override
  @JsonKey(name: 'n_photo')
  final String? nPhoto;
  @override
  @JsonKey(name: 'n_nid_front')
  final String? nNidFront;
  @override
  @JsonKey(name: 'n_nid_back')
  final String? nNidBack;
  final List<AccountHolderRequest>? _accountHolders;
  @override
  List<AccountHolderRequest>? get accountHolders {
    final value = _accountHolders;
    if (value == null) return null;
    if (_accountHolders is EqualUnmodifiableListView) return _accountHolders;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AccountRequest(accountType: $accountType, availableBalance: $availableBalance, branchId: $branchId, nName: $nName, nEmail: $nEmail, nPhone: $nPhone, relation: $relation, nPhoto: $nPhoto, nNidFront: $nNidFront, nNidBack: $nNidBack, accountHolders: $accountHolders)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AccountRequestImpl &&
            (identical(other.accountType, accountType) ||
                other.accountType == accountType) &&
            (identical(other.availableBalance, availableBalance) ||
                other.availableBalance == availableBalance) &&
            (identical(other.branchId, branchId) ||
                other.branchId == branchId) &&
            (identical(other.nName, nName) || other.nName == nName) &&
            (identical(other.nEmail, nEmail) || other.nEmail == nEmail) &&
            (identical(other.nPhone, nPhone) || other.nPhone == nPhone) &&
            (identical(other.relation, relation) ||
                other.relation == relation) &&
            (identical(other.nPhoto, nPhoto) || other.nPhoto == nPhoto) &&
            (identical(other.nNidFront, nNidFront) ||
                other.nNidFront == nNidFront) &&
            (identical(other.nNidBack, nNidBack) ||
                other.nNidBack == nNidBack) &&
            const DeepCollectionEquality().equals(
              other._accountHolders,
              _accountHolders,
            ));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    accountType,
    availableBalance,
    branchId,
    nName,
    nEmail,
    nPhone,
    relation,
    nPhoto,
    nNidFront,
    nNidBack,
    const DeepCollectionEquality().hash(_accountHolders),
  );

  /// Create a copy of AccountRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$AccountRequestImplCopyWith<_$AccountRequestImpl> get copyWith =>
      __$$AccountRequestImplCopyWithImpl<_$AccountRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$AccountRequestImplToJson(this);
  }
}

abstract class _AccountRequest implements AccountRequest {
  const factory _AccountRequest({
    final AccountType? accountType,
    final double? availableBalance,
    final int? branchId,
    @JsonKey(name: 'n_name') final String? nName,
    @JsonKey(name: 'n_email') final String? nEmail,
    @JsonKey(name: 'n_phone') final String? nPhone,
    final NomineeRelation? relation,
    @JsonKey(name: 'n_photo') final String? nPhoto,
    @JsonKey(name: 'n_nid_front') final String? nNidFront,
    @JsonKey(name: 'n_nid_back') final String? nNidBack,
    final List<AccountHolderRequest>? accountHolders,
  }) = _$AccountRequestImpl;

  factory _AccountRequest.fromJson(Map<String, dynamic> json) =
      _$AccountRequestImpl.fromJson;

  @override
  AccountType? get accountType;
  @override
  double? get availableBalance;
  @override
  int? get branchId;
  @override
  @JsonKey(name: 'n_name')
  String? get nName;
  @override
  @JsonKey(name: 'n_email')
  String? get nEmail;
  @override
  @JsonKey(name: 'n_phone')
  String? get nPhone;
  @override
  NomineeRelation? get relation;
  @override
  @JsonKey(name: 'n_photo')
  String? get nPhoto;
  @override
  @JsonKey(name: 'n_nid_front')
  String? get nNidFront;
  @override
  @JsonKey(name: 'n_nid_back')
  String? get nNidBack;
  @override
  List<AccountHolderRequest>? get accountHolders;

  /// Create a copy of AccountRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$AccountRequestImplCopyWith<_$AccountRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

AccountResponse _$AccountResponseFromJson(Map<String, dynamic> json) {
  return _AccountResponse.fromJson(json);
}

/// @nodoc
mixin _$AccountResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get accountNumber => throw _privateConstructorUsedError;
  AccountType? get accountType => throw _privateConstructorUsedError;
  AccountStatus? get accountStatus => throw _privateConstructorUsedError;
  double? get availableBalance => throw _privateConstructorUsedError;
  double? get currentBalance => throw _privateConstructorUsedError;
  double? get holdBalance => throw _privateConstructorUsedError;
  String? get branchName => throw _privateConstructorUsedError;
  String? get branchRoutingNumber => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_name')
  String? get nName => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_email')
  String? get nEmail => throw _privateConstructorUsedError;
  NomineeRelation? get relation => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_phone')
  String? get nPhone => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_photo')
  String? get nPhoto => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_nid_front')
  String? get nNidFront => throw _privateConstructorUsedError;
  @JsonKey(name: 'n_nid_back')
  String? get nNidBack => throw _privateConstructorUsedError;
  List<AccountHolderResponse> get holderResponses =>
      throw _privateConstructorUsedError;

  /// Serializes this AccountResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of AccountResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $AccountResponseCopyWith<AccountResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AccountResponseCopyWith<$Res> {
  factory $AccountResponseCopyWith(
    AccountResponse value,
    $Res Function(AccountResponse) then,
  ) = _$AccountResponseCopyWithImpl<$Res, AccountResponse>;
  @useResult
  $Res call({
    int? id,
    String? accountNumber,
    AccountType? accountType,
    AccountStatus? accountStatus,
    double? availableBalance,
    double? currentBalance,
    double? holdBalance,
    String? branchName,
    String? branchRoutingNumber,
    @JsonKey(name: 'n_name') String? nName,
    @JsonKey(name: 'n_email') String? nEmail,
    NomineeRelation? relation,
    @JsonKey(name: 'n_phone') String? nPhone,
    @JsonKey(name: 'n_photo') String? nPhoto,
    @JsonKey(name: 'n_nid_front') String? nNidFront,
    @JsonKey(name: 'n_nid_back') String? nNidBack,
    List<AccountHolderResponse> holderResponses,
  });
}

/// @nodoc
class _$AccountResponseCopyWithImpl<$Res, $Val extends AccountResponse>
    implements $AccountResponseCopyWith<$Res> {
  _$AccountResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of AccountResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? accountNumber = freezed,
    Object? accountType = freezed,
    Object? accountStatus = freezed,
    Object? availableBalance = freezed,
    Object? currentBalance = freezed,
    Object? holdBalance = freezed,
    Object? branchName = freezed,
    Object? branchRoutingNumber = freezed,
    Object? nName = freezed,
    Object? nEmail = freezed,
    Object? relation = freezed,
    Object? nPhone = freezed,
    Object? nPhoto = freezed,
    Object? nNidFront = freezed,
    Object? nNidBack = freezed,
    Object? holderResponses = null,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            accountNumber: freezed == accountNumber
                ? _value.accountNumber
                : accountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            accountType: freezed == accountType
                ? _value.accountType
                : accountType // ignore: cast_nullable_to_non_nullable
                      as AccountType?,
            accountStatus: freezed == accountStatus
                ? _value.accountStatus
                : accountStatus // ignore: cast_nullable_to_non_nullable
                      as AccountStatus?,
            availableBalance: freezed == availableBalance
                ? _value.availableBalance
                : availableBalance // ignore: cast_nullable_to_non_nullable
                      as double?,
            currentBalance: freezed == currentBalance
                ? _value.currentBalance
                : currentBalance // ignore: cast_nullable_to_non_nullable
                      as double?,
            holdBalance: freezed == holdBalance
                ? _value.holdBalance
                : holdBalance // ignore: cast_nullable_to_non_nullable
                      as double?,
            branchName: freezed == branchName
                ? _value.branchName
                : branchName // ignore: cast_nullable_to_non_nullable
                      as String?,
            branchRoutingNumber: freezed == branchRoutingNumber
                ? _value.branchRoutingNumber
                : branchRoutingNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            nName: freezed == nName
                ? _value.nName
                : nName // ignore: cast_nullable_to_non_nullable
                      as String?,
            nEmail: freezed == nEmail
                ? _value.nEmail
                : nEmail // ignore: cast_nullable_to_non_nullable
                      as String?,
            relation: freezed == relation
                ? _value.relation
                : relation // ignore: cast_nullable_to_non_nullable
                      as NomineeRelation?,
            nPhone: freezed == nPhone
                ? _value.nPhone
                : nPhone // ignore: cast_nullable_to_non_nullable
                      as String?,
            nPhoto: freezed == nPhoto
                ? _value.nPhoto
                : nPhoto // ignore: cast_nullable_to_non_nullable
                      as String?,
            nNidFront: freezed == nNidFront
                ? _value.nNidFront
                : nNidFront // ignore: cast_nullable_to_non_nullable
                      as String?,
            nNidBack: freezed == nNidBack
                ? _value.nNidBack
                : nNidBack // ignore: cast_nullable_to_non_nullable
                      as String?,
            holderResponses: null == holderResponses
                ? _value.holderResponses
                : holderResponses // ignore: cast_nullable_to_non_nullable
                      as List<AccountHolderResponse>,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$AccountResponseImplCopyWith<$Res>
    implements $AccountResponseCopyWith<$Res> {
  factory _$$AccountResponseImplCopyWith(
    _$AccountResponseImpl value,
    $Res Function(_$AccountResponseImpl) then,
  ) = __$$AccountResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    String? accountNumber,
    AccountType? accountType,
    AccountStatus? accountStatus,
    double? availableBalance,
    double? currentBalance,
    double? holdBalance,
    String? branchName,
    String? branchRoutingNumber,
    @JsonKey(name: 'n_name') String? nName,
    @JsonKey(name: 'n_email') String? nEmail,
    NomineeRelation? relation,
    @JsonKey(name: 'n_phone') String? nPhone,
    @JsonKey(name: 'n_photo') String? nPhoto,
    @JsonKey(name: 'n_nid_front') String? nNidFront,
    @JsonKey(name: 'n_nid_back') String? nNidBack,
    List<AccountHolderResponse> holderResponses,
  });
}

/// @nodoc
class __$$AccountResponseImplCopyWithImpl<$Res>
    extends _$AccountResponseCopyWithImpl<$Res, _$AccountResponseImpl>
    implements _$$AccountResponseImplCopyWith<$Res> {
  __$$AccountResponseImplCopyWithImpl(
    _$AccountResponseImpl _value,
    $Res Function(_$AccountResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of AccountResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? accountNumber = freezed,
    Object? accountType = freezed,
    Object? accountStatus = freezed,
    Object? availableBalance = freezed,
    Object? currentBalance = freezed,
    Object? holdBalance = freezed,
    Object? branchName = freezed,
    Object? branchRoutingNumber = freezed,
    Object? nName = freezed,
    Object? nEmail = freezed,
    Object? relation = freezed,
    Object? nPhone = freezed,
    Object? nPhoto = freezed,
    Object? nNidFront = freezed,
    Object? nNidBack = freezed,
    Object? holderResponses = null,
  }) {
    return _then(
      _$AccountResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        accountNumber: freezed == accountNumber
            ? _value.accountNumber
            : accountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        accountType: freezed == accountType
            ? _value.accountType
            : accountType // ignore: cast_nullable_to_non_nullable
                  as AccountType?,
        accountStatus: freezed == accountStatus
            ? _value.accountStatus
            : accountStatus // ignore: cast_nullable_to_non_nullable
                  as AccountStatus?,
        availableBalance: freezed == availableBalance
            ? _value.availableBalance
            : availableBalance // ignore: cast_nullable_to_non_nullable
                  as double?,
        currentBalance: freezed == currentBalance
            ? _value.currentBalance
            : currentBalance // ignore: cast_nullable_to_non_nullable
                  as double?,
        holdBalance: freezed == holdBalance
            ? _value.holdBalance
            : holdBalance // ignore: cast_nullable_to_non_nullable
                  as double?,
        branchName: freezed == branchName
            ? _value.branchName
            : branchName // ignore: cast_nullable_to_non_nullable
                  as String?,
        branchRoutingNumber: freezed == branchRoutingNumber
            ? _value.branchRoutingNumber
            : branchRoutingNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        nName: freezed == nName
            ? _value.nName
            : nName // ignore: cast_nullable_to_non_nullable
                  as String?,
        nEmail: freezed == nEmail
            ? _value.nEmail
            : nEmail // ignore: cast_nullable_to_non_nullable
                  as String?,
        relation: freezed == relation
            ? _value.relation
            : relation // ignore: cast_nullable_to_non_nullable
                  as NomineeRelation?,
        nPhone: freezed == nPhone
            ? _value.nPhone
            : nPhone // ignore: cast_nullable_to_non_nullable
                  as String?,
        nPhoto: freezed == nPhoto
            ? _value.nPhoto
            : nPhoto // ignore: cast_nullable_to_non_nullable
                  as String?,
        nNidFront: freezed == nNidFront
            ? _value.nNidFront
            : nNidFront // ignore: cast_nullable_to_non_nullable
                  as String?,
        nNidBack: freezed == nNidBack
            ? _value.nNidBack
            : nNidBack // ignore: cast_nullable_to_non_nullable
                  as String?,
        holderResponses: null == holderResponses
            ? _value._holderResponses
            : holderResponses // ignore: cast_nullable_to_non_nullable
                  as List<AccountHolderResponse>,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$AccountResponseImpl implements _AccountResponse {
  const _$AccountResponseImpl({
    this.id,
    this.accountNumber,
    this.accountType,
    this.accountStatus,
    this.availableBalance,
    this.currentBalance,
    this.holdBalance,
    this.branchName,
    this.branchRoutingNumber,
    @JsonKey(name: 'n_name') this.nName,
    @JsonKey(name: 'n_email') this.nEmail,
    this.relation,
    @JsonKey(name: 'n_phone') this.nPhone,
    @JsonKey(name: 'n_photo') this.nPhoto,
    @JsonKey(name: 'n_nid_front') this.nNidFront,
    @JsonKey(name: 'n_nid_back') this.nNidBack,
    final List<AccountHolderResponse> holderResponses = const [],
  }) : _holderResponses = holderResponses;

  factory _$AccountResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$AccountResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? accountNumber;
  @override
  final AccountType? accountType;
  @override
  final AccountStatus? accountStatus;
  @override
  final double? availableBalance;
  @override
  final double? currentBalance;
  @override
  final double? holdBalance;
  @override
  final String? branchName;
  @override
  final String? branchRoutingNumber;
  @override
  @JsonKey(name: 'n_name')
  final String? nName;
  @override
  @JsonKey(name: 'n_email')
  final String? nEmail;
  @override
  final NomineeRelation? relation;
  @override
  @JsonKey(name: 'n_phone')
  final String? nPhone;
  @override
  @JsonKey(name: 'n_photo')
  final String? nPhoto;
  @override
  @JsonKey(name: 'n_nid_front')
  final String? nNidFront;
  @override
  @JsonKey(name: 'n_nid_back')
  final String? nNidBack;
  final List<AccountHolderResponse> _holderResponses;
  @override
  @JsonKey()
  List<AccountHolderResponse> get holderResponses {
    if (_holderResponses is EqualUnmodifiableListView) return _holderResponses;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_holderResponses);
  }

  @override
  String toString() {
    return 'AccountResponse(id: $id, accountNumber: $accountNumber, accountType: $accountType, accountStatus: $accountStatus, availableBalance: $availableBalance, currentBalance: $currentBalance, holdBalance: $holdBalance, branchName: $branchName, branchRoutingNumber: $branchRoutingNumber, nName: $nName, nEmail: $nEmail, relation: $relation, nPhone: $nPhone, nPhoto: $nPhoto, nNidFront: $nNidFront, nNidBack: $nNidBack, holderResponses: $holderResponses)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AccountResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.accountNumber, accountNumber) ||
                other.accountNumber == accountNumber) &&
            (identical(other.accountType, accountType) ||
                other.accountType == accountType) &&
            (identical(other.accountStatus, accountStatus) ||
                other.accountStatus == accountStatus) &&
            (identical(other.availableBalance, availableBalance) ||
                other.availableBalance == availableBalance) &&
            (identical(other.currentBalance, currentBalance) ||
                other.currentBalance == currentBalance) &&
            (identical(other.holdBalance, holdBalance) ||
                other.holdBalance == holdBalance) &&
            (identical(other.branchName, branchName) ||
                other.branchName == branchName) &&
            (identical(other.branchRoutingNumber, branchRoutingNumber) ||
                other.branchRoutingNumber == branchRoutingNumber) &&
            (identical(other.nName, nName) || other.nName == nName) &&
            (identical(other.nEmail, nEmail) || other.nEmail == nEmail) &&
            (identical(other.relation, relation) ||
                other.relation == relation) &&
            (identical(other.nPhone, nPhone) || other.nPhone == nPhone) &&
            (identical(other.nPhoto, nPhoto) || other.nPhoto == nPhoto) &&
            (identical(other.nNidFront, nNidFront) ||
                other.nNidFront == nNidFront) &&
            (identical(other.nNidBack, nNidBack) ||
                other.nNidBack == nNidBack) &&
            const DeepCollectionEquality().equals(
              other._holderResponses,
              _holderResponses,
            ));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    accountNumber,
    accountType,
    accountStatus,
    availableBalance,
    currentBalance,
    holdBalance,
    branchName,
    branchRoutingNumber,
    nName,
    nEmail,
    relation,
    nPhone,
    nPhoto,
    nNidFront,
    nNidBack,
    const DeepCollectionEquality().hash(_holderResponses),
  );

  /// Create a copy of AccountResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$AccountResponseImplCopyWith<_$AccountResponseImpl> get copyWith =>
      __$$AccountResponseImplCopyWithImpl<_$AccountResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$AccountResponseImplToJson(this);
  }
}

abstract class _AccountResponse implements AccountResponse {
  const factory _AccountResponse({
    final int? id,
    final String? accountNumber,
    final AccountType? accountType,
    final AccountStatus? accountStatus,
    final double? availableBalance,
    final double? currentBalance,
    final double? holdBalance,
    final String? branchName,
    final String? branchRoutingNumber,
    @JsonKey(name: 'n_name') final String? nName,
    @JsonKey(name: 'n_email') final String? nEmail,
    final NomineeRelation? relation,
    @JsonKey(name: 'n_phone') final String? nPhone,
    @JsonKey(name: 'n_photo') final String? nPhoto,
    @JsonKey(name: 'n_nid_front') final String? nNidFront,
    @JsonKey(name: 'n_nid_back') final String? nNidBack,
    final List<AccountHolderResponse> holderResponses,
  }) = _$AccountResponseImpl;

  factory _AccountResponse.fromJson(Map<String, dynamic> json) =
      _$AccountResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get accountNumber;
  @override
  AccountType? get accountType;
  @override
  AccountStatus? get accountStatus;
  @override
  double? get availableBalance;
  @override
  double? get currentBalance;
  @override
  double? get holdBalance;
  @override
  String? get branchName;
  @override
  String? get branchRoutingNumber;
  @override
  @JsonKey(name: 'n_name')
  String? get nName;
  @override
  @JsonKey(name: 'n_email')
  String? get nEmail;
  @override
  NomineeRelation? get relation;
  @override
  @JsonKey(name: 'n_phone')
  String? get nPhone;
  @override
  @JsonKey(name: 'n_photo')
  String? get nPhoto;
  @override
  @JsonKey(name: 'n_nid_front')
  String? get nNidFront;
  @override
  @JsonKey(name: 'n_nid_back')
  String? get nNidBack;
  @override
  List<AccountHolderResponse> get holderResponses;

  /// Create a copy of AccountResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$AccountResponseImplCopyWith<_$AccountResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

AccountHolderRequest _$AccountHolderRequestFromJson(Map<String, dynamic> json) {
  return _AccountHolderRequest.fromJson(json);
}

/// @nodoc
mixin _$AccountHolderRequest {
  HolderType? get holderType => throw _privateConstructorUsedError;
  bool? get canWithdraw => throw _privateConstructorUsedError;
  bool? get canDeposit => throw _privateConstructorUsedError;
  bool? get canApproveTransaction => throw _privateConstructorUsedError;
  String? get signature => throw _privateConstructorUsedError;
  int? get customerId => throw _privateConstructorUsedError;

  /// Serializes this AccountHolderRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of AccountHolderRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $AccountHolderRequestCopyWith<AccountHolderRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AccountHolderRequestCopyWith<$Res> {
  factory $AccountHolderRequestCopyWith(
    AccountHolderRequest value,
    $Res Function(AccountHolderRequest) then,
  ) = _$AccountHolderRequestCopyWithImpl<$Res, AccountHolderRequest>;
  @useResult
  $Res call({
    HolderType? holderType,
    bool? canWithdraw,
    bool? canDeposit,
    bool? canApproveTransaction,
    String? signature,
    int? customerId,
  });
}

/// @nodoc
class _$AccountHolderRequestCopyWithImpl<
  $Res,
  $Val extends AccountHolderRequest
>
    implements $AccountHolderRequestCopyWith<$Res> {
  _$AccountHolderRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of AccountHolderRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? holderType = freezed,
    Object? canWithdraw = freezed,
    Object? canDeposit = freezed,
    Object? canApproveTransaction = freezed,
    Object? signature = freezed,
    Object? customerId = freezed,
  }) {
    return _then(
      _value.copyWith(
            holderType: freezed == holderType
                ? _value.holderType
                : holderType // ignore: cast_nullable_to_non_nullable
                      as HolderType?,
            canWithdraw: freezed == canWithdraw
                ? _value.canWithdraw
                : canWithdraw // ignore: cast_nullable_to_non_nullable
                      as bool?,
            canDeposit: freezed == canDeposit
                ? _value.canDeposit
                : canDeposit // ignore: cast_nullable_to_non_nullable
                      as bool?,
            canApproveTransaction: freezed == canApproveTransaction
                ? _value.canApproveTransaction
                : canApproveTransaction // ignore: cast_nullable_to_non_nullable
                      as bool?,
            signature: freezed == signature
                ? _value.signature
                : signature // ignore: cast_nullable_to_non_nullable
                      as String?,
            customerId: freezed == customerId
                ? _value.customerId
                : customerId // ignore: cast_nullable_to_non_nullable
                      as int?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$AccountHolderRequestImplCopyWith<$Res>
    implements $AccountHolderRequestCopyWith<$Res> {
  factory _$$AccountHolderRequestImplCopyWith(
    _$AccountHolderRequestImpl value,
    $Res Function(_$AccountHolderRequestImpl) then,
  ) = __$$AccountHolderRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    HolderType? holderType,
    bool? canWithdraw,
    bool? canDeposit,
    bool? canApproveTransaction,
    String? signature,
    int? customerId,
  });
}

/// @nodoc
class __$$AccountHolderRequestImplCopyWithImpl<$Res>
    extends _$AccountHolderRequestCopyWithImpl<$Res, _$AccountHolderRequestImpl>
    implements _$$AccountHolderRequestImplCopyWith<$Res> {
  __$$AccountHolderRequestImplCopyWithImpl(
    _$AccountHolderRequestImpl _value,
    $Res Function(_$AccountHolderRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of AccountHolderRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? holderType = freezed,
    Object? canWithdraw = freezed,
    Object? canDeposit = freezed,
    Object? canApproveTransaction = freezed,
    Object? signature = freezed,
    Object? customerId = freezed,
  }) {
    return _then(
      _$AccountHolderRequestImpl(
        holderType: freezed == holderType
            ? _value.holderType
            : holderType // ignore: cast_nullable_to_non_nullable
                  as HolderType?,
        canWithdraw: freezed == canWithdraw
            ? _value.canWithdraw
            : canWithdraw // ignore: cast_nullable_to_non_nullable
                  as bool?,
        canDeposit: freezed == canDeposit
            ? _value.canDeposit
            : canDeposit // ignore: cast_nullable_to_non_nullable
                  as bool?,
        canApproveTransaction: freezed == canApproveTransaction
            ? _value.canApproveTransaction
            : canApproveTransaction // ignore: cast_nullable_to_non_nullable
                  as bool?,
        signature: freezed == signature
            ? _value.signature
            : signature // ignore: cast_nullable_to_non_nullable
                  as String?,
        customerId: freezed == customerId
            ? _value.customerId
            : customerId // ignore: cast_nullable_to_non_nullable
                  as int?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$AccountHolderRequestImpl implements _AccountHolderRequest {
  const _$AccountHolderRequestImpl({
    this.holderType,
    this.canWithdraw,
    this.canDeposit,
    this.canApproveTransaction,
    this.signature,
    this.customerId,
  });

  factory _$AccountHolderRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$AccountHolderRequestImplFromJson(json);

  @override
  final HolderType? holderType;
  @override
  final bool? canWithdraw;
  @override
  final bool? canDeposit;
  @override
  final bool? canApproveTransaction;
  @override
  final String? signature;
  @override
  final int? customerId;

  @override
  String toString() {
    return 'AccountHolderRequest(holderType: $holderType, canWithdraw: $canWithdraw, canDeposit: $canDeposit, canApproveTransaction: $canApproveTransaction, signature: $signature, customerId: $customerId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AccountHolderRequestImpl &&
            (identical(other.holderType, holderType) ||
                other.holderType == holderType) &&
            (identical(other.canWithdraw, canWithdraw) ||
                other.canWithdraw == canWithdraw) &&
            (identical(other.canDeposit, canDeposit) ||
                other.canDeposit == canDeposit) &&
            (identical(other.canApproveTransaction, canApproveTransaction) ||
                other.canApproveTransaction == canApproveTransaction) &&
            (identical(other.signature, signature) ||
                other.signature == signature) &&
            (identical(other.customerId, customerId) ||
                other.customerId == customerId));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    holderType,
    canWithdraw,
    canDeposit,
    canApproveTransaction,
    signature,
    customerId,
  );

  /// Create a copy of AccountHolderRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$AccountHolderRequestImplCopyWith<_$AccountHolderRequestImpl>
  get copyWith =>
      __$$AccountHolderRequestImplCopyWithImpl<_$AccountHolderRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$AccountHolderRequestImplToJson(this);
  }
}

abstract class _AccountHolderRequest implements AccountHolderRequest {
  const factory _AccountHolderRequest({
    final HolderType? holderType,
    final bool? canWithdraw,
    final bool? canDeposit,
    final bool? canApproveTransaction,
    final String? signature,
    final int? customerId,
  }) = _$AccountHolderRequestImpl;

  factory _AccountHolderRequest.fromJson(Map<String, dynamic> json) =
      _$AccountHolderRequestImpl.fromJson;

  @override
  HolderType? get holderType;
  @override
  bool? get canWithdraw;
  @override
  bool? get canDeposit;
  @override
  bool? get canApproveTransaction;
  @override
  String? get signature;
  @override
  int? get customerId;

  /// Create a copy of AccountHolderRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$AccountHolderRequestImplCopyWith<_$AccountHolderRequestImpl>
  get copyWith => throw _privateConstructorUsedError;
}

AccountHolderResponse _$AccountHolderResponseFromJson(
  Map<String, dynamic> json,
) {
  return _AccountHolderResponse.fromJson(json);
}

/// @nodoc
mixin _$AccountHolderResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get accountHolderName => throw _privateConstructorUsedError;
  HolderType? get holderType => throw _privateConstructorUsedError;
  bool? get canWithdraw => throw _privateConstructorUsedError;
  bool? get canDeposit => throw _privateConstructorUsedError;
  String? get signature => throw _privateConstructorUsedError;
  bool? get canApproveTransaction => throw _privateConstructorUsedError;

  /// Serializes this AccountHolderResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of AccountHolderResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $AccountHolderResponseCopyWith<AccountHolderResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AccountHolderResponseCopyWith<$Res> {
  factory $AccountHolderResponseCopyWith(
    AccountHolderResponse value,
    $Res Function(AccountHolderResponse) then,
  ) = _$AccountHolderResponseCopyWithImpl<$Res, AccountHolderResponse>;
  @useResult
  $Res call({
    int? id,
    String? accountHolderName,
    HolderType? holderType,
    bool? canWithdraw,
    bool? canDeposit,
    String? signature,
    bool? canApproveTransaction,
  });
}

/// @nodoc
class _$AccountHolderResponseCopyWithImpl<
  $Res,
  $Val extends AccountHolderResponse
>
    implements $AccountHolderResponseCopyWith<$Res> {
  _$AccountHolderResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of AccountHolderResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? accountHolderName = freezed,
    Object? holderType = freezed,
    Object? canWithdraw = freezed,
    Object? canDeposit = freezed,
    Object? signature = freezed,
    Object? canApproveTransaction = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            accountHolderName: freezed == accountHolderName
                ? _value.accountHolderName
                : accountHolderName // ignore: cast_nullable_to_non_nullable
                      as String?,
            holderType: freezed == holderType
                ? _value.holderType
                : holderType // ignore: cast_nullable_to_non_nullable
                      as HolderType?,
            canWithdraw: freezed == canWithdraw
                ? _value.canWithdraw
                : canWithdraw // ignore: cast_nullable_to_non_nullable
                      as bool?,
            canDeposit: freezed == canDeposit
                ? _value.canDeposit
                : canDeposit // ignore: cast_nullable_to_non_nullable
                      as bool?,
            signature: freezed == signature
                ? _value.signature
                : signature // ignore: cast_nullable_to_non_nullable
                      as String?,
            canApproveTransaction: freezed == canApproveTransaction
                ? _value.canApproveTransaction
                : canApproveTransaction // ignore: cast_nullable_to_non_nullable
                      as bool?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$AccountHolderResponseImplCopyWith<$Res>
    implements $AccountHolderResponseCopyWith<$Res> {
  factory _$$AccountHolderResponseImplCopyWith(
    _$AccountHolderResponseImpl value,
    $Res Function(_$AccountHolderResponseImpl) then,
  ) = __$$AccountHolderResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    String? accountHolderName,
    HolderType? holderType,
    bool? canWithdraw,
    bool? canDeposit,
    String? signature,
    bool? canApproveTransaction,
  });
}

/// @nodoc
class __$$AccountHolderResponseImplCopyWithImpl<$Res>
    extends
        _$AccountHolderResponseCopyWithImpl<$Res, _$AccountHolderResponseImpl>
    implements _$$AccountHolderResponseImplCopyWith<$Res> {
  __$$AccountHolderResponseImplCopyWithImpl(
    _$AccountHolderResponseImpl _value,
    $Res Function(_$AccountHolderResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of AccountHolderResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? accountHolderName = freezed,
    Object? holderType = freezed,
    Object? canWithdraw = freezed,
    Object? canDeposit = freezed,
    Object? signature = freezed,
    Object? canApproveTransaction = freezed,
  }) {
    return _then(
      _$AccountHolderResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        accountHolderName: freezed == accountHolderName
            ? _value.accountHolderName
            : accountHolderName // ignore: cast_nullable_to_non_nullable
                  as String?,
        holderType: freezed == holderType
            ? _value.holderType
            : holderType // ignore: cast_nullable_to_non_nullable
                  as HolderType?,
        canWithdraw: freezed == canWithdraw
            ? _value.canWithdraw
            : canWithdraw // ignore: cast_nullable_to_non_nullable
                  as bool?,
        canDeposit: freezed == canDeposit
            ? _value.canDeposit
            : canDeposit // ignore: cast_nullable_to_non_nullable
                  as bool?,
        signature: freezed == signature
            ? _value.signature
            : signature // ignore: cast_nullable_to_non_nullable
                  as String?,
        canApproveTransaction: freezed == canApproveTransaction
            ? _value.canApproveTransaction
            : canApproveTransaction // ignore: cast_nullable_to_non_nullable
                  as bool?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$AccountHolderResponseImpl implements _AccountHolderResponse {
  const _$AccountHolderResponseImpl({
    this.id,
    this.accountHolderName,
    this.holderType,
    this.canWithdraw,
    this.canDeposit,
    this.signature,
    this.canApproveTransaction,
  });

  factory _$AccountHolderResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$AccountHolderResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? accountHolderName;
  @override
  final HolderType? holderType;
  @override
  final bool? canWithdraw;
  @override
  final bool? canDeposit;
  @override
  final String? signature;
  @override
  final bool? canApproveTransaction;

  @override
  String toString() {
    return 'AccountHolderResponse(id: $id, accountHolderName: $accountHolderName, holderType: $holderType, canWithdraw: $canWithdraw, canDeposit: $canDeposit, signature: $signature, canApproveTransaction: $canApproveTransaction)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AccountHolderResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.accountHolderName, accountHolderName) ||
                other.accountHolderName == accountHolderName) &&
            (identical(other.holderType, holderType) ||
                other.holderType == holderType) &&
            (identical(other.canWithdraw, canWithdraw) ||
                other.canWithdraw == canWithdraw) &&
            (identical(other.canDeposit, canDeposit) ||
                other.canDeposit == canDeposit) &&
            (identical(other.signature, signature) ||
                other.signature == signature) &&
            (identical(other.canApproveTransaction, canApproveTransaction) ||
                other.canApproveTransaction == canApproveTransaction));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    accountHolderName,
    holderType,
    canWithdraw,
    canDeposit,
    signature,
    canApproveTransaction,
  );

  /// Create a copy of AccountHolderResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$AccountHolderResponseImplCopyWith<_$AccountHolderResponseImpl>
  get copyWith =>
      __$$AccountHolderResponseImplCopyWithImpl<_$AccountHolderResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$AccountHolderResponseImplToJson(this);
  }
}

abstract class _AccountHolderResponse implements AccountHolderResponse {
  const factory _AccountHolderResponse({
    final int? id,
    final String? accountHolderName,
    final HolderType? holderType,
    final bool? canWithdraw,
    final bool? canDeposit,
    final String? signature,
    final bool? canApproveTransaction,
  }) = _$AccountHolderResponseImpl;

  factory _AccountHolderResponse.fromJson(Map<String, dynamic> json) =
      _$AccountHolderResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get accountHolderName;
  @override
  HolderType? get holderType;
  @override
  bool? get canWithdraw;
  @override
  bool? get canDeposit;
  @override
  String? get signature;
  @override
  bool? get canApproveTransaction;

  /// Create a copy of AccountHolderResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$AccountHolderResponseImplCopyWith<_$AccountHolderResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}
