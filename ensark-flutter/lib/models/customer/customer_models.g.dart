// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'customer_models.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$AddressRequestImpl _$$AddressRequestImplFromJson(Map<String, dynamic> json) =>
    _$AddressRequestImpl(
      holdingNo: json['holdingNo'] as String?,
      area: json['area'] as String?,
      postalCode: json['postalCode'] as String?,
      addressType: $enumDecode(_$AddressTypeEnumMap, json['addressType']),
      policeStationId: (json['policeStationId'] as num).toInt(),
    );

Map<String, dynamic> _$$AddressRequestImplToJson(
  _$AddressRequestImpl instance,
) => <String, dynamic>{
  'holdingNo': instance.holdingNo,
  'area': instance.area,
  'postalCode': instance.postalCode,
  'addressType': _$AddressTypeEnumMap[instance.addressType]!,
  'policeStationId': instance.policeStationId,
};

const _$AddressTypeEnumMap = {
  AddressType.PERMANENT: 'PERMANENT',
  AddressType.PRESENT: 'PRESENT',
};

_$AddressResponseImpl _$$AddressResponseImplFromJson(
  Map<String, dynamic> json,
) => _$AddressResponseImpl(
  id: (json['id'] as num).toInt(),
  holdingNo: json['holdingNo'] as String?,
  area: json['area'] as String?,
  postalCode: json['postalCode'] as String?,
  addressType: $enumDecode(_$AddressTypeEnumMap, json['addressType']),
  policeStationId: (json['policeStationId'] as num?)?.toInt(),
  policeStationName: json['policeStationName'] as String?,
  districtId: (json['districtId'] as num?)?.toInt(),
  districtName: json['districtName'] as String?,
  divisionId: (json['divisionId'] as num?)?.toInt(),
  divisionName: json['divisionName'] as String?,
);

Map<String, dynamic> _$$AddressResponseImplToJson(
  _$AddressResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'holdingNo': instance.holdingNo,
  'area': instance.area,
  'postalCode': instance.postalCode,
  'addressType': _$AddressTypeEnumMap[instance.addressType]!,
  'policeStationId': instance.policeStationId,
  'policeStationName': instance.policeStationName,
  'districtId': instance.districtId,
  'districtName': instance.districtName,
  'divisionId': instance.divisionId,
  'divisionName': instance.divisionName,
};

_$KycRequestImpl _$$KycRequestImplFromJson(Map<String, dynamic> json) =>
    _$KycRequestImpl(
      id: (json['id'] as num?)?.toInt(),
      path: json['path'] as String?,
      docType: $enumDecode(_$DocumentTypeEnumMap, json['doc_type']),
    );

Map<String, dynamic> _$$KycRequestImplToJson(_$KycRequestImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'path': instance.path,
      'doc_type': _$DocumentTypeEnumMap[instance.docType]!,
    };

const _$DocumentTypeEnumMap = {
  DocumentType.NID: 'NID',
  DocumentType.PASSPORT: 'PASSPORT',
  DocumentType.DRIVING_LICENSE: 'DRIVING_LICENSE',
  DocumentType.BIRTH_CERTIFICATE: 'BIRTH_CERTIFICATE',
};

_$CustomerRequestImpl _$$CustomerRequestImplFromJson(
  Map<String, dynamic> json,
) => _$CustomerRequestImpl(
  email: json['email'] as String,
  password: json['password'] as String,
  name: json['name'] as String?,
  gender: $enumDecodeNullable(_$GenderEnumMap, json['gender']),
  phone: json['phone'] as String?,
  occupation: $enumDecodeNullable(
    _$CustomerOccupationEnumMap,
    json['occupation'],
  ),
  dob: const IsoDateConverter().fromJson(json['dob'] as String?),
  profile: json['profile'] as String?,
  addresses: (json['addresses'] as List<dynamic>)
      .map((e) => AddressRequest.fromJson(e as Map<String, dynamic>))
      .toList(),
  kycRequests: (json['kycRequests'] as List<dynamic>?)
      ?.map((e) => KycRequest.fromJson(e as Map<String, dynamic>))
      .toList(),
);

Map<String, dynamic> _$$CustomerRequestImplToJson(
  _$CustomerRequestImpl instance,
) => <String, dynamic>{
  'email': instance.email,
  'password': instance.password,
  'name': instance.name,
  'gender': _$GenderEnumMap[instance.gender],
  'phone': instance.phone,
  'occupation': _$CustomerOccupationEnumMap[instance.occupation],
  'dob': const IsoDateConverter().toJson(instance.dob),
  'profile': instance.profile,
  'addresses': instance.addresses.map((e) => e.toJson()).toList(),
  'kycRequests': instance.kycRequests?.map((e) => e.toJson()).toList(),
};

const _$GenderEnumMap = {
  Gender.MALE: 'MALE',
  Gender.FEMALE: 'FEMALE',
  Gender.OTHER: 'OTHER',
};

const _$CustomerOccupationEnumMap = {
  CustomerOccupation.STUDENT: 'STUDENT',
  CustomerOccupation.SERVICE_HOLDER: 'SERVICE_HOLDER',
  CustomerOccupation.GOVERNMENT_EMPLOYEE: 'GOVERNMENT_EMPLOYEE',
  CustomerOccupation.BUSINESS_OWNER: 'BUSINESS_OWNER',
  CustomerOccupation.SELF_EMPLOYED: 'SELF_EMPLOYED',
  CustomerOccupation.FREELANCER: 'FREELANCER',
  CustomerOccupation.DOCTOR: 'DOCTOR',
  CustomerOccupation.ENGINEER: 'ENGINEER',
  CustomerOccupation.TEACHER: 'TEACHER',
  CustomerOccupation.LAWYER: 'LAWYER',
  CustomerOccupation.ACCOUNTANT: 'ACCOUNTANT',
  CustomerOccupation.ARCHITECT: 'ARCHITECT',
  CustomerOccupation.CONSULTANT: 'CONSULTANT',
  CustomerOccupation.FARMER: 'FARMER',
  CustomerOccupation.LABORER: 'LABORER',
  CustomerOccupation.DRIVER: 'DRIVER',
  CustomerOccupation.MECHANIC: 'MECHANIC',
  CustomerOccupation.ELECTRICIAN: 'ELECTRICIAN',
  CustomerOccupation.PLUMBER: 'PLUMBER',
  CustomerOccupation.POLICE: 'POLICE',
  CustomerOccupation.MILITARY: 'MILITARY',
  CustomerOccupation.CIVIL_SERVANT: 'CIVIL_SERVANT',
  CustomerOccupation.BANKER: 'BANKER',
  CustomerOccupation.NGO_EMPLOYEE: 'NGO_EMPLOYEE',
  CustomerOccupation.RETIRED: 'RETIRED',
  CustomerOccupation.HOMEMAKER: 'HOMEMAKER',
  CustomerOccupation.UNEMPLOYED: 'UNEMPLOYED',
  CustomerOccupation.FOREIGN_EMPLOYEE: 'FOREIGN_EMPLOYEE',
  CustomerOccupation.EXPATRIATE: 'EXPATRIATE',
  CustomerOccupation.POLITICIAN: 'POLITICIAN',
  CustomerOccupation.JOURNALIST: 'JOURNALIST',
  CustomerOccupation.ARTIST: 'ARTIST',
  CustomerOccupation.WRITER: 'WRITER',
  CustomerOccupation.ACTOR: 'ACTOR',
  CustomerOccupation.MUSICIAN: 'MUSICIAN',
  CustomerOccupation.RELIGIOUS_LEADER: 'RELIGIOUS_LEADER',
  CustomerOccupation.OTHERS: 'OTHERS',
};

_$CustomerResponseImpl _$$CustomerResponseImplFromJson(
  Map<String, dynamic> json,
) => _$CustomerResponseImpl(
  id: (json['id'] as num).toInt(),
  email: json['email'] as String,
  role: $enumDecode(_$RoleEnumMap, json['role']),
  isEmailVerified: json['isEmailVerified'] as bool,
  active: json['active'] as bool,
  name: json['name'] as String?,
  gender: $enumDecodeNullable(_$GenderEnumMap, json['gender']),
  phone: json['phone'] as String?,
  occupation: $enumDecodeNullable(
    _$CustomerOccupationEnumMap,
    json['occupation'],
  ),
  dob: const IsoDateConverter().fromJson(json['dob'] as String?),
  profile: json['profile'] as String?,
  addresses: (json['addresses'] as List<dynamic>)
      .map((e) => AddressResponse.fromJson(e as Map<String, dynamic>))
      .toList(),
  documents: (json['documents'] as List<dynamic>?)
      ?.map((e) => KycRequest.fromJson(e as Map<String, dynamic>))
      .toList(),
  kycStatus: $enumDecodeNullable(_$KYCStatusEnumMap, json['kycStatus']),
  status: $enumDecodeNullable(_$CustomerStatusEnumMap, json['status']),
);

Map<String, dynamic> _$$CustomerResponseImplToJson(
  _$CustomerResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'email': instance.email,
  'role': _$RoleEnumMap[instance.role]!,
  'isEmailVerified': instance.isEmailVerified,
  'active': instance.active,
  'name': instance.name,
  'gender': _$GenderEnumMap[instance.gender],
  'phone': instance.phone,
  'occupation': _$CustomerOccupationEnumMap[instance.occupation],
  'dob': const IsoDateConverter().toJson(instance.dob),
  'profile': instance.profile,
  'addresses': instance.addresses.map((e) => e.toJson()).toList(),
  'documents': instance.documents?.map((e) => e.toJson()).toList(),
  'kycStatus': _$KYCStatusEnumMap[instance.kycStatus],
  'status': _$CustomerStatusEnumMap[instance.status],
};

const _$RoleEnumMap = {
  Role.SUPER_ADMIN: 'SUPER_ADMIN',
  Role.ADMIN: 'ADMIN',
  Role.BRANCH_MANAGER: 'BRANCH_MANAGER',
  Role.ACCOUNTANT: 'ACCOUNTANT',
  Role.CASHIER: 'CASHIER',
  Role.LOAN_OFFICER: 'LOAN_OFFICER',
  Role.CUSTOMER_SERVICE: 'CUSTOMER_SERVICE',
  Role.ATM_MANAGER: 'ATM_MANAGER',
  Role.AUDITOR: 'AUDITOR',
  Role.CUSTOMER: 'CUSTOMER',
};

const _$KYCStatusEnumMap = {
  KYCStatus.PENDING: 'PENDING',
  KYCStatus.UNDER_REVIEW: 'UNDER_REVIEW',
  KYCStatus.VERIFIED: 'VERIFIED',
  KYCStatus.REJECTED: 'REJECTED',
  KYCStatus.EXPIRED: 'EXPIRED',
};

const _$CustomerStatusEnumMap = {
  CustomerStatus.ACTIVE: 'ACTIVE',
  CustomerStatus.INACTIVE: 'INACTIVE',
  CustomerStatus.BLOCKED: 'BLOCKED',
  CustomerStatus.PENDING: 'PENDING',
};
