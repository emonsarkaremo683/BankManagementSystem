// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'account_models.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$AccountRequestImpl _$$AccountRequestImplFromJson(Map<String, dynamic> json) =>
    _$AccountRequestImpl(
      accountType: $enumDecodeNullable(
        _$AccountTypeEnumMap,
        json['accountType'],
      ),
      availableBalance: (json['availableBalance'] as num?)?.toDouble(),
      branchId: (json['branchId'] as num?)?.toInt(),
      nName: json['n_name'] as String?,
      nEmail: json['n_email'] as String?,
      nPhone: json['n_phone'] as String?,
      relation: $enumDecodeNullable(_$NomineeRelationEnumMap, json['relation']),
      nPhoto: json['n_photo'] as String?,
      nNidFront: json['n_nid_front'] as String?,
      nNidBack: json['n_nid_back'] as String?,
      accountHolders: (json['accountHolders'] as List<dynamic>?)
          ?.map((e) => AccountHolderRequest.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$AccountRequestImplToJson(
  _$AccountRequestImpl instance,
) => <String, dynamic>{
  'accountType': _$AccountTypeEnumMap[instance.accountType],
  'availableBalance': instance.availableBalance,
  'branchId': instance.branchId,
  'n_name': instance.nName,
  'n_email': instance.nEmail,
  'n_phone': instance.nPhone,
  'relation': _$NomineeRelationEnumMap[instance.relation],
  'n_photo': instance.nPhoto,
  'n_nid_front': instance.nNidFront,
  'n_nid_back': instance.nNidBack,
  'accountHolders': instance.accountHolders?.map((e) => e.toJson()).toList(),
};

const _$AccountTypeEnumMap = {
  AccountType.SAVINGS: 'SAVINGS',
  AccountType.CURRENT: 'CURRENT',
  AccountType.FIXED_DEPOSIT: 'FIXED_DEPOSIT',
  AccountType.JOINT_ACCOUNT: 'JOINT_ACCOUNT',
  AccountType.STUDENT: 'STUDENT',
  AccountType.BUSINESS: 'BUSINESS',
};

const _$NomineeRelationEnumMap = {
  NomineeRelation.FATHER: 'FATHER',
  NomineeRelation.MOTHER: 'MOTHER',
  NomineeRelation.SISTER: 'SISTER',
  NomineeRelation.BROTHER: 'BROTHER',
  NomineeRelation.UNCLE: 'UNCLE',
  NomineeRelation.AUNTY: 'AUNTY',
  NomineeRelation.COUSIN: 'COUSIN',
  NomineeRelation.OTHER: 'OTHER',
};

_$AccountResponseImpl _$$AccountResponseImplFromJson(
  Map<String, dynamic> json,
) => _$AccountResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  accountNumber: json['accountNumber'] as String?,
  accountType: $enumDecodeNullable(_$AccountTypeEnumMap, json['accountType']),
  accountStatus: $enumDecodeNullable(
    _$AccountStatusEnumMap,
    json['accountStatus'],
  ),
  availableBalance: (json['availableBalance'] as num?)?.toDouble(),
  currentBalance: (json['currentBalance'] as num?)?.toDouble(),
  holdBalance: (json['holdBalance'] as num?)?.toDouble(),
  branchName: json['branchName'] as String?,
  branchRoutingNumber: json['branchRoutingNumber'] as String?,
  nName: json['n_name'] as String?,
  nEmail: json['n_email'] as String?,
  relation: $enumDecodeNullable(_$NomineeRelationEnumMap, json['relation']),
  nPhone: json['n_phone'] as String?,
  nPhoto: json['n_photo'] as String?,
  nNidFront: json['n_nid_front'] as String?,
  nNidBack: json['n_nid_back'] as String?,
  holderResponses:
      (json['holderResponses'] as List<dynamic>?)
          ?.map(
            (e) => AccountHolderResponse.fromJson(e as Map<String, dynamic>),
          )
          .toList() ??
      const [],
);

Map<String, dynamic> _$$AccountResponseImplToJson(
  _$AccountResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'accountNumber': instance.accountNumber,
  'accountType': _$AccountTypeEnumMap[instance.accountType],
  'accountStatus': _$AccountStatusEnumMap[instance.accountStatus],
  'availableBalance': instance.availableBalance,
  'currentBalance': instance.currentBalance,
  'holdBalance': instance.holdBalance,
  'branchName': instance.branchName,
  'branchRoutingNumber': instance.branchRoutingNumber,
  'n_name': instance.nName,
  'n_email': instance.nEmail,
  'relation': _$NomineeRelationEnumMap[instance.relation],
  'n_phone': instance.nPhone,
  'n_photo': instance.nPhoto,
  'n_nid_front': instance.nNidFront,
  'n_nid_back': instance.nNidBack,
  'holderResponses': instance.holderResponses.map((e) => e.toJson()).toList(),
};

const _$AccountStatusEnumMap = {
  AccountStatus.ACTIVE: 'ACTIVE',
  AccountStatus.INACTIVE: 'INACTIVE',
  AccountStatus.BLOCKED: 'BLOCKED',
  AccountStatus.CLOSED: 'CLOSED',
  AccountStatus.FREEZE: 'FREEZE',
  AccountStatus.PENDING: 'PENDING',
};

_$AccountHolderRequestImpl _$$AccountHolderRequestImplFromJson(
  Map<String, dynamic> json,
) => _$AccountHolderRequestImpl(
  holderType: $enumDecodeNullable(_$HolderTypeEnumMap, json['holderType']),
  canWithdraw: json['canWithdraw'] as bool?,
  canDeposit: json['canDeposit'] as bool?,
  canApproveTransaction: json['canApproveTransaction'] as bool?,
  signature: json['signature'] as String?,
  customerId: (json['customerId'] as num?)?.toInt(),
);

Map<String, dynamic> _$$AccountHolderRequestImplToJson(
  _$AccountHolderRequestImpl instance,
) => <String, dynamic>{
  'holderType': _$HolderTypeEnumMap[instance.holderType],
  'canWithdraw': instance.canWithdraw,
  'canDeposit': instance.canDeposit,
  'canApproveTransaction': instance.canApproveTransaction,
  'signature': instance.signature,
  'customerId': instance.customerId,
};

const _$HolderTypeEnumMap = {
  HolderType.PRIMARY: 'PRIMARY',
  HolderType.SECONDARY: 'SECONDARY',
  HolderType.OPTIONAL: 'OPTIONAL',
};

_$AccountHolderResponseImpl _$$AccountHolderResponseImplFromJson(
  Map<String, dynamic> json,
) => _$AccountHolderResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  accountHolderName: json['accountHolderName'] as String?,
  holderType: $enumDecodeNullable(_$HolderTypeEnumMap, json['holderType']),
  canWithdraw: json['canWithdraw'] as bool?,
  canDeposit: json['canDeposit'] as bool?,
  signature: json['signature'] as String?,
  canApproveTransaction: json['canApproveTransaction'] as bool?,
);

Map<String, dynamic> _$$AccountHolderResponseImplToJson(
  _$AccountHolderResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'accountHolderName': instance.accountHolderName,
  'holderType': _$HolderTypeEnumMap[instance.holderType],
  'canWithdraw': instance.canWithdraw,
  'canDeposit': instance.canDeposit,
  'signature': instance.signature,
  'canApproveTransaction': instance.canApproveTransaction,
};
