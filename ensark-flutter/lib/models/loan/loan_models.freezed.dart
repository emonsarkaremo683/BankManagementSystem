// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'loan_models.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
  'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models',
);

LoanApplicationRequest _$LoanApplicationRequestFromJson(
  Map<String, dynamic> json,
) {
  return _LoanApplicationRequest.fromJson(json);
}

/// @nodoc
mixin _$LoanApplicationRequest {
  int? get accountId => throw _privateConstructorUsedError;
  double? get principalAmount => throw _privateConstructorUsedError;
  double? get annualInterestRate => throw _privateConstructorUsedError;
  int? get tenureMonths => throw _privateConstructorUsedError;
  GuarantorRequest? get guarantor => throw _privateConstructorUsedError;

  /// Serializes this LoanApplicationRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of LoanApplicationRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $LoanApplicationRequestCopyWith<LoanApplicationRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LoanApplicationRequestCopyWith<$Res> {
  factory $LoanApplicationRequestCopyWith(
    LoanApplicationRequest value,
    $Res Function(LoanApplicationRequest) then,
  ) = _$LoanApplicationRequestCopyWithImpl<$Res, LoanApplicationRequest>;
  @useResult
  $Res call({
    int? accountId,
    double? principalAmount,
    double? annualInterestRate,
    int? tenureMonths,
    GuarantorRequest? guarantor,
  });

  $GuarantorRequestCopyWith<$Res>? get guarantor;
}

/// @nodoc
class _$LoanApplicationRequestCopyWithImpl<
  $Res,
  $Val extends LoanApplicationRequest
>
    implements $LoanApplicationRequestCopyWith<$Res> {
  _$LoanApplicationRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of LoanApplicationRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? accountId = freezed,
    Object? principalAmount = freezed,
    Object? annualInterestRate = freezed,
    Object? tenureMonths = freezed,
    Object? guarantor = freezed,
  }) {
    return _then(
      _value.copyWith(
            accountId: freezed == accountId
                ? _value.accountId
                : accountId // ignore: cast_nullable_to_non_nullable
                      as int?,
            principalAmount: freezed == principalAmount
                ? _value.principalAmount
                : principalAmount // ignore: cast_nullable_to_non_nullable
                      as double?,
            annualInterestRate: freezed == annualInterestRate
                ? _value.annualInterestRate
                : annualInterestRate // ignore: cast_nullable_to_non_nullable
                      as double?,
            tenureMonths: freezed == tenureMonths
                ? _value.tenureMonths
                : tenureMonths // ignore: cast_nullable_to_non_nullable
                      as int?,
            guarantor: freezed == guarantor
                ? _value.guarantor
                : guarantor // ignore: cast_nullable_to_non_nullable
                      as GuarantorRequest?,
          )
          as $Val,
    );
  }

  /// Create a copy of LoanApplicationRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @pragma('vm:prefer-inline')
  $GuarantorRequestCopyWith<$Res>? get guarantor {
    if (_value.guarantor == null) {
      return null;
    }

    return $GuarantorRequestCopyWith<$Res>(_value.guarantor!, (value) {
      return _then(_value.copyWith(guarantor: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$LoanApplicationRequestImplCopyWith<$Res>
    implements $LoanApplicationRequestCopyWith<$Res> {
  factory _$$LoanApplicationRequestImplCopyWith(
    _$LoanApplicationRequestImpl value,
    $Res Function(_$LoanApplicationRequestImpl) then,
  ) = __$$LoanApplicationRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? accountId,
    double? principalAmount,
    double? annualInterestRate,
    int? tenureMonths,
    GuarantorRequest? guarantor,
  });

  @override
  $GuarantorRequestCopyWith<$Res>? get guarantor;
}

/// @nodoc
class __$$LoanApplicationRequestImplCopyWithImpl<$Res>
    extends
        _$LoanApplicationRequestCopyWithImpl<$Res, _$LoanApplicationRequestImpl>
    implements _$$LoanApplicationRequestImplCopyWith<$Res> {
  __$$LoanApplicationRequestImplCopyWithImpl(
    _$LoanApplicationRequestImpl _value,
    $Res Function(_$LoanApplicationRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of LoanApplicationRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? accountId = freezed,
    Object? principalAmount = freezed,
    Object? annualInterestRate = freezed,
    Object? tenureMonths = freezed,
    Object? guarantor = freezed,
  }) {
    return _then(
      _$LoanApplicationRequestImpl(
        accountId: freezed == accountId
            ? _value.accountId
            : accountId // ignore: cast_nullable_to_non_nullable
                  as int?,
        principalAmount: freezed == principalAmount
            ? _value.principalAmount
            : principalAmount // ignore: cast_nullable_to_non_nullable
                  as double?,
        annualInterestRate: freezed == annualInterestRate
            ? _value.annualInterestRate
            : annualInterestRate // ignore: cast_nullable_to_non_nullable
                  as double?,
        tenureMonths: freezed == tenureMonths
            ? _value.tenureMonths
            : tenureMonths // ignore: cast_nullable_to_non_nullable
                  as int?,
        guarantor: freezed == guarantor
            ? _value.guarantor
            : guarantor // ignore: cast_nullable_to_non_nullable
                  as GuarantorRequest?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$LoanApplicationRequestImpl implements _LoanApplicationRequest {
  const _$LoanApplicationRequestImpl({
    this.accountId,
    this.principalAmount,
    this.annualInterestRate,
    this.tenureMonths,
    this.guarantor,
  });

  factory _$LoanApplicationRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$LoanApplicationRequestImplFromJson(json);

  @override
  final int? accountId;
  @override
  final double? principalAmount;
  @override
  final double? annualInterestRate;
  @override
  final int? tenureMonths;
  @override
  final GuarantorRequest? guarantor;

  @override
  String toString() {
    return 'LoanApplicationRequest(accountId: $accountId, principalAmount: $principalAmount, annualInterestRate: $annualInterestRate, tenureMonths: $tenureMonths, guarantor: $guarantor)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoanApplicationRequestImpl &&
            (identical(other.accountId, accountId) ||
                other.accountId == accountId) &&
            (identical(other.principalAmount, principalAmount) ||
                other.principalAmount == principalAmount) &&
            (identical(other.annualInterestRate, annualInterestRate) ||
                other.annualInterestRate == annualInterestRate) &&
            (identical(other.tenureMonths, tenureMonths) ||
                other.tenureMonths == tenureMonths) &&
            (identical(other.guarantor, guarantor) ||
                other.guarantor == guarantor));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    accountId,
    principalAmount,
    annualInterestRate,
    tenureMonths,
    guarantor,
  );

  /// Create a copy of LoanApplicationRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$LoanApplicationRequestImplCopyWith<_$LoanApplicationRequestImpl>
  get copyWith =>
      __$$LoanApplicationRequestImplCopyWithImpl<_$LoanApplicationRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$LoanApplicationRequestImplToJson(this);
  }
}

abstract class _LoanApplicationRequest implements LoanApplicationRequest {
  const factory _LoanApplicationRequest({
    final int? accountId,
    final double? principalAmount,
    final double? annualInterestRate,
    final int? tenureMonths,
    final GuarantorRequest? guarantor,
  }) = _$LoanApplicationRequestImpl;

  factory _LoanApplicationRequest.fromJson(Map<String, dynamic> json) =
      _$LoanApplicationRequestImpl.fromJson;

  @override
  int? get accountId;
  @override
  double? get principalAmount;
  @override
  double? get annualInterestRate;
  @override
  int? get tenureMonths;
  @override
  GuarantorRequest? get guarantor;

  /// Create a copy of LoanApplicationRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$LoanApplicationRequestImplCopyWith<_$LoanApplicationRequestImpl>
  get copyWith => throw _privateConstructorUsedError;
}

GuarantorRequest _$GuarantorRequestFromJson(Map<String, dynamic> json) {
  return _GuarantorRequest.fromJson(json);
}

/// @nodoc
mixin _$GuarantorRequest {
  String? get name => throw _privateConstructorUsedError;
  String? get phone => throw _privateConstructorUsedError;
  String? get address => throw _privateConstructorUsedError;
  String? get nidNumber => throw _privateConstructorUsedError;
  String? get relation => throw _privateConstructorUsedError;

  /// Serializes this GuarantorRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of GuarantorRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $GuarantorRequestCopyWith<GuarantorRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $GuarantorRequestCopyWith<$Res> {
  factory $GuarantorRequestCopyWith(
    GuarantorRequest value,
    $Res Function(GuarantorRequest) then,
  ) = _$GuarantorRequestCopyWithImpl<$Res, GuarantorRequest>;
  @useResult
  $Res call({
    String? name,
    String? phone,
    String? address,
    String? nidNumber,
    String? relation,
  });
}

/// @nodoc
class _$GuarantorRequestCopyWithImpl<$Res, $Val extends GuarantorRequest>
    implements $GuarantorRequestCopyWith<$Res> {
  _$GuarantorRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of GuarantorRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = freezed,
    Object? phone = freezed,
    Object? address = freezed,
    Object? nidNumber = freezed,
    Object? relation = freezed,
  }) {
    return _then(
      _value.copyWith(
            name: freezed == name
                ? _value.name
                : name // ignore: cast_nullable_to_non_nullable
                      as String?,
            phone: freezed == phone
                ? _value.phone
                : phone // ignore: cast_nullable_to_non_nullable
                      as String?,
            address: freezed == address
                ? _value.address
                : address // ignore: cast_nullable_to_non_nullable
                      as String?,
            nidNumber: freezed == nidNumber
                ? _value.nidNumber
                : nidNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            relation: freezed == relation
                ? _value.relation
                : relation // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$GuarantorRequestImplCopyWith<$Res>
    implements $GuarantorRequestCopyWith<$Res> {
  factory _$$GuarantorRequestImplCopyWith(
    _$GuarantorRequestImpl value,
    $Res Function(_$GuarantorRequestImpl) then,
  ) = __$$GuarantorRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    String? name,
    String? phone,
    String? address,
    String? nidNumber,
    String? relation,
  });
}

/// @nodoc
class __$$GuarantorRequestImplCopyWithImpl<$Res>
    extends _$GuarantorRequestCopyWithImpl<$Res, _$GuarantorRequestImpl>
    implements _$$GuarantorRequestImplCopyWith<$Res> {
  __$$GuarantorRequestImplCopyWithImpl(
    _$GuarantorRequestImpl _value,
    $Res Function(_$GuarantorRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of GuarantorRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = freezed,
    Object? phone = freezed,
    Object? address = freezed,
    Object? nidNumber = freezed,
    Object? relation = freezed,
  }) {
    return _then(
      _$GuarantorRequestImpl(
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
        phone: freezed == phone
            ? _value.phone
            : phone // ignore: cast_nullable_to_non_nullable
                  as String?,
        address: freezed == address
            ? _value.address
            : address // ignore: cast_nullable_to_non_nullable
                  as String?,
        nidNumber: freezed == nidNumber
            ? _value.nidNumber
            : nidNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        relation: freezed == relation
            ? _value.relation
            : relation // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$GuarantorRequestImpl implements _GuarantorRequest {
  const _$GuarantorRequestImpl({
    this.name,
    this.phone,
    this.address,
    this.nidNumber,
    this.relation,
  });

  factory _$GuarantorRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$GuarantorRequestImplFromJson(json);

  @override
  final String? name;
  @override
  final String? phone;
  @override
  final String? address;
  @override
  final String? nidNumber;
  @override
  final String? relation;

  @override
  String toString() {
    return 'GuarantorRequest(name: $name, phone: $phone, address: $address, nidNumber: $nidNumber, relation: $relation)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$GuarantorRequestImpl &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.phone, phone) || other.phone == phone) &&
            (identical(other.address, address) || other.address == address) &&
            (identical(other.nidNumber, nidNumber) ||
                other.nidNumber == nidNumber) &&
            (identical(other.relation, relation) ||
                other.relation == relation));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode =>
      Object.hash(runtimeType, name, phone, address, nidNumber, relation);

  /// Create a copy of GuarantorRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$GuarantorRequestImplCopyWith<_$GuarantorRequestImpl> get copyWith =>
      __$$GuarantorRequestImplCopyWithImpl<_$GuarantorRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$GuarantorRequestImplToJson(this);
  }
}

abstract class _GuarantorRequest implements GuarantorRequest {
  const factory _GuarantorRequest({
    final String? name,
    final String? phone,
    final String? address,
    final String? nidNumber,
    final String? relation,
  }) = _$GuarantorRequestImpl;

  factory _GuarantorRequest.fromJson(Map<String, dynamic> json) =
      _$GuarantorRequestImpl.fromJson;

  @override
  String? get name;
  @override
  String? get phone;
  @override
  String? get address;
  @override
  String? get nidNumber;
  @override
  String? get relation;

  /// Create a copy of GuarantorRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$GuarantorRequestImplCopyWith<_$GuarantorRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

LoanApplicationResponse _$LoanApplicationResponseFromJson(
  Map<String, dynamic> json,
) {
  return _LoanApplicationResponse.fromJson(json);
}

/// @nodoc
mixin _$LoanApplicationResponse {
  int? get loanId => throw _privateConstructorUsedError;
  int? get accountId => throw _privateConstructorUsedError;
  String? get accountNumber => throw _privateConstructorUsedError;
  double? get principalAmount => throw _privateConstructorUsedError;
  double? get annualInterestRate => throw _privateConstructorUsedError;
  int? get tenureMonths => throw _privateConstructorUsedError;
  double? get emiAmount => throw _privateConstructorUsedError;
  double? get totalPayable => throw _privateConstructorUsedError;
  double? get outstandingBalance => throw _privateConstructorUsedError;
  double? get disbursementCharge => throw _privateConstructorUsedError;
  LoanStatus? get status => throw _privateConstructorUsedError;
  DateTime? get applicationDate => throw _privateConstructorUsedError;
  DateTime? get approvalDate => throw _privateConstructorUsedError;
  DateTime? get disbursementDate => throw _privateConstructorUsedError;
  DateTime? get nextDueDate => throw _privateConstructorUsedError;
  String? get rejectionReason => throw _privateConstructorUsedError;
  String? get disbursementTransactionRef => throw _privateConstructorUsedError;
  List<GuarantorResponse>? get guarantors => throw _privateConstructorUsedError;
  List<DocumentResponse>? get documents => throw _privateConstructorUsedError;

  /// Serializes this LoanApplicationResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of LoanApplicationResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $LoanApplicationResponseCopyWith<LoanApplicationResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LoanApplicationResponseCopyWith<$Res> {
  factory $LoanApplicationResponseCopyWith(
    LoanApplicationResponse value,
    $Res Function(LoanApplicationResponse) then,
  ) = _$LoanApplicationResponseCopyWithImpl<$Res, LoanApplicationResponse>;
  @useResult
  $Res call({
    int? loanId,
    int? accountId,
    String? accountNumber,
    double? principalAmount,
    double? annualInterestRate,
    int? tenureMonths,
    double? emiAmount,
    double? totalPayable,
    double? outstandingBalance,
    double? disbursementCharge,
    LoanStatus? status,
    DateTime? applicationDate,
    DateTime? approvalDate,
    DateTime? disbursementDate,
    DateTime? nextDueDate,
    String? rejectionReason,
    String? disbursementTransactionRef,
    List<GuarantorResponse>? guarantors,
    List<DocumentResponse>? documents,
  });
}

/// @nodoc
class _$LoanApplicationResponseCopyWithImpl<
  $Res,
  $Val extends LoanApplicationResponse
>
    implements $LoanApplicationResponseCopyWith<$Res> {
  _$LoanApplicationResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of LoanApplicationResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loanId = freezed,
    Object? accountId = freezed,
    Object? accountNumber = freezed,
    Object? principalAmount = freezed,
    Object? annualInterestRate = freezed,
    Object? tenureMonths = freezed,
    Object? emiAmount = freezed,
    Object? totalPayable = freezed,
    Object? outstandingBalance = freezed,
    Object? disbursementCharge = freezed,
    Object? status = freezed,
    Object? applicationDate = freezed,
    Object? approvalDate = freezed,
    Object? disbursementDate = freezed,
    Object? nextDueDate = freezed,
    Object? rejectionReason = freezed,
    Object? disbursementTransactionRef = freezed,
    Object? guarantors = freezed,
    Object? documents = freezed,
  }) {
    return _then(
      _value.copyWith(
            loanId: freezed == loanId
                ? _value.loanId
                : loanId // ignore: cast_nullable_to_non_nullable
                      as int?,
            accountId: freezed == accountId
                ? _value.accountId
                : accountId // ignore: cast_nullable_to_non_nullable
                      as int?,
            accountNumber: freezed == accountNumber
                ? _value.accountNumber
                : accountNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            principalAmount: freezed == principalAmount
                ? _value.principalAmount
                : principalAmount // ignore: cast_nullable_to_non_nullable
                      as double?,
            annualInterestRate: freezed == annualInterestRate
                ? _value.annualInterestRate
                : annualInterestRate // ignore: cast_nullable_to_non_nullable
                      as double?,
            tenureMonths: freezed == tenureMonths
                ? _value.tenureMonths
                : tenureMonths // ignore: cast_nullable_to_non_nullable
                      as int?,
            emiAmount: freezed == emiAmount
                ? _value.emiAmount
                : emiAmount // ignore: cast_nullable_to_non_nullable
                      as double?,
            totalPayable: freezed == totalPayable
                ? _value.totalPayable
                : totalPayable // ignore: cast_nullable_to_non_nullable
                      as double?,
            outstandingBalance: freezed == outstandingBalance
                ? _value.outstandingBalance
                : outstandingBalance // ignore: cast_nullable_to_non_nullable
                      as double?,
            disbursementCharge: freezed == disbursementCharge
                ? _value.disbursementCharge
                : disbursementCharge // ignore: cast_nullable_to_non_nullable
                      as double?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as LoanStatus?,
            applicationDate: freezed == applicationDate
                ? _value.applicationDate
                : applicationDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            approvalDate: freezed == approvalDate
                ? _value.approvalDate
                : approvalDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            disbursementDate: freezed == disbursementDate
                ? _value.disbursementDate
                : disbursementDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            nextDueDate: freezed == nextDueDate
                ? _value.nextDueDate
                : nextDueDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            rejectionReason: freezed == rejectionReason
                ? _value.rejectionReason
                : rejectionReason // ignore: cast_nullable_to_non_nullable
                      as String?,
            disbursementTransactionRef: freezed == disbursementTransactionRef
                ? _value.disbursementTransactionRef
                : disbursementTransactionRef // ignore: cast_nullable_to_non_nullable
                      as String?,
            guarantors: freezed == guarantors
                ? _value.guarantors
                : guarantors // ignore: cast_nullable_to_non_nullable
                      as List<GuarantorResponse>?,
            documents: freezed == documents
                ? _value.documents
                : documents // ignore: cast_nullable_to_non_nullable
                      as List<DocumentResponse>?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$LoanApplicationResponseImplCopyWith<$Res>
    implements $LoanApplicationResponseCopyWith<$Res> {
  factory _$$LoanApplicationResponseImplCopyWith(
    _$LoanApplicationResponseImpl value,
    $Res Function(_$LoanApplicationResponseImpl) then,
  ) = __$$LoanApplicationResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? loanId,
    int? accountId,
    String? accountNumber,
    double? principalAmount,
    double? annualInterestRate,
    int? tenureMonths,
    double? emiAmount,
    double? totalPayable,
    double? outstandingBalance,
    double? disbursementCharge,
    LoanStatus? status,
    DateTime? applicationDate,
    DateTime? approvalDate,
    DateTime? disbursementDate,
    DateTime? nextDueDate,
    String? rejectionReason,
    String? disbursementTransactionRef,
    List<GuarantorResponse>? guarantors,
    List<DocumentResponse>? documents,
  });
}

/// @nodoc
class __$$LoanApplicationResponseImplCopyWithImpl<$Res>
    extends
        _$LoanApplicationResponseCopyWithImpl<
          $Res,
          _$LoanApplicationResponseImpl
        >
    implements _$$LoanApplicationResponseImplCopyWith<$Res> {
  __$$LoanApplicationResponseImplCopyWithImpl(
    _$LoanApplicationResponseImpl _value,
    $Res Function(_$LoanApplicationResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of LoanApplicationResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loanId = freezed,
    Object? accountId = freezed,
    Object? accountNumber = freezed,
    Object? principalAmount = freezed,
    Object? annualInterestRate = freezed,
    Object? tenureMonths = freezed,
    Object? emiAmount = freezed,
    Object? totalPayable = freezed,
    Object? outstandingBalance = freezed,
    Object? disbursementCharge = freezed,
    Object? status = freezed,
    Object? applicationDate = freezed,
    Object? approvalDate = freezed,
    Object? disbursementDate = freezed,
    Object? nextDueDate = freezed,
    Object? rejectionReason = freezed,
    Object? disbursementTransactionRef = freezed,
    Object? guarantors = freezed,
    Object? documents = freezed,
  }) {
    return _then(
      _$LoanApplicationResponseImpl(
        loanId: freezed == loanId
            ? _value.loanId
            : loanId // ignore: cast_nullable_to_non_nullable
                  as int?,
        accountId: freezed == accountId
            ? _value.accountId
            : accountId // ignore: cast_nullable_to_non_nullable
                  as int?,
        accountNumber: freezed == accountNumber
            ? _value.accountNumber
            : accountNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        principalAmount: freezed == principalAmount
            ? _value.principalAmount
            : principalAmount // ignore: cast_nullable_to_non_nullable
                  as double?,
        annualInterestRate: freezed == annualInterestRate
            ? _value.annualInterestRate
            : annualInterestRate // ignore: cast_nullable_to_non_nullable
                  as double?,
        tenureMonths: freezed == tenureMonths
            ? _value.tenureMonths
            : tenureMonths // ignore: cast_nullable_to_non_nullable
                  as int?,
        emiAmount: freezed == emiAmount
            ? _value.emiAmount
            : emiAmount // ignore: cast_nullable_to_non_nullable
                  as double?,
        totalPayable: freezed == totalPayable
            ? _value.totalPayable
            : totalPayable // ignore: cast_nullable_to_non_nullable
                  as double?,
        outstandingBalance: freezed == outstandingBalance
            ? _value.outstandingBalance
            : outstandingBalance // ignore: cast_nullable_to_non_nullable
                  as double?,
        disbursementCharge: freezed == disbursementCharge
            ? _value.disbursementCharge
            : disbursementCharge // ignore: cast_nullable_to_non_nullable
                  as double?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as LoanStatus?,
        applicationDate: freezed == applicationDate
            ? _value.applicationDate
            : applicationDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        approvalDate: freezed == approvalDate
            ? _value.approvalDate
            : approvalDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        disbursementDate: freezed == disbursementDate
            ? _value.disbursementDate
            : disbursementDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        nextDueDate: freezed == nextDueDate
            ? _value.nextDueDate
            : nextDueDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        rejectionReason: freezed == rejectionReason
            ? _value.rejectionReason
            : rejectionReason // ignore: cast_nullable_to_non_nullable
                  as String?,
        disbursementTransactionRef: freezed == disbursementTransactionRef
            ? _value.disbursementTransactionRef
            : disbursementTransactionRef // ignore: cast_nullable_to_non_nullable
                  as String?,
        guarantors: freezed == guarantors
            ? _value._guarantors
            : guarantors // ignore: cast_nullable_to_non_nullable
                  as List<GuarantorResponse>?,
        documents: freezed == documents
            ? _value._documents
            : documents // ignore: cast_nullable_to_non_nullable
                  as List<DocumentResponse>?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$LoanApplicationResponseImpl implements _LoanApplicationResponse {
  const _$LoanApplicationResponseImpl({
    this.loanId,
    this.accountId,
    this.accountNumber,
    this.principalAmount,
    this.annualInterestRate,
    this.tenureMonths,
    this.emiAmount,
    this.totalPayable,
    this.outstandingBalance,
    this.disbursementCharge,
    this.status,
    this.applicationDate,
    this.approvalDate,
    this.disbursementDate,
    this.nextDueDate,
    this.rejectionReason,
    this.disbursementTransactionRef,
    final List<GuarantorResponse>? guarantors,
    final List<DocumentResponse>? documents,
  }) : _guarantors = guarantors,
       _documents = documents;

  factory _$LoanApplicationResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$LoanApplicationResponseImplFromJson(json);

  @override
  final int? loanId;
  @override
  final int? accountId;
  @override
  final String? accountNumber;
  @override
  final double? principalAmount;
  @override
  final double? annualInterestRate;
  @override
  final int? tenureMonths;
  @override
  final double? emiAmount;
  @override
  final double? totalPayable;
  @override
  final double? outstandingBalance;
  @override
  final double? disbursementCharge;
  @override
  final LoanStatus? status;
  @override
  final DateTime? applicationDate;
  @override
  final DateTime? approvalDate;
  @override
  final DateTime? disbursementDate;
  @override
  final DateTime? nextDueDate;
  @override
  final String? rejectionReason;
  @override
  final String? disbursementTransactionRef;
  final List<GuarantorResponse>? _guarantors;
  @override
  List<GuarantorResponse>? get guarantors {
    final value = _guarantors;
    if (value == null) return null;
    if (_guarantors is EqualUnmodifiableListView) return _guarantors;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<DocumentResponse>? _documents;
  @override
  List<DocumentResponse>? get documents {
    final value = _documents;
    if (value == null) return null;
    if (_documents is EqualUnmodifiableListView) return _documents;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'LoanApplicationResponse(loanId: $loanId, accountId: $accountId, accountNumber: $accountNumber, principalAmount: $principalAmount, annualInterestRate: $annualInterestRate, tenureMonths: $tenureMonths, emiAmount: $emiAmount, totalPayable: $totalPayable, outstandingBalance: $outstandingBalance, disbursementCharge: $disbursementCharge, status: $status, applicationDate: $applicationDate, approvalDate: $approvalDate, disbursementDate: $disbursementDate, nextDueDate: $nextDueDate, rejectionReason: $rejectionReason, disbursementTransactionRef: $disbursementTransactionRef, guarantors: $guarantors, documents: $documents)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoanApplicationResponseImpl &&
            (identical(other.loanId, loanId) || other.loanId == loanId) &&
            (identical(other.accountId, accountId) ||
                other.accountId == accountId) &&
            (identical(other.accountNumber, accountNumber) ||
                other.accountNumber == accountNumber) &&
            (identical(other.principalAmount, principalAmount) ||
                other.principalAmount == principalAmount) &&
            (identical(other.annualInterestRate, annualInterestRate) ||
                other.annualInterestRate == annualInterestRate) &&
            (identical(other.tenureMonths, tenureMonths) ||
                other.tenureMonths == tenureMonths) &&
            (identical(other.emiAmount, emiAmount) ||
                other.emiAmount == emiAmount) &&
            (identical(other.totalPayable, totalPayable) ||
                other.totalPayable == totalPayable) &&
            (identical(other.outstandingBalance, outstandingBalance) ||
                other.outstandingBalance == outstandingBalance) &&
            (identical(other.disbursementCharge, disbursementCharge) ||
                other.disbursementCharge == disbursementCharge) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.applicationDate, applicationDate) ||
                other.applicationDate == applicationDate) &&
            (identical(other.approvalDate, approvalDate) ||
                other.approvalDate == approvalDate) &&
            (identical(other.disbursementDate, disbursementDate) ||
                other.disbursementDate == disbursementDate) &&
            (identical(other.nextDueDate, nextDueDate) ||
                other.nextDueDate == nextDueDate) &&
            (identical(other.rejectionReason, rejectionReason) ||
                other.rejectionReason == rejectionReason) &&
            (identical(
                  other.disbursementTransactionRef,
                  disbursementTransactionRef,
                ) ||
                other.disbursementTransactionRef ==
                    disbursementTransactionRef) &&
            const DeepCollectionEquality().equals(
              other._guarantors,
              _guarantors,
            ) &&
            const DeepCollectionEquality().equals(
              other._documents,
              _documents,
            ));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hashAll([
    runtimeType,
    loanId,
    accountId,
    accountNumber,
    principalAmount,
    annualInterestRate,
    tenureMonths,
    emiAmount,
    totalPayable,
    outstandingBalance,
    disbursementCharge,
    status,
    applicationDate,
    approvalDate,
    disbursementDate,
    nextDueDate,
    rejectionReason,
    disbursementTransactionRef,
    const DeepCollectionEquality().hash(_guarantors),
    const DeepCollectionEquality().hash(_documents),
  ]);

  /// Create a copy of LoanApplicationResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$LoanApplicationResponseImplCopyWith<_$LoanApplicationResponseImpl>
  get copyWith =>
      __$$LoanApplicationResponseImplCopyWithImpl<
        _$LoanApplicationResponseImpl
      >(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$LoanApplicationResponseImplToJson(this);
  }
}

abstract class _LoanApplicationResponse implements LoanApplicationResponse {
  const factory _LoanApplicationResponse({
    final int? loanId,
    final int? accountId,
    final String? accountNumber,
    final double? principalAmount,
    final double? annualInterestRate,
    final int? tenureMonths,
    final double? emiAmount,
    final double? totalPayable,
    final double? outstandingBalance,
    final double? disbursementCharge,
    final LoanStatus? status,
    final DateTime? applicationDate,
    final DateTime? approvalDate,
    final DateTime? disbursementDate,
    final DateTime? nextDueDate,
    final String? rejectionReason,
    final String? disbursementTransactionRef,
    final List<GuarantorResponse>? guarantors,
    final List<DocumentResponse>? documents,
  }) = _$LoanApplicationResponseImpl;

  factory _LoanApplicationResponse.fromJson(Map<String, dynamic> json) =
      _$LoanApplicationResponseImpl.fromJson;

  @override
  int? get loanId;
  @override
  int? get accountId;
  @override
  String? get accountNumber;
  @override
  double? get principalAmount;
  @override
  double? get annualInterestRate;
  @override
  int? get tenureMonths;
  @override
  double? get emiAmount;
  @override
  double? get totalPayable;
  @override
  double? get outstandingBalance;
  @override
  double? get disbursementCharge;
  @override
  LoanStatus? get status;
  @override
  DateTime? get applicationDate;
  @override
  DateTime? get approvalDate;
  @override
  DateTime? get disbursementDate;
  @override
  DateTime? get nextDueDate;
  @override
  String? get rejectionReason;
  @override
  String? get disbursementTransactionRef;
  @override
  List<GuarantorResponse>? get guarantors;
  @override
  List<DocumentResponse>? get documents;

  /// Create a copy of LoanApplicationResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$LoanApplicationResponseImplCopyWith<_$LoanApplicationResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}

GuarantorResponse _$GuarantorResponseFromJson(Map<String, dynamic> json) {
  return _GuarantorResponse.fromJson(json);
}

/// @nodoc
mixin _$GuarantorResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  String? get phone => throw _privateConstructorUsedError;
  String? get address => throw _privateConstructorUsedError;
  String? get nidNumber => throw _privateConstructorUsedError;
  String? get relation => throw _privateConstructorUsedError;
  String? get photoPath => throw _privateConstructorUsedError;

  /// Serializes this GuarantorResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of GuarantorResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $GuarantorResponseCopyWith<GuarantorResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $GuarantorResponseCopyWith<$Res> {
  factory $GuarantorResponseCopyWith(
    GuarantorResponse value,
    $Res Function(GuarantorResponse) then,
  ) = _$GuarantorResponseCopyWithImpl<$Res, GuarantorResponse>;
  @useResult
  $Res call({
    int? id,
    String? name,
    String? phone,
    String? address,
    String? nidNumber,
    String? relation,
    String? photoPath,
  });
}

/// @nodoc
class _$GuarantorResponseCopyWithImpl<$Res, $Val extends GuarantorResponse>
    implements $GuarantorResponseCopyWith<$Res> {
  _$GuarantorResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of GuarantorResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? name = freezed,
    Object? phone = freezed,
    Object? address = freezed,
    Object? nidNumber = freezed,
    Object? relation = freezed,
    Object? photoPath = freezed,
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
            phone: freezed == phone
                ? _value.phone
                : phone // ignore: cast_nullable_to_non_nullable
                      as String?,
            address: freezed == address
                ? _value.address
                : address // ignore: cast_nullable_to_non_nullable
                      as String?,
            nidNumber: freezed == nidNumber
                ? _value.nidNumber
                : nidNumber // ignore: cast_nullable_to_non_nullable
                      as String?,
            relation: freezed == relation
                ? _value.relation
                : relation // ignore: cast_nullable_to_non_nullable
                      as String?,
            photoPath: freezed == photoPath
                ? _value.photoPath
                : photoPath // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$GuarantorResponseImplCopyWith<$Res>
    implements $GuarantorResponseCopyWith<$Res> {
  factory _$$GuarantorResponseImplCopyWith(
    _$GuarantorResponseImpl value,
    $Res Function(_$GuarantorResponseImpl) then,
  ) = __$$GuarantorResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    String? name,
    String? phone,
    String? address,
    String? nidNumber,
    String? relation,
    String? photoPath,
  });
}

/// @nodoc
class __$$GuarantorResponseImplCopyWithImpl<$Res>
    extends _$GuarantorResponseCopyWithImpl<$Res, _$GuarantorResponseImpl>
    implements _$$GuarantorResponseImplCopyWith<$Res> {
  __$$GuarantorResponseImplCopyWithImpl(
    _$GuarantorResponseImpl _value,
    $Res Function(_$GuarantorResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of GuarantorResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? name = freezed,
    Object? phone = freezed,
    Object? address = freezed,
    Object? nidNumber = freezed,
    Object? relation = freezed,
    Object? photoPath = freezed,
  }) {
    return _then(
      _$GuarantorResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
        phone: freezed == phone
            ? _value.phone
            : phone // ignore: cast_nullable_to_non_nullable
                  as String?,
        address: freezed == address
            ? _value.address
            : address // ignore: cast_nullable_to_non_nullable
                  as String?,
        nidNumber: freezed == nidNumber
            ? _value.nidNumber
            : nidNumber // ignore: cast_nullable_to_non_nullable
                  as String?,
        relation: freezed == relation
            ? _value.relation
            : relation // ignore: cast_nullable_to_non_nullable
                  as String?,
        photoPath: freezed == photoPath
            ? _value.photoPath
            : photoPath // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$GuarantorResponseImpl implements _GuarantorResponse {
  const _$GuarantorResponseImpl({
    this.id,
    this.name,
    this.phone,
    this.address,
    this.nidNumber,
    this.relation,
    this.photoPath,
  });

  factory _$GuarantorResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$GuarantorResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? name;
  @override
  final String? phone;
  @override
  final String? address;
  @override
  final String? nidNumber;
  @override
  final String? relation;
  @override
  final String? photoPath;

  @override
  String toString() {
    return 'GuarantorResponse(id: $id, name: $name, phone: $phone, address: $address, nidNumber: $nidNumber, relation: $relation, photoPath: $photoPath)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$GuarantorResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.phone, phone) || other.phone == phone) &&
            (identical(other.address, address) || other.address == address) &&
            (identical(other.nidNumber, nidNumber) ||
                other.nidNumber == nidNumber) &&
            (identical(other.relation, relation) ||
                other.relation == relation) &&
            (identical(other.photoPath, photoPath) ||
                other.photoPath == photoPath));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    name,
    phone,
    address,
    nidNumber,
    relation,
    photoPath,
  );

  /// Create a copy of GuarantorResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$GuarantorResponseImplCopyWith<_$GuarantorResponseImpl> get copyWith =>
      __$$GuarantorResponseImplCopyWithImpl<_$GuarantorResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$GuarantorResponseImplToJson(this);
  }
}

abstract class _GuarantorResponse implements GuarantorResponse {
  const factory _GuarantorResponse({
    final int? id,
    final String? name,
    final String? phone,
    final String? address,
    final String? nidNumber,
    final String? relation,
    final String? photoPath,
  }) = _$GuarantorResponseImpl;

  factory _GuarantorResponse.fromJson(Map<String, dynamic> json) =
      _$GuarantorResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get name;
  @override
  String? get phone;
  @override
  String? get address;
  @override
  String? get nidNumber;
  @override
  String? get relation;
  @override
  String? get photoPath;

  /// Create a copy of GuarantorResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$GuarantorResponseImplCopyWith<_$GuarantorResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

DocumentResponse _$DocumentResponseFromJson(Map<String, dynamic> json) {
  return _DocumentResponse.fromJson(json);
}

/// @nodoc
mixin _$DocumentResponse {
  int? get id => throw _privateConstructorUsedError;
  String? get fileName => throw _privateConstructorUsedError;
  String? get originalFileName => throw _privateConstructorUsedError;
  String? get contentType => throw _privateConstructorUsedError;
  int? get fileSize => throw _privateConstructorUsedError;

  /// Serializes this DocumentResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of DocumentResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $DocumentResponseCopyWith<DocumentResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $DocumentResponseCopyWith<$Res> {
  factory $DocumentResponseCopyWith(
    DocumentResponse value,
    $Res Function(DocumentResponse) then,
  ) = _$DocumentResponseCopyWithImpl<$Res, DocumentResponse>;
  @useResult
  $Res call({
    int? id,
    String? fileName,
    String? originalFileName,
    String? contentType,
    int? fileSize,
  });
}

/// @nodoc
class _$DocumentResponseCopyWithImpl<$Res, $Val extends DocumentResponse>
    implements $DocumentResponseCopyWith<$Res> {
  _$DocumentResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of DocumentResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? fileName = freezed,
    Object? originalFileName = freezed,
    Object? contentType = freezed,
    Object? fileSize = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            fileName: freezed == fileName
                ? _value.fileName
                : fileName // ignore: cast_nullable_to_non_nullable
                      as String?,
            originalFileName: freezed == originalFileName
                ? _value.originalFileName
                : originalFileName // ignore: cast_nullable_to_non_nullable
                      as String?,
            contentType: freezed == contentType
                ? _value.contentType
                : contentType // ignore: cast_nullable_to_non_nullable
                      as String?,
            fileSize: freezed == fileSize
                ? _value.fileSize
                : fileSize // ignore: cast_nullable_to_non_nullable
                      as int?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$DocumentResponseImplCopyWith<$Res>
    implements $DocumentResponseCopyWith<$Res> {
  factory _$$DocumentResponseImplCopyWith(
    _$DocumentResponseImpl value,
    $Res Function(_$DocumentResponseImpl) then,
  ) = __$$DocumentResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    String? fileName,
    String? originalFileName,
    String? contentType,
    int? fileSize,
  });
}

/// @nodoc
class __$$DocumentResponseImplCopyWithImpl<$Res>
    extends _$DocumentResponseCopyWithImpl<$Res, _$DocumentResponseImpl>
    implements _$$DocumentResponseImplCopyWith<$Res> {
  __$$DocumentResponseImplCopyWithImpl(
    _$DocumentResponseImpl _value,
    $Res Function(_$DocumentResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of DocumentResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? fileName = freezed,
    Object? originalFileName = freezed,
    Object? contentType = freezed,
    Object? fileSize = freezed,
  }) {
    return _then(
      _$DocumentResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        fileName: freezed == fileName
            ? _value.fileName
            : fileName // ignore: cast_nullable_to_non_nullable
                  as String?,
        originalFileName: freezed == originalFileName
            ? _value.originalFileName
            : originalFileName // ignore: cast_nullable_to_non_nullable
                  as String?,
        contentType: freezed == contentType
            ? _value.contentType
            : contentType // ignore: cast_nullable_to_non_nullable
                  as String?,
        fileSize: freezed == fileSize
            ? _value.fileSize
            : fileSize // ignore: cast_nullable_to_non_nullable
                  as int?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$DocumentResponseImpl implements _DocumentResponse {
  const _$DocumentResponseImpl({
    this.id,
    this.fileName,
    this.originalFileName,
    this.contentType,
    this.fileSize,
  });

  factory _$DocumentResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$DocumentResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final String? fileName;
  @override
  final String? originalFileName;
  @override
  final String? contentType;
  @override
  final int? fileSize;

  @override
  String toString() {
    return 'DocumentResponse(id: $id, fileName: $fileName, originalFileName: $originalFileName, contentType: $contentType, fileSize: $fileSize)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DocumentResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.fileName, fileName) ||
                other.fileName == fileName) &&
            (identical(other.originalFileName, originalFileName) ||
                other.originalFileName == originalFileName) &&
            (identical(other.contentType, contentType) ||
                other.contentType == contentType) &&
            (identical(other.fileSize, fileSize) ||
                other.fileSize == fileSize));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    fileName,
    originalFileName,
    contentType,
    fileSize,
  );

  /// Create a copy of DocumentResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$DocumentResponseImplCopyWith<_$DocumentResponseImpl> get copyWith =>
      __$$DocumentResponseImplCopyWithImpl<_$DocumentResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$DocumentResponseImplToJson(this);
  }
}

abstract class _DocumentResponse implements DocumentResponse {
  const factory _DocumentResponse({
    final int? id,
    final String? fileName,
    final String? originalFileName,
    final String? contentType,
    final int? fileSize,
  }) = _$DocumentResponseImpl;

  factory _DocumentResponse.fromJson(Map<String, dynamic> json) =
      _$DocumentResponseImpl.fromJson;

  @override
  int? get id;
  @override
  String? get fileName;
  @override
  String? get originalFileName;
  @override
  String? get contentType;
  @override
  int? get fileSize;

  /// Create a copy of DocumentResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$DocumentResponseImplCopyWith<_$DocumentResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

LoanRepaymentResponse _$LoanRepaymentResponseFromJson(
  Map<String, dynamic> json,
) {
  return _LoanRepaymentResponse.fromJson(json);
}

/// @nodoc
mixin _$LoanRepaymentResponse {
  int? get id => throw _privateConstructorUsedError;
  int? get loanId => throw _privateConstructorUsedError;
  int? get installmentNumber => throw _privateConstructorUsedError;
  DateTime? get dueDate => throw _privateConstructorUsedError;
  double? get principalComponent => throw _privateConstructorUsedError;
  double? get interestComponent => throw _privateConstructorUsedError;
  double? get emiAmount => throw _privateConstructorUsedError;
  double? get remainingBalanceAfter => throw _privateConstructorUsedError;
  RepaymentStatus? get status => throw _privateConstructorUsedError;
  DateTime? get paidDate => throw _privateConstructorUsedError;
  String? get transactionRef => throw _privateConstructorUsedError;

  /// Serializes this LoanRepaymentResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of LoanRepaymentResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $LoanRepaymentResponseCopyWith<LoanRepaymentResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LoanRepaymentResponseCopyWith<$Res> {
  factory $LoanRepaymentResponseCopyWith(
    LoanRepaymentResponse value,
    $Res Function(LoanRepaymentResponse) then,
  ) = _$LoanRepaymentResponseCopyWithImpl<$Res, LoanRepaymentResponse>;
  @useResult
  $Res call({
    int? id,
    int? loanId,
    int? installmentNumber,
    DateTime? dueDate,
    double? principalComponent,
    double? interestComponent,
    double? emiAmount,
    double? remainingBalanceAfter,
    RepaymentStatus? status,
    DateTime? paidDate,
    String? transactionRef,
  });
}

/// @nodoc
class _$LoanRepaymentResponseCopyWithImpl<
  $Res,
  $Val extends LoanRepaymentResponse
>
    implements $LoanRepaymentResponseCopyWith<$Res> {
  _$LoanRepaymentResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of LoanRepaymentResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? loanId = freezed,
    Object? installmentNumber = freezed,
    Object? dueDate = freezed,
    Object? principalComponent = freezed,
    Object? interestComponent = freezed,
    Object? emiAmount = freezed,
    Object? remainingBalanceAfter = freezed,
    Object? status = freezed,
    Object? paidDate = freezed,
    Object? transactionRef = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            loanId: freezed == loanId
                ? _value.loanId
                : loanId // ignore: cast_nullable_to_non_nullable
                      as int?,
            installmentNumber: freezed == installmentNumber
                ? _value.installmentNumber
                : installmentNumber // ignore: cast_nullable_to_non_nullable
                      as int?,
            dueDate: freezed == dueDate
                ? _value.dueDate
                : dueDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            principalComponent: freezed == principalComponent
                ? _value.principalComponent
                : principalComponent // ignore: cast_nullable_to_non_nullable
                      as double?,
            interestComponent: freezed == interestComponent
                ? _value.interestComponent
                : interestComponent // ignore: cast_nullable_to_non_nullable
                      as double?,
            emiAmount: freezed == emiAmount
                ? _value.emiAmount
                : emiAmount // ignore: cast_nullable_to_non_nullable
                      as double?,
            remainingBalanceAfter: freezed == remainingBalanceAfter
                ? _value.remainingBalanceAfter
                : remainingBalanceAfter // ignore: cast_nullable_to_non_nullable
                      as double?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as RepaymentStatus?,
            paidDate: freezed == paidDate
                ? _value.paidDate
                : paidDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            transactionRef: freezed == transactionRef
                ? _value.transactionRef
                : transactionRef // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$LoanRepaymentResponseImplCopyWith<$Res>
    implements $LoanRepaymentResponseCopyWith<$Res> {
  factory _$$LoanRepaymentResponseImplCopyWith(
    _$LoanRepaymentResponseImpl value,
    $Res Function(_$LoanRepaymentResponseImpl) then,
  ) = __$$LoanRepaymentResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    int? loanId,
    int? installmentNumber,
    DateTime? dueDate,
    double? principalComponent,
    double? interestComponent,
    double? emiAmount,
    double? remainingBalanceAfter,
    RepaymentStatus? status,
    DateTime? paidDate,
    String? transactionRef,
  });
}

/// @nodoc
class __$$LoanRepaymentResponseImplCopyWithImpl<$Res>
    extends
        _$LoanRepaymentResponseCopyWithImpl<$Res, _$LoanRepaymentResponseImpl>
    implements _$$LoanRepaymentResponseImplCopyWith<$Res> {
  __$$LoanRepaymentResponseImplCopyWithImpl(
    _$LoanRepaymentResponseImpl _value,
    $Res Function(_$LoanRepaymentResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of LoanRepaymentResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? loanId = freezed,
    Object? installmentNumber = freezed,
    Object? dueDate = freezed,
    Object? principalComponent = freezed,
    Object? interestComponent = freezed,
    Object? emiAmount = freezed,
    Object? remainingBalanceAfter = freezed,
    Object? status = freezed,
    Object? paidDate = freezed,
    Object? transactionRef = freezed,
  }) {
    return _then(
      _$LoanRepaymentResponseImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        loanId: freezed == loanId
            ? _value.loanId
            : loanId // ignore: cast_nullable_to_non_nullable
                  as int?,
        installmentNumber: freezed == installmentNumber
            ? _value.installmentNumber
            : installmentNumber // ignore: cast_nullable_to_non_nullable
                  as int?,
        dueDate: freezed == dueDate
            ? _value.dueDate
            : dueDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        principalComponent: freezed == principalComponent
            ? _value.principalComponent
            : principalComponent // ignore: cast_nullable_to_non_nullable
                  as double?,
        interestComponent: freezed == interestComponent
            ? _value.interestComponent
            : interestComponent // ignore: cast_nullable_to_non_nullable
                  as double?,
        emiAmount: freezed == emiAmount
            ? _value.emiAmount
            : emiAmount // ignore: cast_nullable_to_non_nullable
                  as double?,
        remainingBalanceAfter: freezed == remainingBalanceAfter
            ? _value.remainingBalanceAfter
            : remainingBalanceAfter // ignore: cast_nullable_to_non_nullable
                  as double?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as RepaymentStatus?,
        paidDate: freezed == paidDate
            ? _value.paidDate
            : paidDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        transactionRef: freezed == transactionRef
            ? _value.transactionRef
            : transactionRef // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$LoanRepaymentResponseImpl implements _LoanRepaymentResponse {
  const _$LoanRepaymentResponseImpl({
    this.id,
    this.loanId,
    this.installmentNumber,
    this.dueDate,
    this.principalComponent,
    this.interestComponent,
    this.emiAmount,
    this.remainingBalanceAfter,
    this.status,
    this.paidDate,
    this.transactionRef,
  });

  factory _$LoanRepaymentResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$LoanRepaymentResponseImplFromJson(json);

  @override
  final int? id;
  @override
  final int? loanId;
  @override
  final int? installmentNumber;
  @override
  final DateTime? dueDate;
  @override
  final double? principalComponent;
  @override
  final double? interestComponent;
  @override
  final double? emiAmount;
  @override
  final double? remainingBalanceAfter;
  @override
  final RepaymentStatus? status;
  @override
  final DateTime? paidDate;
  @override
  final String? transactionRef;

  @override
  String toString() {
    return 'LoanRepaymentResponse(id: $id, loanId: $loanId, installmentNumber: $installmentNumber, dueDate: $dueDate, principalComponent: $principalComponent, interestComponent: $interestComponent, emiAmount: $emiAmount, remainingBalanceAfter: $remainingBalanceAfter, status: $status, paidDate: $paidDate, transactionRef: $transactionRef)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoanRepaymentResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.loanId, loanId) || other.loanId == loanId) &&
            (identical(other.installmentNumber, installmentNumber) ||
                other.installmentNumber == installmentNumber) &&
            (identical(other.dueDate, dueDate) || other.dueDate == dueDate) &&
            (identical(other.principalComponent, principalComponent) ||
                other.principalComponent == principalComponent) &&
            (identical(other.interestComponent, interestComponent) ||
                other.interestComponent == interestComponent) &&
            (identical(other.emiAmount, emiAmount) ||
                other.emiAmount == emiAmount) &&
            (identical(other.remainingBalanceAfter, remainingBalanceAfter) ||
                other.remainingBalanceAfter == remainingBalanceAfter) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.paidDate, paidDate) ||
                other.paidDate == paidDate) &&
            (identical(other.transactionRef, transactionRef) ||
                other.transactionRef == transactionRef));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    loanId,
    installmentNumber,
    dueDate,
    principalComponent,
    interestComponent,
    emiAmount,
    remainingBalanceAfter,
    status,
    paidDate,
    transactionRef,
  );

  /// Create a copy of LoanRepaymentResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$LoanRepaymentResponseImplCopyWith<_$LoanRepaymentResponseImpl>
  get copyWith =>
      __$$LoanRepaymentResponseImplCopyWithImpl<_$LoanRepaymentResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$LoanRepaymentResponseImplToJson(this);
  }
}

abstract class _LoanRepaymentResponse implements LoanRepaymentResponse {
  const factory _LoanRepaymentResponse({
    final int? id,
    final int? loanId,
    final int? installmentNumber,
    final DateTime? dueDate,
    final double? principalComponent,
    final double? interestComponent,
    final double? emiAmount,
    final double? remainingBalanceAfter,
    final RepaymentStatus? status,
    final DateTime? paidDate,
    final String? transactionRef,
  }) = _$LoanRepaymentResponseImpl;

  factory _LoanRepaymentResponse.fromJson(Map<String, dynamic> json) =
      _$LoanRepaymentResponseImpl.fromJson;

  @override
  int? get id;
  @override
  int? get loanId;
  @override
  int? get installmentNumber;
  @override
  DateTime? get dueDate;
  @override
  double? get principalComponent;
  @override
  double? get interestComponent;
  @override
  double? get emiAmount;
  @override
  double? get remainingBalanceAfter;
  @override
  RepaymentStatus? get status;
  @override
  DateTime? get paidDate;
  @override
  String? get transactionRef;

  /// Create a copy of LoanRepaymentResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$LoanRepaymentResponseImplCopyWith<_$LoanRepaymentResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}

LoanScheduleResponse _$LoanScheduleResponseFromJson(Map<String, dynamic> json) {
  return _LoanScheduleResponse.fromJson(json);
}

/// @nodoc
mixin _$LoanScheduleResponse {
  int? get repaymentId => throw _privateConstructorUsedError;
  int? get installmentNumber => throw _privateConstructorUsedError;
  DateTime? get dueDate => throw _privateConstructorUsedError;
  double? get principalComponent => throw _privateConstructorUsedError;
  double? get interestComponent => throw _privateConstructorUsedError;
  double? get emiAmount => throw _privateConstructorUsedError;
  double? get remainingBalanceAfter => throw _privateConstructorUsedError;
  RepaymentStatus? get status => throw _privateConstructorUsedError;
  DateTime? get paidDate => throw _privateConstructorUsedError;
  String? get transactionRef => throw _privateConstructorUsedError;

  /// Serializes this LoanScheduleResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of LoanScheduleResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $LoanScheduleResponseCopyWith<LoanScheduleResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LoanScheduleResponseCopyWith<$Res> {
  factory $LoanScheduleResponseCopyWith(
    LoanScheduleResponse value,
    $Res Function(LoanScheduleResponse) then,
  ) = _$LoanScheduleResponseCopyWithImpl<$Res, LoanScheduleResponse>;
  @useResult
  $Res call({
    int? repaymentId,
    int? installmentNumber,
    DateTime? dueDate,
    double? principalComponent,
    double? interestComponent,
    double? emiAmount,
    double? remainingBalanceAfter,
    RepaymentStatus? status,
    DateTime? paidDate,
    String? transactionRef,
  });
}

/// @nodoc
class _$LoanScheduleResponseCopyWithImpl<
  $Res,
  $Val extends LoanScheduleResponse
>
    implements $LoanScheduleResponseCopyWith<$Res> {
  _$LoanScheduleResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of LoanScheduleResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? repaymentId = freezed,
    Object? installmentNumber = freezed,
    Object? dueDate = freezed,
    Object? principalComponent = freezed,
    Object? interestComponent = freezed,
    Object? emiAmount = freezed,
    Object? remainingBalanceAfter = freezed,
    Object? status = freezed,
    Object? paidDate = freezed,
    Object? transactionRef = freezed,
  }) {
    return _then(
      _value.copyWith(
            repaymentId: freezed == repaymentId
                ? _value.repaymentId
                : repaymentId // ignore: cast_nullable_to_non_nullable
                      as int?,
            installmentNumber: freezed == installmentNumber
                ? _value.installmentNumber
                : installmentNumber // ignore: cast_nullable_to_non_nullable
                      as int?,
            dueDate: freezed == dueDate
                ? _value.dueDate
                : dueDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            principalComponent: freezed == principalComponent
                ? _value.principalComponent
                : principalComponent // ignore: cast_nullable_to_non_nullable
                      as double?,
            interestComponent: freezed == interestComponent
                ? _value.interestComponent
                : interestComponent // ignore: cast_nullable_to_non_nullable
                      as double?,
            emiAmount: freezed == emiAmount
                ? _value.emiAmount
                : emiAmount // ignore: cast_nullable_to_non_nullable
                      as double?,
            remainingBalanceAfter: freezed == remainingBalanceAfter
                ? _value.remainingBalanceAfter
                : remainingBalanceAfter // ignore: cast_nullable_to_non_nullable
                      as double?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as RepaymentStatus?,
            paidDate: freezed == paidDate
                ? _value.paidDate
                : paidDate // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            transactionRef: freezed == transactionRef
                ? _value.transactionRef
                : transactionRef // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$LoanScheduleResponseImplCopyWith<$Res>
    implements $LoanScheduleResponseCopyWith<$Res> {
  factory _$$LoanScheduleResponseImplCopyWith(
    _$LoanScheduleResponseImpl value,
    $Res Function(_$LoanScheduleResponseImpl) then,
  ) = __$$LoanScheduleResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? repaymentId,
    int? installmentNumber,
    DateTime? dueDate,
    double? principalComponent,
    double? interestComponent,
    double? emiAmount,
    double? remainingBalanceAfter,
    RepaymentStatus? status,
    DateTime? paidDate,
    String? transactionRef,
  });
}

/// @nodoc
class __$$LoanScheduleResponseImplCopyWithImpl<$Res>
    extends _$LoanScheduleResponseCopyWithImpl<$Res, _$LoanScheduleResponseImpl>
    implements _$$LoanScheduleResponseImplCopyWith<$Res> {
  __$$LoanScheduleResponseImplCopyWithImpl(
    _$LoanScheduleResponseImpl _value,
    $Res Function(_$LoanScheduleResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of LoanScheduleResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? repaymentId = freezed,
    Object? installmentNumber = freezed,
    Object? dueDate = freezed,
    Object? principalComponent = freezed,
    Object? interestComponent = freezed,
    Object? emiAmount = freezed,
    Object? remainingBalanceAfter = freezed,
    Object? status = freezed,
    Object? paidDate = freezed,
    Object? transactionRef = freezed,
  }) {
    return _then(
      _$LoanScheduleResponseImpl(
        repaymentId: freezed == repaymentId
            ? _value.repaymentId
            : repaymentId // ignore: cast_nullable_to_non_nullable
                  as int?,
        installmentNumber: freezed == installmentNumber
            ? _value.installmentNumber
            : installmentNumber // ignore: cast_nullable_to_non_nullable
                  as int?,
        dueDate: freezed == dueDate
            ? _value.dueDate
            : dueDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        principalComponent: freezed == principalComponent
            ? _value.principalComponent
            : principalComponent // ignore: cast_nullable_to_non_nullable
                  as double?,
        interestComponent: freezed == interestComponent
            ? _value.interestComponent
            : interestComponent // ignore: cast_nullable_to_non_nullable
                  as double?,
        emiAmount: freezed == emiAmount
            ? _value.emiAmount
            : emiAmount // ignore: cast_nullable_to_non_nullable
                  as double?,
        remainingBalanceAfter: freezed == remainingBalanceAfter
            ? _value.remainingBalanceAfter
            : remainingBalanceAfter // ignore: cast_nullable_to_non_nullable
                  as double?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as RepaymentStatus?,
        paidDate: freezed == paidDate
            ? _value.paidDate
            : paidDate // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        transactionRef: freezed == transactionRef
            ? _value.transactionRef
            : transactionRef // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$LoanScheduleResponseImpl implements _LoanScheduleResponse {
  const _$LoanScheduleResponseImpl({
    this.repaymentId,
    this.installmentNumber,
    this.dueDate,
    this.principalComponent,
    this.interestComponent,
    this.emiAmount,
    this.remainingBalanceAfter,
    this.status,
    this.paidDate,
    this.transactionRef,
  });

  factory _$LoanScheduleResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$LoanScheduleResponseImplFromJson(json);

  @override
  final int? repaymentId;
  @override
  final int? installmentNumber;
  @override
  final DateTime? dueDate;
  @override
  final double? principalComponent;
  @override
  final double? interestComponent;
  @override
  final double? emiAmount;
  @override
  final double? remainingBalanceAfter;
  @override
  final RepaymentStatus? status;
  @override
  final DateTime? paidDate;
  @override
  final String? transactionRef;

  @override
  String toString() {
    return 'LoanScheduleResponse(repaymentId: $repaymentId, installmentNumber: $installmentNumber, dueDate: $dueDate, principalComponent: $principalComponent, interestComponent: $interestComponent, emiAmount: $emiAmount, remainingBalanceAfter: $remainingBalanceAfter, status: $status, paidDate: $paidDate, transactionRef: $transactionRef)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoanScheduleResponseImpl &&
            (identical(other.repaymentId, repaymentId) ||
                other.repaymentId == repaymentId) &&
            (identical(other.installmentNumber, installmentNumber) ||
                other.installmentNumber == installmentNumber) &&
            (identical(other.dueDate, dueDate) || other.dueDate == dueDate) &&
            (identical(other.principalComponent, principalComponent) ||
                other.principalComponent == principalComponent) &&
            (identical(other.interestComponent, interestComponent) ||
                other.interestComponent == interestComponent) &&
            (identical(other.emiAmount, emiAmount) ||
                other.emiAmount == emiAmount) &&
            (identical(other.remainingBalanceAfter, remainingBalanceAfter) ||
                other.remainingBalanceAfter == remainingBalanceAfter) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.paidDate, paidDate) ||
                other.paidDate == paidDate) &&
            (identical(other.transactionRef, transactionRef) ||
                other.transactionRef == transactionRef));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    repaymentId,
    installmentNumber,
    dueDate,
    principalComponent,
    interestComponent,
    emiAmount,
    remainingBalanceAfter,
    status,
    paidDate,
    transactionRef,
  );

  /// Create a copy of LoanScheduleResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$LoanScheduleResponseImplCopyWith<_$LoanScheduleResponseImpl>
  get copyWith =>
      __$$LoanScheduleResponseImplCopyWithImpl<_$LoanScheduleResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$LoanScheduleResponseImplToJson(this);
  }
}

abstract class _LoanScheduleResponse implements LoanScheduleResponse {
  const factory _LoanScheduleResponse({
    final int? repaymentId,
    final int? installmentNumber,
    final DateTime? dueDate,
    final double? principalComponent,
    final double? interestComponent,
    final double? emiAmount,
    final double? remainingBalanceAfter,
    final RepaymentStatus? status,
    final DateTime? paidDate,
    final String? transactionRef,
  }) = _$LoanScheduleResponseImpl;

  factory _LoanScheduleResponse.fromJson(Map<String, dynamic> json) =
      _$LoanScheduleResponseImpl.fromJson;

  @override
  int? get repaymentId;
  @override
  int? get installmentNumber;
  @override
  DateTime? get dueDate;
  @override
  double? get principalComponent;
  @override
  double? get interestComponent;
  @override
  double? get emiAmount;
  @override
  double? get remainingBalanceAfter;
  @override
  RepaymentStatus? get status;
  @override
  DateTime? get paidDate;
  @override
  String? get transactionRef;

  /// Create a copy of LoanScheduleResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$LoanScheduleResponseImplCopyWith<_$LoanScheduleResponseImpl>
  get copyWith => throw _privateConstructorUsedError;
}
