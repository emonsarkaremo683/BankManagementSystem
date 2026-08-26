// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'other_models.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
  'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models',
);

ChequeBookRequest _$ChequeBookRequestFromJson(Map<String, dynamic> json) {
  return _ChequeBookRequest.fromJson(json);
}

/// @nodoc
mixin _$ChequeBookRequest {
  int? get accountId => throw _privateConstructorUsedError;
  int? get numberOfLeaves => throw _privateConstructorUsedError;

  /// Serializes this ChequeBookRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of ChequeBookRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $ChequeBookRequestCopyWith<ChequeBookRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ChequeBookRequestCopyWith<$Res> {
  factory $ChequeBookRequestCopyWith(
    ChequeBookRequest value,
    $Res Function(ChequeBookRequest) then,
  ) = _$ChequeBookRequestCopyWithImpl<$Res, ChequeBookRequest>;
  @useResult
  $Res call({int? accountId, int? numberOfLeaves});
}

/// @nodoc
class _$ChequeBookRequestCopyWithImpl<$Res, $Val extends ChequeBookRequest>
    implements $ChequeBookRequestCopyWith<$Res> {
  _$ChequeBookRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of ChequeBookRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? accountId = freezed, Object? numberOfLeaves = freezed}) {
    return _then(
      _value.copyWith(
            accountId: freezed == accountId
                ? _value.accountId
                : accountId // ignore: cast_nullable_to_non_nullable
                      as int?,
            numberOfLeaves: freezed == numberOfLeaves
                ? _value.numberOfLeaves
                : numberOfLeaves // ignore: cast_nullable_to_non_nullable
                      as int?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$ChequeBookRequestImplCopyWith<$Res>
    implements $ChequeBookRequestCopyWith<$Res> {
  factory _$$ChequeBookRequestImplCopyWith(
    _$ChequeBookRequestImpl value,
    $Res Function(_$ChequeBookRequestImpl) then,
  ) = __$$ChequeBookRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({int? accountId, int? numberOfLeaves});
}

/// @nodoc
class __$$ChequeBookRequestImplCopyWithImpl<$Res>
    extends _$ChequeBookRequestCopyWithImpl<$Res, _$ChequeBookRequestImpl>
    implements _$$ChequeBookRequestImplCopyWith<$Res> {
  __$$ChequeBookRequestImplCopyWithImpl(
    _$ChequeBookRequestImpl _value,
    $Res Function(_$ChequeBookRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of ChequeBookRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? accountId = freezed, Object? numberOfLeaves = freezed}) {
    return _then(
      _$ChequeBookRequestImpl(
        accountId: freezed == accountId
            ? _value.accountId
            : accountId // ignore: cast_nullable_to_non_nullable
                  as int?,
        numberOfLeaves: freezed == numberOfLeaves
            ? _value.numberOfLeaves
            : numberOfLeaves // ignore: cast_nullable_to_non_nullable
                  as int?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$ChequeBookRequestImpl implements _ChequeBookRequest {
  const _$ChequeBookRequestImpl({this.accountId, this.numberOfLeaves});

  factory _$ChequeBookRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$ChequeBookRequestImplFromJson(json);

  @override
  final int? accountId;
  @override
  final int? numberOfLeaves;

  @override
  String toString() {
    return 'ChequeBookRequest(accountId: $accountId, numberOfLeaves: $numberOfLeaves)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ChequeBookRequestImpl &&
            (identical(other.accountId, accountId) ||
                other.accountId == accountId) &&
            (identical(other.numberOfLeaves, numberOfLeaves) ||
                other.numberOfLeaves == numberOfLeaves));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, accountId, numberOfLeaves);

  /// Create a copy of ChequeBookRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$ChequeBookRequestImplCopyWith<_$ChequeBookRequestImpl> get copyWith =>
      __$$ChequeBookRequestImplCopyWithImpl<_$ChequeBookRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$ChequeBookRequestImplToJson(this);
  }
}

abstract class _ChequeBookRequest implements ChequeBookRequest {
  const factory _ChequeBookRequest({
    final int? accountId,
    final int? numberOfLeaves,
  }) = _$ChequeBookRequestImpl;

  factory _ChequeBookRequest.fromJson(Map<String, dynamic> json) =
      _$ChequeBookRequestImpl.fromJson;

  @override
  int? get accountId;
  @override
  int? get numberOfLeaves;

  /// Create a copy of ChequeBookRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$ChequeBookRequestImplCopyWith<_$ChequeBookRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

ChequeBookResponse _$ChequeBookResponseFromJson(Map<String, dynamic> json) {
  return _ChequeBookResponse.fromJson(json);
}

/// @nodoc
mixin _$ChequeBookResponse {
  int? get chequeBookId => throw _privateConstructorUsedError;
  String? get bookSerialNumber => throw _privateConstructorUsedError;
  int? get numberOfLeaves => throw _privateConstructorUsedError;
  int? get startLeafNumber => throw _privateConstructorUsedError;
  int? get endLeafNumber => throw _privateConstructorUsedError;
  ChequeBookStatus? get status => throw _privateConstructorUsedError;
  int? get accountId => throw _privateConstructorUsedError;
  String? get accountNumber => throw _privateConstructorUsedError;
  DateTime? get applicationDate => throw _privateConstructorUsedError;
  DateTime? get approvalDate => throw _privateConstructorUsedError;
  DateTime? get deliveryDate => throw _privateConstructorUsedError;
  DateTime? get activationDate => throw _privateConstructorUsedError;
  DateTime? get expiryDate => throw _privateConstructorUsedError;
  String? get rejectionReason => throw _privateConstructorUsedError;
  List<ChequeLeafResponse>? get leaves => throw _privateConstructorUsedError;

  /// Serializes this ChequeBookResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of ChequeBookResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $ChequeBookResponseCopyWith<ChequeBookResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ChequeBookResponseCopyWith<$Res> {
  factory $ChequeBookResponseCopyWith(
    ChequeBookResponse value,
    $Res Function(ChequeBookResponse) then,
  ) = _$ChequeBookResponseCopyWithImpl<$Res, ChequeBookResponse>;
  @useResult
  $Res call({
    int? chequeBookId,
    String? bookSerialNumber,
    int? numberOfLeaves,
    int? startLeafNumber,
    int? endLeafNumber,
    ChequeBookStatus? status,
    int? accountId,
    String? accountNumber,
    DateTime? applicationDate,
    DateTime? approvalDate,
    DateTime? deliveryDate,
    DateTime? activationDate,
    DateTime? expiryDate,
    String? rejectionReason,
    List<ChequeLeafResponse>? leaves,
  });
}

/// @nodoc
class _$ChequeBookResponseCopyWithImpl<$Res, $Val extends ChequeBookResponse>
    implements $ChequeBookResponseCopyWith<$Res> {
  _$ChequeBookResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of ChequeBookResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? chequeBookId = freezed,
    Object? bookSerialNumber = freezed,
    Object? numberOfLeaves = freezed,
    Object? startLeafNumber = freezed,
    Object? endLeafNumber = freezed,
    Object? status = freezed,
    Object? accountId = freezed,
    Object? accountNumber = freezed,
    Object? applicationDate = freezed,
    Object? approvalDate = freezed,
    Object? deliveryDate = freezed,
    Object? activationDate = freezed,
    Object? expiryDate = freezed,
    Object? rejectionReason = freezed,
    Object? leaves = freezed,
  }) {
    return _then(
      _value.copyWith(
            chequeBookId: freezed == chequeBookId
                ? _value.chequeBookId
                : chequeBookId // ignore: cast_nullable_to_non_nullable
                      as int?,
            bookSerialNumber: freezed == bookSerialNumber
                ? _value.bookSerialNumber
                : bookSerialNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            numberOfLeaves: freezed == numberOfLeaves
                ? _value.numberOfLeaves
                : numberOfLeaves // ignore: cast_nullable_to_non_nullable
                      as int?,
            startLeafNumber: freezed == startLeafNumber
                ? _value.startLeafNumber
                : startLeafNumber // ignore: cast_nullable_to_non_nullable
                      as int?,
            endLeafNumber: freezed == endLeafNumber
                ? _value.endLeafNumber
                : endLeafNumber // ignore: cast_nullable_to_non_nullable
                      as int?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as ChequeBookStatus?,
            accountId: freezed == accountId
                ? _value.accountId
                : accountId // ignore: cast_nullable_to_non_nullable
                      as int?,
            accountNumber: freezed == accountNumber
                ? _value.accountNumber
                : accountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            applicationDate: freezed == applicationDate
                ? _value.applicationDate
                : applicationDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            approvalDate: freezed == approvalDate
                ? _value.approvalDate
                : approvalDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            deliveryDate: freezed == deliveryDate
                ? _value.deliveryDate
                : deliveryDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            activationDate: freezed == activationDate
                ? _value.activationDate
                : activationDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            expiryDate: freezed == expiryDate
                ? _value.expiryDate
                : expiryDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            rejectionReason: freezed == rejectionReason
                ? _value.rejectionReason
                : rejectionReason // ignore: cast_nullable_to_non_nullable
                      as String?,
            leaves: freezed == leaves
                ? _value.leaves
                : leaves // ignore: cast_nullable_to_non_nullable
                      as List<ChequeLeafResponse>?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$ChequeBookResponseImplCopyWith<$Res>
    implements $ChequeBookResponseCopyWith<$Res> {
  factory _$$ChequeBookResponseImplCopyWith(
    _$ChequeBookResponseImpl value,
    $Res Function(_$ChequeBookResponseImpl) then,
  ) = __$$ChequeBookResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? chequeBookId,
    String? bookSerialNumber,
    int? numberOfLeaves,
    int? startLeafNumber,
    int? endLeafNumber,
    ChequeBookStatus? status,
    int? accountId,
    String? accountNumber,
    DateTime? applicationDate,
    DateTime? approvalDate,
    DateTime? deliveryDate,
    DateTime? activationDate,
    DateTime? expiryDate,
    String? rejectionReason,
    List<ChequeLeafResponse>? leaves,
  });
}

/// @nodoc
class __$$ChequeBookResponseImplCopyWithImpl<$Res>
    extends _$ChequeBookResponseCopyWithImpl<$Res, _$ChequeBookResponseImpl>
    implements _$$ChequeBookResponseImplCopyWith<$Res> {
  __$$ChequeBookResponseImplCopyWithImpl(
    _$ChequeBookResponseImpl _value,
    $Res Function(_$ChequeBookResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of ChequeBookResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? chequeBookId = freezed,
    Object? bookSerialNumber = freezed,
    Object? numberOfLeaves = freezed,
    Object? startLeafNumber = freezed,
    Object? endLeafNumber = freezed,
    Object? status = freezed,
    Object? accountId = freezed,
    Object? accountNumber = freezed,
    Object? applicationDate = freezed,
    Object? approvalDate = freezed,
    Object? deliveryDate = freezed,
    Object? activationDate = freezed,
    Object? expiryDate = freezed,
    Object? rejectionReason = freezed,
    Object? leaves = freezed,
  }) {
    return _then(
      _$ChequeBookResponseImpl(
        chequeBookId: freezed == chequeBookId
            ? _value.chequeBookId
            : chequeBookId // ignore: cast_nullable_to_non_nullable
                  as int?,
        bookSerialNumber: freezed == bookSerialNumber
            ? _value.bookSerialNumber
            : bookSerialNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        numberOfLeaves: freezed == numberOfLeaves
            ? _value.numberOfLeaves
            : numberOfLeaves // ignore: cast_nullable_to_non_nullable
                  as int?,
        startLeafNumber: freezed == startLeafNumber
            ? _value.startLeafNumber
            : startLeafNumber // ignore: cast_nullable_to_non_nullable
                  as int?,
        endLeafNumber: freezed == endLeafNumber
            ? _value.endLeafNumber
            : endLeafNumber // ignore: cast_nullable_to_non_nullable
                  as int?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as ChequeBookStatus?,
        accountId: freezed == accountId
            ? _value.accountId
            : accountId // ignore: cast_nullable_to_non_nullable
                  as int?,
        accountNumber: freezed == accountNumber
            ? _value.accountNumber
            : accountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        applicationDate: freezed == applicationDate
            ? _value.applicationDate
            : applicationDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        approvalDate: freezed == approvalDate
            ? _value.approvalDate
            : approvalDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        deliveryDate: freezed == deliveryDate
            ? _value.deliveryDate
            : deliveryDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        activationDate: freezed == activationDate
            ? _value.activationDate
            : activationDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        expiryDate: freezed == expiryDate
            ? _value.expiryDate
            : expiryDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        rejectionReason: freezed == rejectionReason
            ? _value.rejectionReason
            : rejectionReason // ignore: cast_nullable_to_non_nullable
                  as String?,
        leaves: freezed == leaves
            ? _value._leaves
            : leaves // ignore: cast_nullable_to_non_nullable
                  as List<ChequeLeafResponse>?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$ChequeBookResponseImpl implements _ChequeBookResponse {
  const _$ChequeBookResponseImpl({
    this.chequeBookId,
    this.bookSerialNumber,
    this.numberOfLeaves,
    this.startLeafNumber,
    this.endLeafNumber,
    this.status,
    this.accountId,
    this.accountNumber,
    this.applicationDate,
    this.approvalDate,
    this.deliveryDate,
    this.activationDate,
    this.expiryDate,
    this.rejectionReason,
    final List<ChequeLeafResponse>? leaves,
  }) : _leaves = leaves;

  factory _$ChequeBookResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$ChequeBookResponseImplFromJson(json);

  @override
  final int? chequeBookId;
  @override
  final String? bookSerialNumber;
  @override
  final int? numberOfLeaves;
  @override
  final int? startLeafNumber;
  @override
  final int? endLeafNumber;
  @override
  final ChequeBookStatus? status;
  @override
  final int? accountId;
  @override
  final String? accountNumber;
  @override
  final DateTime? applicationDate;
  @override
  final DateTime? approvalDate;
  @override
  final DateTime? deliveryDate;
  @override
  final DateTime? activationDate;
  @override
  final DateTime? expiryDate;
  @override
  final String? rejectionReason;
  final List<ChequeLeafResponse>? _leaves;
  @override
  List<ChequeLeafResponse>? get leaves {
    final value = _leaves;
    if (value == null) return null;
    if (_leaves is EqualUnmodifiableListView) return _leaves;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'ChequeBookResponse(chequeBookId: $chequeBookId, bookSerialNumber: $bookSerialNumber, numberOfLeaves: $numberOfLeaves, startLeafNumber: $startLeafNumber, endLeafNumber: $endLeafNumber, status: $status, accountId: $accountId, accountNumber: $accountNumber, applicationDate: $applicationDate, approvalDate: $approvalDate, deliveryDate: $deliveryDate, activationDate: $activationDate, expiryDate: $expiryDate, rejectionReason: $rejectionReason, leaves: $leaves)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ChequeBookResponseImpl &&
            (identical(other.chequeBookId, chequeBookId) ||
                other.chequeBookId == chequeBookId) &&
            (identical(other.bookSerialNumber, bookSerialNumber) ||
                other.bookSerialNumber == bookSerialNumber) &&
            (identical(other.numberOfLeaves, numberOfLeaves) ||
                other.numberOfLeaves == numberOfLeaves) &&
            (identical(other.startLeafNumber, startLeafNumber) ||
                other.startLeafNumber == startLeafNumber) &&
            (identical(other.endLeafNumber, endLeafNumber) ||
                other.endLeafNumber == endLeafNumber) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.accountId, accountId) ||
                other.accountId == accountId) &&
            (identical(other.accountNumber, accountNumber) ||
                other.accountNumber == accountNumber) &&
            (identical(other.applicationDate, applicationDate) ||
                other.applicationDate == applicationDate) &&
            (identical(other.approvalDate, approvalDate) ||
                other.approvalDate == approvalDate) &&
            (identical(other.deliveryDate, deliveryDate) ||
                other.deliveryDate == deliveryDate) &&
            (identical(other.activationDate, activationDate) ||
                other.activationDate == activationDate) &&
            (identical(other.expiryDate, expiryDate) ||
                other.expiryDate == expiryDate) &&
            (identical(other.rejectionReason, rejectionReason) ||
                other.rejectionReason == rejectionReason) &&
            const DeepCollectionEquality().equals(other._leaves, _leaves));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    chequeBookId,
    bookSerialNumber,
    numberOfLeaves,
    startLeafNumber,
    endLeafNumber,
    status,
    accountId,
    accountNumber,
    applicationDate,
    approvalDate,
    deliveryDate,
    activationDate,
    expiryDate,
    rejectionReason,
    const DeepCollectionEquality().hash(_leaves),
  );

  /// Create a copy of ChequeBookResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$ChequeBookResponseImplCopyWith<_$ChequeBookResponseImpl> get copyWith =>
      __$$ChequeBookResponseImplCopyWithImpl<_$ChequeBookResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$ChequeBookResponseImplToJson(this);
  }
}

abstract class _ChequeBookResponse implements ChequeBookResponse {
  const factory _ChequeBookResponse({
    final int? chequeBookId,
    final String? bookSerialNumber,
    final int? numberOfLeaves,
    final int? startLeafNumber,
    final int? endLeafNumber,
    final ChequeBookStatus? status,
    final int? accountId,
    final String? accountNumber,
    final DateTime? applicationDate,
    final DateTime? approvalDate,
    final DateTime? deliveryDate,
    final DateTime? activationDate,
    final DateTime? expiryDate,
    final String? rejectionReason,
    final List<ChequeLeafResponse>? leaves,
  }) = _$ChequeBookResponseImpl;

  factory _ChequeBookResponse.fromJson(Map<String, dynamic> json) =
      _$ChequeBookResponseImpl.fromJson;

  @override
  int? get chequeBookId;
  @override
  String? get bookSerialNumber;
  @override
  int? get numberOfLeaves;
  @override
  int? get startLeafNumber;
  @override
  int? get endLeafNumber;
  @override
  ChequeBookStatus? get status;
  @override
  int? get accountId;
  @override
  String? get accountNumber;
  @override
  DateTime? get applicationDate;
  @override
  DateTime? get approvalDate;
  @override
  DateTime? get deliveryDate;
  @override
  DateTime? get activationDate;
  @override
  DateTime? get expiryDate;
  @override
  String? get rejectionReason;
  @override
  List<ChequeLeafResponse>? get leaves;

  /// Create a copy of ChequeBookResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$ChequeBookResponseImplCopyWith<_$ChequeBookResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

ChequeLeafResponse _$ChequeLeafResponseFromJson(Map<String, dynamic> json) {
  return _ChequeLeafResponse.fromJson(json);
}

/// @nodoc
mixin _$ChequeLeafResponse {
  int? get leafId => throw _privateConstructorUsedError;
  int? get leafNumber => throw _privateConstructorUsedError;
  String? get chequeNumber => throw _privateConstructorUsedError;
  double? get amount => throw _privateConstructorUsedError;
  String? get payeeName => throw _privateConstructorUsedError;
  String? get remarks => throw _privateConstructorUsedError;
  ChequeLeafStatus? get status => throw _privateConstructorUsedError;
  DateTime? get issueDate => throw _privateConstructorUsedError;
  DateTime? get clearanceDate => throw _privateConstructorUsedError;
  DateTime? get expiryDate => throw _privateConstructorUsedError;
  String? get bounceReason => throw _privateConstructorUsedError;
  String? get transactionReference => throw _privateConstructorUsedError;
  int? get chequeBookId => throw _privateConstructorUsedError;
  String? get bookSerialNumber => throw _privateConstructorUsedError;

  /// Serializes this ChequeLeafResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of ChequeLeafResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $ChequeLeafResponseCopyWith<ChequeLeafResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ChequeLeafResponseCopyWith<$Res> {
  factory $ChequeLeafResponseCopyWith(
    ChequeLeafResponse value,
    $Res Function(ChequeLeafResponse) then,
  ) = _$ChequeLeafResponseCopyWithImpl<$Res, ChequeLeafResponse>;
  @useResult
  $Res call({
    int? leafId,
    int? leafNumber,
    String? chequeNumber,
    double? amount,
    String? payeeName,
    String? remarks,
    ChequeLeafStatus? status,
    DateTime? issueDate,
    DateTime? clearanceDate,
    DateTime? expiryDate,
    String? bounceReason,
    String? transactionReference,
    int? chequeBookId,
    String? bookSerialNumber,
  });
}

/// @nodoc
class _$ChequeLeafResponseCopyWithImpl<$Res, $Val extends ChequeLeafResponse>
    implements $ChequeLeafResponseCopyWith<$Res> {
  _$ChequeLeafResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of ChequeLeafResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? leafId = freezed,
    Object? leafNumber = freezed,
    Object? chequeNumber = freezed,
    Object? amount = freezed,
    Object? payeeName = freezed,
    Object? remarks = freezed,
    Object? status = freezed,
    Object? issueDate = freezed,
    Object? clearanceDate = freezed,
    Object? expiryDate = freezed,
    Object? bounceReason = freezed,
    Object? transactionReference = freezed,
    Object? chequeBookId = freezed,
    Object? bookSerialNumber = freezed,
  }) {
    return _then(
      _value.copyWith(
            leafId: freezed == leafId
                ? _value.leafId
                : leafId // ignore: cast_nullable_to_non_nullable
                      as int?,
            leafNumber: freezed == leafNumber
                ? _value.leafNumber
                : leafNumber // ignore: cast_nullable_to_non_nullable
                      as int?,
            chequeNumber: freezed == chequeNumber
                ? _value.chequeNumber
                : chequeNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            amount: freezed == amount
                ? _value.amount
                : amount // ignore: cast_nullable_to_non_nullable
                      as double?,
            payeeName: freezed == payeeName
                ? _value.payeeName
                : payeeName // ignore: cast_nullable_to_non_nullable
                      as String?,
            remarks: freezed == remarks
                ? _value.remarks
                : remarks // ignore: cast_nullable_to_non_nullable
                      as String?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as ChequeLeafStatus?,
            issueDate: freezed == issueDate
                ? _value.issueDate
                : issueDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            clearanceDate: freezed == clearanceDate
                ? _value.clearanceDate
                : clearanceDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            expiryDate: freezed == expiryDate
                ? _value.expiryDate
                : expiryDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            bounceReason: freezed == bounceReason
                ? _value.bounceReason
                : bounceReason // ignore: cast_nullable_to_non_nullable
                      as String?,
            transactionReference: freezed == transactionReference
                ? _value.transactionReference
                : transactionReference // ignore: cast_nullable_to_non_nullable
                      as String?,
            chequeBookId: freezed == chequeBookId
                ? _value.chequeBookId
                : chequeBookId // ignore: cast_nullable_to_non_nullable
                      as int?,
            bookSerialNumber: freezed == bookSerialNumber
                ? _value.bookSerialNumber
                : bookSerialNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$ChequeLeafResponseImplCopyWith<$Res>
    implements $ChequeLeafResponseCopyWith<$Res> {
  factory _$$ChequeLeafResponseImplCopyWith(
    _$ChequeLeafResponseImpl value,
    $Res Function(_$ChequeLeafResponseImpl) then,
  ) = __$$ChequeLeafResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? leafId,
    int? leafNumber,
    String? chequeNumber,
    double? amount,
    String? payeeName,
    String? remarks,
    ChequeLeafStatus? status,
    DateTime? issueDate,
    DateTime? clearanceDate,
    DateTime? expiryDate,
    String? bounceReason,
    String? transactionReference,
    int? chequeBookId,
    String? bookSerialNumber,
  });
}

/// @nodoc
class __$$ChequeLeafResponseImplCopyWithImpl<$Res>
    extends _$ChequeLeafResponseCopyWithImpl<$Res, _$ChequeLeafResponseImpl>
    implements _$$ChequeLeafResponseImplCopyWith<$Res> {
  __$$ChequeLeafResponseImplCopyWithImpl(
    _$ChequeLeafResponseImpl _value,
    $Res Function(_$ChequeLeafResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of ChequeLeafResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? leafId = freezed,
    Object? leafNumber = freezed,
    Object? chequeNumber = freezed,
    Object? amount = freezed,
    Object? payeeName = freezed,
    Object? remarks = freezed,
    Object? status = freezed,
    Object? issueDate = freezed,
    Object? clearanceDate = freezed,
    Object? expiryDate = freezed,
    Object? bounceReason = freezed,
    Object? transactionReference = freezed,
    Object? chequeBookId = freezed,
    Object? bookSerialNumber = freezed,
  }) {
    return _then(
      _$ChequeLeafResponseImpl(
        leafId: freezed == leafId
            ? _value.leafId
            : leafId // ignore: cast_nullable_to_non_nullable
                  as int?,
        leafNumber: freezed == leafNumber
            ? _value.leafNumber
            : leafNumber // ignore: cast_nullable_to_non_nullable
                  as int?,
        chequeNumber: freezed == chequeNumber
            ? _value.chequeNumber
            : chequeNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        amount: freezed == amount
            ? _value.amount
            : amount // ignore: cast_nullable_to_non_nullable
                  as double?,
        payeeName: freezed == payeeName
            ? _value.payeeName
            : payeeName // ignore: cast_nullable_to_non_nullable
                  as String?,
        remarks: freezed == remarks
            ? _value.remarks
            : remarks // ignore: cast_nullable_to_non_nullable
                  as String?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as ChequeLeafStatus?,
        issueDate: freezed == issueDate
            ? _value.issueDate
            : issueDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        clearanceDate: freezed == clearanceDate
            ? _value.clearanceDate
            : clearanceDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        expiryDate: freezed == expiryDate
            ? _value.expiryDate
            : expiryDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        bounceReason: freezed == bounceReason
            ? _value.bounceReason
            : bounceReason // ignore: cast_nullable_to_non_nullable
                  as String?,
        transactionReference: freezed == transactionReference
            ? _value.transactionReference
            : transactionReference // ignore: cast_nullable_to_non_nullable
                  as String?,
        chequeBookId: freezed == chequeBookId
            ? _value.chequeBookId
            : chequeBookId // ignore: cast_nullable_to_non_nullable
                  as int?,
        bookSerialNumber: freezed == bookSerialNumber
            ? _value.bookSerialNumber
            : bookSerialNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$ChequeLeafResponseImpl implements _ChequeLeafResponse {
  const _$ChequeLeafResponseImpl({
    this.leafId,
    this.leafNumber,
    this.chequeNumber,
    this.amount,
    this.payeeName,
    this.remarks,
    this.status,
    this.issueDate,
    this.clearanceDate,
    this.expiryDate,
    this.bounceReason,
    this.transactionReference,
    this.chequeBookId,
    this.bookSerialNumber,
  });

  factory _$ChequeLeafResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$ChequeLeafResponseImplFromJson(json);

  @override
  final int? leafId;
  @override
  final int? leafNumber;
  @override
  final String? chequeNumber;
  @override
  final double? amount;
  @override
  final String? payeeName;
  @override
  final String? remarks;
  @override
  final ChequeLeafStatus? status;
  @override
  final DateTime? issueDate;
  @override
  final DateTime? clearanceDate;
  @override
  final DateTime? expiryDate;
  @override
  final String? bounceReason;
  @override
  final String? transactionReference;
  @override
  final int? chequeBookId;
  @override
  final String? bookSerialNumber;

  @override
  String toString() {
    return 'ChequeLeafResponse(leafId: $leafId, leafNumber: $leafNumber, chequeNumber: $chequeNumber, amount: $amount, payeeName: $payeeName, remarks: $remarks, status: $status, issueDate: $issueDate, clearanceDate: $clearanceDate, expiryDate: $expiryDate, bounceReason: $bounceReason, transactionReference: $transactionReference, chequeBookId: $chequeBookId, bookSerialNumber: $bookSerialNumber)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ChequeLeafResponseImpl &&
            (identical(other.leafId, leafId) || other.leafId == leafId) &&
            (identical(other.leafNumber, leafNumber) ||
                other.leafNumber == leafNumber) &&
            (identical(other.chequeNumber, chequeNumber) ||
                other.chequeNumber == chequeNumber) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.payeeName, payeeName) ||
                other.payeeName == payeeName) &&
            (identical(other.remarks, remarks) || other.remarks == remarks) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.issueDate, issueDate) ||
                other.issueDate == issueDate) &&
            (identical(other.clearanceDate, clearanceDate) ||
                other.clearanceDate == clearanceDate) &&
            (identical(other.expiryDate, expiryDate) ||
                other.expiryDate == expiryDate) &&
            (identical(other.bounceReason, bounceReason) ||
                other.bounceReason == bounceReason) &&
            (identical(other.transactionReference, transactionReference) ||
                other.transactionReference == transactionReference) &&
            (identical(other.chequeBookId, chequeBookId) ||
                other.chequeBookId == chequeBookId) &&
            (identical(other.bookSerialNumber, bookSerialNumber) ||
                other.bookSerialNumber == bookSerialNumber));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    leafId,
    leafNumber,
    chequeNumber,
    amount,
    payeeName,
    remarks,
    status,
    issueDate,
    clearanceDate,
    expiryDate,
    bounceReason,
    transactionReference,
    chequeBookId,
    bookSerialNumber,
  );

  /// Create a copy of ChequeLeafResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$ChequeLeafResponseImplCopyWith<_$ChequeLeafResponseImpl> get copyWith =>
      __$$ChequeLeafResponseImplCopyWithImpl<_$ChequeLeafResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$ChequeLeafResponseImplToJson(this);
  }
}

abstract class _ChequeLeafResponse implements ChequeLeafResponse {
  const factory _ChequeLeafResponse({
    final int? leafId,
    final int? leafNumber,
    final String? chequeNumber,
    final double? amount,
    final String? payeeName,
    final String? remarks,
    final ChequeLeafStatus? status,
    final DateTime? issueDate,
    final DateTime? clearanceDate,
    final DateTime? expiryDate,
    final String? bounceReason,
    final String? transactionReference,
    final int? chequeBookId,
    final String? bookSerialNumber,
  }) = _$ChequeLeafResponseImpl;

  factory _ChequeLeafResponse.fromJson(Map<String, dynamic> json) =
      _$ChequeLeafResponseImpl.fromJson;

  @override
  int? get leafId;
  @override
  int? get leafNumber;
  @override
  String? get chequeNumber;
  @override
  double? get amount;
  @override
  String? get payeeName;
  @override
  String? get remarks;
  @override
  ChequeLeafStatus? get status;
  @override
  DateTime? get issueDate;
  @override
  DateTime? get clearanceDate;
  @override
  DateTime? get expiryDate;
  @override
  String? get bounceReason;
  @override
  String? get transactionReference;
  @override
  int? get chequeBookId;
  @override
  String? get bookSerialNumber;

  /// Create a copy of ChequeLeafResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$ChequeLeafResponseImplCopyWith<_$ChequeLeafResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

StandingOrderRequest _$StandingOrderRequestFromJson(Map<String, dynamic> json) {
  return _StandingOrderRequest.fromJson(json);
}

/// @nodoc
mixin _$StandingOrderRequest {
  int? get sourceAccountId => throw _privateConstructorUsedError;
  String? get targetAccountNumber => throw _privateConstructorUsedError;
  String? get targetAccountName => throw _privateConstructorUsedError;
  double? get amount => throw _privateConstructorUsedError;
  StandingOrderFrequency? get frequency => throw _privateConstructorUsedError;
  String? get startDate => throw _privateConstructorUsedError;
  String? get endDate => throw _privateConstructorUsedError;
  int? get maxExecutions => throw _privateConstructorUsedError;
  String? get description => throw _privateConstructorUsedError;

  /// Serializes this StandingOrderRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of StandingOrderRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $StandingOrderRequestCopyWith<StandingOrderRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $StandingOrderRequestCopyWith<$Res> {
  factory $StandingOrderRequestCopyWith(
    StandingOrderRequest value,
    $Res Function(StandingOrderRequest) then,
  ) = _$StandingOrderRequestCopyWithImpl<$Res, StandingOrderRequest>;
  @useResult
  $Res call({
    int? sourceAccountId,
    String? targetAccountNumber,
    String? targetAccountName,
    double? amount,
    StandingOrderFrequency? frequency,
    String? startDate,
    String? endDate,
    int? maxExecutions,
    String? description,
  });
}

/// @nodoc
class _$StandingOrderRequestCopyWithImpl<
  $Res,
  $Val extends StandingOrderRequest
>
    implements $StandingOrderRequestCopyWith<$Res> {
  _$StandingOrderRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of StandingOrderRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? sourceAccountId = freezed,
    Object? targetAccountNumber = freezed,
    Object? targetAccountName = freezed,
    Object? amount = freezed,
    Object? frequency = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? maxExecutions = freezed,
    Object? description = freezed,
  }) {
    return _then(
      _value.copyWith(
            sourceAccountId: freezed == sourceAccountId
                ? _value.sourceAccountId
                : sourceAccountId // ignore: cast_nullable_to_non_nullable
                      as int?,
            targetAccountNumber: freezed == targetAccountNumber
                ? _value.targetAccountNumber
                : targetAccountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            targetAccountName: freezed == targetAccountName
                ? _value.targetAccountName
                : targetAccountName // ignore: cast_nullable_to_non_nullable
                      as String?,
            amount: freezed == amount
                ? _value.amount
                : amount // ignore: cast_nullable_to_non_nullable
                      as double?,
            frequency: freezed == frequency
                ? _value.frequency
                : frequency // ignore: cast_nullable_to_non_nullable
                      as StandingOrderFrequency?,
            startDate: freezed == startDate
                ? _value.startDate
                : startDate // ignore: cast_nullable_to_non_nullable
                      as String?,
            endDate: freezed == endDate
                ? _value.endDate
                : endDate // ignore: cast_nullable_to_non_nullable
                      as String?,
            maxExecutions: freezed == maxExecutions
                ? _value.maxExecutions
                : maxExecutions // ignore: cast_nullable_to_non_nullable
                      as int?,
            description: freezed == description
                ? _value.description
                : description // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$StandingOrderRequestImplCopyWith<$Res>
    implements $StandingOrderRequestCopyWith<$Res> {
  factory _$$StandingOrderRequestImplCopyWith(
    _$StandingOrderRequestImpl value,
    $Res Function(_$StandingOrderRequestImpl) then,
  ) = __$$StandingOrderRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? sourceAccountId,
    String? targetAccountNumber,
    String? targetAccountName,
    double? amount,
    StandingOrderFrequency? frequency,
    String? startDate,
    String? endDate,
    int? maxExecutions,
    String? description,
  });
}

/// @nodoc
class __$$StandingOrderRequestImplCopyWithImpl<$Res>
    extends _$StandingOrderRequestCopyWithImpl<$Res, _$StandingOrderRequestImpl>
    implements _$$StandingOrderRequestImplCopyWith<$Res> {
  __$$StandingOrderRequestImplCopyWithImpl(
    _$StandingOrderRequestImpl _value,
    $Res Function(_$StandingOrderRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of StandingOrderRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? sourceAccountId = freezed,
    Object? targetAccountNumber = freezed,
    Object? targetAccountName = freezed,
    Object? amount = freezed,
    Object? frequency = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? maxExecutions = freezed,
    Object? description = freezed,
  }) {
    return _then(
      _$StandingOrderRequestImpl(
        sourceAccountId: freezed == sourceAccountId
            ? _value.sourceAccountId
            : sourceAccountId // ignore: cast_nullable_to_non_nullable
                  as int?,
        targetAccountNumber: freezed == targetAccountNumber
            ? _value.targetAccountNumber
            : targetAccountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        targetAccountName: freezed == targetAccountName
            ? _value.targetAccountName
            : targetAccountName // ignore: cast_nullable_to_non_nullable
                  as String?,
        amount: freezed == amount
            ? _value.amount
            : amount // ignore: cast_nullable_to_non_nullable
                  as double?,
        frequency: freezed == frequency
            ? _value.frequency
            : frequency // ignore: cast_nullable_to_non_nullable
                  as StandingOrderFrequency?,
        startDate: freezed == startDate
            ? _value.startDate
            : startDate // ignore: cast_nullable_to_non_nullable
                  as String?,
        endDate: freezed == endDate
            ? _value.endDate
            : endDate // ignore: cast_nullable_to_non_nullable
                  as String?,
        maxExecutions: freezed == maxExecutions
            ? _value.maxExecutions
            : maxExecutions // ignore: cast_nullable_to_non_nullable
                  as int?,
        description: freezed == description
            ? _value.description
            : description // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$StandingOrderRequestImpl implements _StandingOrderRequest {
  const _$StandingOrderRequestImpl({
    this.sourceAccountId,
    this.targetAccountNumber,
    this.targetAccountName,
    this.amount,
    this.frequency,
    this.startDate,
    this.endDate,
    this.maxExecutions,
    this.description,
  });

  factory _$StandingOrderRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$StandingOrderRequestImplFromJson(json);

  @override
  final int? sourceAccountId;
  @override
  final String? targetAccountNumber;
  @override
  final String? targetAccountName;
  @override
  final double? amount;
  @override
  final StandingOrderFrequency? frequency;
  @override
  final String? startDate;
  @override
  final String? endDate;
  @override
  final int? maxExecutions;
  @override
  final String? description;

  @override
  String toString() {
    return 'StandingOrderRequest(sourceAccountId: $sourceAccountId, targetAccountNumber: $targetAccountNumber, targetAccountName: $targetAccountName, amount: $amount, frequency: $frequency, startDate: $startDate, endDate: $endDate, maxExecutions: $maxExecutions, description: $description)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$StandingOrderRequestImpl &&
            (identical(other.sourceAccountId, sourceAccountId) ||
                other.sourceAccountId == sourceAccountId) &&
            (identical(other.targetAccountNumber, targetAccountNumber) ||
                other.targetAccountNumber == targetAccountNumber) &&
            (identical(other.targetAccountName, targetAccountName) ||
                other.targetAccountName == targetAccountName) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.frequency, frequency) ||
                other.frequency == frequency) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            (identical(other.maxExecutions, maxExecutions) ||
                other.maxExecutions == maxExecutions) &&
            (identical(other.description, description) ||
                other.description == description));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    sourceAccountId,
    targetAccountNumber,
    targetAccountName,
    amount,
    frequency,
    startDate,
    endDate,
    maxExecutions,
    description,
  );

  /// Create a copy of StandingOrderRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$StandingOrderRequestImplCopyWith<_$StandingOrderRequestImpl>
  get copyWith =>
      __$$StandingOrderRequestImplCopyWithImpl<_$StandingOrderRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$StandingOrderRequestImplToJson(this);
  }
}

abstract class _StandingOrderRequest implements StandingOrderRequest {
  const factory _StandingOrderRequest({
    final int? sourceAccountId,
    final String? targetAccountNumber,
    final String? targetAccountName,
    final double? amount,
    final StandingOrderFrequency? frequency,
    final String? startDate,
    final String? endDate,
    final int? maxExecutions,
    final String? description,
  }) = _$StandingOrderRequestImpl;

  factory _StandingOrderRequest.fromJson(Map<String, dynamic> json) =
      _$StandingOrderRequestImpl.fromJson;

  @override
  int? get sourceAccountId;
  @override
  String? get targetAccountNumber;
  @override
  String? get targetAccountName;
  @override
  double? get amount;
  @override
  StandingOrderFrequency? get frequency;
  @override
  String? get startDate;
  @override
  String? get endDate;
  @override
  int? get maxExecutions;
  @override
  String? get description;

  /// Create a copy of StandingOrderRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$StandingOrderRequestImplCopyWith<_$StandingOrderRequestImpl>
  get copyWith => throw _privateConstructorUsedError;
}

StandingOrderResponse _$StandingOrderResponseFromJson(
  Map<String, dynamic> json,
) {
  return _StandingOrderResponse.fromJson(json);
}

/// @nodoc
mixin _$StandingOrderResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get sourceAccountNumber => throw _privateConstructorUsedError;
  String? get targetAccountNumber => throw _privateConstructorUsedError;
  String? get targetAccountName => throw _privateConstructorUsedError;
  double? get amount => throw _privateConstructorUsedError;
  StandingOrderFrequency? get frequency => throw _privateConstructorUsedError;
  StandingOrderStatus? get status => throw _privateConstructorUsedError;
  String? get startDate => throw _privateConstructorUsedError;
  String? get endDate => throw _privateConstructorUsedError;
  String? get nextExecutionDate => throw _privateConstructorUsedError;
  String? get lastExecutionDate => throw _privateConstructorUsedError;
  int? get executionCount => throw _privateConstructorUsedError;
  int? get maxExecutions => throw _privateConstructorUsedError;
  String? get description => throw _privateConstructorUsedError;

  /// Serializes this StandingOrderResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of StandingOrderResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $StandingOrderResponseCopyWith<StandingOrderResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $StandingOrderResponseCopyWith<$Res> {
  factory $StandingOrderResponseCopyWith(
    StandingOrderResponse value,
    $Res Function(StandingOrderResponse) then,
  ) = _$StandingOrderResponseCopyWithImpl<$Res, StandingOrderResponse>;
  @useResult
  $Res call({
    int? id,
    String? sourceAccountNumber,
    String? targetAccountNumber,
    String? targetAccountName,
    double? amount,
    StandingOrderFrequency? frequency,
    StandingOrderStatus? status,
    String? startDate,
    String? endDate,
    String? nextExecutionDate,
    String? lastExecutionDate,
    int? executionCount,
    int? maxExecutions,
    String? description,
  });
}

/// @nodoc
class _$StandingOrderResponseCopyWithImpl<
  $Res,
  $Val extends StandingOrderResponse
>
    implements $StandingOrderResponseCopyWith<$Res> {
  _$StandingOrderResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of StandingOrderResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? sourceAccountNumber = freezed,
    Object? targetAccountNumber = freezed,
    Object? targetAccountName = freezed,
    Object? amount = freezed,
    Object? frequency = freezed,
    Object? status = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? nextExecutionDate = freezed,
    Object? lastExecutionDate = freezed,
    Object? executionCount = freezed,
    Object? maxExecutions = freezed,
    Object? description = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            sourceAccountNumber: freezed == sourceAccountNumber
                ? _value.sourceAccountNumber
                : sourceAccountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            targetAccountNumber: freezed == targetAccountNumber
                ? _value.targetAccountNumber
                : targetAccountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            targetAccountName: freezed == targetAccountName
                ? _value.targetAccountName
                : targetAccountName // ignore: cast_nullable_to_non_nullable
                      as String?,
            amount: freezed == amount
                ? _value.amount
                : amount // ignore: cast_nullable_to_non_nullable
                      as double?,
            frequency: freezed == frequency
                ? _value.frequency
                : frequency // ignore: cast_nullable_to_non_nullable
                      as StandingOrderFrequency?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as StandingOrderStatus?,
            startDate: freezed == startDate
                ? _value.startDate
                : startDate // ignore: cast_nullable_to_non_nullable
                      as String?,
            endDate: freezed == endDate
                ? _value.endDate
                : endDate // ignore: cast_nullable_to_non_nullable
                      as String?,
            nextExecutionDate: freezed == nextExecutionDate
                ? _value.nextExecutionDate
                : nextExecutionDate // ignore: cast_nullable_to_non_nullable
                      as String?,
            lastExecutionDate: freezed == lastExecutionDate
                ? _value.lastExecutionDate
                : lastExecutionDate // ignore: cast_nullable_to_non_nullable
                      as String?,
            executionCount: freezed == executionCount
                ? _value.executionCount
                : executionCount // ignore: cast_nullable_to_non_nullable
                      as int?,
            maxExecutions: freezed == maxExecutions
                ? _value.maxExecutions
                : maxExecutions // ignore: cast_nullable_to_non_nullable
                      as int?,
            description: freezed == description
                ? _value.description
                : description // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$StandingOrderResponseImplCopyWith<$Res>
    implements $StandingOrderResponseCopyWith<$Res> {
  factory _$$StandingOrderResponseImplCopyWith(
    _$StandingOrderResponseImpl value,
    $Res Function(_$StandingOrderResponseImpl) then,
  ) = __$$StandingOrderResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    String? sourceAccountNumber,
    String? targetAccountNumber,
    String? targetAccountName,
    double? amount,
    StandingOrderFrequency? frequency,
    StandingOrderStatus? status,
    String? startDate,
    String? endDate,
    String? nextExecutionDate,
    String? lastExecutionDate,
    int? executionCount,
    int? maxExecutions,
    String? description,
  });
}

/// @nodoc
class __$$StandingOrderResponseImplCopyWithImpl<$Res>
    extends
        _$StandingOrderResponseCopyWithImpl<$Res, _$StandingOrderResponseImpl>
    implements _$$StandingOrderResponseImplCopyWith<$Res> {
  __$$StandingOrderResponseImplCopyWithImpl(
    _$StandingOrderResponseImpl _value,
    $Res Function(_$StandingOrderResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of StandingOrderResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? sourceAccountNumber = freezed,
    Object? targetAccountNumber = freezed,
    Object? targetAccountName = freezed,
    Object? amount = freezed,
    Object? frequency = freezed,
    Object? status = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? nextExecutionDate = freezed,
    Object? lastExecutionDate = freezed,
    Object? executionCount = freezed,
    Object? maxExecutions = freezed,
    Object? description = freezed,
  }) {
    return _then(
      _$StandingOrderResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        sourceAccountNumber: freezed == sourceAccountNumber
            ? _value.sourceAccountNumber
            : sourceAccountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        targetAccountNumber: freezed == targetAccountNumber
            ? _value.targetAccountNumber
            : targetAccountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        targetAccountName: freezed == targetAccountName
            ? _value.targetAccountName
            : targetAccountName // ignore: cast_nullable_to_non_nullable
                  as String?,
        amount: freezed == amount
            ? _value.amount
            : amount // ignore: cast_nullable_to_non_nullable
                  as double?,
        frequency: freezed == frequency
            ? _value.frequency
            : frequency // ignore: cast_nullable_to_non_nullable
                  as StandingOrderFrequency?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as StandingOrderStatus?,
        startDate: freezed == startDate
            ? _value.startDate
            : startDate // ignore: cast_nullable_to_non_nullable
                  as String?,
        endDate: freezed == endDate
            ? _value.endDate
            : endDate // ignore: cast_nullable_to_non_nullable
                  as String?,
        nextExecutionDate: freezed == nextExecutionDate
            ? _value.nextExecutionDate
            : nextExecutionDate // ignore: cast_nullable_to_non_nullable
                  as String?,
        lastExecutionDate: freezed == lastExecutionDate
            ? _value.lastExecutionDate
            : lastExecutionDate // ignore: cast_nullable_to_non_nullable
                  as String?,
        executionCount: freezed == executionCount
            ? _value.executionCount
            : executionCount // ignore: cast_nullable_to_non_nullable
                  as int?,
        maxExecutions: freezed == maxExecutions
            ? _value.maxExecutions
            : maxExecutions // ignore: cast_nullable_to_non_nullable
                  as int?,
        description: freezed == description
            ? _value.description
            : description // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$StandingOrderResponseImpl implements _StandingOrderResponse {
  const _$StandingOrderResponseImpl({
    this.id,
    this.sourceAccountNumber,
    this.targetAccountNumber,
    this.targetAccountName,
    this.amount,
    this.frequency,
    this.status,
    this.startDate,
    this.endDate,
    this.nextExecutionDate,
    this.lastExecutionDate,
    this.executionCount,
    this.maxExecutions,
    this.description,
  });

  factory _$StandingOrderResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$StandingOrderResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? sourceAccountNumber;
  @override
  final String? targetAccountNumber;
  @override
  final String? targetAccountName;
  @override
  final double? amount;
  @override
  final StandingOrderFrequency? frequency;
  @override
  final StandingOrderStatus? status;
  @override
  final String? startDate;
  @override
  final String? endDate;
  @override
  final String? nextExecutionDate;
  @override
  final String? lastExecutionDate;
  @override
  final int? executionCount;
  @override
  final int? maxExecutions;
  @override
  final String? description;

  @override
  String toString() {
    return 'StandingOrderResponse(id: $id, sourceAccountNumber: $sourceAccountNumber, targetAccountNumber: $targetAccountNumber, targetAccountName: $targetAccountName, amount: $amount, frequency: $frequency, status: $status, startDate: $startDate, endDate: $endDate, nextExecutionDate: $nextExecutionDate, lastExecutionDate: $lastExecutionDate, executionCount: $executionCount, maxExecutions: $maxExecutions, description: $description)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$StandingOrderResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.sourceAccountNumber, sourceAccountNumber) ||
                other.sourceAccountNumber == sourceAccountNumber) &&
            (identical(other.targetAccountNumber, targetAccountNumber) ||
                other.targetAccountNumber == targetAccountNumber) &&
            (identical(other.targetAccountName, targetAccountName) ||
                other.targetAccountName == targetAccountName) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.frequency, frequency) ||
                other.frequency == frequency) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            (identical(other.nextExecutionDate, nextExecutionDate) ||
                other.nextExecutionDate == nextExecutionDate) &&
            (identical(other.lastExecutionDate, lastExecutionDate) ||
                other.lastExecutionDate == lastExecutionDate) &&
            (identical(other.executionCount, executionCount) ||
                other.executionCount == executionCount) &&
            (identical(other.maxExecutions, maxExecutions) ||
                other.maxExecutions == maxExecutions) &&
            (identical(other.description, description) ||
                other.description == description));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    sourceAccountNumber,
    targetAccountNumber,
    targetAccountName,
    amount,
    frequency,
    status,
    startDate,
    endDate,
    nextExecutionDate,
    lastExecutionDate,
    executionCount,
    maxExecutions,
    description,
  );

  /// Create a copy of StandingOrderResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$StandingOrderResponseImplCopyWith<_$StandingOrderResponseImpl>
  get copyWith =>
      __$$StandingOrderResponseImplCopyWithImpl<_$StandingOrderResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$StandingOrderResponseImplToJson(this);
  }
}

abstract class _StandingOrderResponse implements StandingOrderResponse {
  const factory _StandingOrderResponse({
    final int? id,
    final String? sourceAccountNumber,
    final String? targetAccountNumber,
    final String? targetAccountName,
    final double? amount,
    final StandingOrderFrequency? frequency,
    final StandingOrderStatus? status,
    final String? startDate,
    final String? endDate,
    final String? nextExecutionDate,
    final String? lastExecutionDate,
    final int? executionCount,
    final int? maxExecutions,
    final String? description,
  }) = _$StandingOrderResponseImpl;

  factory _StandingOrderResponse.fromJson(Map<String, dynamic> json) =
      _$StandingOrderResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get sourceAccountNumber;
  @override
  String? get targetAccountNumber;
  @override
  String? get targetAccountName;
  @override
  double? get amount;
  @override
  StandingOrderFrequency? get frequency;
  @override
  StandingOrderStatus? get status;
  @override
  String? get startDate;
  @override
  String? get endDate;
  @override
  String? get nextExecutionDate;
  @override
  String? get lastExecutionDate;
  @override
  int? get executionCount;
  @override
  int? get maxExecutions;
  @override
  String? get description;

  /// Create a copy of StandingOrderResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$StandingOrderResponseImplCopyWith<_$StandingOrderResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}

BeneficiaryRequest _$BeneficiaryRequestFromJson(Map<String, dynamic> json) {
  return _BeneficiaryRequest.fromJson(json);
}

/// @nodoc
mixin _$BeneficiaryRequest {
  String? get accNumber => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  String? get provider => throw _privateConstructorUsedError;
  String? get routingNumber => throw _privateConstructorUsedError;
  BeneficiaryType? get beneficiaryType => throw _privateConstructorUsedError;
  int? get customerId => throw _privateConstructorUsedError;

  /// Serializes this BeneficiaryRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of BeneficiaryRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $BeneficiaryRequestCopyWith<BeneficiaryRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $BeneficiaryRequestCopyWith<$Res> {
  factory $BeneficiaryRequestCopyWith(
    BeneficiaryRequest value,
    $Res Function(BeneficiaryRequest) then,
  ) = _$BeneficiaryRequestCopyWithImpl<$Res, BeneficiaryRequest>;
  @useResult
  $Res call({
    String? accNumber,
    String? name,
    String? provider,
    String? routingNumber,
    BeneficiaryType? beneficiaryType,
    int? customerId,
  });
}

/// @nodoc
class _$BeneficiaryRequestCopyWithImpl<$Res, $Val extends BeneficiaryRequest>
    implements $BeneficiaryRequestCopyWith<$Res> {
  _$BeneficiaryRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of BeneficiaryRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? accNumber = freezed,
    Object? name = freezed,
    Object? provider = freezed,
    Object? routingNumber = freezed,
    Object? beneficiaryType = freezed,
    Object? customerId = freezed,
  }) {
    return _then(
      _value.copyWith(
            accNumber: freezed == accNumber
                ? _value.accNumber
                : accNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            name: freezed == name
                ? _value.name
                : name // ignore: cast_nullable_to_non_nullable
                      as String?,
            provider: freezed == provider
                ? _value.provider
                : provider // ignore: cast_nullable_to_non_nullable
                      as String?,
            routingNumber: freezed == routingNumber
                ? _value.routingNumber
                : routingNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            beneficiaryType: freezed == beneficiaryType
                ? _value.beneficiaryType
                : beneficiaryType // ignore: cast_nullable_to_non_nullable
                      as BeneficiaryType?,
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
abstract class _$$BeneficiaryRequestImplCopyWith<$Res>
    implements $BeneficiaryRequestCopyWith<$Res> {
  factory _$$BeneficiaryRequestImplCopyWith(
    _$BeneficiaryRequestImpl value,
    $Res Function(_$BeneficiaryRequestImpl) then,
  ) = __$$BeneficiaryRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    String? accNumber,
    String? name,
    String? provider,
    String? routingNumber,
    BeneficiaryType? beneficiaryType,
    int? customerId,
  });
}

/// @nodoc
class __$$BeneficiaryRequestImplCopyWithImpl<$Res>
    extends _$BeneficiaryRequestCopyWithImpl<$Res, _$BeneficiaryRequestImpl>
    implements _$$BeneficiaryRequestImplCopyWith<$Res> {
  __$$BeneficiaryRequestImplCopyWithImpl(
    _$BeneficiaryRequestImpl _value,
    $Res Function(_$BeneficiaryRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of BeneficiaryRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? accNumber = freezed,
    Object? name = freezed,
    Object? provider = freezed,
    Object? routingNumber = freezed,
    Object? beneficiaryType = freezed,
    Object? customerId = freezed,
  }) {
    return _then(
      _$BeneficiaryRequestImpl(
        accNumber: freezed == accNumber
            ? _value.accNumber
            : accNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
        provider: freezed == provider
            ? _value.provider
            : provider // ignore: cast_nullable_to_non_nullable
                  as String?,
        routingNumber: freezed == routingNumber
            ? _value.routingNumber
            : routingNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        beneficiaryType: freezed == beneficiaryType
            ? _value.beneficiaryType
            : beneficiaryType // ignore: cast_nullable_to_non_nullable
                  as BeneficiaryType?,
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
class _$BeneficiaryRequestImpl implements _BeneficiaryRequest {
  const _$BeneficiaryRequestImpl({
    this.accNumber,
    this.name,
    this.provider,
    this.routingNumber,
    this.beneficiaryType,
    this.customerId,
  });

  factory _$BeneficiaryRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$BeneficiaryRequestImplFromJson(json);

  @override
  final String? accNumber;
  @override
  final String? name;
  @override
  final String? provider;
  @override
  final String? routingNumber;
  @override
  final BeneficiaryType? beneficiaryType;
  @override
  final int? customerId;

  @override
  String toString() {
    return 'BeneficiaryRequest(accNumber: $accNumber, name: $name, provider: $provider, routingNumber: $routingNumber, beneficiaryType: $beneficiaryType, customerId: $customerId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$BeneficiaryRequestImpl &&
            (identical(other.accNumber, accNumber) ||
                other.accNumber == accNumber) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.provider, provider) ||
                other.provider == provider) &&
            (identical(other.routingNumber, routingNumber) ||
                other.routingNumber == routingNumber) &&
            (identical(other.beneficiaryType, beneficiaryType) ||
                other.beneficiaryType == beneficiaryType) &&
            (identical(other.customerId, customerId) ||
                other.customerId == customerId));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    accNumber,
    name,
    provider,
    routingNumber,
    beneficiaryType,
    customerId,
  );

  /// Create a copy of BeneficiaryRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$BeneficiaryRequestImplCopyWith<_$BeneficiaryRequestImpl> get copyWith =>
      __$$BeneficiaryRequestImplCopyWithImpl<_$BeneficiaryRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$BeneficiaryRequestImplToJson(this);
  }
}

abstract class _BeneficiaryRequest implements BeneficiaryRequest {
  const factory _BeneficiaryRequest({
    final String? accNumber,
    final String? name,
    final String? provider,
    final String? routingNumber,
    final BeneficiaryType? beneficiaryType,
    final int? customerId,
  }) = _$BeneficiaryRequestImpl;

  factory _BeneficiaryRequest.fromJson(Map<String, dynamic> json) =
      _$BeneficiaryRequestImpl.fromJson;

  @override
  String? get accNumber;
  @override
  String? get name;
  @override
  String? get provider;
  @override
  String? get routingNumber;
  @override
  BeneficiaryType? get beneficiaryType;
  @override
  int? get customerId;

  /// Create a copy of BeneficiaryRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$BeneficiaryRequestImplCopyWith<_$BeneficiaryRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

BeneficiaryResponse _$BeneficiaryResponseFromJson(Map<String, dynamic> json) {
  return _BeneficiaryResponse.fromJson(json);
}

/// @nodoc
mixin _$BeneficiaryResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get accNumber => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  String? get provider => throw _privateConstructorUsedError;
  String? get routingNumber => throw _privateConstructorUsedError;
  BeneficiaryType? get beneficiaryType => throw _privateConstructorUsedError;
  int? get customerId => throw _privateConstructorUsedError;
  String? get customerName => throw _privateConstructorUsedError;
  bool? get isVerified => throw _privateConstructorUsedError;
  bool? get isBlocked => throw _privateConstructorUsedError;
  String? get blockReason => throw _privateConstructorUsedError;

  /// Serializes this BeneficiaryResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of BeneficiaryResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $BeneficiaryResponseCopyWith<BeneficiaryResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $BeneficiaryResponseCopyWith<$Res> {
  factory $BeneficiaryResponseCopyWith(
    BeneficiaryResponse value,
    $Res Function(BeneficiaryResponse) then,
  ) = _$BeneficiaryResponseCopyWithImpl<$Res, BeneficiaryResponse>;
  @useResult
  $Res call({
    int? id,
    String? accNumber,
    String? name,
    String? provider,
    String? routingNumber,
    BeneficiaryType? beneficiaryType,
    int? customerId,
    String? customerName,
    bool? isVerified,
    bool? isBlocked,
    String? blockReason,
  });
}

/// @nodoc
class _$BeneficiaryResponseCopyWithImpl<$Res, $Val extends BeneficiaryResponse>
    implements $BeneficiaryResponseCopyWith<$Res> {
  _$BeneficiaryResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of BeneficiaryResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? accNumber = freezed,
    Object? name = freezed,
    Object? provider = freezed,
    Object? routingNumber = freezed,
    Object? beneficiaryType = freezed,
    Object? customerId = freezed,
    Object? customerName = freezed,
    Object? isVerified = freezed,
    Object? isBlocked = freezed,
    Object? blockReason = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            accNumber: freezed == accNumber
                ? _value.accNumber
                : accNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            name: freezed == name
                ? _value.name
                : name // ignore: cast_nullable_to_non_nullable
                      as String?,
            provider: freezed == provider
                ? _value.provider
                : provider // ignore: cast_nullable_to_non_nullable
                      as String?,
            routingNumber: freezed == routingNumber
                ? _value.routingNumber
                : routingNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            beneficiaryType: freezed == beneficiaryType
                ? _value.beneficiaryType
                : beneficiaryType // ignore: cast_nullable_to_non_nullable
                      as BeneficiaryType?,
            customerId: freezed == customerId
                ? _value.customerId
                : customerId // ignore: cast_nullable_to_non_nullable
                      as int?,
            customerName: freezed == customerName
                ? _value.customerName
                : customerName // ignore: cast_nullable_to_non_nullable
                      as String?,
            isVerified: freezed == isVerified
                ? _value.isVerified
                : isVerified // ignore: cast_nullable_to_non_nullable
                      as bool?,
            isBlocked: freezed == isBlocked
                ? _value.isBlocked
                : isBlocked // ignore: cast_nullable_to_non_nullable
                      as bool?,
            blockReason: freezed == blockReason
                ? _value.blockReason
                : blockReason // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$BeneficiaryResponseImplCopyWith<$Res>
    implements $BeneficiaryResponseCopyWith<$Res> {
  factory _$$BeneficiaryResponseImplCopyWith(
    _$BeneficiaryResponseImpl value,
    $Res Function(_$BeneficiaryResponseImpl) then,
  ) = __$$BeneficiaryResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    String? accNumber,
    String? name,
    String? provider,
    String? routingNumber,
    BeneficiaryType? beneficiaryType,
    int? customerId,
    String? customerName,
    bool? isVerified,
    bool? isBlocked,
    String? blockReason,
  });
}

/// @nodoc
class __$$BeneficiaryResponseImplCopyWithImpl<$Res>
    extends _$BeneficiaryResponseCopyWithImpl<$Res, _$BeneficiaryResponseImpl>
    implements _$$BeneficiaryResponseImplCopyWith<$Res> {
  __$$BeneficiaryResponseImplCopyWithImpl(
    _$BeneficiaryResponseImpl _value,
    $Res Function(_$BeneficiaryResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of BeneficiaryResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? accNumber = freezed,
    Object? name = freezed,
    Object? provider = freezed,
    Object? routingNumber = freezed,
    Object? beneficiaryType = freezed,
    Object? customerId = freezed,
    Object? customerName = freezed,
    Object? isVerified = freezed,
    Object? isBlocked = freezed,
    Object? blockReason = freezed,
  }) {
    return _then(
      _$BeneficiaryResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        accNumber: freezed == accNumber
            ? _value.accNumber
            : accNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
        provider: freezed == provider
            ? _value.provider
            : provider // ignore: cast_nullable_to_non_nullable
                  as String?,
        routingNumber: freezed == routingNumber
            ? _value.routingNumber
            : routingNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        beneficiaryType: freezed == beneficiaryType
            ? _value.beneficiaryType
            : beneficiaryType // ignore: cast_nullable_to_non_nullable
                  as BeneficiaryType?,
        customerId: freezed == customerId
            ? _value.customerId
            : customerId // ignore: cast_nullable_to_non_nullable
                  as int?,
        customerName: freezed == customerName
            ? _value.customerName
            : customerName // ignore: cast_nullable_to_non_nullable
                  as String?,
        isVerified: freezed == isVerified
            ? _value.isVerified
            : isVerified // ignore: cast_nullable_to_non_nullable
                  as bool?,
        isBlocked: freezed == isBlocked
            ? _value.isBlocked
            : isBlocked // ignore: cast_nullable_to_non_nullable
                  as bool?,
        blockReason: freezed == blockReason
            ? _value.blockReason
            : blockReason // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$BeneficiaryResponseImpl implements _BeneficiaryResponse {
  const _$BeneficiaryResponseImpl({
    this.id,
    this.accNumber,
    this.name,
    this.provider,
    this.routingNumber,
    this.beneficiaryType,
    this.customerId,
    this.customerName,
    this.isVerified,
    this.isBlocked,
    this.blockReason,
  });

  factory _$BeneficiaryResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$BeneficiaryResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? accNumber;
  @override
  final String? name;
  @override
  final String? provider;
  @override
  final String? routingNumber;
  @override
  final BeneficiaryType? beneficiaryType;
  @override
  final int? customerId;
  @override
  final String? customerName;
  @override
  final bool? isVerified;
  @override
  final bool? isBlocked;
  @override
  final String? blockReason;

  @override
  String toString() {
    return 'BeneficiaryResponse(id: $id, accNumber: $accNumber, name: $name, provider: $provider, routingNumber: $routingNumber, beneficiaryType: $beneficiaryType, customerId: $customerId, customerName: $customerName, isVerified: $isVerified, isBlocked: $isBlocked, blockReason: $blockReason)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$BeneficiaryResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.accNumber, accNumber) ||
                other.accNumber == accNumber) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.provider, provider) ||
                other.provider == provider) &&
            (identical(other.routingNumber, routingNumber) ||
                other.routingNumber == routingNumber) &&
            (identical(other.beneficiaryType, beneficiaryType) ||
                other.beneficiaryType == beneficiaryType) &&
            (identical(other.customerId, customerId) ||
                other.customerId == customerId) &&
            (identical(other.customerName, customerName) ||
                other.customerName == customerName) &&
            (identical(other.isVerified, isVerified) ||
                other.isVerified == isVerified) &&
            (identical(other.isBlocked, isBlocked) ||
                other.isBlocked == isBlocked) &&
            (identical(other.blockReason, blockReason) ||
                other.blockReason == blockReason));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    accNumber,
    name,
    provider,
    routingNumber,
    beneficiaryType,
    customerId,
    customerName,
    isVerified,
    isBlocked,
    blockReason,
  );

  /// Create a copy of BeneficiaryResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$BeneficiaryResponseImplCopyWith<_$BeneficiaryResponseImpl> get copyWith =>
      __$$BeneficiaryResponseImplCopyWithImpl<_$BeneficiaryResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$BeneficiaryResponseImplToJson(this);
  }
}

abstract class _BeneficiaryResponse implements BeneficiaryResponse {
  const factory _BeneficiaryResponse({
    final int? id,
    final String? accNumber,
    final String? name,
    final String? provider,
    final String? routingNumber,
    final BeneficiaryType? beneficiaryType,
    final int? customerId,
    final String? customerName,
    final bool? isVerified,
    final bool? isBlocked,
    final String? blockReason,
  }) = _$BeneficiaryResponseImpl;

  factory _BeneficiaryResponse.fromJson(Map<String, dynamic> json) =
      _$BeneficiaryResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get accNumber;
  @override
  String? get name;
  @override
  String? get provider;
  @override
  String? get routingNumber;
  @override
  BeneficiaryType? get beneficiaryType;
  @override
  int? get customerId;
  @override
  String? get customerName;
  @override
  bool? get isVerified;
  @override
  bool? get isBlocked;
  @override
  String? get blockReason;

  /// Create a copy of BeneficiaryResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$BeneficiaryResponseImplCopyWith<_$BeneficiaryResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

NotificationResponse _$NotificationResponseFromJson(Map<String, dynamic> json) {
  return _NotificationResponse.fromJson(json);
}

/// @nodoc
mixin _$NotificationResponse {
  int? get id => throw _privateConstructorUsedError;
  @JsonKey(unknownEnumValue: NotificationType.SYSTEM)
  NotificationType? get type => throw _privateConstructorUsedError;
  String? get title => throw _privateConstructorUsedError;
  String? get message => throw _privateConstructorUsedError;
  bool? get read => throw _privateConstructorUsedError;
  String? get referenceId => throw _privateConstructorUsedError;
  String? get referenceType => throw _privateConstructorUsedError;
  String? get createdAt => throw _privateConstructorUsedError;

  /// Serializes this NotificationResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of NotificationResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $NotificationResponseCopyWith<NotificationResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $NotificationResponseCopyWith<$Res> {
  factory $NotificationResponseCopyWith(
    NotificationResponse value,
    $Res Function(NotificationResponse) then,
  ) = _$NotificationResponseCopyWithImpl<$Res, NotificationResponse>;
  @useResult
  $Res call({
    int? id,
    @JsonKey(unknownEnumValue: NotificationType.SYSTEM) NotificationType? type,
    String? title,
    String? message,
    bool? read,
    String? referenceId,
    String? referenceType,
    String? createdAt,
  });
}

/// @nodoc
class _$NotificationResponseCopyWithImpl<
  $Res,
  $Val extends NotificationResponse
>
    implements $NotificationResponseCopyWith<$Res> {
  _$NotificationResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of NotificationResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? type = freezed,
    Object? title = freezed,
    Object? message = freezed,
    Object? read = freezed,
    Object? referenceId = freezed,
    Object? referenceType = freezed,
    Object? createdAt = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            type: freezed == type
                ? _value.type
                : type // ignore: cast_nullable_to_non_nullable
                      as NotificationType?,
            title: freezed == title
                ? _value.title
                : title // ignore: cast_nullable_to_non_nullable
                      as String?,
            message: freezed == message
                ? _value.message
                : message // ignore: cast_nullable_to_non_nullable
                      as String?,
            read: freezed == read
                ? _value.read
                : read // ignore: cast_nullable_to_non_nullable
                      as bool?,
            referenceId: freezed == referenceId
                ? _value.referenceId
                : referenceId // ignore: cast_nullable_to_non_nullable
                      as String?,
            referenceType: freezed == referenceType
                ? _value.referenceType
                : referenceType // ignore: cast_nullable_to_non_nullable
                      as String?,
            createdAt: freezed == createdAt
                ? _value.createdAt
                : createdAt // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$NotificationResponseImplCopyWith<$Res>
    implements $NotificationResponseCopyWith<$Res> {
  factory _$$NotificationResponseImplCopyWith(
    _$NotificationResponseImpl value,
    $Res Function(_$NotificationResponseImpl) then,
  ) = __$$NotificationResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    @JsonKey(unknownEnumValue: NotificationType.SYSTEM) NotificationType? type,
    String? title,
    String? message,
    bool? read,
    String? referenceId,
    String? referenceType,
    String? createdAt,
  });
}

/// @nodoc
class __$$NotificationResponseImplCopyWithImpl<$Res>
    extends _$NotificationResponseCopyWithImpl<$Res, _$NotificationResponseImpl>
    implements _$$NotificationResponseImplCopyWith<$Res> {
  __$$NotificationResponseImplCopyWithImpl(
    _$NotificationResponseImpl _value,
    $Res Function(_$NotificationResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of NotificationResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? type = freezed,
    Object? title = freezed,
    Object? message = freezed,
    Object? read = freezed,
    Object? referenceId = freezed,
    Object? referenceType = freezed,
    Object? createdAt = freezed,
  }) {
    return _then(
      _$NotificationResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        type: freezed == type
            ? _value.type
            : type // ignore: cast_nullable_to_non_nullable
                  as NotificationType?,
        title: freezed == title
            ? _value.title
            : title // ignore: cast_nullable_to_non_nullable
                  as String?,
        message: freezed == message
            ? _value.message
            : message // ignore: cast_nullable_to_non_nullable
                  as String?,
        read: freezed == read
            ? _value.read
            : read // ignore: cast_nullable_to_non_nullable
                  as bool?,
        referenceId: freezed == referenceId
            ? _value.referenceId
            : referenceId // ignore: cast_nullable_to_non_nullable
                  as String?,
        referenceType: freezed == referenceType
            ? _value.referenceType
            : referenceType // ignore: cast_nullable_to_non_nullable
                  as String?,
        createdAt: freezed == createdAt
            ? _value.createdAt
            : createdAt // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$NotificationResponseImpl implements _NotificationResponse {
  const _$NotificationResponseImpl({
    this.id,
    @JsonKey(unknownEnumValue: NotificationType.SYSTEM) this.type,
    this.title,
    this.message,
    this.read,
    this.referenceId,
    this.referenceType,
    this.createdAt,
  });

  factory _$NotificationResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$NotificationResponseImplFromJson(json);

  @override
  final int? id;
  @override
  @JsonKey(unknownEnumValue: NotificationType.SYSTEM)
  final NotificationType? type;
  @override
  final String? title;
  @override
  final String? message;
  @override
  final bool? read;
  @override
  final String? referenceId;
  @override
  final String? referenceType;
  @override
  final String? createdAt;

  @override
  String toString() {
    return 'NotificationResponse(id: $id, type: $type, title: $title, message: $message, read: $read, referenceId: $referenceId, referenceType: $referenceType, createdAt: $createdAt)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$NotificationResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.title, title) || other.title == title) &&
            (identical(other.message, message) || other.message == message) &&
            (identical(other.read, read) || other.read == read) &&
            (identical(other.referenceId, referenceId) ||
                other.referenceId == referenceId) &&
            (identical(other.referenceType, referenceType) ||
                other.referenceType == referenceType) &&
            (identical(other.createdAt, createdAt) ||
                other.createdAt == createdAt));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    type,
    title,
    message,
    read,
    referenceId,
    referenceType,
    createdAt,
  );

  /// Create a copy of NotificationResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$NotificationResponseImplCopyWith<_$NotificationResponseImpl>
  get copyWith =>
      __$$NotificationResponseImplCopyWithImpl<_$NotificationResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$NotificationResponseImplToJson(this);
  }
}

abstract class _NotificationResponse implements NotificationResponse {
  const factory _NotificationResponse({
    final int? id,
    @JsonKey(unknownEnumValue: NotificationType.SYSTEM)
    final NotificationType? type,
    final String? title,
    final String? message,
    final bool? read,
    final String? referenceId,
    final String? referenceType,
    final String? createdAt,
  }) = _$NotificationResponseImpl;

  factory _NotificationResponse.fromJson(Map<String, dynamic> json) =
      _$NotificationResponseImpl.fromJson;

  @override
  int? get id;
  @override
  @JsonKey(unknownEnumValue: NotificationType.SYSTEM)
  NotificationType? get type;
  @override
  String? get title;
  @override
  String? get message;
  @override
  bool? get read;
  @override
  String? get referenceId;
  @override
  String? get referenceType;
  @override
  String? get createdAt;

  /// Create a copy of NotificationResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$NotificationResponseImplCopyWith<_$NotificationResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}

BranchResponse _$BranchResponseFromJson(Map<String, dynamic> json) {
  return _BranchResponse.fromJson(json);
}

/// @nodoc
mixin _$BranchResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  String? get address => throw _privateConstructorUsedError;
  String? get routingNumber => throw _privateConstructorUsedError;
  String? get branchCode => throw _privateConstructorUsedError;
  String? get email => throw _privateConstructorUsedError;
  String? get phoneNumber => throw _privateConstructorUsedError;
  BranchType? get type => throw _privateConstructorUsedError;
  BranchStatus? get status => throw _privateConstructorUsedError;

  /// Serializes this BranchResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of BranchResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $BranchResponseCopyWith<BranchResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $BranchResponseCopyWith<$Res> {
  factory $BranchResponseCopyWith(
    BranchResponse value,
    $Res Function(BranchResponse) then,
  ) = _$BranchResponseCopyWithImpl<$Res, BranchResponse>;
  @useResult
  $Res call({
    int? id,
    String? name,
    String? address,
    String? routingNumber,
    String? branchCode,
    String? email,
    String? phoneNumber,
    BranchType? type,
    BranchStatus? status,
  });
}

/// @nodoc
class _$BranchResponseCopyWithImpl<$Res, $Val extends BranchResponse>
    implements $BranchResponseCopyWith<$Res> {
  _$BranchResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of BranchResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? name = freezed,
    Object? address = freezed,
    Object? routingNumber = freezed,
    Object? branchCode = freezed,
    Object? email = freezed,
    Object? phoneNumber = freezed,
    Object? type = freezed,
    Object? status = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            name: freezed == name
                ? _value.name
                : name // ignore: cast_nullable_to_non_nullable
                      as String?,
            address: freezed == address
                ? _value.address
                : address // ignore: cast_nullable_to_non_nullable
                      as String?,
            routingNumber: freezed == routingNumber
                ? _value.routingNumber
                : routingNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            branchCode: freezed == branchCode
                ? _value.branchCode
                : branchCode // ignore: cast_nullable_to_non_nullable
                      as String?,
            email: freezed == email
                ? _value.email
                : email // ignore: cast_nullable_to_non_nullable
                      as String?,
            phoneNumber: freezed == phoneNumber
                ? _value.phoneNumber
                : phoneNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            type: freezed == type
                ? _value.type
                : type // ignore: cast_nullable_to_non_nullable
                      as BranchType?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as BranchStatus?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$BranchResponseImplCopyWith<$Res>
    implements $BranchResponseCopyWith<$Res> {
  factory _$$BranchResponseImplCopyWith(
    _$BranchResponseImpl value,
    $Res Function(_$BranchResponseImpl) then,
  ) = __$$BranchResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    String? name,
    String? address,
    String? routingNumber,
    String? branchCode,
    String? email,
    String? phoneNumber,
    BranchType? type,
    BranchStatus? status,
  });
}

/// @nodoc
class __$$BranchResponseImplCopyWithImpl<$Res>
    extends _$BranchResponseCopyWithImpl<$Res, _$BranchResponseImpl>
    implements _$$BranchResponseImplCopyWith<$Res> {
  __$$BranchResponseImplCopyWithImpl(
    _$BranchResponseImpl _value,
    $Res Function(_$BranchResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of BranchResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? name = freezed,
    Object? address = freezed,
    Object? routingNumber = freezed,
    Object? branchCode = freezed,
    Object? email = freezed,
    Object? phoneNumber = freezed,
    Object? type = freezed,
    Object? status = freezed,
  }) {
    return _then(
      _$BranchResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
        address: freezed == address
            ? _value.address
            : address // ignore: cast_nullable_to_non_nullable
                  as String?,
        routingNumber: freezed == routingNumber
            ? _value.routingNumber
            : routingNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        branchCode: freezed == branchCode
            ? _value.branchCode
            : branchCode // ignore: cast_nullable_to_non_nullable
                  as String?,
        email: freezed == email
            ? _value.email
            : email // ignore: cast_nullable_to_non_nullable
                  as String?,
        phoneNumber: freezed == phoneNumber
            ? _value.phoneNumber
            : phoneNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        type: freezed == type
            ? _value.type
            : type // ignore: cast_nullable_to_non_nullable
                  as BranchType?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as BranchStatus?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$BranchResponseImpl implements _BranchResponse {
  const _$BranchResponseImpl({
    this.id,
    this.name,
    this.address,
    this.routingNumber,
    this.branchCode,
    this.email,
    this.phoneNumber,
    this.type,
    this.status,
  });

  factory _$BranchResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$BranchResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? name;
  @override
  final String? address;
  @override
  final String? routingNumber;
  @override
  final String? branchCode;
  @override
  final String? email;
  @override
  final String? phoneNumber;
  @override
  final BranchType? type;
  @override
  final BranchStatus? status;

  @override
  String toString() {
    return 'BranchResponse(id: $id, name: $name, address: $address, routingNumber: $routingNumber, branchCode: $branchCode, email: $email, phoneNumber: $phoneNumber, type: $type, status: $status)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$BranchResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.address, address) || other.address == address) &&
            (identical(other.routingNumber, routingNumber) ||
                other.routingNumber == routingNumber) &&
            (identical(other.branchCode, branchCode) ||
                other.branchCode == branchCode) &&
            (identical(other.email, email) || other.email == email) &&
            (identical(other.phoneNumber, phoneNumber) ||
                other.phoneNumber == phoneNumber) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.status, status) || other.status == status));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    name,
    address,
    routingNumber,
    branchCode,
    email,
    phoneNumber,
    type,
    status,
  );

  /// Create a copy of BranchResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$BranchResponseImplCopyWith<_$BranchResponseImpl> get copyWith =>
      __$$BranchResponseImplCopyWithImpl<_$BranchResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$BranchResponseImplToJson(this);
  }
}

abstract class _BranchResponse implements BranchResponse {
  const factory _BranchResponse({
    final int? id,
    final String? name,
    final String? address,
    final String? routingNumber,
    final String? branchCode,
    final String? email,
    final String? phoneNumber,
    final BranchType? type,
    final BranchStatus? status,
  }) = _$BranchResponseImpl;

  factory _BranchResponse.fromJson(Map<String, dynamic> json) =
      _$BranchResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get name;
  @override
  String? get address;
  @override
  String? get routingNumber;
  @override
  String? get branchCode;
  @override
  String? get email;
  @override
  String? get phoneNumber;
  @override
  BranchType? get type;
  @override
  BranchStatus? get status;

  /// Create a copy of BranchResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$BranchResponseImplCopyWith<_$BranchResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

DivisionResponse _$DivisionResponseFromJson(Map<String, dynamic> json) {
  return _DivisionResponse.fromJson(json);
}

/// @nodoc
mixin _$DivisionResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;

  /// Serializes this DivisionResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of DivisionResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $DivisionResponseCopyWith<DivisionResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $DivisionResponseCopyWith<$Res> {
  factory $DivisionResponseCopyWith(
    DivisionResponse value,
    $Res Function(DivisionResponse) then,
  ) = _$DivisionResponseCopyWithImpl<$Res, DivisionResponse>;
  @useResult
  $Res call({int? id, String? name});
}

/// @nodoc
class _$DivisionResponseCopyWithImpl<$Res, $Val extends DivisionResponse>
    implements $DivisionResponseCopyWith<$Res> {
  _$DivisionResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of DivisionResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? id = freezed, Object? name = freezed}) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            name: freezed == name
                ? _value.name
                : name // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$DivisionResponseImplCopyWith<$Res>
    implements $DivisionResponseCopyWith<$Res> {
  factory _$$DivisionResponseImplCopyWith(
    _$DivisionResponseImpl value,
    $Res Function(_$DivisionResponseImpl) then,
  ) = __$$DivisionResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({int? id, String? name});
}

/// @nodoc
class __$$DivisionResponseImplCopyWithImpl<$Res>
    extends _$DivisionResponseCopyWithImpl<$Res, _$DivisionResponseImpl>
    implements _$$DivisionResponseImplCopyWith<$Res> {
  __$$DivisionResponseImplCopyWithImpl(
    _$DivisionResponseImpl _value,
    $Res Function(_$DivisionResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of DivisionResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? id = freezed, Object? name = freezed}) {
    return _then(
      _$DivisionResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$DivisionResponseImpl implements _DivisionResponse {
  const _$DivisionResponseImpl({this.id, this.name});

  factory _$DivisionResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$DivisionResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? name;

  @override
  String toString() {
    return 'DivisionResponse(id: $id, name: $name)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DivisionResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.name, name) || other.name == name));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, id, name);

  /// Create a copy of DivisionResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$DivisionResponseImplCopyWith<_$DivisionResponseImpl> get copyWith =>
      __$$DivisionResponseImplCopyWithImpl<_$DivisionResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$DivisionResponseImplToJson(this);
  }
}

abstract class _DivisionResponse implements DivisionResponse {
  const factory _DivisionResponse({final int? id, final String? name}) =
      _$DivisionResponseImpl;

  factory _DivisionResponse.fromJson(Map<String, dynamic> json) =
      _$DivisionResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get name;

  /// Create a copy of DivisionResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$DivisionResponseImplCopyWith<_$DivisionResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

DistrictResponse _$DistrictResponseFromJson(Map<String, dynamic> json) {
  return _DistrictResponse.fromJson(json);
}

/// @nodoc
mixin _$DistrictResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;

  /// Serializes this DistrictResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of DistrictResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $DistrictResponseCopyWith<DistrictResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $DistrictResponseCopyWith<$Res> {
  factory $DistrictResponseCopyWith(
    DistrictResponse value,
    $Res Function(DistrictResponse) then,
  ) = _$DistrictResponseCopyWithImpl<$Res, DistrictResponse>;
  @useResult
  $Res call({int? id, String? name});
}

/// @nodoc
class _$DistrictResponseCopyWithImpl<$Res, $Val extends DistrictResponse>
    implements $DistrictResponseCopyWith<$Res> {
  _$DistrictResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of DistrictResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? id = freezed, Object? name = freezed}) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            name: freezed == name
                ? _value.name
                : name // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$DistrictResponseImplCopyWith<$Res>
    implements $DistrictResponseCopyWith<$Res> {
  factory _$$DistrictResponseImplCopyWith(
    _$DistrictResponseImpl value,
    $Res Function(_$DistrictResponseImpl) then,
  ) = __$$DistrictResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({int? id, String? name});
}

/// @nodoc
class __$$DistrictResponseImplCopyWithImpl<$Res>
    extends _$DistrictResponseCopyWithImpl<$Res, _$DistrictResponseImpl>
    implements _$$DistrictResponseImplCopyWith<$Res> {
  __$$DistrictResponseImplCopyWithImpl(
    _$DistrictResponseImpl _value,
    $Res Function(_$DistrictResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of DistrictResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? id = freezed, Object? name = freezed}) {
    return _then(
      _$DistrictResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$DistrictResponseImpl implements _DistrictResponse {
  const _$DistrictResponseImpl({this.id, this.name});

  factory _$DistrictResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$DistrictResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? name;

  @override
  String toString() {
    return 'DistrictResponse(id: $id, name: $name)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DistrictResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.name, name) || other.name == name));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, id, name);

  /// Create a copy of DistrictResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$DistrictResponseImplCopyWith<_$DistrictResponseImpl> get copyWith =>
      __$$DistrictResponseImplCopyWithImpl<_$DistrictResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$DistrictResponseImplToJson(this);
  }
}

abstract class _DistrictResponse implements DistrictResponse {
  const factory _DistrictResponse({final int? id, final String? name}) =
      _$DistrictResponseImpl;

  factory _DistrictResponse.fromJson(Map<String, dynamic> json) =
      _$DistrictResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get name;

  /// Create a copy of DistrictResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$DistrictResponseImplCopyWith<_$DistrictResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

PoliceStationResponse _$PoliceStationResponseFromJson(
  Map<String, dynamic> json,
) {
  return _PoliceStationResponse.fromJson(json);
}

/// @nodoc
mixin _$PoliceStationResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;

  /// Serializes this PoliceStationResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of PoliceStationResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $PoliceStationResponseCopyWith<PoliceStationResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $PoliceStationResponseCopyWith<$Res> {
  factory $PoliceStationResponseCopyWith(
    PoliceStationResponse value,
    $Res Function(PoliceStationResponse) then,
  ) = _$PoliceStationResponseCopyWithImpl<$Res, PoliceStationResponse>;
  @useResult
  $Res call({int? id, String? name});
}

/// @nodoc
class _$PoliceStationResponseCopyWithImpl<
  $Res,
  $Val extends PoliceStationResponse
>
    implements $PoliceStationResponseCopyWith<$Res> {
  _$PoliceStationResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of PoliceStationResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? id = freezed, Object? name = freezed}) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            name: freezed == name
                ? _value.name
                : name // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$PoliceStationResponseImplCopyWith<$Res>
    implements $PoliceStationResponseCopyWith<$Res> {
  factory _$$PoliceStationResponseImplCopyWith(
    _$PoliceStationResponseImpl value,
    $Res Function(_$PoliceStationResponseImpl) then,
  ) = __$$PoliceStationResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({int? id, String? name});
}

/// @nodoc
class __$$PoliceStationResponseImplCopyWithImpl<$Res>
    extends
        _$PoliceStationResponseCopyWithImpl<$Res, _$PoliceStationResponseImpl>
    implements _$$PoliceStationResponseImplCopyWith<$Res> {
  __$$PoliceStationResponseImplCopyWithImpl(
    _$PoliceStationResponseImpl _value,
    $Res Function(_$PoliceStationResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of PoliceStationResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? id = freezed, Object? name = freezed}) {
    return _then(
      _$PoliceStationResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$PoliceStationResponseImpl implements _PoliceStationResponse {
  const _$PoliceStationResponseImpl({this.id, this.name});

  factory _$PoliceStationResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$PoliceStationResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? name;

  @override
  String toString() {
    return 'PoliceStationResponse(id: $id, name: $name)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$PoliceStationResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.name, name) || other.name == name));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, id, name);

  /// Create a copy of PoliceStationResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$PoliceStationResponseImplCopyWith<_$PoliceStationResponseImpl>
  get copyWith =>
      __$$PoliceStationResponseImplCopyWithImpl<_$PoliceStationResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$PoliceStationResponseImplToJson(this);
  }
}

abstract class _PoliceStationResponse implements PoliceStationResponse {
  const factory _PoliceStationResponse({final int? id, final String? name}) =
      _$PoliceStationResponseImpl;

  factory _PoliceStationResponse.fromJson(Map<String, dynamic> json) =
      _$PoliceStationResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get name;

  /// Create a copy of PoliceStationResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$PoliceStationResponseImplCopyWith<_$PoliceStationResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}

CurrencyResponse _$CurrencyResponseFromJson(Map<String, dynamic> json) {
  return _CurrencyResponse.fromJson(json);
}

/// @nodoc
mixin _$CurrencyResponse {
  Currency? get currency => throw _privateConstructorUsedError;
  double? get rate => throw _privateConstructorUsedError;

  /// Serializes this CurrencyResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of CurrencyResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $CurrencyResponseCopyWith<CurrencyResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CurrencyResponseCopyWith<$Res> {
  factory $CurrencyResponseCopyWith(
    CurrencyResponse value,
    $Res Function(CurrencyResponse) then,
  ) = _$CurrencyResponseCopyWithImpl<$Res, CurrencyResponse>;
  @useResult
  $Res call({Currency? currency, double? rate});
}

/// @nodoc
class _$CurrencyResponseCopyWithImpl<$Res, $Val extends CurrencyResponse>
    implements $CurrencyResponseCopyWith<$Res> {
  _$CurrencyResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of CurrencyResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? currency = freezed, Object? rate = freezed}) {
    return _then(
      _value.copyWith(
            currency: freezed == currency
                ? _value.currency
                : currency // ignore: cast_nullable_to_non_nullable
                      as Currency?,
            rate: freezed == rate
                ? _value.rate
                : rate // ignore: cast_nullable_to_non_nullable
                      as double?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$CurrencyResponseImplCopyWith<$Res>
    implements $CurrencyResponseCopyWith<$Res> {
  factory _$$CurrencyResponseImplCopyWith(
    _$CurrencyResponseImpl value,
    $Res Function(_$CurrencyResponseImpl) then,
  ) = __$$CurrencyResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({Currency? currency, double? rate});
}

/// @nodoc
class __$$CurrencyResponseImplCopyWithImpl<$Res>
    extends _$CurrencyResponseCopyWithImpl<$Res, _$CurrencyResponseImpl>
    implements _$$CurrencyResponseImplCopyWith<$Res> {
  __$$CurrencyResponseImplCopyWithImpl(
    _$CurrencyResponseImpl _value,
    $Res Function(_$CurrencyResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of CurrencyResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({Object? currency = freezed, Object? rate = freezed}) {
    return _then(
      _$CurrencyResponseImpl(
        currency: freezed == currency
            ? _value.currency
            : currency // ignore: cast_nullable_to_non_nullable
                  as Currency?,
        rate: freezed == rate
            ? _value.rate
            : rate // ignore: cast_nullable_to_non_nullable
                  as double?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$CurrencyResponseImpl implements _CurrencyResponse {
  const _$CurrencyResponseImpl({this.currency, this.rate});

  factory _$CurrencyResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$CurrencyResponseImplFromJson(json);

  @override
  final Currency? currency;
  @override
  final double? rate;

  @override
  String toString() {
    return 'CurrencyResponse(currency: $currency, rate: $rate)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CurrencyResponseImpl &&
            (identical(other.currency, currency) ||
                other.currency == currency) &&
            (identical(other.rate, rate) || other.rate == rate));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, currency, rate);

  /// Create a copy of CurrencyResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$CurrencyResponseImplCopyWith<_$CurrencyResponseImpl> get copyWith =>
      __$$CurrencyResponseImplCopyWithImpl<_$CurrencyResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$CurrencyResponseImplToJson(this);
  }
}

abstract class _CurrencyResponse implements CurrencyResponse {
  const factory _CurrencyResponse({
    final Currency? currency,
    final double? rate,
  }) = _$CurrencyResponseImpl;

  factory _CurrencyResponse.fromJson(Map<String, dynamic> json) =
      _$CurrencyResponseImpl.fromJson;

  @override
  Currency? get currency;
  @override
  double? get rate;

  /// Create a copy of CurrencyResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$CurrencyResponseImplCopyWith<_$CurrencyResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

CustomerDashboardResponse _$CustomerDashboardResponseFromJson(
  Map<String, dynamic> json,
) {
  return _CustomerDashboardResponse.fromJson(json);
}

/// @nodoc
mixin _$CustomerDashboardResponse {
  double? get balance => throw _privateConstructorUsedError;
  double? get totalCredit => throw _privateConstructorUsedError;
  double? get totalDebit => throw _privateConstructorUsedError;
  double? get totalLoan => throw _privateConstructorUsedError;
  int? get totalCard => throw _privateConstructorUsedError;
  int? get totalTransaction => throw _privateConstructorUsedError;
  int? get totalBeneficiary => throw _privateConstructorUsedError;
  int? get totalAccount => throw _privateConstructorUsedError;
  List<CardResponse>? get cards => throw _privateConstructorUsedError;
  List<AccountResponse>? get accounts => throw _privateConstructorUsedError;
  List<JournalResponse>? get last30DaysTransactions =>
      throw _privateConstructorUsedError;
  List<JournalResponse>? get recentTransactions =>
      throw _privateConstructorUsedError;

  /// Serializes this CustomerDashboardResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of CustomerDashboardResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $CustomerDashboardResponseCopyWith<CustomerDashboardResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CustomerDashboardResponseCopyWith<$Res> {
  factory $CustomerDashboardResponseCopyWith(
    CustomerDashboardResponse value,
    $Res Function(CustomerDashboardResponse) then,
  ) = _$CustomerDashboardResponseCopyWithImpl<$Res, CustomerDashboardResponse>;
  @useResult
  $Res call({
    double? balance,
    double? totalCredit,
    double? totalDebit,
    double? totalLoan,
    int? totalCard,
    int? totalTransaction,
    int? totalBeneficiary,
    int? totalAccount,
    List<CardResponse>? cards,
    List<AccountResponse>? accounts,
    List<JournalResponse>? last30DaysTransactions,
    List<JournalResponse>? recentTransactions,
  });
}

/// @nodoc
class _$CustomerDashboardResponseCopyWithImpl<
  $Res,
  $Val extends CustomerDashboardResponse
>
    implements $CustomerDashboardResponseCopyWith<$Res> {
  _$CustomerDashboardResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of CustomerDashboardResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? balance = freezed,
    Object? totalCredit = freezed,
    Object? totalDebit = freezed,
    Object? totalLoan = freezed,
    Object? totalCard = freezed,
    Object? totalTransaction = freezed,
    Object? totalBeneficiary = freezed,
    Object? totalAccount = freezed,
    Object? cards = freezed,
    Object? accounts = freezed,
    Object? last30DaysTransactions = freezed,
    Object? recentTransactions = freezed,
  }) {
    return _then(
      _value.copyWith(
            balance: freezed == balance
                ? _value.balance
                : balance // ignore: cast_nullable_to_non_nullable
                      as double?,
            totalCredit: freezed == totalCredit
                ? _value.totalCredit
                : totalCredit // ignore: cast_nullable_to_non_nullable
                      as double?,
            totalDebit: freezed == totalDebit
                ? _value.totalDebit
                : totalDebit // ignore: cast_nullable_to_non_nullable
                      as double?,
            totalLoan: freezed == totalLoan
                ? _value.totalLoan
                : totalLoan // ignore: cast_nullable_to_non_nullable
                      as double?,
            totalCard: freezed == totalCard
                ? _value.totalCard
                : totalCard // ignore: cast_nullable_to_non_nullable
                      as int?,
            totalTransaction: freezed == totalTransaction
                ? _value.totalTransaction
                : totalTransaction // ignore: cast_nullable_to_non_nullable
                      as int?,
            totalBeneficiary: freezed == totalBeneficiary
                ? _value.totalBeneficiary
                : totalBeneficiary // ignore: cast_nullable_to_non_nullable
                      as int?,
            totalAccount: freezed == totalAccount
                ? _value.totalAccount
                : totalAccount // ignore: cast_nullable_to_non_nullable
                      as int?,
            cards: freezed == cards
                ? _value.cards
                : cards // ignore: cast_nullable_to_non_nullable
                      as List<CardResponse>?,
            accounts: freezed == accounts
                ? _value.accounts
                : accounts // ignore: cast_nullable_to_non_nullable
                      as List<AccountResponse>?,
            last30DaysTransactions: freezed == last30DaysTransactions
                ? _value.last30DaysTransactions
                : last30DaysTransactions // ignore: cast_nullable_to_non_nullable
                      as List<JournalResponse>?,
            recentTransactions: freezed == recentTransactions
                ? _value.recentTransactions
                : recentTransactions // ignore: cast_nullable_to_non_nullable
                      as List<JournalResponse>?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$CustomerDashboardResponseImplCopyWith<$Res>
    implements $CustomerDashboardResponseCopyWith<$Res> {
  factory _$$CustomerDashboardResponseImplCopyWith(
    _$CustomerDashboardResponseImpl value,
    $Res Function(_$CustomerDashboardResponseImpl) then,
  ) = __$$CustomerDashboardResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    double? balance,
    double? totalCredit,
    double? totalDebit,
    double? totalLoan,
    int? totalCard,
    int? totalTransaction,
    int? totalBeneficiary,
    int? totalAccount,
    List<CardResponse>? cards,
    List<AccountResponse>? accounts,
    List<JournalResponse>? last30DaysTransactions,
    List<JournalResponse>? recentTransactions,
  });
}

/// @nodoc
class __$$CustomerDashboardResponseImplCopyWithImpl<$Res>
    extends
        _$CustomerDashboardResponseCopyWithImpl<
          $Res,
          _$CustomerDashboardResponseImpl
        >
    implements _$$CustomerDashboardResponseImplCopyWith<$Res> {
  __$$CustomerDashboardResponseImplCopyWithImpl(
    _$CustomerDashboardResponseImpl _value,
    $Res Function(_$CustomerDashboardResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of CustomerDashboardResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? balance = freezed,
    Object? totalCredit = freezed,
    Object? totalDebit = freezed,
    Object? totalLoan = freezed,
    Object? totalCard = freezed,
    Object? totalTransaction = freezed,
    Object? totalBeneficiary = freezed,
    Object? totalAccount = freezed,
    Object? cards = freezed,
    Object? accounts = freezed,
    Object? last30DaysTransactions = freezed,
    Object? recentTransactions = freezed,
  }) {
    return _then(
      _$CustomerDashboardResponseImpl(
        balance: freezed == balance
            ? _value.balance
            : balance // ignore: cast_nullable_to_non_nullable
                  as double?,
        totalCredit: freezed == totalCredit
            ? _value.totalCredit
            : totalCredit // ignore: cast_nullable_to_non_nullable
                  as double?,
        totalDebit: freezed == totalDebit
            ? _value.totalDebit
            : totalDebit // ignore: cast_nullable_to_non_nullable
                  as double?,
        totalLoan: freezed == totalLoan
            ? _value.totalLoan
            : totalLoan // ignore: cast_nullable_to_non_nullable
                  as double?,
        totalCard: freezed == totalCard
            ? _value.totalCard
            : totalCard // ignore: cast_nullable_to_non_nullable
                  as int?,
        totalTransaction: freezed == totalTransaction
            ? _value.totalTransaction
            : totalTransaction // ignore: cast_nullable_to_non_nullable
                  as int?,
        totalBeneficiary: freezed == totalBeneficiary
            ? _value.totalBeneficiary
            : totalBeneficiary // ignore: cast_nullable_to_non_nullable
                  as int?,
        totalAccount: freezed == totalAccount
            ? _value.totalAccount
            : totalAccount // ignore: cast_nullable_to_non_nullable
                  as int?,
        cards: freezed == cards
            ? _value._cards
            : cards // ignore: cast_nullable_to_non_nullable
                  as List<CardResponse>?,
        accounts: freezed == accounts
            ? _value._accounts
            : accounts // ignore: cast_nullable_to_non_nullable
                  as List<AccountResponse>?,
        last30DaysTransactions: freezed == last30DaysTransactions
            ? _value._last30DaysTransactions
            : last30DaysTransactions // ignore: cast_nullable_to_non_nullable
                  as List<JournalResponse>?,
        recentTransactions: freezed == recentTransactions
            ? _value._recentTransactions
            : recentTransactions // ignore: cast_nullable_to_non_nullable
                  as List<JournalResponse>?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$CustomerDashboardResponseImpl implements _CustomerDashboardResponse {
  const _$CustomerDashboardResponseImpl({
    this.balance,
    this.totalCredit,
    this.totalDebit,
    this.totalLoan,
    this.totalCard,
    this.totalTransaction,
    this.totalBeneficiary,
    this.totalAccount,
    final List<CardResponse>? cards,
    final List<AccountResponse>? accounts,
    final List<JournalResponse>? last30DaysTransactions,
    final List<JournalResponse>? recentTransactions,
  }) : _cards = cards,
       _accounts = accounts,
       _last30DaysTransactions = last30DaysTransactions,
       _recentTransactions = recentTransactions;

  factory _$CustomerDashboardResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$CustomerDashboardResponseImplFromJson(json);

  @override
  final double? balance;
  @override
  final double? totalCredit;
  @override
  final double? totalDebit;
  @override
  final double? totalLoan;
  @override
  final int? totalCard;
  @override
  final int? totalTransaction;
  @override
  final int? totalBeneficiary;
  @override
  final int? totalAccount;
  final List<CardResponse>? _cards;
  @override
  List<CardResponse>? get cards {
    final value = _cards;
    if (value == null) return null;
    if (_cards is EqualUnmodifiableListView) return _cards;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<AccountResponse>? _accounts;
  @override
  List<AccountResponse>? get accounts {
    final value = _accounts;
    if (value == null) return null;
    if (_accounts is EqualUnmodifiableListView) return _accounts;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<JournalResponse>? _last30DaysTransactions;
  @override
  List<JournalResponse>? get last30DaysTransactions {
    final value = _last30DaysTransactions;
    if (value == null) return null;
    if (_last30DaysTransactions is EqualUnmodifiableListView)
      return _last30DaysTransactions;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<JournalResponse>? _recentTransactions;
  @override
  List<JournalResponse>? get recentTransactions {
    final value = _recentTransactions;
    if (value == null) return null;
    if (_recentTransactions is EqualUnmodifiableListView)
      return _recentTransactions;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'CustomerDashboardResponse(balance: $balance, totalCredit: $totalCredit, totalDebit: $totalDebit, totalLoan: $totalLoan, totalCard: $totalCard, totalTransaction: $totalTransaction, totalBeneficiary: $totalBeneficiary, totalAccount: $totalAccount, cards: $cards, accounts: $accounts, last30DaysTransactions: $last30DaysTransactions, recentTransactions: $recentTransactions)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CustomerDashboardResponseImpl &&
            (identical(other.balance, balance) || other.balance == balance) &&
            (identical(other.totalCredit, totalCredit) ||
                other.totalCredit == totalCredit) &&
            (identical(other.totalDebit, totalDebit) ||
                other.totalDebit == totalDebit) &&
            (identical(other.totalLoan, totalLoan) ||
                other.totalLoan == totalLoan) &&
            (identical(other.totalCard, totalCard) ||
                other.totalCard == totalCard) &&
            (identical(other.totalTransaction, totalTransaction) ||
                other.totalTransaction == totalTransaction) &&
            (identical(other.totalBeneficiary, totalBeneficiary) ||
                other.totalBeneficiary == totalBeneficiary) &&
            (identical(other.totalAccount, totalAccount) ||
                other.totalAccount == totalAccount) &&
            const DeepCollectionEquality().equals(other._cards, _cards) &&
            const DeepCollectionEquality().equals(other._accounts, _accounts) &&
            const DeepCollectionEquality().equals(
              other._last30DaysTransactions,
              _last30DaysTransactions,
            ) &&
            const DeepCollectionEquality().equals(
              other._recentTransactions,
              _recentTransactions,
            ));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    balance,
    totalCredit,
    totalDebit,
    totalLoan,
    totalCard,
    totalTransaction,
    totalBeneficiary,
    totalAccount,
    const DeepCollectionEquality().hash(_cards),
    const DeepCollectionEquality().hash(_accounts),
    const DeepCollectionEquality().hash(_last30DaysTransactions),
    const DeepCollectionEquality().hash(_recentTransactions),
  );

  /// Create a copy of CustomerDashboardResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$CustomerDashboardResponseImplCopyWith<_$CustomerDashboardResponseImpl>
  get copyWith =>
      __$$CustomerDashboardResponseImplCopyWithImpl<
        _$CustomerDashboardResponseImpl
      >(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$CustomerDashboardResponseImplToJson(this);
  }
}

abstract class _CustomerDashboardResponse implements CustomerDashboardResponse {
  const factory _CustomerDashboardResponse({
    final double? balance,
    final double? totalCredit,
    final double? totalDebit,
    final double? totalLoan,
    final int? totalCard,
    final int? totalTransaction,
    final int? totalBeneficiary,
    final int? totalAccount,
    final List<CardResponse>? cards,
    final List<AccountResponse>? accounts,
    final List<JournalResponse>? last30DaysTransactions,
    final List<JournalResponse>? recentTransactions,
  }) = _$CustomerDashboardResponseImpl;

  factory _CustomerDashboardResponse.fromJson(Map<String, dynamic> json) =
      _$CustomerDashboardResponseImpl.fromJson;

  @override
  double? get balance;
  @override
  double? get totalCredit;
  @override
  double? get totalDebit;
  @override
  double? get totalLoan;
  @override
  int? get totalCard;
  @override
  int? get totalTransaction;
  @override
  int? get totalBeneficiary;
  @override
  int? get totalAccount;
  @override
  List<CardResponse>? get cards;
  @override
  List<AccountResponse>? get accounts;
  @override
  List<JournalResponse>? get last30DaysTransactions;
  @override
  List<JournalResponse>? get recentTransactions;

  /// Create a copy of CustomerDashboardResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$CustomerDashboardResponseImplCopyWith<_$CustomerDashboardResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}
