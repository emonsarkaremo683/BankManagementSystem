// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'loan_models.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$LoanApplicationRequestImpl _$$LoanApplicationRequestImplFromJson(
  Map<String, dynamic> json,
) => _$LoanApplicationRequestImpl(
  accountId: (json['accountId'] as num?)?.toInt(),
  principalAmount: (json['principalAmount'] as num?)?.toDouble(),
  annualInterestRate: (json['annualInterestRate'] as num?)?.toDouble(),
  tenureMonths: (json['tenureMonths'] as num?)?.toInt(),
  guarantor: json['guarantor'] == null
      ? null
      : GuarantorRequest.fromJson(json['guarantor'] as Map<String, dynamic>),
);

Map<String, dynamic> _$$LoanApplicationRequestImplToJson(
  _$LoanApplicationRequestImpl instance,
) => <String, dynamic>{
  'accountId': instance.accountId,
  'principalAmount': instance.principalAmount,
  'annualInterestRate': instance.annualInterestRate,
  'tenureMonths': instance.tenureMonths,
  'guarantor': instance.guarantor?.toJson(),
};

_$GuarantorRequestImpl _$$GuarantorRequestImplFromJson(
  Map<String, dynamic> json,
) => _$GuarantorRequestImpl(
  name: json['name'] as String?,
  phone: json['phone'] as String?,
  address: json['address'] as String?,
  nidNumber: json['nidNumber'] as String?,
  relation: json['relation'] as String?,
);

Map<String, dynamic> _$$GuarantorRequestImplToJson(
  _$GuarantorRequestImpl instance,
) => <String, dynamic>{
  'name': instance.name,
  'phone': instance.phone,
  'address': instance.address,
  'nidNumber': instance.nidNumber,
  'relation': instance.relation,
};

_$LoanApplicationResponseImpl _$$LoanApplicationResponseImplFromJson(
  Map<String, dynamic> json,
) => _$LoanApplicationResponseImpl(
  loanId: (json['loanId'] as num?)?.toInt(),
  accountId: (json['accountId'] as num?)?.toInt(),
  accountNumber: json['accountNumber'] as String?,
  principalAmount: (json['principalAmount'] as num?)?.toDouble(),
  annualInterestRate: (json['annualInterestRate'] as num?)?.toDouble(),
  tenureMonths: (json['tenureMonths'] as num?)?.toInt(),
  emiAmount: (json['emiAmount'] as num?)?.toDouble(),
  totalPayable: (json['totalPayable'] as num?)?.toDouble(),
  outstandingBalance: (json['outstandingBalance'] as num?)?.toDouble(),
  disbursementCharge: (json['disbursementCharge'] as num?)?.toDouble(),
  status: $enumDecodeNullable(_$LoanStatusEnumMap, json['status']),
  applicationDate: json['applicationDate'] == null
      ? null
      : DateTime.parse(json['applicationDate'] as String),
  approvalDate: json['approvalDate'] == null
      ? null
      : DateTime.parse(json['approvalDate'] as String),
  disbursementDate: json['disbursementDate'] == null
      ? null
      : DateTime.parse(json['disbursementDate'] as String),
  nextDueDate: json['nextDueDate'] == null
      ? null
      : DateTime.parse(json['nextDueDate'] as String),
  rejectionReason: json['rejectionReason'] as String?,
  disbursementTransactionRef: json['disbursementTransactionRef'] as String?,
  guarantors: (json['guarantors'] as List<dynamic>?)
      ?.map((e) => GuarantorResponse.fromJson(e as Map<String, dynamic>))
      .toList(),
  documents: (json['documents'] as List<dynamic>?)
      ?.map((e) => DocumentResponse.fromJson(e as Map<String, dynamic>))
      .toList(),
);

Map<String, dynamic> _$$LoanApplicationResponseImplToJson(
  _$LoanApplicationResponseImpl instance,
) => <String, dynamic>{
  'loanId': instance.loanId,
  'accountId': instance.accountId,
  'accountNumber': instance.accountNumber,
  'principalAmount': instance.principalAmount,
  'annualInterestRate': instance.annualInterestRate,
  'tenureMonths': instance.tenureMonths,
  'emiAmount': instance.emiAmount,
  'totalPayable': instance.totalPayable,
  'outstandingBalance': instance.outstandingBalance,
  'disbursementCharge': instance.disbursementCharge,
  'status': _$LoanStatusEnumMap[instance.status],
  'applicationDate': instance.applicationDate?.toIso8601String(),
  'approvalDate': instance.approvalDate?.toIso8601String(),
  'disbursementDate': instance.disbursementDate?.toIso8601String(),
  'nextDueDate': instance.nextDueDate?.toIso8601String(),
  'rejectionReason': instance.rejectionReason,
  'disbursementTransactionRef': instance.disbursementTransactionRef,
  'guarantors': instance.guarantors?.map((e) => e.toJson()).toList(),
  'documents': instance.documents?.map((e) => e.toJson()).toList(),
};

const _$LoanStatusEnumMap = {
  LoanStatus.PENDING: 'PENDING',
  LoanStatus.APPROVED: 'APPROVED',
  LoanStatus.REJECTED: 'REJECTED',
  LoanStatus.DISBURSED: 'DISBURSED',
  LoanStatus.ACTIVE: 'ACTIVE',
  LoanStatus.CLOSED: 'CLOSED',
  LoanStatus.OVERDUE: 'OVERDUE',
  LoanStatus.DEFAULTED: 'DEFAULTED',
};

_$GuarantorResponseImpl _$$GuarantorResponseImplFromJson(
  Map<String, dynamic> json,
) => _$GuarantorResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  name: json['name'] as String?,
  phone: json['phone'] as String?,
  address: json['address'] as String?,
  nidNumber: json['nidNumber'] as String?,
  relation: json['relation'] as String?,
  photoPath: json['photoPath'] as String?,
);

Map<String, dynamic> _$$GuarantorResponseImplToJson(
  _$GuarantorResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'name': instance.name,
  'phone': instance.phone,
  'address': instance.address,
  'nidNumber': instance.nidNumber,
  'relation': instance.relation,
  'photoPath': instance.photoPath,
};

_$DocumentResponseImpl _$$DocumentResponseImplFromJson(
  Map<String, dynamic> json,
) => _$DocumentResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  fileName: json['fileName'] as String?,
  originalFileName: json['originalFileName'] as String?,
  contentType: json['contentType'] as String?,
  fileSize: (json['fileSize'] as num?)?.toInt(),
);

Map<String, dynamic> _$$DocumentResponseImplToJson(
  _$DocumentResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'fileName': instance.fileName,
  'originalFileName': instance.originalFileName,
  'contentType': instance.contentType,
  'fileSize': instance.fileSize,
};

_$LoanRepaymentResponseImpl _$$LoanRepaymentResponseImplFromJson(
  Map<String, dynamic> json,
) => _$LoanRepaymentResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  loanId: (json['loanId'] as num?)?.toInt(),
  installmentNumber: (json['installmentNumber'] as num?)?.toInt(),
  dueDate: json['dueDate'] == null
      ? null
      : DateTime.parse(json['dueDate'] as String),
  principalComponent: (json['principalComponent'] as num?)?.toDouble(),
  interestComponent: (json['interestComponent'] as num?)?.toDouble(),
  emiAmount: (json['emiAmount'] as num?)?.toDouble(),
  remainingBalanceAfter: (json['remainingBalanceAfter'] as num?)?.toDouble(),
  status: $enumDecodeNullable(_$RepaymentStatusEnumMap, json['status']),
  paidDate: json['paidDate'] == null
      ? null
      : DateTime.parse(json['paidDate'] as String),
  transactionRef: json['transactionRef'] as String?,
);

Map<String, dynamic> _$$LoanRepaymentResponseImplToJson(
  _$LoanRepaymentResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'loanId': instance.loanId,
  'installmentNumber': instance.installmentNumber,
  'dueDate': instance.dueDate?.toIso8601String(),
  'principalComponent': instance.principalComponent,
  'interestComponent': instance.interestComponent,
  'emiAmount': instance.emiAmount,
  'remainingBalanceAfter': instance.remainingBalanceAfter,
  'status': _$RepaymentStatusEnumMap[instance.status],
  'paidDate': instance.paidDate?.toIso8601String(),
  'transactionRef': instance.transactionRef,
};

const _$RepaymentStatusEnumMap = {
  RepaymentStatus.PENDING: 'PENDING',
  RepaymentStatus.PAID: 'PAID',
  RepaymentStatus.LATE: 'LATE',
  RepaymentStatus.MISSED: 'MISSED',
};

_$LoanScheduleResponseImpl _$$LoanScheduleResponseImplFromJson(
  Map<String, dynamic> json,
) => _$LoanScheduleResponseImpl(
  repaymentId: (json['repaymentId'] as num?)?.toInt(),
  installmentNumber: (json['installmentNumber'] as num?)?.toInt(),
  dueDate: json['dueDate'] == null
      ? null
      : DateTime.parse(json['dueDate'] as String),
  principalComponent: (json['principalComponent'] as num?)?.toDouble(),
  interestComponent: (json['interestComponent'] as num?)?.toDouble(),
  emiAmount: (json['emiAmount'] as num?)?.toDouble(),
  remainingBalanceAfter: (json['remainingBalanceAfter'] as num?)?.toDouble(),
  status: $enumDecodeNullable(_$RepaymentStatusEnumMap, json['status']),
  paidDate: json['paidDate'] == null
      ? null
      : DateTime.parse(json['paidDate'] as String),
  transactionRef: json['transactionRef'] as String?,
);

Map<String, dynamic> _$$LoanScheduleResponseImplToJson(
  _$LoanScheduleResponseImpl instance,
) => <String, dynamic>{
  'repaymentId': instance.repaymentId,
  'installmentNumber': instance.installmentNumber,
  'dueDate': instance.dueDate?.toIso8601String(),
  'principalComponent': instance.principalComponent,
  'interestComponent': instance.interestComponent,
  'emiAmount': instance.emiAmount,
  'remainingBalanceAfter': instance.remainingBalanceAfter,
  'status': _$RepaymentStatusEnumMap[instance.status],
  'paidDate': instance.paidDate?.toIso8601String(),
  'transactionRef': instance.transactionRef,
};
