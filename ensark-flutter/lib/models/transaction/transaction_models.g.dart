// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'transaction_models.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$TransactionRequestImpl _$$TransactionRequestImplFromJson(
  Map<String, dynamic> json,
) => _$TransactionRequestImpl(
  amount: (json['amount'] as num?)?.toDouble(),
  remarks: json['remarks'] as String?,
);

Map<String, dynamic> _$$TransactionRequestImplToJson(
  _$TransactionRequestImpl instance,
) => <String, dynamic>{'amount': instance.amount, 'remarks': instance.remarks};

_$TransactionResponseImpl _$$TransactionResponseImplFromJson(
  Map<String, dynamic> json,
) => _$TransactionResponseImpl(
  transactionId: json['transactionId'] as String?,
  referenceNo: json['referenceNo'] as String?,
  transactionType: $enumDecodeNullable(
    _$TransactionTypeEnumMap,
    json['transactionType'],
  ),
  channel: $enumDecodeNullable(_$TransactionChannelEnumMap, json['channel']),
  status: $enumDecodeNullable(_$TransactionStatusEnumMap, json['status']),
  amount: (json['amount'] as num?)?.toDouble(),
  chargeAmount: (json['chargeAmount'] as num?)?.toDouble(),
  vatAmount: (json['vatAmount'] as num?)?.toDouble(),
  remarks: json['remarks'] as String?,
  createdAt: json['createdAt'] == null
      ? null
      : DateTime.parse(json['createdAt'] as String),
  journals:
      (json['journals'] as List<dynamic>?)
          ?.map((e) => JournalResponse.fromJson(e as Map<String, dynamic>))
          .toList() ??
      const [],
);

Map<String, dynamic> _$$TransactionResponseImplToJson(
  _$TransactionResponseImpl instance,
) => <String, dynamic>{
  'transactionId': instance.transactionId,
  'referenceNo': instance.referenceNo,
  'transactionType': _$TransactionTypeEnumMap[instance.transactionType],
  'channel': _$TransactionChannelEnumMap[instance.channel],
  'status': _$TransactionStatusEnumMap[instance.status],
  'amount': instance.amount,
  'chargeAmount': instance.chargeAmount,
  'vatAmount': instance.vatAmount,
  'remarks': instance.remarks,
  'createdAt': instance.createdAt?.toIso8601String(),
  'journals': instance.journals.map((e) => e.toJson()).toList(),
};

const _$TransactionTypeEnumMap = {
  TransactionType.DEPOSIT: 'DEPOSIT',
  TransactionType.WITHDRAW: 'WITHDRAW',
  TransactionType.TRANSFER: 'TRANSFER',
  TransactionType.PAYMENT: 'PAYMENT',
  TransactionType.REFUND: 'REFUND',
  TransactionType.REVERSE: 'REVERSE',
  TransactionType.ATM_WITHDRAW: 'ATM_WITHDRAW',
  TransactionType.ATM_DEPOSIT: 'ATM_DEPOSIT',
  TransactionType.LOAN_DISBURSEMENT: 'LOAN_DISBURSEMENT',
  TransactionType.LOAN_REPAYMENT: 'LOAN_REPAYMENT',
  TransactionType.LOAN_FORECLOSURE: 'LOAN_FORECLOSURE',
  TransactionType.CARD_PURCHASE: 'CARD_PURCHASE',
  TransactionType.CARD_REVERSAL: 'CARD_REVERSAL',
  TransactionType.BRANCH_OPENING: 'BRANCH_OPENING',
  TransactionType.INTEREST_POSTING: 'INTEREST_POSTING',
  TransactionType.CHEQUE_ISSUE_CHARGE: 'CHEQUE_ISSUE_CHARGE',
  TransactionType.CARD_ISSUE_CHARGE: 'CARD_ISSUE_CHARGE',
  TransactionType.ATM_REFILL: 'ATM_REFILL',
  TransactionType.CREDIT_BILLING: 'CREDIT_BILLING',
  TransactionType.CREDIT_PAYMENT: 'CREDIT_PAYMENT',
  TransactionType.STANDING_ORDER_EXECUTION: 'STANDING_ORDER_EXECUTION',
};

const _$TransactionChannelEnumMap = {
  TransactionChannel.BRANCH: 'BRANCH',
  TransactionChannel.ATM: 'ATM',
  TransactionChannel.INTERNET_BANKING: 'INTERNET_BANKING',
  TransactionChannel.MOBILE_BANKING: 'MOBILE_BANKING',
  TransactionChannel.POS: 'POS',
  TransactionChannel.E_COMMERCE: 'E_COMMERCE',
  TransactionChannel.QR_PAYMENT: 'QR_PAYMENT',
  TransactionChannel.CARD: 'CARD',
  TransactionChannel.BEFTN: 'BEFTN',
  TransactionChannel.NPSB: 'NPSB',
  TransactionChannel.RTGS: 'RTGS',
  TransactionChannel.SWIFT: 'SWIFT',
  TransactionChannel.AGENT_BANKING: 'AGENT_BANKING',
  TransactionChannel.API: 'API',
  TransactionChannel.SYSTEM: 'SYSTEM',
};

const _$TransactionStatusEnumMap = {
  TransactionStatus.SUCCESS: 'SUCCESS',
  TransactionStatus.FAILED: 'FAILED',
  TransactionStatus.PENDING: 'PENDING',
  TransactionStatus.CANCELLED: 'CANCELLED',
  TransactionStatus.REVERSED: 'REVERSED',
};

_$JournalResponseImpl _$$JournalResponseImplFromJson(
  Map<String, dynamic> json,
) => _$JournalResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  accountNumber: json['accountNumber'] as String?,
  amount: (json['amount'] as num?)?.toDouble(),
  type: json['entryType'] as String?,
  remarks: json['remarks'] as String?,
  createdAt: json['date'] == null
      ? null
      : DateTime.parse(json['date'] as String),
  transactionId: json['transactionId'] as String?,
  particulars: json['particulars'] as String?,
  counterpartyAccountNumber: json['counterpartyAccountNumber'] as String?,
  counterpartyName: json['counterpartyName'] as String?,
);

Map<String, dynamic> _$$JournalResponseImplToJson(
  _$JournalResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'accountNumber': instance.accountNumber,
  'amount': instance.amount,
  'entryType': instance.type,
  'remarks': instance.remarks,
  'date': instance.createdAt?.toIso8601String(),
  'transactionId': instance.transactionId,
  'particulars': instance.particulars,
  'counterpartyAccountNumber': instance.counterpartyAccountNumber,
  'counterpartyName': instance.counterpartyName,
};

_$AccountTransactionRequestImpl _$$AccountTransactionRequestImplFromJson(
  Map<String, dynamic> json,
) => _$AccountTransactionRequestImpl(
  senderAccountId: (json['senderAccountId'] as num?)?.toInt(),
  receiverAccountId: (json['receiverAccountId'] as num?)?.toInt(),
  receiverAccountNumber: json['receiverAccountNumber'] as String?,
  receiverName: json['receiverName'] as String?,
  bankName: json['bankName'] as String?,
  routingNumber: json['routingNumber'] as String?,
  beneficiaryId: (json['beneficiaryId'] as num?)?.toInt(),
  request: json['request'] == null
      ? null
      : TransactionRequest.fromJson(json['request'] as Map<String, dynamic>),
);

Map<String, dynamic> _$$AccountTransactionRequestImplToJson(
  _$AccountTransactionRequestImpl instance,
) => <String, dynamic>{
  'senderAccountId': instance.senderAccountId,
  'receiverAccountId': instance.receiverAccountId,
  'receiverAccountNumber': instance.receiverAccountNumber,
  'receiverName': instance.receiverName,
  'bankName': instance.bankName,
  'routingNumber': instance.routingNumber,
  'beneficiaryId': instance.beneficiaryId,
  'request': instance.request?.toJson(),
};

_$AccountTransactionResponseImpl _$$AccountTransactionResponseImplFromJson(
  Map<String, dynamic> json,
) => _$AccountTransactionResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  transactionId: json['transactionId'] as String?,
  senderAccountNumber: json['senderAccountNumber'] as String?,
  senderName: json['senderName'] as String?,
  receiverAccountNumber: json['receiverAccountNumber'] as String?,
  receiverName: json['receiverName'] as String?,
  bankName: json['bankName'] as String?,
  direction: json['direction'] as String?,
  response: json['response'] == null
      ? null
      : TransactionResponse.fromJson(json['response'] as Map<String, dynamic>),
);

Map<String, dynamic> _$$AccountTransactionResponseImplToJson(
  _$AccountTransactionResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'transactionId': instance.transactionId,
  'senderAccountNumber': instance.senderAccountNumber,
  'senderName': instance.senderName,
  'receiverAccountNumber': instance.receiverAccountNumber,
  'receiverName': instance.receiverName,
  'bankName': instance.bankName,
  'direction': instance.direction,
  'response': instance.response?.toJson(),
};

_$OtpInitiateResponseImpl _$$OtpInitiateResponseImplFromJson(
  Map<String, dynamic> json,
) => _$OtpInitiateResponseImpl(
  otpReferenceId: (json['otpReferenceId'] as num?)?.toInt(),
  maskedEmail: json['maskedEmail'] as String?,
  expiresAt: json['expiresAt'] == null
      ? null
      : DateTime.parse(json['expiresAt'] as String),
);

Map<String, dynamic> _$$OtpInitiateResponseImplToJson(
  _$OtpInitiateResponseImpl instance,
) => <String, dynamic>{
  'otpReferenceId': instance.otpReferenceId,
  'maskedEmail': instance.maskedEmail,
  'expiresAt': instance.expiresAt?.toIso8601String(),
};

_$OtpVerifyRequestImpl _$$OtpVerifyRequestImplFromJson(
  Map<String, dynamic> json,
) => _$OtpVerifyRequestImpl(
  otpReferenceId: (json['otpReferenceId'] as num?)?.toInt(),
  otpCode: json['otpCode'] as String?,
);

Map<String, dynamic> _$$OtpVerifyRequestImplToJson(
  _$OtpVerifyRequestImpl instance,
) => <String, dynamic>{
  'otpReferenceId': instance.otpReferenceId,
  'otpCode': instance.otpCode,
};

_$BalanceCheckRequestImpl _$$BalanceCheckRequestImplFromJson(
  Map<String, dynamic> json,
) => _$BalanceCheckRequestImpl(
  cardNumber: json['cardNumber'] as String?,
  pin: json['pin'] as String?,
);

Map<String, dynamic> _$$BalanceCheckRequestImplToJson(
  _$BalanceCheckRequestImpl instance,
) => <String, dynamic>{'cardNumber': instance.cardNumber, 'pin': instance.pin};
