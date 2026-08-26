// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'customer_models.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
  'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models',
);

AddressRequest _$AddressRequestFromJson(Map<String, dynamic> json) {
  return _AddressRequest.fromJson(json);
}

/// @nodoc
mixin _$AddressRequest {
  String? get holdingNo => throw _privateConstructorUsedError;
  String? get area => throw _privateConstructorUsedError;
  String? get postalCode => throw _privateConstructorUsedError;
  AddressType get addressType => throw _privateConstructorUsedError;
  int get policeStationId => throw _privateConstructorUsedError;

  /// Serializes this AddressRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of AddressRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $AddressRequestCopyWith<AddressRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AddressRequestCopyWith<$Res> {
  factory $AddressRequestCopyWith(
    AddressRequest value,
    $Res Function(AddressRequest) then,
  ) = _$AddressRequestCopyWithImpl<$Res, AddressRequest>;
  @useResult
  $Res call({
    String? holdingNo,
    String? area,
    String? postalCode,
    AddressType addressType,
    int policeStationId,
  });
}

/// @nodoc
class _$AddressRequestCopyWithImpl<$Res, $Val extends AddressRequest>
    implements $AddressRequestCopyWith<$Res> {
  _$AddressRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of AddressRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? holdingNo = freezed,
    Object? area = freezed,
    Object? postalCode = freezed,
    Object? addressType = null,
    Object? policeStationId = null,
  }) {
    return _then(
      _value.copyWith(
            holdingNo: freezed == holdingNo
                ? _value.holdingNo
                : holdingNo // ignore: cast_nullable_to_non_nullable
                      as String?,
            area: freezed == area
                ? _value.area
                : area // ignore: cast_nullable_to_non_nullable
                      as String?,
            postalCode: freezed == postalCode
                ? _value.postalCode
                : postalCode // ignore: cast_nullable_to_non_nullable
                      as String?,
            addressType: null == addressType
                ? _value.addressType
                : addressType // ignore: cast_nullable_to_non_nullable
                      as AddressType,
            policeStationId: null == policeStationId
                ? _value.policeStationId
                : policeStationId // ignore: cast_nullable_to_non_nullable
                      as int,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$AddressRequestImplCopyWith<$Res>
    implements $AddressRequestCopyWith<$Res> {
  factory _$$AddressRequestImplCopyWith(
    _$AddressRequestImpl value,
    $Res Function(_$AddressRequestImpl) then,
  ) = __$$AddressRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    String? holdingNo,
    String? area,
    String? postalCode,
    AddressType addressType,
    int policeStationId,
  });
}

/// @nodoc
class __$$AddressRequestImplCopyWithImpl<$Res>
    extends _$AddressRequestCopyWithImpl<$Res, _$AddressRequestImpl>
    implements _$$AddressRequestImplCopyWith<$Res> {
  __$$AddressRequestImplCopyWithImpl(
    _$AddressRequestImpl _value,
    $Res Function(_$AddressRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of AddressRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? holdingNo = freezed,
    Object? area = freezed,
    Object? postalCode = freezed,
    Object? addressType = null,
    Object? policeStationId = null,
  }) {
    return _then(
      _$AddressRequestImpl(
        holdingNo: freezed == holdingNo
            ? _value.holdingNo
            : holdingNo // ignore: cast_nullable_to_non_nullable
                  as String?,
        area: freezed == area
            ? _value.area
            : area // ignore: cast_nullable_to_non_nullable
                  as String?,
        postalCode: freezed == postalCode
            ? _value.postalCode
            : postalCode // ignore: cast_nullable_to_non_nullable
                  as String?,
        addressType: null == addressType
            ? _value.addressType
            : addressType // ignore: cast_nullable_to_non_nullable
                  as AddressType,
        policeStationId: null == policeStationId
            ? _value.policeStationId
            : policeStationId // ignore: cast_nullable_to_non_nullable
                  as int,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$AddressRequestImpl implements _AddressRequest {
  const _$AddressRequestImpl({
    this.holdingNo,
    this.area,
    this.postalCode,
    required this.addressType,
    required this.policeStationId,
  });

  factory _$AddressRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$AddressRequestImplFromJson(json);

  @override
  final String? holdingNo;
  @override
  final String? area;
  @override
  final String? postalCode;
  @override
  final AddressType addressType;
  @override
  final int policeStationId;

  @override
  String toString() {
    return 'AddressRequest(holdingNo: $holdingNo, area: $area, postalCode: $postalCode, addressType: $addressType, policeStationId: $policeStationId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AddressRequestImpl &&
            (identical(other.holdingNo, holdingNo) ||
                other.holdingNo == holdingNo) &&
            (identical(other.area, area) || other.area == area) &&
            (identical(other.postalCode, postalCode) ||
                other.postalCode == postalCode) &&
            (identical(other.addressType, addressType) ||
                other.addressType == addressType) &&
            (identical(other.policeStationId, policeStationId) ||
                other.policeStationId == policeStationId));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    holdingNo,
    area,
    postalCode,
    addressType,
    policeStationId,
  );

  /// Create a copy of AddressRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$AddressRequestImplCopyWith<_$AddressRequestImpl> get copyWith =>
      __$$AddressRequestImplCopyWithImpl<_$AddressRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$AddressRequestImplToJson(this);
  }
}

abstract class _AddressRequest implements AddressRequest {
  const factory _AddressRequest({
    final String? holdingNo,
    final String? area,
    final String? postalCode,
    required final AddressType addressType,
    required final int policeStationId,
  }) = _$AddressRequestImpl;

  factory _AddressRequest.fromJson(Map<String, dynamic> json) =
      _$AddressRequestImpl.fromJson;

  @override
  String? get holdingNo;
  @override
  String? get area;
  @override
  String? get postalCode;
  @override
  AddressType get addressType;
  @override
  int get policeStationId;

  /// Create a copy of AddressRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$AddressRequestImplCopyWith<_$AddressRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

AddressResponse _$AddressResponseFromJson(Map<String, dynamic> json) {
  return _AddressResponse.fromJson(json);
}

/// @nodoc
mixin _$AddressResponse {
  int get id => throw _privateConstructorUsedError;
  String? get holdingNo => throw _privateConstructorUsedError;
  String? get area => throw _privateConstructorUsedError;
  String? get postalCode => throw _privateConstructorUsedError;
  AddressType get addressType => throw _privateConstructorUsedError;
  int? get policeStationId => throw _privateConstructorUsedError;
  String? get policeStationName => throw _privateConstructorUsedError;
  int? get districtId => throw _privateConstructorUsedError;
  String? get districtName => throw _privateConstructorUsedError;
  int? get divisionId => throw _privateConstructorUsedError;
  String? get divisionName => throw _privateConstructorUsedError;

  /// Serializes this AddressResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of AddressResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $AddressResponseCopyWith<AddressResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AddressResponseCopyWith<$Res> {
  factory $AddressResponseCopyWith(
    AddressResponse value,
    $Res Function(AddressResponse) then,
  ) = _$AddressResponseCopyWithImpl<$Res, AddressResponse>;
  @useResult
  $Res call({
    int id,
    String? holdingNo,
    String? area,
    String? postalCode,
    AddressType addressType,
    int? policeStationId,
    String? policeStationName,
    int? districtId,
    String? districtName,
    int? divisionId,
    String? divisionName,
  });
}

/// @nodoc
class _$AddressResponseCopyWithImpl<$Res, $Val extends AddressResponse>
    implements $AddressResponseCopyWith<$Res> {
  _$AddressResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of AddressResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
    Object? holdingNo = freezed,
    Object? area = freezed,
    Object? postalCode = freezed,
    Object? addressType = null,
    Object? policeStationId = freezed,
    Object? policeStationName = freezed,
    Object? districtId = freezed,
    Object? districtName = freezed,
    Object? divisionId = freezed,
    Object? divisionName = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: null == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int,
            holdingNo: freezed == holdingNo
                ? _value.holdingNo
                : holdingNo // ignore: cast_nullable_to_non_nullable
                      as String?,
            area: freezed == area
                ? _value.area
                : area // ignore: cast_nullable_to_non_nullable
                      as String?,
            postalCode: freezed == postalCode
                ? _value.postalCode
                : postalCode // ignore: cast_nullable_to_non_nullable
                      as String?,
            addressType: null == addressType
                ? _value.addressType
                : addressType // ignore: cast_nullable_to_non_nullable
                      as AddressType,
            policeStationId: freezed == policeStationId
                ? _value.policeStationId
                : policeStationId // ignore: cast_nullable_to_non_nullable
                      as int?,
            policeStationName: freezed == policeStationName
                ? _value.policeStationName
                : policeStationName // ignore: cast_nullable_to_non_nullable
                      as String?,
            districtId: freezed == districtId
                ? _value.districtId
                : districtId // ignore: cast_nullable_to_non_nullable
                      as int?,
            districtName: freezed == districtName
                ? _value.districtName
                : districtName // ignore: cast_nullable_to_non_nullable
                      as String?,
            divisionId: freezed == divisionId
                ? _value.divisionId
                : divisionId // ignore: cast_nullable_to_non_nullable
                      as int?,
            divisionName: freezed == divisionName
                ? _value.divisionName
                : divisionName // ignore: cast_nullable_to_non_nullable
                      as String?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$AddressResponseImplCopyWith<$Res>
    implements $AddressResponseCopyWith<$Res> {
  factory _$$AddressResponseImplCopyWith(
    _$AddressResponseImpl value,
    $Res Function(_$AddressResponseImpl) then,
  ) = __$$AddressResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int id,
    String? holdingNo,
    String? area,
    String? postalCode,
    AddressType addressType,
    int? policeStationId,
    String? policeStationName,
    int? districtId,
    String? districtName,
    int? divisionId,
    String? divisionName,
  });
}

/// @nodoc
class __$$AddressResponseImplCopyWithImpl<$Res>
    extends _$AddressResponseCopyWithImpl<$Res, _$AddressResponseImpl>
    implements _$$AddressResponseImplCopyWith<$Res> {
  __$$AddressResponseImplCopyWithImpl(
    _$AddressResponseImpl _value,
    $Res Function(_$AddressResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of AddressResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
    Object? holdingNo = freezed,
    Object? area = freezed,
    Object? postalCode = freezed,
    Object? addressType = null,
    Object? policeStationId = freezed,
    Object? policeStationName = freezed,
    Object? districtId = freezed,
    Object? districtName = freezed,
    Object? divisionId = freezed,
    Object? divisionName = freezed,
  }) {
    return _then(
      _$AddressResponseImpl(
        id: null == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int,
        holdingNo: freezed == holdingNo
            ? _value.holdingNo
            : holdingNo // ignore: cast_nullable_to_non_nullable
                  as String?,
        area: freezed == area
            ? _value.area
            : area // ignore: cast_nullable_to_non_nullable
                  as String?,
        postalCode: freezed == postalCode
            ? _value.postalCode
            : postalCode // ignore: cast_nullable_to_non_nullable
                  as String?,
        addressType: null == addressType
            ? _value.addressType
            : addressType // ignore: cast_nullable_to_non_nullable
                  as AddressType,
        policeStationId: freezed == policeStationId
            ? _value.policeStationId
            : policeStationId // ignore: cast_nullable_to_non_nullable
                  as int?,
        policeStationName: freezed == policeStationName
            ? _value.policeStationName
            : policeStationName // ignore: cast_nullable_to_non_nullable
                  as String?,
        districtId: freezed == districtId
            ? _value.districtId
            : districtId // ignore: cast_nullable_to_non_nullable
                  as int?,
        districtName: freezed == districtName
            ? _value.districtName
            : districtName // ignore: cast_nullable_to_non_nullable
                  as String?,
        divisionId: freezed == divisionId
            ? _value.divisionId
            : divisionId // ignore: cast_nullable_to_non_nullable
                  as int?,
        divisionName: freezed == divisionName
            ? _value.divisionName
            : divisionName // ignore: cast_nullable_to_non_nullable
                  as String?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$AddressResponseImpl implements _AddressResponse {
  const _$AddressResponseImpl({
    required this.id,
    this.holdingNo,
    this.area,
    this.postalCode,
    required this.addressType,
    this.policeStationId,
    this.policeStationName,
    this.districtId,
    this.districtName,
    this.divisionId,
    this.divisionName,
  });

  factory _$AddressResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$AddressResponseImplFromJson(json);

  @override
  final int id;
  @override
  final String? holdingNo;
  @override
  final String? area;
  @override
  final String? postalCode;
  @override
  final AddressType addressType;
  @override
  final int? policeStationId;
  @override
  final String? policeStationName;
  @override
  final int? districtId;
  @override
  final String? districtName;
  @override
  final int? divisionId;
  @override
  final String? divisionName;

  @override
  String toString() {
    return 'AddressResponse(id: $id, holdingNo: $holdingNo, area: $area, postalCode: $postalCode, addressType: $addressType, policeStationId: $policeStationId, policeStationName: $policeStationName, districtId: $districtId, districtName: $districtName, divisionId: $divisionId, divisionName: $divisionName)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AddressResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.holdingNo, holdingNo) ||
                other.holdingNo == holdingNo) &&
            (identical(other.area, area) || other.area == area) &&
            (identical(other.postalCode, postalCode) ||
                other.postalCode == postalCode) &&
            (identical(other.addressType, addressType) ||
                other.addressType == addressType) &&
            (identical(other.policeStationId, policeStationId) ||
                other.policeStationId == policeStationId) &&
            (identical(other.policeStationName, policeStationName) ||
                other.policeStationName == policeStationName) &&
            (identical(other.districtId, districtId) ||
                other.districtId == districtId) &&
            (identical(other.districtName, districtName) ||
                other.districtName == districtName) &&
            (identical(other.divisionId, divisionId) ||
                other.divisionId == divisionId) &&
            (identical(other.divisionName, divisionName) ||
                other.divisionName == divisionName));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    holdingNo,
    area,
    postalCode,
    addressType,
    policeStationId,
    policeStationName,
    districtId,
    districtName,
    divisionId,
    divisionName,
  );

  /// Create a copy of AddressResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$AddressResponseImplCopyWith<_$AddressResponseImpl> get copyWith =>
      __$$AddressResponseImplCopyWithImpl<_$AddressResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$AddressResponseImplToJson(this);
  }
}

abstract class _AddressResponse implements AddressResponse {
  const factory _AddressResponse({
    required final int id,
    final String? holdingNo,
    final String? area,
    final String? postalCode,
    required final AddressType addressType,
    final int? policeStationId,
    final String? policeStationName,
    final int? districtId,
    final String? districtName,
    final int? divisionId,
    final String? divisionName,
  }) = _$AddressResponseImpl;

  factory _AddressResponse.fromJson(Map<String, dynamic> json) =
      _$AddressResponseImpl.fromJson;

  @override
  int get id;
  @override
  String? get holdingNo;
  @override
  String? get area;
  @override
  String? get postalCode;
  @override
  AddressType get addressType;
  @override
  int? get policeStationId;
  @override
  String? get policeStationName;
  @override
  int? get districtId;
  @override
  String? get districtName;
  @override
  int? get divisionId;
  @override
  String? get divisionName;

  /// Create a copy of AddressResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$AddressResponseImplCopyWith<_$AddressResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

KycRequest _$KycRequestFromJson(Map<String, dynamic> json) {
  return _KycRequest.fromJson(json);
}

/// @nodoc
mixin _$KycRequest {
  int? get id => throw _privateConstructorUsedError;
  String? get path => throw _privateConstructorUsedError;
  @JsonKey(name: 'doc_type')
  DocumentType get docType => throw _privateConstructorUsedError;

  /// Serializes this KycRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of KycRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $KycRequestCopyWith<KycRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $KycRequestCopyWith<$Res> {
  factory $KycRequestCopyWith(
    KycRequest value,
    $Res Function(KycRequest) then,
  ) = _$KycRequestCopyWithImpl<$Res, KycRequest>;
  @useResult
  $Res call({
    int? id,
    String? path,
    @JsonKey(name: 'doc_type') DocumentType docType,
  });
}

/// @nodoc
class _$KycRequestCopyWithImpl<$Res, $Val extends KycRequest>
    implements $KycRequestCopyWith<$Res> {
  _$KycRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of KycRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? path = freezed,
    Object? docType = null,
  }) {
    return _then(
      _value.copyWith(
            id: freezed == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int?,
            path: freezed == path
                ? _value.path
                : path // ignore: cast_nullable_to_non_nullable
                      as String?,
            docType: null == docType
                ? _value.docType
                : docType // ignore: cast_nullable_to_non_nullable
                      as DocumentType,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$KycRequestImplCopyWith<$Res>
    implements $KycRequestCopyWith<$Res> {
  factory _$$KycRequestImplCopyWith(
    _$KycRequestImpl value,
    $Res Function(_$KycRequestImpl) then,
  ) = __$$KycRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int? id,
    String? path,
    @JsonKey(name: 'doc_type') DocumentType docType,
  });
}

/// @nodoc
class __$$KycRequestImplCopyWithImpl<$Res>
    extends _$KycRequestCopyWithImpl<$Res, _$KycRequestImpl>
    implements _$$KycRequestImplCopyWith<$Res> {
  __$$KycRequestImplCopyWithImpl(
    _$KycRequestImpl _value,
    $Res Function(_$KycRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of KycRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? path = freezed,
    Object? docType = null,
  }) {
    return _then(
      _$KycRequestImpl(
        id: freezed == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int?,
        path: freezed == path
            ? _value.path
            : path // ignore: cast_nullable_to_non_nullable
                  as String?,
        docType: null == docType
            ? _value.docType
            : docType // ignore: cast_nullable_to_non_nullable
                  as DocumentType,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$KycRequestImpl implements _KycRequest {
  const _$KycRequestImpl({
    this.id,
    this.path,
    @JsonKey(name: 'doc_type') required this.docType,
  });

  factory _$KycRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$KycRequestImplFromJson(json);

  @override
  final int? id;
  @override
  final String? path;
  @override
  @JsonKey(name: 'doc_type')
  final DocumentType docType;

  @override
  String toString() {
    return 'KycRequest(id: $id, path: $path, docType: $docType)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$KycRequestImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.path, path) || other.path == path) &&
            (identical(other.docType, docType) || other.docType == docType));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(runtimeType, id, path, docType);

  /// Create a copy of KycRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$KycRequestImplCopyWith<_$KycRequestImpl> get copyWith =>
      __$$KycRequestImplCopyWithImpl<_$KycRequestImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$KycRequestImplToJson(this);
  }
}

abstract class _KycRequest implements KycRequest {
  const factory _KycRequest({
    final int? id,
    final String? path,
    @JsonKey(name: 'doc_type') required final DocumentType docType,
  }) = _$KycRequestImpl;

  factory _KycRequest.fromJson(Map<String, dynamic> json) =
      _$KycRequestImpl.fromJson;

  @override
  int? get id;
  @override
  String? get path;
  @override
  @JsonKey(name: 'doc_type')
  DocumentType get docType;

  /// Create a copy of KycRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$KycRequestImplCopyWith<_$KycRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

CustomerRequest _$CustomerRequestFromJson(Map<String, dynamic> json) {
  return _CustomerRequest.fromJson(json);
}

/// @nodoc
mixin _$CustomerRequest {
  String get email => throw _privateConstructorUsedError;
  String get password => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  Gender? get gender => throw _privateConstructorUsedError;
  String? get phone => throw _privateConstructorUsedError;
  CustomerOccupation? get occupation => throw _privateConstructorUsedError;
  @IsoDateConverter()
  DateTime? get dob => throw _privateConstructorUsedError;
  String? get profile => throw _privateConstructorUsedError;
  List<AddressRequest> get addresses => throw _privateConstructorUsedError;
  List<KycRequest>? get kycRequests => throw _privateConstructorUsedError;

  /// Serializes this CustomerRequest to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of CustomerRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $CustomerRequestCopyWith<CustomerRequest> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CustomerRequestCopyWith<$Res> {
  factory $CustomerRequestCopyWith(
    CustomerRequest value,
    $Res Function(CustomerRequest) then,
  ) = _$CustomerRequestCopyWithImpl<$Res, CustomerRequest>;
  @useResult
  $Res call({
    String email,
    String password,
    String? name,
    Gender? gender,
    String? phone,
    CustomerOccupation? occupation,
    @IsoDateConverter() DateTime? dob,
    String? profile,
    List<AddressRequest> addresses,
    List<KycRequest>? kycRequests,
  });
}

/// @nodoc
class _$CustomerRequestCopyWithImpl<$Res, $Val extends CustomerRequest>
    implements $CustomerRequestCopyWith<$Res> {
  _$CustomerRequestCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of CustomerRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? email = null,
    Object? password = null,
    Object? name = freezed,
    Object? gender = freezed,
    Object? phone = freezed,
    Object? occupation = freezed,
    Object? dob = freezed,
    Object? profile = freezed,
    Object? addresses = null,
    Object? kycRequests = freezed,
  }) {
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
            name: freezed == name
                ? _value.name
                : name // ignore: cast_nullable_to_non_nullable
                      as String?,
            gender: freezed == gender
                ? _value.gender
                : gender // ignore: cast_nullable_to_non_nullable
                      as Gender?,
            phone: freezed == phone
                ? _value.phone
                : phone // ignore: cast_nullable_to_non_nullable
                      as String?,
            occupation: freezed == occupation
                ? _value.occupation
                : occupation // ignore: cast_nullable_to_non_nullable
                      as CustomerOccupation?,
            dob: freezed == dob
                ? _value.dob
                : dob // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            profile: freezed == profile
                ? _value.profile
                : profile // ignore: cast_nullable_to_non_nullable
                      as String?,
            addresses: null == addresses
                ? _value.addresses
                : addresses // ignore: cast_nullable_to_non_nullable
                      as List<AddressRequest>,
            kycRequests: freezed == kycRequests
                ? _value.kycRequests
                : kycRequests // ignore: cast_nullable_to_non_nullable
                      as List<KycRequest>?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$CustomerRequestImplCopyWith<$Res>
    implements $CustomerRequestCopyWith<$Res> {
  factory _$$CustomerRequestImplCopyWith(
    _$CustomerRequestImpl value,
    $Res Function(_$CustomerRequestImpl) then,
  ) = __$$CustomerRequestImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    String email,
    String password,
    String? name,
    Gender? gender,
    String? phone,
    CustomerOccupation? occupation,
    @IsoDateConverter() DateTime? dob,
    String? profile,
    List<AddressRequest> addresses,
    List<KycRequest>? kycRequests,
  });
}

/// @nodoc
class __$$CustomerRequestImplCopyWithImpl<$Res>
    extends _$CustomerRequestCopyWithImpl<$Res, _$CustomerRequestImpl>
    implements _$$CustomerRequestImplCopyWith<$Res> {
  __$$CustomerRequestImplCopyWithImpl(
    _$CustomerRequestImpl _value,
    $Res Function(_$CustomerRequestImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of CustomerRequest
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? email = null,
    Object? password = null,
    Object? name = freezed,
    Object? gender = freezed,
    Object? phone = freezed,
    Object? occupation = freezed,
    Object? dob = freezed,
    Object? profile = freezed,
    Object? addresses = null,
    Object? kycRequests = freezed,
  }) {
    return _then(
      _$CustomerRequestImpl(
        email: null == email
            ? _value.email
            : email // ignore: cast_nullable_to_non_nullable
                  as String,
        password: null == password
            ? _value.password
            : password // ignore: cast_nullable_to_non_nullable
                  as String,
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
        gender: freezed == gender
            ? _value.gender
            : gender // ignore: cast_nullable_to_non_nullable
                  as Gender?,
        phone: freezed == phone
            ? _value.phone
            : phone // ignore: cast_nullable_to_non_nullable
                  as String?,
        occupation: freezed == occupation
            ? _value.occupation
            : occupation // ignore: cast_nullable_to_non_nullable
                  as CustomerOccupation?,
        dob: freezed == dob
            ? _value.dob
            : dob // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        profile: freezed == profile
            ? _value.profile
            : profile // ignore: cast_nullable_to_non_nullable
                  as String?,
        addresses: null == addresses
            ? _value._addresses
            : addresses // ignore: cast_nullable_to_non_nullable
                  as List<AddressRequest>,
        kycRequests: freezed == kycRequests
            ? _value._kycRequests
            : kycRequests // ignore: cast_nullable_to_non_nullable
                  as List<KycRequest>?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$CustomerRequestImpl implements _CustomerRequest {
  const _$CustomerRequestImpl({
    required this.email,
    required this.password,
    this.name,
    this.gender,
    this.phone,
    this.occupation,
    @IsoDateConverter() this.dob,
    this.profile,
    required final List<AddressRequest> addresses,
    final List<KycRequest>? kycRequests,
  }) : _addresses = addresses,
       _kycRequests = kycRequests;

  factory _$CustomerRequestImpl.fromJson(Map<String, dynamic> json) =>
      _$$CustomerRequestImplFromJson(json);

  @override
  final String email;
  @override
  final String password;
  @override
  final String? name;
  @override
  final Gender? gender;
  @override
  final String? phone;
  @override
  final CustomerOccupation? occupation;
  @override
  @IsoDateConverter()
  final DateTime? dob;
  @override
  final String? profile;
  final List<AddressRequest> _addresses;
  @override
  List<AddressRequest> get addresses {
    if (_addresses is EqualUnmodifiableListView) return _addresses;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_addresses);
  }

  final List<KycRequest>? _kycRequests;
  @override
  List<KycRequest>? get kycRequests {
    final value = _kycRequests;
    if (value == null) return null;
    if (_kycRequests is EqualUnmodifiableListView) return _kycRequests;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'CustomerRequest(email: $email, password: $password, name: $name, gender: $gender, phone: $phone, occupation: $occupation, dob: $dob, profile: $profile, addresses: $addresses, kycRequests: $kycRequests)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CustomerRequestImpl &&
            (identical(other.email, email) || other.email == email) &&
            (identical(other.password, password) ||
                other.password == password) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.gender, gender) || other.gender == gender) &&
            (identical(other.phone, phone) || other.phone == phone) &&
            (identical(other.occupation, occupation) ||
                other.occupation == occupation) &&
            (identical(other.dob, dob) || other.dob == dob) &&
            (identical(other.profile, profile) || other.profile == profile) &&
            const DeepCollectionEquality().equals(
              other._addresses,
              _addresses,
            ) &&
            const DeepCollectionEquality().equals(
              other._kycRequests,
              _kycRequests,
            ));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    email,
    password,
    name,
    gender,
    phone,
    occupation,
    dob,
    profile,
    const DeepCollectionEquality().hash(_addresses),
    const DeepCollectionEquality().hash(_kycRequests),
  );

  /// Create a copy of CustomerRequest
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$CustomerRequestImplCopyWith<_$CustomerRequestImpl> get copyWith =>
      __$$CustomerRequestImplCopyWithImpl<_$CustomerRequestImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$CustomerRequestImplToJson(this);
  }
}

abstract class _CustomerRequest implements CustomerRequest {
  const factory _CustomerRequest({
    required final String email,
    required final String password,
    final String? name,
    final Gender? gender,
    final String? phone,
    final CustomerOccupation? occupation,
    @IsoDateConverter() final DateTime? dob,
    final String? profile,
    required final List<AddressRequest> addresses,
    final List<KycRequest>? kycRequests,
  }) = _$CustomerRequestImpl;

  factory _CustomerRequest.fromJson(Map<String, dynamic> json) =
      _$CustomerRequestImpl.fromJson;

  @override
  String get email;
  @override
  String get password;
  @override
  String? get name;
  @override
  Gender? get gender;
  @override
  String? get phone;
  @override
  CustomerOccupation? get occupation;
  @override
  @IsoDateConverter()
  DateTime? get dob;
  @override
  String? get profile;
  @override
  List<AddressRequest> get addresses;
  @override
  List<KycRequest>? get kycRequests;

  /// Create a copy of CustomerRequest
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$CustomerRequestImplCopyWith<_$CustomerRequestImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

CustomerResponse _$CustomerResponseFromJson(Map<String, dynamic> json) {
  return _CustomerResponse.fromJson(json);
}

/// @nodoc
mixin _$CustomerResponse {
  int get id => throw _privateConstructorUsedError;
  String get email => throw _privateConstructorUsedError;
  Role get role => throw _privateConstructorUsedError;
  @JsonKey(name: 'isEmailVerified')
  bool get isEmailVerified => throw _privateConstructorUsedError;
  bool get active => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  Gender? get gender => throw _privateConstructorUsedError;
  String? get phone => throw _privateConstructorUsedError;
  CustomerOccupation? get occupation => throw _privateConstructorUsedError;
  @IsoDateConverter()
  DateTime? get dob => throw _privateConstructorUsedError;
  String? get profile => throw _privateConstructorUsedError;
  List<AddressResponse> get addresses => throw _privateConstructorUsedError;
  List<KycRequest>? get documents => throw _privateConstructorUsedError;
  KYCStatus? get kycStatus => throw _privateConstructorUsedError;
  CustomerStatus? get status => throw _privateConstructorUsedError;

  /// Serializes this CustomerResponse to a JSON map.
  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;

  /// Create a copy of CustomerResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  $CustomerResponseCopyWith<CustomerResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CustomerResponseCopyWith<$Res> {
  factory $CustomerResponseCopyWith(
    CustomerResponse value,
    $Res Function(CustomerResponse) then,
  ) = _$CustomerResponseCopyWithImpl<$Res, CustomerResponse>;
  @useResult
  $Res call({
    int id,
    String email,
    Role role,
    @JsonKey(name: 'isEmailVerified') bool isEmailVerified,
    bool active,
    String? name,
    Gender? gender,
    String? phone,
    CustomerOccupation? occupation,
    @IsoDateConverter() DateTime? dob,
    String? profile,
    List<AddressResponse> addresses,
    List<KycRequest>? documents,
    KYCStatus? kycStatus,
    CustomerStatus? status,
  });
}

/// @nodoc
class _$CustomerResponseCopyWithImpl<$Res, $Val extends CustomerResponse>
    implements $CustomerResponseCopyWith<$Res> {
  _$CustomerResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  /// Create a copy of CustomerResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
    Object? email = null,
    Object? role = null,
    Object? isEmailVerified = null,
    Object? active = null,
    Object? name = freezed,
    Object? gender = freezed,
    Object? phone = freezed,
    Object? occupation = freezed,
    Object? dob = freezed,
    Object? profile = freezed,
    Object? addresses = null,
    Object? documents = freezed,
    Object? kycStatus = freezed,
    Object? status = freezed,
  }) {
    return _then(
      _value.copyWith(
            id: null == id
                ? _value.id
                : id // ignore: cast_nullable_to_non_nullable
                      as int,
            email: null == email
                ? _value.email
                : email // ignore: cast_nullable_to_non_nullable
                      as String,
            role: null == role
                ? _value.role
                : role // ignore: cast_nullable_to_non_nullable
                      as Role,
            isEmailVerified: null == isEmailVerified
                ? _value.isEmailVerified
                : isEmailVerified // ignore: cast_nullable_to_non_nullable
                      as bool,
            active: null == active
                ? _value.active
                : active // ignore: cast_nullable_to_non_nullable
                      as bool,
            name: freezed == name
                ? _value.name
                : name // ignore: cast_nullable_to_non_nullable
                      as String?,
            gender: freezed == gender
                ? _value.gender
                : gender // ignore: cast_nullable_to_non_nullable
                      as Gender?,
            phone: freezed == phone
                ? _value.phone
                : phone // ignore: cast_nullable_to_non_nullable
                      as String?,
            occupation: freezed == occupation
                ? _value.occupation
                : occupation // ignore: cast_nullable_to_non_nullable
                      as CustomerOccupation?,
            dob: freezed == dob
                ? _value.dob
                : dob // ignore: cast_nullable_to_non_nullable
                      as DateTime?,
            profile: freezed == profile
                ? _value.profile
                : profile // ignore: cast_nullable_to_non_nullable
                      as String?,
            addresses: null == addresses
                ? _value.addresses
                : addresses // ignore: cast_nullable_to_non_nullable
                      as List<AddressResponse>,
            documents: freezed == documents
                ? _value.documents
                : documents // ignore: cast_nullable_to_non_nullable
                      as List<KycRequest>?,
            kycStatus: freezed == kycStatus
                ? _value.kycStatus
                : kycStatus // ignore: cast_nullable_to_non_nullable
                      as KYCStatus?,
            status: freezed == status
                ? _value.status
                : status // ignore: cast_nullable_to_non_nullable
                      as CustomerStatus?,
          )
          as $Val,
    );
  }
}

/// @nodoc
abstract class _$$CustomerResponseImplCopyWith<$Res>
    implements $CustomerResponseCopyWith<$Res> {
  factory _$$CustomerResponseImplCopyWith(
    _$CustomerResponseImpl value,
    $Res Function(_$CustomerResponseImpl) then,
  ) = __$$CustomerResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({
    int id,
    String email,
    Role role,
    @JsonKey(name: 'isEmailVerified') bool isEmailVerified,
    bool active,
    String? name,
    Gender? gender,
    String? phone,
    CustomerOccupation? occupation,
    @IsoDateConverter() DateTime? dob,
    String? profile,
    List<AddressResponse> addresses,
    List<KycRequest>? documents,
    KYCStatus? kycStatus,
    CustomerStatus? status,
  });
}

/// @nodoc
class __$$CustomerResponseImplCopyWithImpl<$Res>
    extends _$CustomerResponseCopyWithImpl<$Res, _$CustomerResponseImpl>
    implements _$$CustomerResponseImplCopyWith<$Res> {
  __$$CustomerResponseImplCopyWithImpl(
    _$CustomerResponseImpl _value,
    $Res Function(_$CustomerResponseImpl) _then,
  ) : super(_value, _then);

  /// Create a copy of CustomerResponse
  /// with the given fields replaced by the non-null parameter values.
  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
    Object? email = null,
    Object? role = null,
    Object? isEmailVerified = null,
    Object? active = null,
    Object? name = freezed,
    Object? gender = freezed,
    Object? phone = freezed,
    Object? occupation = freezed,
    Object? dob = freezed,
    Object? profile = freezed,
    Object? addresses = null,
    Object? documents = freezed,
    Object? kycStatus = freezed,
    Object? status = freezed,
  }) {
    return _then(
      _$CustomerResponseImpl(
        id: null == id
            ? _value.id
            : id // ignore: cast_nullable_to_non_nullable
                  as int,
        email: null == email
            ? _value.email
            : email // ignore: cast_nullable_to_non_nullable
                  as String,
        role: null == role
            ? _value.role
            : role // ignore: cast_nullable_to_non_nullable
                  as Role,
        isEmailVerified: null == isEmailVerified
            ? _value.isEmailVerified
            : isEmailVerified // ignore: cast_nullable_to_non_nullable
                  as bool,
        active: null == active
            ? _value.active
            : active // ignore: cast_nullable_to_non_nullable
                  as bool,
        name: freezed == name
            ? _value.name
            : name // ignore: cast_nullable_to_non_nullable
                  as String?,
        gender: freezed == gender
            ? _value.gender
            : gender // ignore: cast_nullable_to_non_nullable
                  as Gender?,
        phone: freezed == phone
            ? _value.phone
            : phone // ignore: cast_nullable_to_non_nullable
                  as String?,
        occupation: freezed == occupation
            ? _value.occupation
            : occupation // ignore: cast_nullable_to_non_nullable
                  as CustomerOccupation?,
        dob: freezed == dob
            ? _value.dob
            : dob // ignore: cast_nullable_to_non_nullable
                  as DateTime?,
        profile: freezed == profile
            ? _value.profile
            : profile // ignore: cast_nullable_to_non_nullable
                  as String?,
        addresses: null == addresses
            ? _value._addresses
            : addresses // ignore: cast_nullable_to_non_nullable
                  as List<AddressResponse>,
        documents: freezed == documents
            ? _value._documents
            : documents // ignore: cast_nullable_to_non_nullable
                  as List<KycRequest>?,
        kycStatus: freezed == kycStatus
            ? _value.kycStatus
            : kycStatus // ignore: cast_nullable_to_non_nullable
                  as KYCStatus?,
        status: freezed == status
            ? _value.status
            : status // ignore: cast_nullable_to_non_nullable
                  as CustomerStatus?,
      ),
    );
  }
}

/// @nodoc
@JsonSerializable()
class _$CustomerResponseImpl implements _CustomerResponse {
  const _$CustomerResponseImpl({
    required this.id,
    required this.email,
    required this.role,
    @JsonKey(name: 'isEmailVerified') required this.isEmailVerified,
    required this.active,
    this.name,
    this.gender,
    this.phone,
    this.occupation,
    @IsoDateConverter() this.dob,
    this.profile,
    required final List<AddressResponse> addresses,
    final List<KycRequest>? documents,
    this.kycStatus,
    this.status,
  }) : _addresses = addresses,
       _documents = documents;

  factory _$CustomerResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$CustomerResponseImplFromJson(json);

  @override
  final int id;
  @override
  final String email;
  @override
  final Role role;
  @override
  @JsonKey(name: 'isEmailVerified')
  final bool isEmailVerified;
  @override
  final bool active;
  @override
  final String? name;
  @override
  final Gender? gender;
  @override
  final String? phone;
  @override
  final CustomerOccupation? occupation;
  @override
  @IsoDateConverter()
  final DateTime? dob;
  @override
  final String? profile;
  final List<AddressResponse> _addresses;
  @override
  List<AddressResponse> get addresses {
    if (_addresses is EqualUnmodifiableListView) return _addresses;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_addresses);
  }

  final List<KycRequest>? _documents;
  @override
  List<KycRequest>? get documents {
    final value = _documents;
    if (value == null) return null;
    if (_documents is EqualUnmodifiableListView) return _documents;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final KYCStatus? kycStatus;
  @override
  final CustomerStatus? status;

  @override
  String toString() {
    return 'CustomerResponse(id: $id, email: $email, role: $role, isEmailVerified: $isEmailVerified, active: $active, name: $name, gender: $gender, phone: $phone, occupation: $occupation, dob: $dob, profile: $profile, addresses: $addresses, documents: $documents, kycStatus: $kycStatus, status: $status)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CustomerResponseImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.email, email) || other.email == email) &&
            (identical(other.role, role) || other.role == role) &&
            (identical(other.isEmailVerified, isEmailVerified) ||
                other.isEmailVerified == isEmailVerified) &&
            (identical(other.active, active) || other.active == active) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.gender, gender) || other.gender == gender) &&
            (identical(other.phone, phone) || other.phone == phone) &&
            (identical(other.occupation, occupation) ||
                other.occupation == occupation) &&
            (identical(other.dob, dob) || other.dob == dob) &&
            (identical(other.profile, profile) || other.profile == profile) &&
            const DeepCollectionEquality().equals(
              other._addresses,
              _addresses,
            ) &&
            const DeepCollectionEquality().equals(
              other._documents,
              _documents,
            ) &&
            (identical(other.kycStatus, kycStatus) ||
                other.kycStatus == kycStatus) &&
            (identical(other.status, status) || other.status == status));
  }

  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  int get hashCode => Object.hash(
    runtimeType,
    id,
    email,
    role,
    isEmailVerified,
    active,
    name,
    gender,
    phone,
    occupation,
    dob,
    profile,
    const DeepCollectionEquality().hash(_addresses),
    const DeepCollectionEquality().hash(_documents),
    kycStatus,
    status,
  );

  /// Create a copy of CustomerResponse
  /// with the given fields replaced by the non-null parameter values.
  @JsonKey(includeFromJson: false, includeToJson: false)
  @override
  @pragma('vm:prefer-inline')
  _$$CustomerResponseImplCopyWith<_$CustomerResponseImpl> get copyWith =>
      __$$CustomerResponseImplCopyWithImpl<_$CustomerResponseImpl>(
        this,
        _$identity,
      );

  @override
  Map<String, dynamic> toJson() {
    return _$$CustomerResponseImplToJson(this);
  }
}

abstract class _CustomerResponse implements CustomerResponse {
  const factory _CustomerResponse({
    required final int id,
    required final String email,
    required final Role role,
    @JsonKey(name: 'isEmailVerified') required final bool isEmailVerified,
    required final bool active,
    final String? name,
    final Gender? gender,
    final String? phone,
    final CustomerOccupation? occupation,
    @IsoDateConverter() final DateTime? dob,
    final String? profile,
    required final List<AddressResponse> addresses,
    final List<KycRequest>? documents,
    final KYCStatus? kycStatus,
    final CustomerStatus? status,
  }) = _$CustomerResponseImpl;

  factory _CustomerResponse.fromJson(Map<String, dynamic> json) =
      _$CustomerResponseImpl.fromJson;

  @override
  int get id;
  @override
  String get email;
  @override
  Role get role;
  @override
  @JsonKey(name: 'isEmailVerified')
  bool get isEmailVerified;
  @override
  bool get active;
  @override
  String? get name;
  @override
  Gender? get gender;
  @override
  String? get phone;
  @override
  CustomerOccupation? get occupation;
  @override
  @IsoDateConverter()
  DateTime? get dob;
  @override
  String? get profile;
  @override
  List<AddressResponse> get addresses;
  @override
  List<KycRequest>? get documents;
  @override
  KYCStatus? get kycStatus;
  @override
  CustomerStatus? get status;

  /// Create a copy of CustomerResponse
  /// with the given fields replaced by the non-null parameter values.
  @override
  @JsonKey(includeFromJson: false, includeToJson: false)
  _$$CustomerResponseImplCopyWith<_$CustomerResponseImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
