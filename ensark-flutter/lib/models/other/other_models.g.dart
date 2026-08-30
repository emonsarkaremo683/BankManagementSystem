// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'other_models.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$ChequeBookRequestImpl _$$ChequeBookRequestImplFromJson(
  Map<String, dynamic> json,
) => _$ChequeBookRequestImpl(
  accountId: (json['accountId'] as num?)?.toInt(),
  numberOfLeaves: (json['numberOfLeaves'] as num?)?.toInt(),
);

Map<String, dynamic> _$$ChequeBookRequestImplToJson(
  _$ChequeBookRequestImpl instance,
) => <String, dynamic>{
  'accountId': instance.accountId,
  'numberOfLeaves': instance.numberOfLeaves,
};

_$ChequeBookResponseImpl _$$ChequeBookResponseImplFromJson(
  Map<String, dynamic> json,
) => _$ChequeBookResponseImpl(
  chequeBookId: (json['chequeBookId'] as num?)?.toInt(),
  bookSerialNumber: json['bookSerialNumber'] as String?,
  numberOfLeaves: (json['numberOfLeaves'] as num?)?.toInt(),
  startLeafNumber: (json['startLeafNumber'] as num?)?.toInt(),
  endLeafNumber: (json['endLeafNumber'] as num?)?.toInt(),
  status: $enumDecodeNullable(
    _$ChequeBookStatusEnumMap,
    json['status'],
    unknownValue: ChequeBookStatus.REQUESTED,
  ),
  accountId: (json['accountId'] as num?)?.toInt(),
  accountNumber: json['accountNumber'] as String?,
  applicationDate: json['applicationDate'] == null
      ? null
      : DateTime.parse(json['applicationDate'] as String),
  approvalDate: json['approvalDate'] == null
      ? null
      : DateTime.parse(json['approvalDate'] as String),
  deliveryDate: json['deliveryDate'] == null
      ? null
      : DateTime.parse(json['deliveryDate'] as String),
  activationDate: json['activationDate'] == null
      ? null
      : DateTime.parse(json['activationDate'] as String),
  expiryDate: json['expiryDate'] == null
      ? null
      : DateTime.parse(json['expiryDate'] as String),
  rejectionReason: json['rejectionReason'] as String?,
  leaves: (json['leaves'] as List<dynamic>?)
      ?.map((e) => ChequeLeafResponse.fromJson(e as Map<String, dynamic>))
      .toList(),
);

Map<String, dynamic> _$$ChequeBookResponseImplToJson(
  _$ChequeBookResponseImpl instance,
) => <String, dynamic>{
  'chequeBookId': instance.chequeBookId,
  'bookSerialNumber': instance.bookSerialNumber,
  'numberOfLeaves': instance.numberOfLeaves,
  'startLeafNumber': instance.startLeafNumber,
  'endLeafNumber': instance.endLeafNumber,
  'status': _$ChequeBookStatusEnumMap[instance.status],
  'accountId': instance.accountId,
  'accountNumber': instance.accountNumber,
  'applicationDate': instance.applicationDate?.toIso8601String(),
  'approvalDate': instance.approvalDate?.toIso8601String(),
  'deliveryDate': instance.deliveryDate?.toIso8601String(),
  'activationDate': instance.activationDate?.toIso8601String(),
  'expiryDate': instance.expiryDate?.toIso8601String(),
  'rejectionReason': instance.rejectionReason,
  'leaves': instance.leaves?.map((e) => e.toJson()).toList(),
};

const _$ChequeBookStatusEnumMap = {
  ChequeBookStatus.REQUESTED: 'REQUESTED',
  ChequeBookStatus.APPROVED: 'APPROVED',
  ChequeBookStatus.REJECTED: 'REJECTED',
  ChequeBookStatus.PRINTED: 'PRINTED',
  ChequeBookStatus.READY_FOR_DELIVERY: 'READY_FOR_DELIVERY',
  ChequeBookStatus.DELIVERED: 'DELIVERED',
  ChequeBookStatus.ACTIVE: 'ACTIVE',
  ChequeBookStatus.EXHAUSTED: 'EXHAUSTED',
  ChequeBookStatus.BLOCKED: 'BLOCKED',
  ChequeBookStatus.EXPIRED: 'EXPIRED',
  ChequeBookStatus.CANCELLED: 'CANCELLED',
};

_$ChequeLeafResponseImpl _$$ChequeLeafResponseImplFromJson(
  Map<String, dynamic> json,
) => _$ChequeLeafResponseImpl(
  leafId: (json['leafId'] as num?)?.toInt(),
  leafNumber: (json['leafNumber'] as num?)?.toInt(),
  chequeNumber: json['chequeNumber'] as String?,
  amount: (json['amount'] as num?)?.toDouble(),
  payeeName: json['payeeName'] as String?,
  remarks: json['remarks'] as String?,
  status: $enumDecodeNullable(
    _$ChequeLeafStatusEnumMap,
    json['status'],
    unknownValue: ChequeLeafStatus.UNUSED,
  ),
  issueDate: json['issueDate'] == null
      ? null
      : DateTime.parse(json['issueDate'] as String),
  clearanceDate: json['clearanceDate'] == null
      ? null
      : DateTime.parse(json['clearanceDate'] as String),
  expiryDate: json['expiryDate'] == null
      ? null
      : DateTime.parse(json['expiryDate'] as String),
  bounceReason: json['bounceReason'] as String?,
  transactionReference: json['transactionReference'] as String?,
  chequeBookId: (json['chequeBookId'] as num?)?.toInt(),
  bookSerialNumber: json['bookSerialNumber'] as String?,
);

Map<String, dynamic> _$$ChequeLeafResponseImplToJson(
  _$ChequeLeafResponseImpl instance,
) => <String, dynamic>{
  'leafId': instance.leafId,
  'leafNumber': instance.leafNumber,
  'chequeNumber': instance.chequeNumber,
  'amount': instance.amount,
  'payeeName': instance.payeeName,
  'remarks': instance.remarks,
  'status': _$ChequeLeafStatusEnumMap[instance.status],
  'issueDate': instance.issueDate?.toIso8601String(),
  'clearanceDate': instance.clearanceDate?.toIso8601String(),
  'expiryDate': instance.expiryDate?.toIso8601String(),
  'bounceReason': instance.bounceReason,
  'transactionReference': instance.transactionReference,
  'chequeBookId': instance.chequeBookId,
  'bookSerialNumber': instance.bookSerialNumber,
};

const _$ChequeLeafStatusEnumMap = {
  ChequeLeafStatus.UNUSED: 'UNUSED',
  ChequeLeafStatus.ISSUED: 'ISSUED',
  ChequeLeafStatus.PRESENTED: 'PRESENTED',
  ChequeLeafStatus.CLEARED: 'CLEARED',
  ChequeLeafStatus.BOUNCED: 'BOUNCED',
  ChequeLeafStatus.STOP_PAYMENT: 'STOP_PAYMENT',
  ChequeLeafStatus.CANCELLED: 'CANCELLED',
  ChequeLeafStatus.EXPIRED: 'EXPIRED',
};

_$StandingOrderRequestImpl _$$StandingOrderRequestImplFromJson(
  Map<String, dynamic> json,
) => _$StandingOrderRequestImpl(
  sourceAccountId: (json['sourceAccountId'] as num?)?.toInt(),
  targetAccountNumber: json['targetAccountNumber'] as String?,
  targetAccountName: json['targetAccountName'] as String?,
  amount: (json['amount'] as num?)?.toDouble(),
  frequency: $enumDecodeNullable(
    _$StandingOrderFrequencyEnumMap,
    json['frequency'],
  ),
  startDate: json['startDate'] as String?,
  endDate: json['endDate'] as String?,
  maxExecutions: (json['maxExecutions'] as num?)?.toInt(),
  description: json['description'] as String?,
);

Map<String, dynamic> _$$StandingOrderRequestImplToJson(
  _$StandingOrderRequestImpl instance,
) => <String, dynamic>{
  'sourceAccountId': instance.sourceAccountId,
  'targetAccountNumber': instance.targetAccountNumber,
  'targetAccountName': instance.targetAccountName,
  'amount': instance.amount,
  'frequency': _$StandingOrderFrequencyEnumMap[instance.frequency],
  'startDate': instance.startDate,
  'endDate': instance.endDate,
  'maxExecutions': instance.maxExecutions,
  'description': instance.description,
};

const _$StandingOrderFrequencyEnumMap = {
  StandingOrderFrequency.DAILY: 'DAILY',
  StandingOrderFrequency.WEEKLY: 'WEEKLY',
  StandingOrderFrequency.BI_WEEKLY: 'BI_WEEKLY',
  StandingOrderFrequency.MONTHLY: 'MONTHLY',
  StandingOrderFrequency.QUARTERLY: 'QUARTERLY',
  StandingOrderFrequency.YEARLY: 'YEARLY',
};

_$StandingOrderResponseImpl _$$StandingOrderResponseImplFromJson(
  Map<String, dynamic> json,
) => _$StandingOrderResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  sourceAccountNumber: json['sourceAccountNumber'] as String?,
  targetAccountNumber: json['targetAccountNumber'] as String?,
  targetAccountName: json['targetAccountName'] as String?,
  amount: (json['amount'] as num?)?.toDouble(),
  frequency: $enumDecodeNullable(
    _$StandingOrderFrequencyEnumMap,
    json['frequency'],
  ),
  status: $enumDecodeNullable(_$StandingOrderStatusEnumMap, json['status']),
  startDate: json['startDate'] as String?,
  endDate: json['endDate'] as String?,
  nextExecutionDate: json['nextExecutionDate'] as String?,
  lastExecutionDate: json['lastExecutionDate'] as String?,
  executionCount: (json['executionCount'] as num?)?.toInt(),
  maxExecutions: (json['maxExecutions'] as num?)?.toInt(),
  description: json['description'] as String?,
);

Map<String, dynamic> _$$StandingOrderResponseImplToJson(
  _$StandingOrderResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'sourceAccountNumber': instance.sourceAccountNumber,
  'targetAccountNumber': instance.targetAccountNumber,
  'targetAccountName': instance.targetAccountName,
  'amount': instance.amount,
  'frequency': _$StandingOrderFrequencyEnumMap[instance.frequency],
  'status': _$StandingOrderStatusEnumMap[instance.status],
  'startDate': instance.startDate,
  'endDate': instance.endDate,
  'nextExecutionDate': instance.nextExecutionDate,
  'lastExecutionDate': instance.lastExecutionDate,
  'executionCount': instance.executionCount,
  'maxExecutions': instance.maxExecutions,
  'description': instance.description,
};

const _$StandingOrderStatusEnumMap = {
  StandingOrderStatus.ACTIVE: 'ACTIVE',
  StandingOrderStatus.PAUSED: 'PAUSED',
  StandingOrderStatus.COMPLETED: 'COMPLETED',
  StandingOrderStatus.CANCELLED: 'CANCELLED',
  StandingOrderStatus.FAILED: 'FAILED',
};

_$BeneficiaryRequestImpl _$$BeneficiaryRequestImplFromJson(
  Map<String, dynamic> json,
) => _$BeneficiaryRequestImpl(
  accNumber: json['accNumber'] as String?,
  name: json['name'] as String?,
  provider: json['provider'] as String?,
  routingNumber: json['routingNumber'] as String?,
  beneficiaryType: $enumDecodeNullable(
    _$BeneficiaryTypeEnumMap,
    json['beneficiaryType'],
  ),
  customerId: (json['customerId'] as num?)?.toInt(),
);

Map<String, dynamic> _$$BeneficiaryRequestImplToJson(
  _$BeneficiaryRequestImpl instance,
) => <String, dynamic>{
  'accNumber': instance.accNumber,
  'name': instance.name,
  'provider': instance.provider,
  'routingNumber': instance.routingNumber,
  'beneficiaryType': _$BeneficiaryTypeEnumMap[instance.beneficiaryType],
  'customerId': instance.customerId,
};

const _$BeneficiaryTypeEnumMap = {
  BeneficiaryType.BKASH: 'BKASH',
  BeneficiaryType.NAGAD: 'NAGAD',
  BeneficiaryType.BANK: 'BANK',
  BeneficiaryType.CARD: 'CARD',
  BeneficiaryType.INTER_BANK: 'INTER_BANK',
};

_$BeneficiaryResponseImpl _$$BeneficiaryResponseImplFromJson(
  Map<String, dynamic> json,
) => _$BeneficiaryResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  accNumber: json['accNumber'] as String?,
  name: json['name'] as String?,
  provider: json['provider'] as String?,
  routingNumber: json['routingNumber'] as String?,
  beneficiaryType: $enumDecodeNullable(
    _$BeneficiaryTypeEnumMap,
    json['beneficiaryType'],
  ),
  customerId: (json['customerId'] as num?)?.toInt(),
  customerName: json['customerName'] as String?,
  isVerified: json['isVerified'] as bool?,
  isBlocked: json['isBlocked'] as bool?,
  blockReason: json['blockReason'] as String?,
);

Map<String, dynamic> _$$BeneficiaryResponseImplToJson(
  _$BeneficiaryResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'accNumber': instance.accNumber,
  'name': instance.name,
  'provider': instance.provider,
  'routingNumber': instance.routingNumber,
  'beneficiaryType': _$BeneficiaryTypeEnumMap[instance.beneficiaryType],
  'customerId': instance.customerId,
  'customerName': instance.customerName,
  'isVerified': instance.isVerified,
  'isBlocked': instance.isBlocked,
  'blockReason': instance.blockReason,
};

_$NotificationResponseImpl _$$NotificationResponseImplFromJson(
  Map<String, dynamic> json,
) => _$NotificationResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  type: $enumDecodeNullable(
    _$NotificationTypeEnumMap,
    json['type'],
    unknownValue: NotificationType.GENERAL,
  ),
  title: json['title'] as String?,
  message: json['message'] as String?,
  read: json['read'] as bool?,
  referenceId: json['referenceId'] as String?,
  referenceType: json['referenceType'] as String?,
  createdAt: json['createdAt'] as String?,
);

Map<String, dynamic> _$$NotificationResponseImplToJson(
  _$NotificationResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'type': _$NotificationTypeEnumMap[instance.type],
  'title': instance.title,
  'message': instance.message,
  'read': instance.read,
  'referenceId': instance.referenceId,
  'referenceType': instance.referenceType,
  'createdAt': instance.createdAt,
};

const _$NotificationTypeEnumMap = {
  NotificationType.TRANSACTION_SUCCESS: 'TRANSACTION_SUCCESS',
  NotificationType.TRANSACTION_FAILED: 'TRANSACTION_FAILED',
  NotificationType.DEPOSIT: 'DEPOSIT',
  NotificationType.WITHDRAW: 'WITHDRAW',
  NotificationType.TRANSFER: 'TRANSFER',
  NotificationType.ACCOUNT_CREATED: 'ACCOUNT_CREATED',
  NotificationType.ACCOUNT_SUSPENDED: 'ACCOUNT_SUSPENDED',
  NotificationType.ACCOUNT_STATUS_CHANGED: 'ACCOUNT_STATUS_CHANGED',
  NotificationType.CARD_APPLICATION: 'CARD_APPLICATION',
  NotificationType.CARD_STATUS_CHANGED: 'CARD_STATUS_CHANGED',
  NotificationType.LOAN_APPLICATION: 'LOAN_APPLICATION',
  NotificationType.LOAN_APPROVED: 'LOAN_APPROVED',
  NotificationType.LOAN_REJECTED: 'LOAN_REJECTED',
  NotificationType.KYC_SUBMITTED: 'KYC_SUBMITTED',
  NotificationType.KYC_VERIFIED: 'KYC_VERIFIED',
  NotificationType.KYC_REJECTED: 'KYC_REJECTED',
  NotificationType.CUSTOMER_REGISTERED: 'CUSTOMER_REGISTERED',
  NotificationType.INTEREST_CREDITED: 'INTEREST_CREDITED',
  NotificationType.CHEQUE_BOOK_REQUEST: 'CHEQUE_BOOK_REQUEST',
  NotificationType.CHEQUE_BOOK_APPROVED: 'CHEQUE_BOOK_APPROVED',
  NotificationType.CHEQUE_BOOK_REJECTED: 'CHEQUE_BOOK_REJECTED',
  NotificationType.CHEQUE_BOOK_DELIVERED: 'CHEQUE_BOOK_DELIVERED',
  NotificationType.CHEQUE_BOOK_ACTIVATED: 'CHEQUE_BOOK_ACTIVATED',
  NotificationType.CHEQUE_BOOK_BLOCKED: 'CHEQUE_BOOK_BLOCKED',
  NotificationType.GENERAL: 'GENERAL',
};

_$BranchResponseImpl _$$BranchResponseImplFromJson(Map<String, dynamic> json) =>
    _$BranchResponseImpl(
      id: (json['id'] as num?)?.toInt(),
      name: json['name'] as String?,
      address: json['address'] as String?,
      routingNumber: json['routingNumber'] as String?,
      branchCode: json['branchCode'] as String?,
      email: json['email'] as String?,
      phoneNumber: json['phoneNumber'] as String?,
      type: $enumDecodeNullable(_$BranchTypeEnumMap, json['type']),
      status: $enumDecodeNullable(_$BranchStatusEnumMap, json['status']),
    );

Map<String, dynamic> _$$BranchResponseImplToJson(
  _$BranchResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'name': instance.name,
  'address': instance.address,
  'routingNumber': instance.routingNumber,
  'branchCode': instance.branchCode,
  'email': instance.email,
  'phoneNumber': instance.phoneNumber,
  'type': _$BranchTypeEnumMap[instance.type],
  'status': _$BranchStatusEnumMap[instance.status],
};

const _$BranchTypeEnumMap = {
  BranchType.HEAD_OFFICE: 'HEAD_OFFICE',
  BranchType.BRANCH: 'BRANCH',
  BranchType.AGENT_BANK: 'AGENT_BANK',
};

const _$BranchStatusEnumMap = {
  BranchStatus.ACTIVE: 'ACTIVE',
  BranchStatus.CLOSED: 'CLOSED',
};

_$DivisionResponseImpl _$$DivisionResponseImplFromJson(
  Map<String, dynamic> json,
) => _$DivisionResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  name: json['name'] as String?,
);

Map<String, dynamic> _$$DivisionResponseImplToJson(
  _$DivisionResponseImpl instance,
) => <String, dynamic>{'id': instance.id, 'name': instance.name};

_$DistrictResponseImpl _$$DistrictResponseImplFromJson(
  Map<String, dynamic> json,
) => _$DistrictResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  name: json['name'] as String?,
);

Map<String, dynamic> _$$DistrictResponseImplToJson(
  _$DistrictResponseImpl instance,
) => <String, dynamic>{'id': instance.id, 'name': instance.name};

_$PoliceStationResponseImpl _$$PoliceStationResponseImplFromJson(
  Map<String, dynamic> json,
) => _$PoliceStationResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  name: json['name'] as String?,
);

Map<String, dynamic> _$$PoliceStationResponseImplToJson(
  _$PoliceStationResponseImpl instance,
) => <String, dynamic>{'id': instance.id, 'name': instance.name};

_$CurrencyResponseImpl _$$CurrencyResponseImplFromJson(
  Map<String, dynamic> json,
) => _$CurrencyResponseImpl(
  currency: $enumDecodeNullable(_$CurrencyEnumMap, json['currency']),
  rate: (json['rate'] as num?)?.toDouble(),
);

Map<String, dynamic> _$$CurrencyResponseImplToJson(
  _$CurrencyResponseImpl instance,
) => <String, dynamic>{
  'currency': _$CurrencyEnumMap[instance.currency],
  'rate': instance.rate,
};

const _$CurrencyEnumMap = {
  Currency.BDT: 'BDT',
  Currency.USD: 'USD',
  Currency.EUR: 'EUR',
  Currency.GBP: 'GBP',
};

_$CustomerDashboardResponseImpl _$$CustomerDashboardResponseImplFromJson(
  Map<String, dynamic> json,
) => _$CustomerDashboardResponseImpl(
  balance: (json['balance'] as num?)?.toDouble(),
  totalCredit: (json['totalCredit'] as num?)?.toDouble(),
  totalDebit: (json['totalDebit'] as num?)?.toDouble(),
  totalLoan: (json['totalLoan'] as num?)?.toDouble(),
  totalCard: (json['totalCard'] as num?)?.toInt(),
  totalTransaction: (json['totalTransaction'] as num?)?.toInt(),
  totalBeneficiary: (json['totalBeneficiary'] as num?)?.toInt(),
  totalAccount: (json['totalAccount'] as num?)?.toInt(),
  cards: (json['cards'] as List<dynamic>?)
      ?.map((e) => CardResponse.fromJson(e as Map<String, dynamic>))
      .toList(),
  accounts: (json['accounts'] as List<dynamic>?)
      ?.map((e) => AccountResponse.fromJson(e as Map<String, dynamic>))
      .toList(),
  last30DaysTransactions: (json['last30DaysTransactions'] as List<dynamic>?)
      ?.map((e) => JournalResponse.fromJson(e as Map<String, dynamic>))
      .toList(),
  recentTransactions: (json['recentTransactions'] as List<dynamic>?)
      ?.map((e) => JournalResponse.fromJson(e as Map<String, dynamic>))
      .toList(),
);

Map<String, dynamic> _$$CustomerDashboardResponseImplToJson(
  _$CustomerDashboardResponseImpl instance,
) => <String, dynamic>{
  'balance': instance.balance,
  'totalCredit': instance.totalCredit,
  'totalDebit': instance.totalDebit,
  'totalLoan': instance.totalLoan,
  'totalCard': instance.totalCard,
  'totalTransaction': instance.totalTransaction,
  'totalBeneficiary': instance.totalBeneficiary,
  'totalAccount': instance.totalAccount,
  'cards': instance.cards?.map((e) => e.toJson()).toList(),
  'accounts': instance.accounts?.map((e) => e.toJson()).toList(),
  'last30DaysTransactions': instance.last30DaysTransactions
      ?.map((e) => e.toJson())
      .toList(),
  'recentTransactions': instance.recentTransactions
      ?.map((e) => e.toJson())
      .toList(),
};
