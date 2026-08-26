// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'card_models.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
  'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models',
);

CardRequest _$CardRequestFromJson(Map<String, dynamic> json) {
  return _CardRequest.fromJson(json);
}

/// @nodoc
mixin _$CardRequest {
  int? get accountId => throw _privateConstructorUsedError;
  CardNetwork? get cardNetwork => throw _privateConstructorUsedError;
  CardType? get cardType => throw _privateConstructorUsedError;
  String? get pin => throw _privateConstructorUsedError;
  bool get isInternationalEnabled => throw _privateConstructorUsedError;
  bool get isOnlineTransactionEnabled => throw _privateConstructorUsedError;

  /// Serializes this CardRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of CardRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $CardRequestCopyWith<CardRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CardRequestCopyWith<$Res> {
  factory $CardRequestCopyWith(
    CardRequest value,
    $Res Function(CardRequest) then,
  ) = _$CardRequestCopyWithImpl<$Res, CardRequest>;
  @useResult
  $Res call({
    int? accountId,
    CardNetwork? cardNetwork,
    CardType? cardType,
    String? pin,
    bool isInternationalEnabled,
    bool isOnlineTransactionEnabled,
  });
}

/// @nodoc
class _$CardRequestCopyWithImpl<$Res, $Val extends CardRequest>
    implements $CardRequestCopyWith<$Res> {
  _$CardRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of CardRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? accountId = freezed,
    Object? cardNetwork = freezed,
    Object? cardType = freezed,
    Object? pin = freezed,
    Object? isInternationalEnabled = null,
    Object? isOnlineTransactionEnabled = null,
  }) {
    return _then(
      _value.copyWith(
            accountId: freezed == accountId
                ? _value.accountId
                : accountId // ignore: cast_nullable_to_non_nullable
                      as int?,
            cardNetwork: freezed == cardNetwork
                ? _value.cardNetwork
                : cardNetwork // ignore: cast_nullable_to_non_nullable
                      as CardNetwork?,
            cardType: freezed == cardType
                ? _value.cardType
                : cardType // ignore: cast_nullable_to_non_nullable
                      as CardType?,
            pin: freezed == pin
                ? _value.pin
                : pin // ignore: cast_nullable_to_non_nullable
                      as String?,
            isInternationalEnabled: null == isInternationalEnabled
                ? _value.isInternationalEnabled
                : isInternationalEnabled // ignore: cast_nullable_to_non_nullable
                      as bool,
            isOnlineTransactionEnabled: null == isOnlineTransactionEnabled
                ? _value.isOnlineTransactionEnabled
                : isOnlineTransactionEnabled // ignore: cast_nullable_to_non_nullable
                      as bool,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$CardRequestImplCopyWith<$Res>
    implements $CardRequestCopyWith<$Res> {
  factory _$$CardRequestImplCopyWith(
    _$CardRequestImpl value,
    $Res Function(_$CardRequestImpl) then,
  ) = __$$CardRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? accountId,
    CardNetwork? cardNetwork,
    CardType? cardType,
    String? pin,
    bool isInternationalEnabled,
    bool isOnlineTransactionEnabled,
  });
}

/// @nodoc
class __$$CardRequestImplCopyWithImpl<$Res>
    extends _$CardRequestCopyWithImpl<$Res, _$CardRequestImpl>
    implements _$$CardRequestImplCopyWith<$Res> {
  __$$CardRequestImplCopyWithImpl(
    _$CardRequestImpl _value,
    $Res Function(_$CardRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of CardRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? accountId = freezed,
    Object? cardNetwork = freezed,
    Object? cardType = freezed,
    Object? pin = freezed,
    Object? isInternationalEnabled = null,
    Object? isOnlineTransactionEnabled = null,
  }) {
    return _then(
      _$CardRequestImpl(
        accountId: freezed == accountId
            ? _value.accountId
            : accountId // ignore: cast_nullable_to_non_nullable
                  as int?,
        cardNetwork: freezed == cardNetwork
            ? _value.cardNetwork
            : cardNetwork // ignore: cast_nullable_to_non_nullable
                  as CardNetwork?,
        cardType: freezed == cardType
            ? _value.cardType
            : cardType // ignore: cast_nullable_to_non_nullable
                  as CardType?,
        pin: freezed == pin
            ? _value.pin
            : pin // ignore: cast_nullable_to_non_nullable
                  as String?,
        isInternationalEnabled: null == isInternationalEnabled
            ? _value.isInternationalEnabled
            : isInternationalEnabled // ignore: cast_nullable_to_non_nullable
                  as bool,
        isOnlineTransactionEnabled: null == isOnlineTransactionEnabled
            ? _value.isOnlineTransactionEnabled
            : isOnlineTransactionEnabled // ignore: cast_nullable_to_non_nullable
                  as bool,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$CardRequestImpl implements _CardRequest {
  const _$CardRequestImpl({
    this.accountId,
    this.cardNetwork,
    this.cardType,
    this.pin,
    this.isInternationalEnabled = false,
    this.isOnlineTransactionEnabled = false,
  });

  factory _$CardRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$CardRequestImplFromJson(json);

  @override
  final int? accountId;
  @override
  final CardNetwork? cardNetwork;
  @override
  final CardType? cardType;
  @override
  final String? pin;
  @override
  @JsonKey()
  final bool isInternationalEnabled;
  @override
  @JsonKey()
  final bool isOnlineTransactionEnabled;

  @override
  String toString() {
    return 'CardRequest(accountId: $accountId, cardNetwork: $cardNetwork, cardType: $cardType, pin: $pin, isInternationalEnabled: $isInternationalEnabled, isOnlineTransactionEnabled: $isOnlineTransactionEnabled)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CardRequestImpl &&
            (identical(other.accountId, accountId) ||
                other.accountId == accountId) &&
            (identical(other.cardNetwork, cardNetwork) ||
                other.cardNetwork == cardNetwork) &&
            (identical(other.cardType, cardType) ||
                other.cardType == cardType) &&
            (identical(other.pin, pin) || other.pin == pin) &&
            (identical(other.isInternationalEnabled, isInternationalEnabled) ||
                other.isInternationalEnabled == isInternationalEnabled) &&
            (identical(
                  other.isOnlineTransactionEnabled,
                  isOnlineTransactionEnabled,
                ) ||
                other.isOnlineTransactionEnabled ==
                    isOnlineTransactionEnabled));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    accountId,
    cardNetwork,
    cardType,
    pin,
    isInternationalEnabled,
    isOnlineTransactionEnabled,
  );

  /// Create a copy of CardRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$CardRequestImplCopyWith<_$CardRequestImpl> get copyWith =>
      __$$CardRequestImplCopyWithImpl<_$CardRequestImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$CardRequestImplToJson(this);
  }
}

abstract class _CardRequest implements CardRequest {
  const factory _CardRequest({
    final int? accountId,
    final CardNetwork? cardNetwork,
    final CardType? cardType,
    final String? pin,
    final bool isInternationalEnabled,
    final bool isOnlineTransactionEnabled,
  }) = _$CardRequestImpl;

  factory _CardRequest.fromJson(Map<String, dynamic> json) =
      _$CardRequestImpl.fromJson;

  @override
  int? get accountId;
  @override
  CardNetwork? get cardNetwork;
  @override
  CardType? get cardType;
  @override
  String? get pin;
  @override
  bool get isInternationalEnabled;
  @override
  bool get isOnlineTransactionEnabled;

  /// Create a copy of CardRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$CardRequestImplCopyWith<_$CardRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

CardResponse _$CardResponseFromJson(Map<String, dynamic> json) {
  return _CardResponse.fromJson(json);
}

/// @nodoc
mixin _$CardResponse {
  int? get cardId => throw _privateConstructorUsedError;
  String? get cardNumber => throw _privateConstructorUsedError;
  String? get cardHolderName => throw _privateConstructorUsedError;
  CardNetwork? get cardNetwork => throw _privateConstructorUsedError;
  CardType? get cardType => throw _privateConstructorUsedError;
  CardStatus? get status => throw _privateConstructorUsedError;
  DateTime? get expiryDate => throw _privateConstructorUsedError;
  double? get dailyLimit => throw _privateConstructorUsedError;
  double? get monthlyLimit => throw _privateConstructorUsedError;
  String? get accountNumber => throw _privateConstructorUsedError;
  bool get isInternationalEnabled => throw _privateConstructorUsedError;
  bool get isOnlineTransactionEnabled => throw _privateConstructorUsedError;
  DateTime? get createdAt => throw _privateConstructorUsedError;

  /// Serializes this CardResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of CardResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $CardResponseCopyWith<CardResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CardResponseCopyWith<$Res> {
  factory $CardResponseCopyWith(
    CardResponse value,
    $Res Function(CardResponse) then,
  ) = _$CardResponseCopyWithImpl<$Res, CardResponse>;
  @useResult
  $Res call({
    int? cardId,
    String? cardNumber,
    String? cardHolderName,
    CardNetwork? cardNetwork,
    CardType? cardType,
    CardStatus? status,
    DateTime? expiryDate,
    double? dailyLimit,
    double? monthlyLimit,
    String? accountNumber,
    bool isInternationalEnabled,
    bool isOnlineTransactionEnabled,
    DateTime? createdAt,
  });
}

/// @nodoc
class _$CardResponseCopyWithImpl<$Res, $Val extends CardResponse>
    implements $CardResponseCopyWith<$Res> {
  _$CardResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of CardResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? cardId = freezed,
    Object? cardNumber = freezed,
    Object? cardHolderName = freezed,
    Object? cardNetwork = freezed,
    Object? cardType = freezed,
    Object? status = freezed,
    Object? expiryDate = freezed,
    Object? dailyLimit = freezed,
    Object? monthlyLimit = freezed,
    Object? accountNumber = freezed,
    Object? isInternationalEnabled = null,
    Object? isOnlineTransactionEnabled = null,
    Object? createdAt = freezed,
  }) {
    return _then(
      _value.copyWith(
            cardId: freezed == cardId
                ? _value.cardId
                : cardId // ignore: cast_nullable_to_non_nullable
                      as int?,
            cardNumber: freezed == cardNumber
                ? _value.cardNumber
                : cardNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            cardHolderName: freezed == cardHolderName
                ? _value.cardHolderName
                : cardHolderName // ignore: cast_nullable_to_non_nullable
                      as String?,
            cardNetwork: freezed == cardNetwork
                ? _value.cardNetwork
                : cardNetwork // ignore: cast_nullable_to_non_nullable
                      as CardNetwork?,
            cardType: freezed == cardType
                ? _value.cardType
                : cardType // ignore: cast_nullable_to_non_nullable
                      as CardType?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as CardStatus?,
            expiryDate: freezed == expiryDate
                ? _value.expiryDate
                : expiryDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            dailyLimit: freezed == dailyLimit
                ? _value.dailyLimit
                : dailyLimit // ignore: cast_nullable_to_non_nullable
                      as double?,
            monthlyLimit: freezed == monthlyLimit
                ? _value.monthlyLimit
                : monthlyLimit // ignore: cast_nullable_to_non_nullable
                      as double?,
            accountNumber: freezed == accountNumber
                ? _value.accountNumber
                : accountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            isInternationalEnabled: null == isInternationalEnabled
                ? _value.isInternationalEnabled
                : isInternationalEnabled // ignore: cast_nullable_to_non_nullable
                      as bool,
            isOnlineTransactionEnabled: null == isOnlineTransactionEnabled
                ? _value.isOnlineTransactionEnabled
                : isOnlineTransactionEnabled // ignore: cast_nullable_to_non_nullable
                      as bool,
            createdAt: freezed == createdAt
                ? _value.createdAt
                : createdAt // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$CardResponseImplCopyWith<$Res>
    implements $CardResponseCopyWith<$Res> {
  factory _$$CardResponseImplCopyWith(
    _$CardResponseImpl value,
    $Res Function(_$CardResponseImpl) then,
  ) = __$$CardResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? cardId,
    String? cardNumber,
    String? cardHolderName,
    CardNetwork? cardNetwork,
    CardType? cardType,
    CardStatus? status,
    DateTime? expiryDate,
    double? dailyLimit,
    double? monthlyLimit,
    String? accountNumber,
    bool isInternationalEnabled,
    bool isOnlineTransactionEnabled,
    DateTime? createdAt,
  });
}

/// @nodoc
class __$$CardResponseImplCopyWithImpl<$Res>
    extends _$CardResponseCopyWithImpl<$Res, _$CardResponseImpl>
    implements _$$CardResponseImplCopyWith<$Res> {
  __$$CardResponseImplCopyWithImpl(
    _$CardResponseImpl _value,
    $Res Function(_$CardResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of CardResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? cardId = freezed,
    Object? cardNumber = freezed,
    Object? cardHolderName = freezed,
    Object? cardNetwork = freezed,
    Object? cardType = freezed,
    Object? status = freezed,
    Object? expiryDate = freezed,
    Object? dailyLimit = freezed,
    Object? monthlyLimit = freezed,
    Object? accountNumber = freezed,
    Object? isInternationalEnabled = null,
    Object? isOnlineTransactionEnabled = null,
    Object? createdAt = freezed,
  }) {
    return _then(
      _$CardResponseImpl(
        cardId: freezed == cardId
            ? _value.cardId
            : cardId // ignore: cast_nullable_to_non_nullable
                  as int?,
        cardNumber: freezed == cardNumber
            ? _value.cardNumber
            : cardNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        cardHolderName: freezed == cardHolderName
            ? _value.cardHolderName
            : cardHolderName // ignore: cast_nullable_to_non_nullable
                  as String?,
        cardNetwork: freezed == cardNetwork
            ? _value.cardNetwork
            : cardNetwork // ignore: cast_nullable_to_non_nullable
                  as CardNetwork?,
        cardType: freezed == cardType
            ? _value.cardType
            : cardType // ignore: cast_nullable_to_non_nullable
                  as CardType?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as CardStatus?,
        expiryDate: freezed == expiryDate
            ? _value.expiryDate
            : expiryDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        dailyLimit: freezed == dailyLimit
            ? _value.dailyLimit
            : dailyLimit // ignore: cast_nullable_to_non_nullable
                  as double?,
        monthlyLimit: freezed == monthlyLimit
            ? _value.monthlyLimit
            : monthlyLimit // ignore: cast_nullable_to_non_nullable
                  as double?,
        accountNumber: freezed == accountNumber
            ? _value.accountNumber
            : accountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        isInternationalEnabled: null == isInternationalEnabled
            ? _value.isInternationalEnabled
            : isInternationalEnabled // ignore: cast_nullable_to_non_nullable
                  as bool,
        isOnlineTransactionEnabled: null == isOnlineTransactionEnabled
            ? _value.isOnlineTransactionEnabled
            : isOnlineTransactionEnabled // ignore: cast_nullable_to_non_nullable
                  as bool,
        createdAt: freezed == createdAt
            ? _value.createdAt
            : createdAt // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$CardResponseImpl implements _CardResponse {
  const _$CardResponseImpl({
    this.cardId,
    this.cardNumber,
    this.cardHolderName,
    this.cardNetwork,
    this.cardType,
    this.status,
    this.expiryDate,
    this.dailyLimit,
    this.monthlyLimit,
    this.accountNumber,
    this.isInternationalEnabled = false,
    this.isOnlineTransactionEnabled = false,
    this.createdAt,
  });

  factory _$CardResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$CardResponseImplFromJson(json);

  @override
  final int? cardId;
  @override
  final String? cardNumber;
  @override
  final String? cardHolderName;
  @override
  final CardNetwork? cardNetwork;
  @override
  final CardType? cardType;
  @override
  final CardStatus? status;
  @override
  final DateTime? expiryDate;
  @override
  final double? dailyLimit;
  @override
  final double? monthlyLimit;
  @override
  final String? accountNumber;
  @override
  @JsonKey()
  final bool isInternationalEnabled;
  @override
  @JsonKey()
  final bool isOnlineTransactionEnabled;
  @override
  final DateTime? createdAt;

  @override
  String toString() {
    return 'CardResponse(cardId: $cardId, cardNumber: $cardNumber, cardHolderName: $cardHolderName, cardNetwork: $cardNetwork, cardType: $cardType, status: $status, expiryDate: $expiryDate, dailyLimit: $dailyLimit, monthlyLimit: $monthlyLimit, accountNumber: $accountNumber, isInternationalEnabled: $isInternationalEnabled, isOnlineTransactionEnabled: $isOnlineTransactionEnabled, createdAt: $createdAt)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CardResponseImpl &&
            (identical(other.cardId, cardId) || other.cardId == cardId) &&
            (identical(other.cardNumber, cardNumber) ||
                other.cardNumber == cardNumber) &&
            (identical(other.cardHolderName, cardHolderName) ||
                other.cardHolderName == cardHolderName) &&
            (identical(other.cardNetwork, cardNetwork) ||
                other.cardNetwork == cardNetwork) &&
            (identical(other.cardType, cardType) ||
                other.cardType == cardType) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.expiryDate, expiryDate) ||
                other.expiryDate == expiryDate) &&
            (identical(other.dailyLimit, dailyLimit) ||
                other.dailyLimit == dailyLimit) &&
            (identical(other.monthlyLimit, monthlyLimit) ||
                other.monthlyLimit == monthlyLimit) &&
            (identical(other.accountNumber, accountNumber) ||
                other.accountNumber == accountNumber) &&
            (identical(other.isInternationalEnabled, isInternationalEnabled) ||
                other.isInternationalEnabled == isInternationalEnabled) &&
            (identical(
                  other.isOnlineTransactionEnabled,
                  isOnlineTransactionEnabled,
                ) ||
                other.isOnlineTransactionEnabled ==
                    isOnlineTransactionEnabled) &&
            (identical(other.createdAt, createdAt) ||
                other.createdAt == createdAt));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    cardId,
    cardNumber,
    cardHolderName,
    cardNetwork,
    cardType,
    status,
    expiryDate,
    dailyLimit,
    monthlyLimit,
    accountNumber,
    isInternationalEnabled,
    isOnlineTransactionEnabled,
    createdAt,
  );

  /// Create a copy of CardResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$CardResponseImplCopyWith<_$CardResponseImpl> get copyWith =>
      __$$CardResponseImplCopyWithImpl<_$CardResponseImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$CardResponseImplToJson(this);
  }
}

abstract class _CardResponse implements CardResponse {
  const factory _CardResponse({
    final int? cardId,
    final String? cardNumber,
    final String? cardHolderName,
    final CardNetwork? cardNetwork,
    final CardType? cardType,
    final CardStatus? status,
    final DateTime? expiryDate,
    final double? dailyLimit,
    final double? monthlyLimit,
    final String? accountNumber,
    final bool isInternationalEnabled,
    final bool isOnlineTransactionEnabled,
    final DateTime? createdAt,
  }) = _$CardResponseImpl;

  factory _CardResponse.fromJson(Map<String, dynamic> json) =
      _$CardResponseImpl.fromJson;

  @override
  int? get cardId;
  @override
  String? get cardNumber;
  @override
  String? get cardHolderName;
  @override
  CardNetwork? get cardNetwork;
  @override
  CardType? get cardType;
  @override
  CardStatus? get status;
  @override
  DateTime? get expiryDate;
  @override
  double? get dailyLimit;
  @override
  double? get monthlyLimit;
  @override
  String? get accountNumber;
  @override
  bool get isInternationalEnabled;
  @override
  bool get isOnlineTransactionEnabled;
  @override
  DateTime? get createdAt;

  /// Create a copy of CardResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$CardResponseImplCopyWith<_$CardResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

PinChangeRequest _$PinChangeRequestFromJson(Map<String, dynamic> json) {
  return _PinChangeRequest.fromJson(json);
}

/// @nodoc
mixin _$PinChangeRequest {
  String? get oldPin => throw _privateConstructorUsedError;
  String? get newPin => throw _privateConstructorUsedError;

  /// Serializes this PinChangeRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of PinChangeRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $PinChangeRequestCopyWith<PinChangeRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $PinChangeRequestCopyWith<$Res> {
  factory $PinChangeRequestCopyWith(
    PinChangeRequest value,
    $Res Function(PinChangeRequest) then,
  ) = _$PinChangeRequestCopyWithImpl<$Res, PinChangeRequest>;
  @useResult
  $Res call({String? oldPin, String? newPin});
}

/// @nodoc
class _$PinChangeRequestCopyWithImpl<$Res, $Val extends PinChangeRequest>
    implements $PinChangeRequestCopyWith<$Res> {
  _$PinChangeRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of PinChangeRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? oldPin = freezed, Object? newPin = freezed}) {
    return _then(
      _value.copyWith(
            oldPin: freezed == oldPin
                ? _value.oldPin
                : oldPin // ignore: cast_nullable_to_non_nullable
                      as String?,
            newPin: freezed == newPin
                ? _value.newPin
                : newPin // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$PinChangeRequestImplCopyWith<$Res>
    implements $PinChangeRequestCopyWith<$Res> {
  factory _$$PinChangeRequestImplCopyWith(
    _$PinChangeRequestImpl value,
    $Res Function(_$PinChangeRequestImpl) then,
  ) = __$$PinChangeRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? oldPin, String? newPin});
}

/// @nodoc
class __$$PinChangeRequestImplCopyWithImpl<$Res>
    extends _$PinChangeRequestCopyWithImpl<$Res, _$PinChangeRequestImpl>
    implements _$$PinChangeRequestImplCopyWith<$Res> {
  __$$PinChangeRequestImplCopyWithImpl(
    _$PinChangeRequestImpl _value,
    $Res Function(_$PinChangeRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of PinChangeRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? oldPin = freezed, Object? newPin = freezed}) {
    return _then(
      _$PinChangeRequestImpl(
        oldPin: freezed == oldPin
            ? _value.oldPin
            : oldPin // ignore: cast_nullable_to_non_nullable
                  as String?,
        newPin: freezed == newPin
            ? _value.newPin
            : newPin // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$PinChangeRequestImpl implements _PinChangeRequest {
  const _$PinChangeRequestImpl({this.oldPin, this.newPin});

  factory _$PinChangeRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$PinChangeRequestImplFromJson(json);

  @override
  final String? oldPin;
  @override
  final String? newPin;

  @override
  String toString() {
    return 'PinChangeRequest(oldPin: $oldPin, newPin: $newPin)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$PinChangeRequestImpl &&
            (identical(other.oldPin, oldPin) || other.oldPin == oldPin) &&
            (identical(other.newPin, newPin) || other.newPin == newPin));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, oldPin, newPin);

  /// Create a copy of PinChangeRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$PinChangeRequestImplCopyWith<_$PinChangeRequestImpl> get copyWith =>
      __$$PinChangeRequestImplCopyWithImpl<_$PinChangeRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$PinChangeRequestImplToJson(this);
  }
}

abstract class _PinChangeRequest implements PinChangeRequest {
  const factory _PinChangeRequest({
    final String? oldPin,
    final String? newPin,
  }) = _$PinChangeRequestImpl;

  factory _PinChangeRequest.fromJson(Map<String, dynamic> json) =
      _$PinChangeRequestImpl.fromJson;

  @override
  String? get oldPin;
  @override
  String? get newPin;

  /// Create a copy of PinChangeRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$PinChangeRequestImplCopyWith<_$PinChangeRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

CardUsageResponse _$CardUsageResponseFromJson(Map<String, dynamic> json) {
  return _CardUsageResponse.fromJson(json);
}

/// @nodoc
mixin _$CardUsageResponse {
  int? get cardId => throw _privateConstructorUsedError;
  String? get cardNumber => throw _privateConstructorUsedError;
  double? get dailyLimit => throw _privateConstructorUsedError;
  double? get monthlyLimit => throw _privateConstructorUsedError;
  double? get currentDailyUsage => throw _privateConstructorUsedError;
  double? get currentMonthlyUsage => throw _privateConstructorUsedError;
  double? get dailyRemaining => throw _privateConstructorUsedError;
  double? get monthlyRemaining => throw _privateConstructorUsedError;

  /// Serializes this CardUsageResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of CardUsageResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $CardUsageResponseCopyWith<CardUsageResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CardUsageResponseCopyWith<$Res> {
  factory $CardUsageResponseCopyWith(
    CardUsageResponse value,
    $Res Function(CardUsageResponse) then,
  ) = _$CardUsageResponseCopyWithImpl<$Res, CardUsageResponse>;
  @useResult
  $Res call({
    int? cardId,
    String? cardNumber,
    double? dailyLimit,
    double? monthlyLimit,
    double? currentDailyUsage,
    double? currentMonthlyUsage,
    double? dailyRemaining,
    double? monthlyRemaining,
  });
}

/// @nodoc
class _$CardUsageResponseCopyWithImpl<$Res, $Val extends CardUsageResponse>
    implements $CardUsageResponseCopyWith<$Res> {
  _$CardUsageResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of CardUsageResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? cardId = freezed,
    Object? cardNumber = freezed,
    Object? dailyLimit = freezed,
    Object? monthlyLimit = freezed,
    Object? currentDailyUsage = freezed,
    Object? currentMonthlyUsage = freezed,
    Object? dailyRemaining = freezed,
    Object? monthlyRemaining = freezed,
  }) {
    return _then(
      _value.copyWith(
            cardId: freezed == cardId
                ? _value.cardId
                : cardId // ignore: cast_nullable_to_non_nullable
                      as int?,
            cardNumber: freezed == cardNumber
                ? _value.cardNumber
                : cardNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            dailyLimit: freezed == dailyLimit
                ? _value.dailyLimit
                : dailyLimit // ignore: cast_nullable_to_non_nullable
                      as double?,
            monthlyLimit: freezed == monthlyLimit
                ? _value.monthlyLimit
                : monthlyLimit // ignore: cast_nullable_to_non_nullable
                      as double?,
            currentDailyUsage: freezed == currentDailyUsage
                ? _value.currentDailyUsage
                : currentDailyUsage // ignore: cast_nullable_to_non_nullable
                      as double?,
            currentMonthlyUsage: freezed == currentMonthlyUsage
                ? _value.currentMonthlyUsage
                : currentMonthlyUsage // ignore: cast_nullable_to_non_nullable
                      as double?,
            dailyRemaining: freezed == dailyRemaining
                ? _value.dailyRemaining
                : dailyRemaining // ignore: cast_nullable_to_non_nullable
                      as double?,
            monthlyRemaining: freezed == monthlyRemaining
                ? _value.monthlyRemaining
                : monthlyRemaining // ignore: cast_nullable_to_non_nullable
                      as double?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$CardUsageResponseImplCopyWith<$Res>
    implements $CardUsageResponseCopyWith<$Res> {
  factory _$$CardUsageResponseImplCopyWith(
    _$CardUsageResponseImpl value,
    $Res Function(_$CardUsageResponseImpl) then,
  ) = __$$CardUsageResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? cardId,
    String? cardNumber,
    double? dailyLimit,
    double? monthlyLimit,
    double? currentDailyUsage,
    double? currentMonthlyUsage,
    double? dailyRemaining,
    double? monthlyRemaining,
  });
}

/// @nodoc
class __$$CardUsageResponseImplCopyWithImpl<$Res>
    extends _$CardUsageResponseCopyWithImpl<$Res, _$CardUsageResponseImpl>
    implements _$$CardUsageResponseImplCopyWith<$Res> {
  __$$CardUsageResponseImplCopyWithImpl(
    _$CardUsageResponseImpl _value,
    $Res Function(_$CardUsageResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of CardUsageResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? cardId = freezed,
    Object? cardNumber = freezed,
    Object? dailyLimit = freezed,
    Object? monthlyLimit = freezed,
    Object? currentDailyUsage = freezed,
    Object? currentMonthlyUsage = freezed,
    Object? dailyRemaining = freezed,
    Object? monthlyRemaining = freezed,
  }) {
    return _then(
      _$CardUsageResponseImpl(
        cardId: freezed == cardId
            ? _value.cardId
            : cardId // ignore: cast_nullable_to_non_nullable
                  as int?,
        cardNumber: freezed == cardNumber
            ? _value.cardNumber
            : cardNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        dailyLimit: freezed == dailyLimit
            ? _value.dailyLimit
            : dailyLimit // ignore: cast_nullable_to_non_nullable
                  as double?,
        monthlyLimit: freezed == monthlyLimit
            ? _value.monthlyLimit
            : monthlyLimit // ignore: cast_nullable_to_non_nullable
                  as double?,
        currentDailyUsage: freezed == currentDailyUsage
            ? _value.currentDailyUsage
            : currentDailyUsage // ignore: cast_nullable_to_non_nullable
                  as double?,
        currentMonthlyUsage: freezed == currentMonthlyUsage
            ? _value.currentMonthlyUsage
            : currentMonthlyUsage // ignore: cast_nullable_to_non_nullable
                  as double?,
        dailyRemaining: freezed == dailyRemaining
            ? _value.dailyRemaining
            : dailyRemaining // ignore: cast_nullable_to_non_nullable
                  as double?,
        monthlyRemaining: freezed == monthlyRemaining
            ? _value.monthlyRemaining
            : monthlyRemaining // ignore: cast_nullable_to_non_nullable
                  as double?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$CardUsageResponseImpl implements _CardUsageResponse {
  const _$CardUsageResponseImpl({
    this.cardId,
    this.cardNumber,
    this.dailyLimit,
    this.monthlyLimit,
    this.currentDailyUsage,
    this.currentMonthlyUsage,
    this.dailyRemaining,
    this.monthlyRemaining,
  });

  factory _$CardUsageResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$CardUsageResponseImplFromJson(json);

  @override
  final int? cardId;
  @override
  final String? cardNumber;
  @override
  final double? dailyLimit;
  @override
  final double? monthlyLimit;
  @override
  final double? currentDailyUsage;
  @override
  final double? currentMonthlyUsage;
  @override
  final double? dailyRemaining;
  @override
  final double? monthlyRemaining;

  @override
  String toString() {
    return 'CardUsageResponse(cardId: $cardId, cardNumber: $cardNumber, dailyLimit: $dailyLimit, monthlyLimit: $monthlyLimit, currentDailyUsage: $currentDailyUsage, currentMonthlyUsage: $currentMonthlyUsage, dailyRemaining: $dailyRemaining, monthlyRemaining: $monthlyRemaining)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CardUsageResponseImpl &&
            (identical(other.cardId, cardId) || other.cardId == cardId) &&
            (identical(other.cardNumber, cardNumber) ||
                other.cardNumber == cardNumber) &&
            (identical(other.dailyLimit, dailyLimit) ||
                other.dailyLimit == dailyLimit) &&
            (identical(other.monthlyLimit, monthlyLimit) ||
                other.monthlyLimit == monthlyLimit) &&
            (identical(other.currentDailyUsage, currentDailyUsage) ||
                other.currentDailyUsage == currentDailyUsage) &&
            (identical(other.currentMonthlyUsage, currentMonthlyUsage) ||
                other.currentMonthlyUsage == currentMonthlyUsage) &&
            (identical(other.dailyRemaining, dailyRemaining) ||
                other.dailyRemaining == dailyRemaining) &&
            (identical(other.monthlyRemaining, monthlyRemaining) ||
                other.monthlyRemaining == monthlyRemaining));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    cardId,
    cardNumber,
    dailyLimit,
    monthlyLimit,
    currentDailyUsage,
    currentMonthlyUsage,
    dailyRemaining,
    monthlyRemaining,
  );

  /// Create a copy of CardUsageResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$CardUsageResponseImplCopyWith<_$CardUsageResponseImpl> get copyWith =>
      __$$CardUsageResponseImplCopyWithImpl<_$CardUsageResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$CardUsageResponseImplToJson(this);
  }
}

abstract class _CardUsageResponse implements CardUsageResponse {
  const factory _CardUsageResponse({
    final int? cardId,
    final String? cardNumber,
    final double? dailyLimit,
    final double? monthlyLimit,
    final double? currentDailyUsage,
    final double? currentMonthlyUsage,
    final double? dailyRemaining,
    final double? monthlyRemaining,
  }) = _$CardUsageResponseImpl;

  factory _CardUsageResponse.fromJson(Map<String, dynamic> json) =
      _$CardUsageResponseImpl.fromJson;

  @override
  int? get cardId;
  @override
  String? get cardNumber;
  @override
  double? get dailyLimit;
  @override
  double? get monthlyLimit;
  @override
  double? get currentDailyUsage;
  @override
  double? get currentMonthlyUsage;
  @override
  double? get dailyRemaining;
  @override
  double? get monthlyRemaining;

  /// Create a copy of CardUsageResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$CardUsageResponseImplCopyWith<_$CardUsageResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

CardSettingsRequest _$CardSettingsRequestFromJson(Map<String, dynamic> json) {
  return _CardSettingsRequest.fromJson(json);
}

/// @nodoc
mixin _$CardSettingsRequest {
  int? get id => throw _privateConstructorUsedError;
  int? get cardId => throw _privateConstructorUsedError;
  CardSettingsRequestType? get requestType =>
      throw _privateConstructorUsedError;
  bool? get requestedValue => throw _privateConstructorUsedError;
  CardType? get requestedCardType => throw _privateConstructorUsedError;
  RequestStatus? get status => throw _privateConstructorUsedError;
  String? get rejectionReason => throw _privateConstructorUsedError;
  int? get requestedById => throw _privateConstructorUsedError;

  /// Serializes this CardSettingsRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of CardSettingsRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $CardSettingsRequestCopyWith<CardSettingsRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CardSettingsRequestCopyWith<$Res> {
  factory $CardSettingsRequestCopyWith(
    CardSettingsRequest value,
    $Res Function(CardSettingsRequest) then,
  ) = _$CardSettingsRequestCopyWithImpl<$Res, CardSettingsRequest>;
  @useResult
  $Res call({
    int? id,
    int? cardId,
    CardSettingsRequestType? requestType,
    bool? requestedValue,
    CardType? requestedCardType,
    RequestStatus? status,
    String? rejectionReason,
    int? requestedById,
  });
}

/// @nodoc
class _$CardSettingsRequestCopyWithImpl<$Res, $Val extends CardSettingsRequest>
    implements $CardSettingsRequestCopyWith<$Res> {
  _$CardSettingsRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of CardSettingsRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? cardId = freezed,
    Object? requestType = freezed,
    Object? requestedValue = freezed,
    Object? requestedCardType = freezed,
    Object? status = freezed,
    Object? rejectionReason = freezed,
    Object? requestedById = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            cardId: freezed == cardId
                ? _value.cardId
                : cardId // ignore: cast_nullable_to_non_nullable
                      as int?,
            requestType: freezed == requestType
                ? _value.requestType
                : requestType // ignore: cast_nullable_to_non_nullable
                      as CardSettingsRequestType?,
            requestedValue: freezed == requestedValue
                ? _value.requestedValue
                : requestedValue // ignore: cast_nullable_to_non_nullable
                      as bool?,
            requestedCardType: freezed == requestedCardType
                ? _value.requestedCardType
                : requestedCardType // ignore: cast_nullable_to_non_nullable
                      as CardType?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as RequestStatus?,
            rejectionReason: freezed == rejectionReason
                ? _value.rejectionReason
                : rejectionReason // ignore: cast_nullable_to_non_nullable
                      as String?,
            requestedById: freezed == requestedById
                ? _value.requestedById
                : requestedById // ignore: cast_nullable_to_non_nullable
                      as int?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$CardSettingsRequestImplCopyWith<$Res>
    implements $CardSettingsRequestCopyWith<$Res> {
  factory _$$CardSettingsRequestImplCopyWith(
    _$CardSettingsRequestImpl value,
    $Res Function(_$CardSettingsRequestImpl) then,
  ) = __$$CardSettingsRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    int? cardId,
    CardSettingsRequestType? requestType,
    bool? requestedValue,
    CardType? requestedCardType,
    RequestStatus? status,
    String? rejectionReason,
    int? requestedById,
  });
}

/// @nodoc
class __$$CardSettingsRequestImplCopyWithImpl<$Res>
    extends _$CardSettingsRequestCopyWithImpl<$Res, _$CardSettingsRequestImpl>
    implements _$$CardSettingsRequestImplCopyWith<$Res> {
  __$$CardSettingsRequestImplCopyWithImpl(
    _$CardSettingsRequestImpl _value,
    $Res Function(_$CardSettingsRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of CardSettingsRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? cardId = freezed,
    Object? requestType = freezed,
    Object? requestedValue = freezed,
    Object? requestedCardType = freezed,
    Object? status = freezed,
    Object? rejectionReason = freezed,
    Object? requestedById = freezed,
  }) {
    return _then(
      _$CardSettingsRequestImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        cardId: freezed == cardId
            ? _value.cardId
            : cardId // ignore: cast_nullable_to_non_nullable
                  as int?,
        requestType: freezed == requestType
            ? _value.requestType
            : requestType // ignore: cast_nullable_to_non_nullable
                  as CardSettingsRequestType?,
        requestedValue: freezed == requestedValue
            ? _value.requestedValue
            : requestedValue // ignore: cast_nullable_to_non_nullable
                  as bool?,
        requestedCardType: freezed == requestedCardType
            ? _value.requestedCardType
            : requestedCardType // ignore: cast_nullable_to_non_nullable
                  as CardType?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as RequestStatus?,
        rejectionReason: freezed == rejectionReason
            ? _value.rejectionReason
            : rejectionReason // ignore: cast_nullable_to_non_nullable
                  as String?,
        requestedById: freezed == requestedById
            ? _value.requestedById
            : requestedById // ignore: cast_nullable_to_non_nullable
                  as int?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$CardSettingsRequestImpl implements _CardSettingsRequest {
  const _$CardSettingsRequestImpl({
    this.id,
    this.cardId,
    this.requestType,
    this.requestedValue,
    this.requestedCardType,
    this.status,
    this.rejectionReason,
    this.requestedById,
  });

  factory _$CardSettingsRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$CardSettingsRequestImplFromJson(json);

  @override
  final int? id;
  @override
  final int? cardId;
  @override
  final CardSettingsRequestType? requestType;
  @override
  final bool? requestedValue;
  @override
  final CardType? requestedCardType;
  @override
  final RequestStatus? status;
  @override
  final String? rejectionReason;
  @override
  final int? requestedById;

  @override
  String toString() {
    return 'CardSettingsRequest(id: $id, cardId: $cardId, requestType: $requestType, requestedValue: $requestedValue, requestedCardType: $requestedCardType, status: $status, rejectionReason: $rejectionReason, requestedById: $requestedById)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CardSettingsRequestImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.cardId, cardId) || other.cardId == cardId) &&
            (identical(other.requestType, requestType) ||
                other.requestType == requestType) &&
            (identical(other.requestedValue, requestedValue) ||
                other.requestedValue == requestedValue) &&
            (identical(other.requestedCardType, requestedCardType) ||
                other.requestedCardType == requestedCardType) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.rejectionReason, rejectionReason) ||
                other.rejectionReason == rejectionReason) &&
            (identical(other.requestedById, requestedById) ||
                other.requestedById == requestedById));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    cardId,
    requestType,
    requestedValue,
    requestedCardType,
    status,
    rejectionReason,
    requestedById,
  );

  /// Create a copy of CardSettingsRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$CardSettingsRequestImplCopyWith<_$CardSettingsRequestImpl> get copyWith =>
      __$$CardSettingsRequestImplCopyWithImpl<_$CardSettingsRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$CardSettingsRequestImplToJson(this);
  }
}

abstract class _CardSettingsRequest implements CardSettingsRequest {
  const factory _CardSettingsRequest({
    final int? id,
    final int? cardId,
    final CardSettingsRequestType? requestType,
    final bool? requestedValue,
    final CardType? requestedCardType,
    final RequestStatus? status,
    final String? rejectionReason,
    final int? requestedById,
  }) = _$CardSettingsRequestImpl;

  factory _CardSettingsRequest.fromJson(Map<String, dynamic> json) =
      _$CardSettingsRequestImpl.fromJson;

  @override
  int? get id;
  @override
  int? get cardId;
  @override
  CardSettingsRequestType? get requestType;
  @override
  bool? get requestedValue;
  @override
  CardType? get requestedCardType;
  @override
  RequestStatus? get status;
  @override
  String? get rejectionReason;
  @override
  int? get requestedById;

  /// Create a copy of CardSettingsRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$CardSettingsRequestImplCopyWith<_$CardSettingsRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

ATMTransactionRequest _$ATMTransactionRequestFromJson(
  Map<String, dynamic> json,
) {
  return _ATMTransactionRequest.fromJson(json);
}

/// @nodoc
mixin _$ATMTransactionRequest {
  int? get atmId => throw _privateConstructorUsedError;
  String? get cardNumber => throw _privateConstructorUsedError;
  ATMTransactionType? get transactionType => throw _privateConstructorUsedError;
  String? get pin => throw _privateConstructorUsedError;
  TransactionRequest? get transactionRequest =>
      throw _privateConstructorUsedError;

  /// Serializes this ATMTransactionRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of ATMTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $ATMTransactionRequestCopyWith<ATMTransactionRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ATMTransactionRequestCopyWith<$Res> {
  factory $ATMTransactionRequestCopyWith(
    ATMTransactionRequest value,
    $Res Function(ATMTransactionRequest) then,
  ) = _$ATMTransactionRequestCopyWithImpl<$Res, ATMTransactionRequest>;
  @useResult
  $Res call({
    int? atmId,
    String? cardNumber,
    ATMTransactionType? transactionType,
    String? pin,
    TransactionRequest? transactionRequest,
  });

  $TransactionRequestCopyWith<$Res>? get transactionRequest;
}

/// @nodoc
class _$ATMTransactionRequestCopyWithImpl<
  $Res,
  $Val extends ATMTransactionRequest
>
    implements $ATMTransactionRequestCopyWith<$Res> {
  _$ATMTransactionRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of ATMTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? atmId = freezed,
    Object? cardNumber = freezed,
    Object? transactionType = freezed,
    Object? pin = freezed,
    Object? transactionRequest = freezed,
  }) {
    return _then(
      _value.copyWith(
            atmId: freezed == atmId
                ? _value.atmId
                : atmId // ignore: cast_nullable_to_non_nullable
                      as int?,
            cardNumber: freezed == cardNumber
                ? _value.cardNumber
                : cardNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            transactionType: freezed == transactionType
                ? _value.transactionType
                : transactionType // ignore: cast_nullable_to_non_nullable
                      as ATMTransactionType?,
            pin: freezed == pin
                ? _value.pin
                : pin // ignore: cast_nullable_to_non_nullable
                      as String?,
            transactionRequest: freezed == transactionRequest
                ? _value.transactionRequest
                : transactionRequest // ignore: cast_nullable_to_non_nullable
                      as TransactionRequest?,
          )
          as $Val,
    );
  }

  /// Create a copy of ATMTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @pragma('vm:prefer-inline')
  $TransactionRequestCopyWith<$Res>? get transactionRequest {
    if (_value.transactionRequest == null) {
      return null;
    }

    return $TransactionRequestCopyWith<$Res>(_value.transactionRequest!, (
      value,
    ) {
      return _then(_value.copyWith(transactionRequest: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$ATMTransactionRequestImplCopyWith<$Res>
    implements $ATMTransactionRequestCopyWith<$Res> {
  factory _$$ATMTransactionRequestImplCopyWith(
    _$ATMTransactionRequestImpl value,
    $Res Function(_$ATMTransactionRequestImpl) then,
  ) = __$$ATMTransactionRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? atmId,
    String? cardNumber,
    ATMTransactionType? transactionType,
    String? pin,
    TransactionRequest? transactionRequest,
  });

  @override
  $TransactionRequestCopyWith<$Res>? get transactionRequest;
}

/// @nodoc
class __$$ATMTransactionRequestImplCopyWithImpl<$Res>
    extends
        _$ATMTransactionRequestCopyWithImpl<$Res, _$ATMTransactionRequestImpl>
    implements _$$ATMTransactionRequestImplCopyWith<$Res> {
  __$$ATMTransactionRequestImplCopyWithImpl(
    _$ATMTransactionRequestImpl _value,
    $Res Function(_$ATMTransactionRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of ATMTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? atmId = freezed,
    Object? cardNumber = freezed,
    Object? transactionType = freezed,
    Object? pin = freezed,
    Object? transactionRequest = freezed,
  }) {
    return _then(
      _$ATMTransactionRequestImpl(
        atmId: freezed == atmId
            ? _value.atmId
            : atmId // ignore: cast_nullable_to_non_nullable
                  as int?,
        cardNumber: freezed == cardNumber
            ? _value.cardNumber
            : cardNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        transactionType: freezed == transactionType
            ? _value.transactionType
            : transactionType // ignore: cast_nullable_to_non_nullable
                  as ATMTransactionType?,
        pin: freezed == pin
            ? _value.pin
            : pin // ignore: cast_nullable_to_non_nullable
                  as String?,
        transactionRequest: freezed == transactionRequest
            ? _value.transactionRequest
            : transactionRequest // ignore: cast_nullable_to_non_nullable
                  as TransactionRequest?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$ATMTransactionRequestImpl implements _ATMTransactionRequest {
  const _$ATMTransactionRequestImpl({
    this.atmId,
    this.cardNumber,
    this.transactionType,
    this.pin,
    this.transactionRequest,
  });

  factory _$ATMTransactionRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$ATMTransactionRequestImplFromJson(json);

  @override
  final int? atmId;
  @override
  final String? cardNumber;
  @override
  final ATMTransactionType? transactionType;
  @override
  final String? pin;
  @override
  final TransactionRequest? transactionRequest;

  @override
  String toString() {
    return 'ATMTransactionRequest(atmId: $atmId, cardNumber: $cardNumber, transactionType: $transactionType, pin: $pin, transactionRequest: $transactionRequest)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ATMTransactionRequestImpl &&
            (identical(other.atmId, atmId) || other.atmId == atmId) &&
            (identical(other.cardNumber, cardNumber) ||
                other.cardNumber == cardNumber) &&
            (identical(other.transactionType, transactionType) ||
                other.transactionType == transactionType) &&
            (identical(other.pin, pin) || other.pin == pin) &&
            (identical(other.transactionRequest, transactionRequest) ||
                other.transactionRequest == transactionRequest));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    atmId,
    cardNumber,
    transactionType,
    pin,
    transactionRequest,
  );

  /// Create a copy of ATMTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$ATMTransactionRequestImplCopyWith<_$ATMTransactionRequestImpl>
  get copyWith =>
      __$$ATMTransactionRequestImplCopyWithImpl<_$ATMTransactionRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$ATMTransactionRequestImplToJson(this);
  }
}

abstract class _ATMTransactionRequest implements ATMTransactionRequest {
  const factory _ATMTransactionRequest({
    final int? atmId,
    final String? cardNumber,
    final ATMTransactionType? transactionType,
    final String? pin,
    final TransactionRequest? transactionRequest,
  }) = _$ATMTransactionRequestImpl;

  factory _ATMTransactionRequest.fromJson(Map<String, dynamic> json) =
      _$ATMTransactionRequestImpl.fromJson;

  @override
  int? get atmId;
  @override
  String? get cardNumber;
  @override
  ATMTransactionType? get transactionType;
  @override
  String? get pin;
  @override
  TransactionRequest? get transactionRequest;

  /// Create a copy of ATMTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$ATMTransactionRequestImplCopyWith<_$ATMTransactionRequestImpl>
  get copyWith => throw _privateConstructorUsedError;
}

ATMTransactionResponse _$ATMTransactionResponseFromJson(
  Map<String, dynamic> json,
) {
  return _ATMTransactionResponse.fromJson(json);
}

/// @nodoc
mixin _$ATMTransactionResponse {
  @JsonKey(name: 'ATMTransactionId')
  int? get atmTransactionId => throw _privateConstructorUsedError;
  ATMTransactionType? get transactionType => throw _privateConstructorUsedError;
  String? get cardNumber => throw _privateConstructorUsedError;
  String? get address => throw _privateConstructorUsedError;
  TransactionResponse? get transactionResponse =>
      throw _privateConstructorUsedError;

  /// Serializes this ATMTransactionResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of ATMTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $ATMTransactionResponseCopyWith<ATMTransactionResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ATMTransactionResponseCopyWith<$Res> {
  factory $ATMTransactionResponseCopyWith(
    ATMTransactionResponse value,
    $Res Function(ATMTransactionResponse) then,
  ) = _$ATMTransactionResponseCopyWithImpl<$Res, ATMTransactionResponse>;
  @useResult
  $Res call({
    @JsonKey(name: 'ATMTransactionId') int? atmTransactionId,
    ATMTransactionType? transactionType,
    String? cardNumber,
    String? address,
    TransactionResponse? transactionResponse,
  });

  $TransactionResponseCopyWith<$Res>? get transactionResponse;
}

/// @nodoc
class _$ATMTransactionResponseCopyWithImpl<
  $Res,
  $Val extends ATMTransactionResponse
>
    implements $ATMTransactionResponseCopyWith<$Res> {
  _$ATMTransactionResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of ATMTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? atmTransactionId = freezed,
    Object? transactionType = freezed,
    Object? cardNumber = freezed,
    Object? address = freezed,
    Object? transactionResponse = freezed,
  }) {
    return _then(
      _value.copyWith(
            atmTransactionId: freezed == atmTransactionId
                ? _value.atmTransactionId
                : atmTransactionId // ignore: cast_nullable_to_non_nullable
                      as int?,
            transactionType: freezed == transactionType
                ? _value.transactionType
                : transactionType // ignore: cast_nullable_to_non_nullable
                      as ATMTransactionType?,
            cardNumber: freezed == cardNumber
                ? _value.cardNumber
                : cardNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            address: freezed == address
                ? _value.address
                : address // ignore: cast_nullable_to_non_nullable
                      as String?,
            transactionResponse: freezed == transactionResponse
                ? _value.transactionResponse
                : transactionResponse // ignore: cast_nullable_to_non_nullable
                      as TransactionResponse?,
          )
          as $Val,
    );
  }

  /// Create a copy of ATMTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @pragma('vm:prefer-inline')
  $TransactionResponseCopyWith<$Res>? get transactionResponse {
    if (_value.transactionResponse == null) {
      return null;
    }

    return $TransactionResponseCopyWith<$Res>(_value.transactionResponse!, (
      value,
    ) {
      return _then(_value.copyWith(transactionResponse: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$ATMTransactionResponseImplCopyWith<$Res>
    implements $ATMTransactionResponseCopyWith<$Res> {
  factory _$$ATMTransactionResponseImplCopyWith(
    _$ATMTransactionResponseImpl value,
    $Res Function(_$ATMTransactionResponseImpl) then,
  ) = __$$ATMTransactionResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    @JsonKey(name: 'ATMTransactionId') int? atmTransactionId,
    ATMTransactionType? transactionType,
    String? cardNumber,
    String? address,
    TransactionResponse? transactionResponse,
  });

  @override
  $TransactionResponseCopyWith<$Res>? get transactionResponse;
}

/// @nodoc
class __$$ATMTransactionResponseImplCopyWithImpl<$Res>
    extends
        _$ATMTransactionResponseCopyWithImpl<$Res, _$ATMTransactionResponseImpl>
    implements _$$ATMTransactionResponseImplCopyWith<$Res> {
  __$$ATMTransactionResponseImplCopyWithImpl(
    _$ATMTransactionResponseImpl _value,
    $Res Function(_$ATMTransactionResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of ATMTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? atmTransactionId = freezed,
    Object? transactionType = freezed,
    Object? cardNumber = freezed,
    Object? address = freezed,
    Object? transactionResponse = freezed,
  }) {
    return _then(
      _$ATMTransactionResponseImpl(
        atmTransactionId: freezed == atmTransactionId
            ? _value.atmTransactionId
            : atmTransactionId // ignore: cast_nullable_to_non_nullable
                  as int?,
        transactionType: freezed == transactionType
            ? _value.transactionType
            : transactionType // ignore: cast_nullable_to_non_nullable
                  as ATMTransactionType?,
        cardNumber: freezed == cardNumber
            ? _value.cardNumber
            : cardNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        address: freezed == address
            ? _value.address
            : address // ignore: cast_nullable_to_non_nullable
                  as String?,
        transactionResponse: freezed == transactionResponse
            ? _value.transactionResponse
            : transactionResponse // ignore: cast_nullable_to_non_nullable
                  as TransactionResponse?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$ATMTransactionResponseImpl implements _ATMTransactionResponse {
  const _$ATMTransactionResponseImpl({
    @JsonKey(name: 'ATMTransactionId') this.atmTransactionId,
    this.transactionType,
    this.cardNumber,
    this.address,
    this.transactionResponse,
  });

  factory _$ATMTransactionResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$ATMTransactionResponseImplFromJson(json);

  @override
  @JsonKey(name: 'ATMTransactionId')
  final int? atmTransactionId;
  @override
  final ATMTransactionType? transactionType;
  @override
  final String? cardNumber;
  @override
  final String? address;
  @override
  final TransactionResponse? transactionResponse;

  @override
  String toString() {
    return 'ATMTransactionResponse(atmTransactionId: $atmTransactionId, transactionType: $transactionType, cardNumber: $cardNumber, address: $address, transactionResponse: $transactionResponse)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ATMTransactionResponseImpl &&
            (identical(other.atmTransactionId, atmTransactionId) ||
                other.atmTransactionId == atmTransactionId) &&
            (identical(other.transactionType, transactionType) ||
                other.transactionType == transactionType) &&
            (identical(other.cardNumber, cardNumber) ||
                other.cardNumber == cardNumber) &&
            (identical(other.address, address) || other.address == address) &&
            (identical(other.transactionResponse, transactionResponse) ||
                other.transactionResponse == transactionResponse));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    atmTransactionId,
    transactionType,
    cardNumber,
    address,
    transactionResponse,
  );

  /// Create a copy of ATMTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$ATMTransactionResponseImplCopyWith<_$ATMTransactionResponseImpl>
  get copyWith =>
      __$$ATMTransactionResponseImplCopyWithImpl<_$ATMTransactionResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$ATMTransactionResponseImplToJson(this);
  }
}

abstract class _ATMTransactionResponse implements ATMTransactionResponse {
  const factory _ATMTransactionResponse({
    @JsonKey(name: 'ATMTransactionId') final int? atmTransactionId,
    final ATMTransactionType? transactionType,
    final String? cardNumber,
    final String? address,
    final TransactionResponse? transactionResponse,
  }) = _$ATMTransactionResponseImpl;

  factory _ATMTransactionResponse.fromJson(Map<String, dynamic> json) =
      _$ATMTransactionResponseImpl.fromJson;

  @override
  @JsonKey(name: 'ATMTransactionId')
  int? get atmTransactionId;
  @override
  ATMTransactionType? get transactionType;
  @override
  String? get cardNumber;
  @override
  String? get address;
  @override
  TransactionResponse? get transactionResponse;

  /// Create a copy of ATMTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$ATMTransactionResponseImplCopyWith<_$ATMTransactionResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}
