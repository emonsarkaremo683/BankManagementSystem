// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'transaction_models.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
  'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models',
);

TransactionRequest _$TransactionRequestFromJson(Map<String, dynamic> json) {
  return _TransactionRequest.fromJson(json);
}

/// @nodoc
mixin _$TransactionRequest {
  double? get amount => throw _privateConstructorUsedError;
  String? get remarks => throw _privateConstructorUsedError;

  /// Serializes this TransactionRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of TransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $TransactionRequestCopyWith<TransactionRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $TransactionRequestCopyWith<$Res> {
  factory $TransactionRequestCopyWith(
    TransactionRequest value,
    $Res Function(TransactionRequest) then,
  ) = _$TransactionRequestCopyWithImpl<$Res, TransactionRequest>;
  @useResult
  $Res call({double? amount, String? remarks});
}

/// @nodoc
class _$TransactionRequestCopyWithImpl<$Res, $Val extends TransactionRequest>
    implements $TransactionRequestCopyWith<$Res> {
  _$TransactionRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of TransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? amount = freezed, Object? remarks = freezed}) {
    return _then(
      _value.copyWith(
            amount: freezed == amount
                ? _value.amount
                : amount // ignore: cast_nullable_to_non_nullable
                      as double?,
            remarks: freezed == remarks
                ? _value.remarks
                : remarks // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$TransactionRequestImplCopyWith<$Res>
    implements $TransactionRequestCopyWith<$Res> {
  factory _$$TransactionRequestImplCopyWith(
    _$TransactionRequestImpl value,
    $Res Function(_$TransactionRequestImpl) then,
  ) = __$$TransactionRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({double? amount, String? remarks});
}

/// @nodoc
class __$$TransactionRequestImplCopyWithImpl<$Res>
    extends _$TransactionRequestCopyWithImpl<$Res, _$TransactionRequestImpl>
    implements _$$TransactionRequestImplCopyWith<$Res> {
  __$$TransactionRequestImplCopyWithImpl(
    _$TransactionRequestImpl _value,
    $Res Function(_$TransactionRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of TransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? amount = freezed, Object? remarks = freezed}) {
    return _then(
      _$TransactionRequestImpl(
        amount: freezed == amount
            ? _value.amount
            : amount // ignore: cast_nullable_to_non_nullable
                  as double?,
        remarks: freezed == remarks
            ? _value.remarks
            : remarks // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$TransactionRequestImpl implements _TransactionRequest {
  const _$TransactionRequestImpl({this.amount, this.remarks});

  factory _$TransactionRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$TransactionRequestImplFromJson(json);

  @override
  final double? amount;
  @override
  final String? remarks;

  @override
  String toString() {
    return 'TransactionRequest(amount: $amount, remarks: $remarks)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TransactionRequestImpl &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.remarks, remarks) || other.remarks == remarks));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, amount, remarks);

  /// Create a copy of TransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$TransactionRequestImplCopyWith<_$TransactionRequestImpl> get copyWith =>
      __$$TransactionRequestImplCopyWithImpl<_$TransactionRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$TransactionRequestImplToJson(this);
  }
}

abstract class _TransactionRequest implements TransactionRequest {
  const factory _TransactionRequest({
    final double? amount,
    final String? remarks,
  }) = _$TransactionRequestImpl;

  factory _TransactionRequest.fromJson(Map<String, dynamic> json) =
      _$TransactionRequestImpl.fromJson;

  @override
  double? get amount;
  @override
  String? get remarks;

  /// Create a copy of TransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$TransactionRequestImplCopyWith<_$TransactionRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

TransactionResponse _$TransactionResponseFromJson(Map<String, dynamic> json) {
  return _TransactionResponse.fromJson(json);
}

/// @nodoc
mixin _$TransactionResponse {
  String? get transactionId => throw _privateConstructorUsedError;
  String? get referenceNo => throw _privateConstructorUsedError;
  TransactionType? get transactionType => throw _privateConstructorUsedError;
  TransactionChannel? get channel => throw _privateConstructorUsedError;
  TransactionStatus? get status => throw _privateConstructorUsedError;
  double? get amount => throw _privateConstructorUsedError;
  double? get chargeAmount => throw _privateConstructorUsedError;
  double? get vatAmount => throw _privateConstructorUsedError;
  String? get remarks => throw _privateConstructorUsedError;
  DateTime? get createdAt => throw _privateConstructorUsedError;
  List<JournalResponse> get journals => throw _privateConstructorUsedError;

  /// Serializes this TransactionResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of TransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $TransactionResponseCopyWith<TransactionResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $TransactionResponseCopyWith<$Res> {
  factory $TransactionResponseCopyWith(
    TransactionResponse value,
    $Res Function(TransactionResponse) then,
  ) = _$TransactionResponseCopyWithImpl<$Res, TransactionResponse>;
  @useResult
  $Res call({
    String? transactionId,
    String? referenceNo,
    TransactionType? transactionType,
    TransactionChannel? channel,
    TransactionStatus? status,
    double? amount,
    double? chargeAmount,
    double? vatAmount,
    String? remarks,
    DateTime? createdAt,
    List<JournalResponse> journals,
  });
}

/// @nodoc
class _$TransactionResponseCopyWithImpl<$Res, $Val extends TransactionResponse>
    implements $TransactionResponseCopyWith<$Res> {
  _$TransactionResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of TransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? transactionId = freezed,
    Object? referenceNo = freezed,
    Object? transactionType = freezed,
    Object? channel = freezed,
    Object? status = freezed,
    Object? amount = freezed,
    Object? chargeAmount = freezed,
    Object? vatAmount = freezed,
    Object? remarks = freezed,
    Object? createdAt = freezed,
    Object? journals = null,
  }) {
    return _then(
      _value.copyWith(
            transactionId: freezed == transactionId
                ? _value.transactionId
                : transactionId // ignore: cast_nullable_to_non_nullable
                      as String?,
            referenceNo: freezed == referenceNo
                ? _value.referenceNo
                : referenceNo // ignore: cast_nullable_to_non_nullable
                      as String?,
            transactionType: freezed == transactionType
                ? _value.transactionType
                : transactionType // ignore: cast_nullable_to_non_nullable
                      as TransactionType?,
            channel: freezed == channel
                ? _value.channel
                : channel // ignore: cast_nullable_to_non_nullable
                      as TransactionChannel?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as TransactionStatus?,
            amount: freezed == amount
                ? _value.amount
                : amount // ignore: cast_nullable_to_non_nullable
                      as double?,
            chargeAmount: freezed == chargeAmount
                ? _value.chargeAmount
                : chargeAmount // ignore: cast_nullable_to_non_nullable
                      as double?,
            vatAmount: freezed == vatAmount
                ? _value.vatAmount
                : vatAmount // ignore: cast_nullable_to_non_nullable
                      as double?,
            remarks: freezed == remarks
                ? _value.remarks
                : remarks // ignore: cast_nullable_to_non_nullable
                      as String?,
            createdAt: freezed == createdAt
                ? _value.createdAt
                : createdAt // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            journals: null == journals
                ? _value.journals
                : journals // ignore: cast_nullable_to_non_nullable
                      as List<JournalResponse>,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$TransactionResponseImplCopyWith<$Res>
    implements $TransactionResponseCopyWith<$Res> {
  factory _$$TransactionResponseImplCopyWith(
    _$TransactionResponseImpl value,
    $Res Function(_$TransactionResponseImpl) then,
  ) = __$$TransactionResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    String? transactionId,
    String? referenceNo,
    TransactionType? transactionType,
    TransactionChannel? channel,
    TransactionStatus? status,
    double? amount,
    double? chargeAmount,
    double? vatAmount,
    String? remarks,
    DateTime? createdAt,
    List<JournalResponse> journals,
  });
}

/// @nodoc
class __$$TransactionResponseImplCopyWithImpl<$Res>
    extends _$TransactionResponseCopyWithImpl<$Res, _$TransactionResponseImpl>
    implements _$$TransactionResponseImplCopyWith<$Res> {
  __$$TransactionResponseImplCopyWithImpl(
    _$TransactionResponseImpl _value,
    $Res Function(_$TransactionResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of TransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? transactionId = freezed,
    Object? referenceNo = freezed,
    Object? transactionType = freezed,
    Object? channel = freezed,
    Object? status = freezed,
    Object? amount = freezed,
    Object? chargeAmount = freezed,
    Object? vatAmount = freezed,
    Object? remarks = freezed,
    Object? createdAt = freezed,
    Object? journals = null,
  }) {
    return _then(
      _$TransactionResponseImpl(
        transactionId: freezed == transactionId
            ? _value.transactionId
            : transactionId // ignore: cast_nullable_to_non_nullable
                  as String?,
        referenceNo: freezed == referenceNo
            ? _value.referenceNo
            : referenceNo // ignore: cast_nullable_to_non_nullable
                  as String?,
        transactionType: freezed == transactionType
            ? _value.transactionType
            : transactionType // ignore: cast_nullable_to_non_nullable
                  as TransactionType?,
        channel: freezed == channel
            ? _value.channel
            : channel // ignore: cast_nullable_to_non_nullable
                  as TransactionChannel?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as TransactionStatus?,
        amount: freezed == amount
            ? _value.amount
            : amount // ignore: cast_nullable_to_non_nullable
                  as double?,
        chargeAmount: freezed == chargeAmount
            ? _value.chargeAmount
            : chargeAmount // ignore: cast_nullable_to_non_nullable
                  as double?,
        vatAmount: freezed == vatAmount
            ? _value.vatAmount
            : vatAmount // ignore: cast_nullable_to_non_nullable
                  as double?,
        remarks: freezed == remarks
            ? _value.remarks
            : remarks // ignore: cast_nullable_to_non_nullable
                  as String?,
        createdAt: freezed == createdAt
            ? _value.createdAt
            : createdAt // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        journals: null == journals
            ? _value._journals
            : journals // ignore: cast_nullable_to_non_nullable
                  as List<JournalResponse>,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$TransactionResponseImpl implements _TransactionResponse {
  const _$TransactionResponseImpl({
    this.transactionId,
    this.referenceNo,
    this.transactionType,
    this.channel,
    this.status,
    this.amount,
    this.chargeAmount,
    this.vatAmount,
    this.remarks,
    this.createdAt,
    final List<JournalResponse> journals = const [],
  }) : _journals = journals;

  factory _$TransactionResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$TransactionResponseImplFromJson(json);

  @override
  final String? transactionId;
  @override
  final String? referenceNo;
  @override
  final TransactionType? transactionType;
  @override
  final TransactionChannel? channel;
  @override
  final TransactionStatus? status;
  @override
  final double? amount;
  @override
  final double? chargeAmount;
  @override
  final double? vatAmount;
  @override
  final String? remarks;
  @override
  final DateTime? createdAt;
  final List<JournalResponse> _journals;
  @override
  @JsonKey()
  List<JournalResponse> get journals {
    if (_journals is EqualUnmodifiableListView) return _journals;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_journals);
  }

  @override
  String toString() {
    return 'TransactionResponse(transactionId: $transactionId, referenceNo: $referenceNo, transactionType: $transactionType, channel: $channel, status: $status, amount: $amount, chargeAmount: $chargeAmount, vatAmount: $vatAmount, remarks: $remarks, createdAt: $createdAt, journals: $journals)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TransactionResponseImpl &&
            (identical(other.transactionId, transactionId) ||
                other.transactionId == transactionId) &&
            (identical(other.referenceNo, referenceNo) ||
                other.referenceNo == referenceNo) &&
            (identical(other.transactionType, transactionType) ||
                other.transactionType == transactionType) &&
            (identical(other.channel, channel) || other.channel == channel) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.chargeAmount, chargeAmount) ||
                other.chargeAmount == chargeAmount) &&
            (identical(other.vatAmount, vatAmount) ||
                other.vatAmount == vatAmount) &&
            (identical(other.remarks, remarks) || other.remarks == remarks) &&
            (identical(other.createdAt, createdAt) ||
                other.createdAt == createdAt) &&
            const DeepCollectionEquality().equals(other._journals, _journals));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    transactionId,
    referenceNo,
    transactionType,
    channel,
    status,
    amount,
    chargeAmount,
    vatAmount,
    remarks,
    createdAt,
    const DeepCollectionEquality().hash(_journals),
  );

  /// Create a copy of TransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$TransactionResponseImplCopyWith<_$TransactionResponseImpl> get copyWith =>
      __$$TransactionResponseImplCopyWithImpl<_$TransactionResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$TransactionResponseImplToJson(this);
  }
}

abstract class _TransactionResponse implements TransactionResponse {
  const factory _TransactionResponse({
    final String? transactionId,
    final String? referenceNo,
    final TransactionType? transactionType,
    final TransactionChannel? channel,
    final TransactionStatus? status,
    final double? amount,
    final double? chargeAmount,
    final double? vatAmount,
    final String? remarks,
    final DateTime? createdAt,
    final List<JournalResponse> journals,
  }) = _$TransactionResponseImpl;

  factory _TransactionResponse.fromJson(Map<String, dynamic> json) =
      _$TransactionResponseImpl.fromJson;

  @override
  String? get transactionId;
  @override
  String? get referenceNo;
  @override
  TransactionType? get transactionType;
  @override
  TransactionChannel? get channel;
  @override
  TransactionStatus? get status;
  @override
  double? get amount;
  @override
  double? get chargeAmount;
  @override
  double? get vatAmount;
  @override
  String? get remarks;
  @override
  DateTime? get createdAt;
  @override
  List<JournalResponse> get journals;

  /// Create a copy of TransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$TransactionResponseImplCopyWith<_$TransactionResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

JournalResponse _$JournalResponseFromJson(Map<String, dynamic> json) {
  return _JournalResponse.fromJson(json);
}

/// @nodoc
mixin _$JournalResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get accountNumber => throw _privateConstructorUsedError;
  double? get amount => throw _privateConstructorUsedError;
  @JsonKey(name: 'entryType')
  String? get type => throw _privateConstructorUsedError; // DEBIT/CREDIT
  String? get remarks => throw _privateConstructorUsedError;
  @JsonKey(name: 'date')
  DateTime? get createdAt => throw _privateConstructorUsedError;
  String? get transactionId => throw _privateConstructorUsedError;
  String? get particulars => throw _privateConstructorUsedError;
  String? get counterpartyAccountNumber => throw _privateConstructorUsedError;
  String? get counterpartyName => throw _privateConstructorUsedError;

  /// Serializes this JournalResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of JournalResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $JournalResponseCopyWith<JournalResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $JournalResponseCopyWith<$Res> {
  factory $JournalResponseCopyWith(
    JournalResponse value,
    $Res Function(JournalResponse) then,
  ) = _$JournalResponseCopyWithImpl<$Res, JournalResponse>;
  @useResult
  $Res call({
    int? id,
    String? accountNumber,
    double? amount,
    @JsonKey(name: 'entryType') String? type,
    String? remarks,
    @JsonKey(name: 'date') DateTime? createdAt,
    String? transactionId,
    String? particulars,
    String? counterpartyAccountNumber,
    String? counterpartyName,
  });
}

/// @nodoc
class _$JournalResponseCopyWithImpl<$Res, $Val extends JournalResponse>
    implements $JournalResponseCopyWith<$Res> {
  _$JournalResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of JournalResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? accountNumber = freezed,
    Object? amount = freezed,
    Object? type = freezed,
    Object? remarks = freezed,
    Object? createdAt = freezed,
    Object? transactionId = freezed,
    Object? particulars = freezed,
    Object? counterpartyAccountNumber = freezed,
    Object? counterpartyName = freezed,
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
            amount: freezed == amount
                ? _value.amount
                : amount // ignore: cast_nullable_to_non_nullable
                      as double?,
            type: freezed == type
                ? _value.type
                : type // ignore: cast_nullable_to_non_nullable
                      as String?,
            remarks: freezed == remarks
                ? _value.remarks
                : remarks // ignore: cast_nullable_to_non_nullable
                      as String?,
            createdAt: freezed == createdAt
                ? _value.createdAt
                : createdAt // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            transactionId: freezed == transactionId
                ? _value.transactionId
                : transactionId // ignore: cast_nullable_to_non_nullable
                      as String?,
            particulars: freezed == particulars
                ? _value.particulars
                : particulars // ignore: cast_nullable_to_non_nullable
                      as String?,
            counterpartyAccountNumber: freezed == counterpartyAccountNumber
                ? _value.counterpartyAccountNumber
                : counterpartyAccountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            counterpartyName: freezed == counterpartyName
                ? _value.counterpartyName
                : counterpartyName // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$JournalResponseImplCopyWith<$Res>
    implements $JournalResponseCopyWith<$Res> {
  factory _$$JournalResponseImplCopyWith(
    _$JournalResponseImpl value,
    $Res Function(_$JournalResponseImpl) then,
  ) = __$$JournalResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    String? accountNumber,
    double? amount,
    @JsonKey(name: 'entryType') String? type,
    String? remarks,
    @JsonKey(name: 'date') DateTime? createdAt,
    String? transactionId,
    String? particulars,
    String? counterpartyAccountNumber,
    String? counterpartyName,
  });
}

/// @nodoc
class __$$JournalResponseImplCopyWithImpl<$Res>
    extends _$JournalResponseCopyWithImpl<$Res, _$JournalResponseImpl>
    implements _$$JournalResponseImplCopyWith<$Res> {
  __$$JournalResponseImplCopyWithImpl(
    _$JournalResponseImpl _value,
    $Res Function(_$JournalResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of JournalResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? accountNumber = freezed,
    Object? amount = freezed,
    Object? type = freezed,
    Object? remarks = freezed,
    Object? createdAt = freezed,
    Object? transactionId = freezed,
    Object? particulars = freezed,
    Object? counterpartyAccountNumber = freezed,
    Object? counterpartyName = freezed,
  }) {
    return _then(
      _$JournalResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        accountNumber: freezed == accountNumber
            ? _value.accountNumber
            : accountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        amount: freezed == amount
            ? _value.amount
            : amount // ignore: cast_nullable_to_non_nullable
                  as double?,
        type: freezed == type
            ? _value.type
            : type // ignore: cast_nullable_to_non_nullable
                  as String?,
        remarks: freezed == remarks
            ? _value.remarks
            : remarks // ignore: cast_nullable_to_non_nullable
                  as String?,
        createdAt: freezed == createdAt
            ? _value.createdAt
            : createdAt // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        transactionId: freezed == transactionId
            ? _value.transactionId
            : transactionId // ignore: cast_nullable_to_non_nullable
                  as String?,
        particulars: freezed == particulars
            ? _value.particulars
            : particulars // ignore: cast_nullable_to_non_nullable
                  as String?,
        counterpartyAccountNumber: freezed == counterpartyAccountNumber
            ? _value.counterpartyAccountNumber
            : counterpartyAccountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        counterpartyName: freezed == counterpartyName
            ? _value.counterpartyName
            : counterpartyName // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$JournalResponseImpl implements _JournalResponse {
  const _$JournalResponseImpl({
    this.id,
    this.accountNumber,
    this.amount,
    @JsonKey(name: 'entryType') this.type,
    this.remarks,
    @JsonKey(name: 'date') this.createdAt,
    this.transactionId,
    this.particulars,
    this.counterpartyAccountNumber,
    this.counterpartyName,
  });

  factory _$JournalResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$JournalResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? accountNumber;
  @override
  final double? amount;
  @override
  @JsonKey(name: 'entryType')
  final String? type;
  // DEBIT/CREDIT
  @override
  final String? remarks;
  @override
  @JsonKey(name: 'date')
  final DateTime? createdAt;
  @override
  final String? transactionId;
  @override
  final String? particulars;
  @override
  final String? counterpartyAccountNumber;
  @override
  final String? counterpartyName;

  @override
  String toString() {
    return 'JournalResponse(id: $id, accountNumber: $accountNumber, amount: $amount, type: $type, remarks: $remarks, createdAt: $createdAt, transactionId: $transactionId, particulars: $particulars, counterpartyAccountNumber: $counterpartyAccountNumber, counterpartyName: $counterpartyName)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$JournalResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.accountNumber, accountNumber) ||
                other.accountNumber == accountNumber) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.remarks, remarks) || other.remarks == remarks) &&
            (identical(other.createdAt, createdAt) ||
                other.createdAt == createdAt) &&
            (identical(other.transactionId, transactionId) ||
                other.transactionId == transactionId) &&
            (identical(other.particulars, particulars) ||
                other.particulars == particulars) &&
            (identical(
                  other.counterpartyAccountNumber,
                  counterpartyAccountNumber,
                ) ||
                other.counterpartyAccountNumber == counterpartyAccountNumber) &&
            (identical(other.counterpartyName, counterpartyName) ||
                other.counterpartyName == counterpartyName));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    accountNumber,
    amount,
    type,
    remarks,
    createdAt,
    transactionId,
    particulars,
    counterpartyAccountNumber,
    counterpartyName,
  );

  /// Create a copy of JournalResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$JournalResponseImplCopyWith<_$JournalResponseImpl> get copyWith =>
      __$$JournalResponseImplCopyWithImpl<_$JournalResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$JournalResponseImplToJson(this);
  }
}

abstract class _JournalResponse implements JournalResponse {
  const factory _JournalResponse({
    final int? id,
    final String? accountNumber,
    final double? amount,
    @JsonKey(name: 'entryType') final String? type,
    final String? remarks,
    @JsonKey(name: 'date') final DateTime? createdAt,
    final String? transactionId,
    final String? particulars,
    final String? counterpartyAccountNumber,
    final String? counterpartyName,
  }) = _$JournalResponseImpl;

  factory _JournalResponse.fromJson(Map<String, dynamic> json) =
      _$JournalResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get accountNumber;
  @override
  double? get amount;
  @override
  @JsonKey(name: 'entryType')
  String? get type; // DEBIT/CREDIT
  @override
  String? get remarks;
  @override
  @JsonKey(name: 'date')
  DateTime? get createdAt;
  @override
  String? get transactionId;
  @override
  String? get particulars;
  @override
  String? get counterpartyAccountNumber;
  @override
  String? get counterpartyName;

  /// Create a copy of JournalResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$JournalResponseImplCopyWith<_$JournalResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

AccountTransactionRequest _$AccountTransactionRequestFromJson(
  Map<String, dynamic> json,
) {
  return _AccountTransactionRequest.fromJson(json);
}

/// @nodoc
mixin _$AccountTransactionRequest {
  int? get senderAccountId => throw _privateConstructorUsedError;
  int? get receiverAccountId => throw _privateConstructorUsedError;
  String? get receiverAccountNumber => throw _privateConstructorUsedError;
  String? get receiverName => throw _privateConstructorUsedError;
  String? get bankName => throw _privateConstructorUsedError;
  String? get routingNumber => throw _privateConstructorUsedError;
  int? get beneficiaryId => throw _privateConstructorUsedError;
  TransactionRequest? get request => throw _privateConstructorUsedError;

  /// Serializes this AccountTransactionRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of AccountTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $AccountTransactionRequestCopyWith<AccountTransactionRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AccountTransactionRequestCopyWith<$Res> {
  factory $AccountTransactionRequestCopyWith(
    AccountTransactionRequest value,
    $Res Function(AccountTransactionRequest) then,
  ) = _$AccountTransactionRequestCopyWithImpl<$Res, AccountTransactionRequest>;
  @useResult
  $Res call({
    int? senderAccountId,
    int? receiverAccountId,
    String? receiverAccountNumber,
    String? receiverName,
    String? bankName,
    String? routingNumber,
    int? beneficiaryId,
    TransactionRequest? request,
  });

  $TransactionRequestCopyWith<$Res>? get request;
}

/// @nodoc
class _$AccountTransactionRequestCopyWithImpl<
  $Res,
  $Val extends AccountTransactionRequest
>
    implements $AccountTransactionRequestCopyWith<$Res> {
  _$AccountTransactionRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of AccountTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? senderAccountId = freezed,
    Object? receiverAccountId = freezed,
    Object? receiverAccountNumber = freezed,
    Object? receiverName = freezed,
    Object? bankName = freezed,
    Object? routingNumber = freezed,
    Object? beneficiaryId = freezed,
    Object? request = freezed,
  }) {
    return _then(
      _value.copyWith(
            senderAccountId: freezed == senderAccountId
                ? _value.senderAccountId
                : senderAccountId // ignore: cast_nullable_to_non_nullable
                      as int?,
            receiverAccountId: freezed == receiverAccountId
                ? _value.receiverAccountId
                : receiverAccountId // ignore: cast_nullable_to_non_nullable
                      as int?,
            receiverAccountNumber: freezed == receiverAccountNumber
                ? _value.receiverAccountNumber
                : receiverAccountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            receiverName: freezed == receiverName
                ? _value.receiverName
                : receiverName // ignore: cast_nullable_to_non_nullable
                      as String?,
            bankName: freezed == bankName
                ? _value.bankName
                : bankName // ignore: cast_nullable_to_non_nullable
                      as String?,
            routingNumber: freezed == routingNumber
                ? _value.routingNumber
                : routingNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            beneficiaryId: freezed == beneficiaryId
                ? _value.beneficiaryId
                : beneficiaryId // ignore: cast_nullable_to_non_nullable
                      as int?,
            request: freezed == request
                ? _value.request
                : request // ignore: cast_nullable_to_non_nullable
                      as TransactionRequest?,
          )
          as $Val,
    );
  }

  /// Create a copy of AccountTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @pragma('vm:prefer-inline')
  $TransactionRequestCopyWith<$Res>? get request {
    if (_value.request == null) {
      return null;
    }

    return $TransactionRequestCopyWith<$Res>(_value.request!, (value) {
      return _then(_value.copyWith(request: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$AccountTransactionRequestImplCopyWith<$Res>
    implements $AccountTransactionRequestCopyWith<$Res> {
  factory _$$AccountTransactionRequestImplCopyWith(
    _$AccountTransactionRequestImpl value,
    $Res Function(_$AccountTransactionRequestImpl) then,
  ) = __$$AccountTransactionRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? senderAccountId,
    int? receiverAccountId,
    String? receiverAccountNumber,
    String? receiverName,
    String? bankName,
    String? routingNumber,
    int? beneficiaryId,
    TransactionRequest? request,
  });

  @override
  $TransactionRequestCopyWith<$Res>? get request;
}

/// @nodoc
class __$$AccountTransactionRequestImplCopyWithImpl<$Res>
    extends
        _$AccountTransactionRequestCopyWithImpl<
          $Res,
          _$AccountTransactionRequestImpl
        >
    implements _$$AccountTransactionRequestImplCopyWith<$Res> {
  __$$AccountTransactionRequestImplCopyWithImpl(
    _$AccountTransactionRequestImpl _value,
    $Res Function(_$AccountTransactionRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of AccountTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? senderAccountId = freezed,
    Object? receiverAccountId = freezed,
    Object? receiverAccountNumber = freezed,
    Object? receiverName = freezed,
    Object? bankName = freezed,
    Object? routingNumber = freezed,
    Object? beneficiaryId = freezed,
    Object? request = freezed,
  }) {
    return _then(
      _$AccountTransactionRequestImpl(
        senderAccountId: freezed == senderAccountId
            ? _value.senderAccountId
            : senderAccountId // ignore: cast_nullable_to_non_nullable
                  as int?,
        receiverAccountId: freezed == receiverAccountId
            ? _value.receiverAccountId
            : receiverAccountId // ignore: cast_nullable_to_non_nullable
                  as int?,
        receiverAccountNumber: freezed == receiverAccountNumber
            ? _value.receiverAccountNumber
            : receiverAccountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        receiverName: freezed == receiverName
            ? _value.receiverName
            : receiverName // ignore: cast_nullable_to_non_nullable
                  as String?,
        bankName: freezed == bankName
            ? _value.bankName
            : bankName // ignore: cast_nullable_to_non_nullable
                  as String?,
        routingNumber: freezed == routingNumber
            ? _value.routingNumber
            : routingNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        beneficiaryId: freezed == beneficiaryId
            ? _value.beneficiaryId
            : beneficiaryId // ignore: cast_nullable_to_non_nullable
                  as int?,
        request: freezed == request
            ? _value.request
            : request // ignore: cast_nullable_to_non_nullable
                  as TransactionRequest?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$AccountTransactionRequestImpl implements _AccountTransactionRequest {
  const _$AccountTransactionRequestImpl({
    this.senderAccountId,
    this.receiverAccountId,
    this.receiverAccountNumber,
    this.receiverName,
    this.bankName,
    this.routingNumber,
    this.beneficiaryId,
    this.request,
  });

  factory _$AccountTransactionRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$AccountTransactionRequestImplFromJson(json);

  @override
  final int? senderAccountId;
  @override
  final int? receiverAccountId;
  @override
  final String? receiverAccountNumber;
  @override
  final String? receiverName;
  @override
  final String? bankName;
  @override
  final String? routingNumber;
  @override
  final int? beneficiaryId;
  @override
  final TransactionRequest? request;

  @override
  String toString() {
    return 'AccountTransactionRequest(senderAccountId: $senderAccountId, receiverAccountId: $receiverAccountId, receiverAccountNumber: $receiverAccountNumber, receiverName: $receiverName, bankName: $bankName, routingNumber: $routingNumber, beneficiaryId: $beneficiaryId, request: $request)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AccountTransactionRequestImpl &&
            (identical(other.senderAccountId, senderAccountId) ||
                other.senderAccountId == senderAccountId) &&
            (identical(other.receiverAccountId, receiverAccountId) ||
                other.receiverAccountId == receiverAccountId) &&
            (identical(other.receiverAccountNumber, receiverAccountNumber) ||
                other.receiverAccountNumber == receiverAccountNumber) &&
            (identical(other.receiverName, receiverName) ||
                other.receiverName == receiverName) &&
            (identical(other.bankName, bankName) ||
                other.bankName == bankName) &&
            (identical(other.routingNumber, routingNumber) ||
                other.routingNumber == routingNumber) &&
            (identical(other.beneficiaryId, beneficiaryId) ||
                other.beneficiaryId == beneficiaryId) &&
            (identical(other.request, request) || other.request == request));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    senderAccountId,
    receiverAccountId,
    receiverAccountNumber,
    receiverName,
    bankName,
    routingNumber,
    beneficiaryId,
    request,
  );

  /// Create a copy of AccountTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$AccountTransactionRequestImplCopyWith<_$AccountTransactionRequestImpl>
  get copyWith =>
      __$$AccountTransactionRequestImplCopyWithImpl<
        _$AccountTransactionRequestImpl
      >(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AccountTransactionRequestImplToJson(this);
  }
}

abstract class _AccountTransactionRequest implements AccountTransactionRequest {
  const factory _AccountTransactionRequest({
    final int? senderAccountId,
    final int? receiverAccountId,
    final String? receiverAccountNumber,
    final String? receiverName,
    final String? bankName,
    final String? routingNumber,
    final int? beneficiaryId,
    final TransactionRequest? request,
  }) = _$AccountTransactionRequestImpl;

  factory _AccountTransactionRequest.fromJson(Map<String, dynamic> json) =
      _$AccountTransactionRequestImpl.fromJson;

  @override
  int? get senderAccountId;
  @override
  int? get receiverAccountId;
  @override
  String? get receiverAccountNumber;
  @override
  String? get receiverName;
  @override
  String? get bankName;
  @override
  String? get routingNumber;
  @override
  int? get beneficiaryId;
  @override
  TransactionRequest? get request;

  /// Create a copy of AccountTransactionRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$AccountTransactionRequestImplCopyWith<_$AccountTransactionRequestImpl>
  get copyWith => throw _privateConstructorUsedError;
}

AccountTransactionResponse _$AccountTransactionResponseFromJson(
  Map<String, dynamic> json,
) {
  return _AccountTransactionResponse.fromJson(json);
}

/// @nodoc
mixin _$AccountTransactionResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get transactionId => throw _privateConstructorUsedError;
  String? get senderAccountNumber => throw _privateConstructorUsedError;
  String? get senderName => throw _privateConstructorUsedError;
  String? get receiverAccountNumber => throw _privateConstructorUsedError;
  String? get receiverName => throw _privateConstructorUsedError;
  String? get bankName => throw _privateConstructorUsedError;
  String? get direction => throw _privateConstructorUsedError;
  TransactionResponse? get response => throw _privateConstructorUsedError;

  /// Serializes this AccountTransactionResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of AccountTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $AccountTransactionResponseCopyWith<AccountTransactionResponse>
  get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AccountTransactionResponseCopyWith<$Res> {
  factory $AccountTransactionResponseCopyWith(
    AccountTransactionResponse value,
    $Res Function(AccountTransactionResponse) then,
  ) =
      _$AccountTransactionResponseCopyWithImpl<
        $Res,
        AccountTransactionResponse
      >;
  @useResult
  $Res call({
    int? id,
    String? transactionId,
    String? senderAccountNumber,
    String? senderName,
    String? receiverAccountNumber,
    String? receiverName,
    String? bankName,
    String? direction,
    TransactionResponse? response,
  });

  $TransactionResponseCopyWith<$Res>? get response;
}

/// @nodoc
class _$AccountTransactionResponseCopyWithImpl<
  $Res,
  $Val extends AccountTransactionResponse
>
    implements $AccountTransactionResponseCopyWith<$Res> {
  _$AccountTransactionResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of AccountTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? transactionId = freezed,
    Object? senderAccountNumber = freezed,
    Object? senderName = freezed,
    Object? receiverAccountNumber = freezed,
    Object? receiverName = freezed,
    Object? bankName = freezed,
    Object? direction = freezed,
    Object? response = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            transactionId: freezed == transactionId
                ? _value.transactionId
                : transactionId // ignore: cast_nullable_to_non_nullable
                      as String?,
            senderAccountNumber: freezed == senderAccountNumber
                ? _value.senderAccountNumber
                : senderAccountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            senderName: freezed == senderName
                ? _value.senderName
                : senderName // ignore: cast_nullable_to_non_nullable
                      as String?,
            receiverAccountNumber: freezed == receiverAccountNumber
                ? _value.receiverAccountNumber
                : receiverAccountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            receiverName: freezed == receiverName
                ? _value.receiverName
                : receiverName // ignore: cast_nullable_to_non_nullable
                      as String?,
            bankName: freezed == bankName
                ? _value.bankName
                : bankName // ignore: cast_nullable_to_non_nullable
                      as String?,
            direction: freezed == direction
                ? _value.direction
                : direction // ignore: cast_nullable_to_non_nullable
                      as String?,
            response: freezed == response
                ? _value.response
                : response // ignore: cast_nullable_to_non_nullable
                      as TransactionResponse?,
          )
          as $Val,
    );
  }

  /// Create a copy of AccountTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @pragma('vm:prefer-inline')
  $TransactionResponseCopyWith<$Res>? get response {
    if (_value.response == null) {
      return null;
    }

    return $TransactionResponseCopyWith<$Res>(_value.response!, (value) {
      return _then(_value.copyWith(response: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$AccountTransactionResponseImplCopyWith<$Res>
    implements $AccountTransactionResponseCopyWith<$Res> {
  factory _$$AccountTransactionResponseImplCopyWith(
    _$AccountTransactionResponseImpl value,
    $Res Function(_$AccountTransactionResponseImpl) then,
  ) = __$$AccountTransactionResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    String? transactionId,
    String? senderAccountNumber,
    String? senderName,
    String? receiverAccountNumber,
    String? receiverName,
    String? bankName,
    String? direction,
    TransactionResponse? response,
  });

  @override
  $TransactionResponseCopyWith<$Res>? get response;
}

/// @nodoc
class __$$AccountTransactionResponseImplCopyWithImpl<$Res>
    extends
        _$AccountTransactionResponseCopyWithImpl<
          $Res,
          _$AccountTransactionResponseImpl
        >
    implements _$$AccountTransactionResponseImplCopyWith<$Res> {
  __$$AccountTransactionResponseImplCopyWithImpl(
    _$AccountTransactionResponseImpl _value,
    $Res Function(_$AccountTransactionResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of AccountTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? transactionId = freezed,
    Object? senderAccountNumber = freezed,
    Object? senderName = freezed,
    Object? receiverAccountNumber = freezed,
    Object? receiverName = freezed,
    Object? bankName = freezed,
    Object? direction = freezed,
    Object? response = freezed,
  }) {
    return _then(
      _$AccountTransactionResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        transactionId: freezed == transactionId
            ? _value.transactionId
            : transactionId // ignore: cast_nullable_to_non_nullable
                  as String?,
        senderAccountNumber: freezed == senderAccountNumber
            ? _value.senderAccountNumber
            : senderAccountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        senderName: freezed == senderName
            ? _value.senderName
            : senderName // ignore: cast_nullable_to_non_nullable
                  as String?,
        receiverAccountNumber: freezed == receiverAccountNumber
            ? _value.receiverAccountNumber
            : receiverAccountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        receiverName: freezed == receiverName
            ? _value.receiverName
            : receiverName // ignore: cast_nullable_to_non_nullable
                  as String?,
        bankName: freezed == bankName
            ? _value.bankName
            : bankName // ignore: cast_nullable_to_non_nullable
                  as String?,
        direction: freezed == direction
            ? _value.direction
            : direction // ignore: cast_nullable_to_non_nullable
                  as String?,
        response: freezed == response
            ? _value.response
            : response // ignore: cast_nullable_to_non_nullable
                  as TransactionResponse?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$AccountTransactionResponseImpl implements _AccountTransactionResponse {
  const _$AccountTransactionResponseImpl({
    this.id,
    this.transactionId,
    this.senderAccountNumber,
    this.senderName,
    this.receiverAccountNumber,
    this.receiverName,
    this.bankName,
    this.direction,
    this.response,
  });

  factory _$AccountTransactionResponseImpl.fromJson(
    Map<String, dynamic> json,
  ) => _$$AccountTransactionResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? transactionId;
  @override
  final String? senderAccountNumber;
  @override
  final String? senderName;
  @override
  final String? receiverAccountNumber;
  @override
  final String? receiverName;
  @override
  final String? bankName;
  @override
  final String? direction;
  @override
  final TransactionResponse? response;

  @override
  String toString() {
    return 'AccountTransactionResponse(id: $id, transactionId: $transactionId, senderAccountNumber: $senderAccountNumber, senderName: $senderName, receiverAccountNumber: $receiverAccountNumber, receiverName: $receiverName, bankName: $bankName, direction: $direction, response: $response)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AccountTransactionResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.transactionId, transactionId) ||
                other.transactionId == transactionId) &&
            (identical(other.senderAccountNumber, senderAccountNumber) ||
                other.senderAccountNumber == senderAccountNumber) &&
            (identical(other.senderName, senderName) ||
                other.senderName == senderName) &&
            (identical(other.receiverAccountNumber, receiverAccountNumber) ||
                other.receiverAccountNumber == receiverAccountNumber) &&
            (identical(other.receiverName, receiverName) ||
                other.receiverName == receiverName) &&
            (identical(other.bankName, bankName) ||
                other.bankName == bankName) &&
            (identical(other.direction, direction) ||
                other.direction == direction) &&
            (identical(other.response, response) ||
                other.response == response));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    transactionId,
    senderAccountNumber,
    senderName,
    receiverAccountNumber,
    receiverName,
    bankName,
    direction,
    response,
  );

  /// Create a copy of AccountTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$AccountTransactionResponseImplCopyWith<_$AccountTransactionResponseImpl>
  get copyWith =>
      __$$AccountTransactionResponseImplCopyWithImpl<
        _$AccountTransactionResponseImpl
      >(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AccountTransactionResponseImplToJson(this);
  }
}

abstract class _AccountTransactionResponse
    implements AccountTransactionResponse {
  const factory _AccountTransactionResponse({
    final int? id,
    final String? transactionId,
    final String? senderAccountNumber,
    final String? senderName,
    final String? receiverAccountNumber,
    final String? receiverName,
    final String? bankName,
    final String? direction,
    final TransactionResponse? response,
  }) = _$AccountTransactionResponseImpl;

  factory _AccountTransactionResponse.fromJson(Map<String, dynamic> json) =
      _$AccountTransactionResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get transactionId;
  @override
  String? get senderAccountNumber;
  @override
  String? get senderName;
  @override
  String? get receiverAccountNumber;
  @override
  String? get receiverName;
  @override
  String? get bankName;
  @override
  String? get direction;
  @override
  TransactionResponse? get response;

  /// Create a copy of AccountTransactionResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$AccountTransactionResponseImplCopyWith<_$AccountTransactionResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}

OtpInitiateResponse _$OtpInitiateResponseFromJson(Map<String, dynamic> json) {
  return _OtpInitiateResponse.fromJson(json);
}

/// @nodoc
mixin _$OtpInitiateResponse {
  int? get otpReferenceId => throw _privateConstructorUsedError;
  String? get maskedEmail => throw _privateConstructorUsedError;
  DateTime? get expiresAt => throw _privateConstructorUsedError;

  /// Serializes this OtpInitiateResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of OtpInitiateResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $OtpInitiateResponseCopyWith<OtpInitiateResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $OtpInitiateResponseCopyWith<$Res> {
  factory $OtpInitiateResponseCopyWith(
    OtpInitiateResponse value,
    $Res Function(OtpInitiateResponse) then,
  ) = _$OtpInitiateResponseCopyWithImpl<$Res, OtpInitiateResponse>;
  @useResult
  $Res call({int? otpReferenceId, String? maskedEmail, DateTime? expiresAt});
}

/// @nodoc
class _$OtpInitiateResponseCopyWithImpl<$Res, $Val extends OtpInitiateResponse>
    implements $OtpInitiateResponseCopyWith<$Res> {
  _$OtpInitiateResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of OtpInitiateResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? otpReferenceId = freezed,
    Object? maskedEmail = freezed,
    Object? expiresAt = freezed,
  }) {
    return _then(
      _value.copyWith(
            otpReferenceId: freezed == otpReferenceId
                ? _value.otpReferenceId
                : otpReferenceId // ignore: cast_nullable_to_non_nullable
                      as int?,
            maskedEmail: freezed == maskedEmail
                ? _value.maskedEmail
                : maskedEmail // ignore: cast_nullable_to_non_nullable
                      as String?,
            expiresAt: freezed == expiresAt
                ? _value.expiresAt
                : expiresAt // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$OtpInitiateResponseImplCopyWith<$Res>
    implements $OtpInitiateResponseCopyWith<$Res> {
  factory _$$OtpInitiateResponseImplCopyWith(
    _$OtpInitiateResponseImpl value,
    $Res Function(_$OtpInitiateResponseImpl) then,
  ) = __$$OtpInitiateResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({int? otpReferenceId, String? maskedEmail, DateTime? expiresAt});
}

/// @nodoc
class __$$OtpInitiateResponseImplCopyWithImpl<$Res>
    extends _$OtpInitiateResponseCopyWithImpl<$Res, _$OtpInitiateResponseImpl>
    implements _$$OtpInitiateResponseImplCopyWith<$Res> {
  __$$OtpInitiateResponseImplCopyWithImpl(
    _$OtpInitiateResponseImpl _value,
    $Res Function(_$OtpInitiateResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of OtpInitiateResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? otpReferenceId = freezed,
    Object? maskedEmail = freezed,
    Object? expiresAt = freezed,
  }) {
    return _then(
      _$OtpInitiateResponseImpl(
        otpReferenceId: freezed == otpReferenceId
            ? _value.otpReferenceId
            : otpReferenceId // ignore: cast_nullable_to_non_nullable
                  as int?,
        maskedEmail: freezed == maskedEmail
            ? _value.maskedEmail
            : maskedEmail // ignore: cast_nullable_to_non_nullable
                  as String?,
        expiresAt: freezed == expiresAt
            ? _value.expiresAt
            : expiresAt // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$OtpInitiateResponseImpl implements _OtpInitiateResponse {
  const _$OtpInitiateResponseImpl({
    this.otpReferenceId,
    this.maskedEmail,
    this.expiresAt,
  });

  factory _$OtpInitiateResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$OtpInitiateResponseImplFromJson(json);

  @override
  final int? otpReferenceId;
  @override
  final String? maskedEmail;
  @override
  final DateTime? expiresAt;

  @override
  String toString() {
    return 'OtpInitiateResponse(otpReferenceId: $otpReferenceId, maskedEmail: $maskedEmail, expiresAt: $expiresAt)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OtpInitiateResponseImpl &&
            (identical(other.otpReferenceId, otpReferenceId) ||
                other.otpReferenceId == otpReferenceId) &&
            (identical(other.maskedEmail, maskedEmail) ||
                other.maskedEmail == maskedEmail) &&
            (identical(other.expiresAt, expiresAt) ||
                other.expiresAt == expiresAt));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode =>
      Object.hash(runtimeType, otpReferenceId, maskedEmail, expiresAt);

  /// Create a copy of OtpInitiateResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$OtpInitiateResponseImplCopyWith<_$OtpInitiateResponseImpl> get copyWith =>
      __$$OtpInitiateResponseImplCopyWithImpl<_$OtpInitiateResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$OtpInitiateResponseImplToJson(this);
  }
}

abstract class _OtpInitiateResponse implements OtpInitiateResponse {
  const factory _OtpInitiateResponse({
    final int? otpReferenceId,
    final String? maskedEmail,
    final DateTime? expiresAt,
  }) = _$OtpInitiateResponseImpl;

  factory _OtpInitiateResponse.fromJson(Map<String, dynamic> json) =
      _$OtpInitiateResponseImpl.fromJson;

  @override
  int? get otpReferenceId;
  @override
  String? get maskedEmail;
  @override
  DateTime? get expiresAt;

  /// Create a copy of OtpInitiateResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$OtpInitiateResponseImplCopyWith<_$OtpInitiateResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

OtpVerifyRequest _$OtpVerifyRequestFromJson(Map<String, dynamic> json) {
  return _OtpVerifyRequest.fromJson(json);
}

/// @nodoc
mixin _$OtpVerifyRequest {
  int? get otpReferenceId => throw _privateConstructorUsedError;
  String? get otpCode => throw _privateConstructorUsedError;

  /// Serializes this OtpVerifyRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of OtpVerifyRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $OtpVerifyRequestCopyWith<OtpVerifyRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $OtpVerifyRequestCopyWith<$Res> {
  factory $OtpVerifyRequestCopyWith(
    OtpVerifyRequest value,
    $Res Function(OtpVerifyRequest) then,
  ) = _$OtpVerifyRequestCopyWithImpl<$Res, OtpVerifyRequest>;
  @useResult
  $Res call({int? otpReferenceId, String? otpCode});
}

/// @nodoc
class _$OtpVerifyRequestCopyWithImpl<$Res, $Val extends OtpVerifyRequest>
    implements $OtpVerifyRequestCopyWith<$Res> {
  _$OtpVerifyRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of OtpVerifyRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? otpReferenceId = freezed, Object? otpCode = freezed}) {
    return _then(
      _value.copyWith(
            otpReferenceId: freezed == otpReferenceId
                ? _value.otpReferenceId
                : otpReferenceId // ignore: cast_nullable_to_non_nullable
                      as int?,
            otpCode: freezed == otpCode
                ? _value.otpCode
                : otpCode // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$OtpVerifyRequestImplCopyWith<$Res>
    implements $OtpVerifyRequestCopyWith<$Res> {
  factory _$$OtpVerifyRequestImplCopyWith(
    _$OtpVerifyRequestImpl value,
    $Res Function(_$OtpVerifyRequestImpl) then,
  ) = __$$OtpVerifyRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({int? otpReferenceId, String? otpCode});
}

/// @nodoc
class __$$OtpVerifyRequestImplCopyWithImpl<$Res>
    extends _$OtpVerifyRequestCopyWithImpl<$Res, _$OtpVerifyRequestImpl>
    implements _$$OtpVerifyRequestImplCopyWith<$Res> {
  __$$OtpVerifyRequestImplCopyWithImpl(
    _$OtpVerifyRequestImpl _value,
    $Res Function(_$OtpVerifyRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of OtpVerifyRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? otpReferenceId = freezed, Object? otpCode = freezed}) {
    return _then(
      _$OtpVerifyRequestImpl(
        otpReferenceId: freezed == otpReferenceId
            ? _value.otpReferenceId
            : otpReferenceId // ignore: cast_nullable_to_non_nullable
                  as int?,
        otpCode: freezed == otpCode
            ? _value.otpCode
            : otpCode // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$OtpVerifyRequestImpl implements _OtpVerifyRequest {
  const _$OtpVerifyRequestImpl({this.otpReferenceId, this.otpCode});

  factory _$OtpVerifyRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$OtpVerifyRequestImplFromJson(json);

  @override
  final int? otpReferenceId;
  @override
  final String? otpCode;

  @override
  String toString() {
    return 'OtpVerifyRequest(otpReferenceId: $otpReferenceId, otpCode: $otpCode)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OtpVerifyRequestImpl &&
            (identical(other.otpReferenceId, otpReferenceId) ||
                other.otpReferenceId == otpReferenceId) &&
            (identical(other.otpCode, otpCode) || other.otpCode == otpCode));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, otpReferenceId, otpCode);

  /// Create a copy of OtpVerifyRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$OtpVerifyRequestImplCopyWith<_$OtpVerifyRequestImpl> get copyWith =>
      __$$OtpVerifyRequestImplCopyWithImpl<_$OtpVerifyRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$OtpVerifyRequestImplToJson(this);
  }
}

abstract class _OtpVerifyRequest implements OtpVerifyRequest {
  const factory _OtpVerifyRequest({
    final int? otpReferenceId,
    final String? otpCode,
  }) = _$OtpVerifyRequestImpl;

  factory _OtpVerifyRequest.fromJson(Map<String, dynamic> json) =
      _$OtpVerifyRequestImpl.fromJson;

  @override
  int? get otpReferenceId;
  @override
  String? get otpCode;

  /// Create a copy of OtpVerifyRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$OtpVerifyRequestImplCopyWith<_$OtpVerifyRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

BalanceCheckRequest _$BalanceCheckRequestFromJson(Map<String, dynamic> json) {
  return _BalanceCheckRequest.fromJson(json);
}

/// @nodoc
mixin _$BalanceCheckRequest {
  String? get cardNumber => throw _privateConstructorUsedError;
  String? get pin => throw _privateConstructorUsedError;

  /// Serializes this BalanceCheckRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of BalanceCheckRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $BalanceCheckRequestCopyWith<BalanceCheckRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $BalanceCheckRequestCopyWith<$Res> {
  factory $BalanceCheckRequestCopyWith(
    BalanceCheckRequest value,
    $Res Function(BalanceCheckRequest) then,
  ) = _$BalanceCheckRequestCopyWithImpl<$Res, BalanceCheckRequest>;
  @useResult
  $Res call({String? cardNumber, String? pin});
}

/// @nodoc
class _$BalanceCheckRequestCopyWithImpl<$Res, $Val extends BalanceCheckRequest>
    implements $BalanceCheckRequestCopyWith<$Res> {
  _$BalanceCheckRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of BalanceCheckRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? cardNumber = freezed, Object? pin = freezed}) {
    return _then(
      _value.copyWith(
            cardNumber: freezed == cardNumber
                ? _value.cardNumber
                : cardNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            pin: freezed == pin
                ? _value.pin
                : pin // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$BalanceCheckRequestImplCopyWith<$Res>
    implements $BalanceCheckRequestCopyWith<$Res> {
  factory _$$BalanceCheckRequestImplCopyWith(
    _$BalanceCheckRequestImpl value,
    $Res Function(_$BalanceCheckRequestImpl) then,
  ) = __$$BalanceCheckRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? cardNumber, String? pin});
}

/// @nodoc
class __$$BalanceCheckRequestImplCopyWithImpl<$Res>
    extends _$BalanceCheckRequestCopyWithImpl<$Res, _$BalanceCheckRequestImpl>
    implements _$$BalanceCheckRequestImplCopyWith<$Res> {
  __$$BalanceCheckRequestImplCopyWithImpl(
    _$BalanceCheckRequestImpl _value,
    $Res Function(_$BalanceCheckRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of BalanceCheckRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? cardNumber = freezed, Object? pin = freezed}) {
    return _then(
      _$BalanceCheckRequestImpl(
        cardNumber: freezed == cardNumber
            ? _value.cardNumber
            : cardNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        pin: freezed == pin
            ? _value.pin
            : pin // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$BalanceCheckRequestImpl implements _BalanceCheckRequest {
  const _$BalanceCheckRequestImpl({this.cardNumber, this.pin});

  factory _$BalanceCheckRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$BalanceCheckRequestImplFromJson(json);

  @override
  final String? cardNumber;
  @override
  final String? pin;

  @override
  String toString() {
    return 'BalanceCheckRequest(cardNumber: $cardNumber, pin: $pin)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$BalanceCheckRequestImpl &&
            (identical(other.cardNumber, cardNumber) ||
                other.cardNumber == cardNumber) &&
            (identical(other.pin, pin) || other.pin == pin));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, cardNumber, pin);

  /// Create a copy of BalanceCheckRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$BalanceCheckRequestImplCopyWith<_$BalanceCheckRequestImpl> get copyWith =>
      __$$BalanceCheckRequestImplCopyWithImpl<_$BalanceCheckRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$BalanceCheckRequestImplToJson(this);
  }
}

abstract class _BalanceCheckRequest implements BalanceCheckRequest {
  const factory _BalanceCheckRequest({
    final String? cardNumber,
    final String? pin,
  }) = _$BalanceCheckRequestImpl;

  factory _BalanceCheckRequest.fromJson(Map<String, dynamic> json) =
      _$BalanceCheckRequestImpl.fromJson;

  @override
  String? get cardNumber;
  @override
  String? get pin;

  /// Create a copy of BalanceCheckRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$BalanceCheckRequestImplCopyWith<_$BalanceCheckRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
