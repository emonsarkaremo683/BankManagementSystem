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
  'journals': instance.journals,
};

const _$TransactionTypeEnumMap = {
  TransactionType.DEPOSIT: 'DEPOSIT',
  TransactionType.WITHDRAWAL: 'WITHDRAWAL',
  TransactionType.TRANSFER: 'TRANSFER',
  TransactionType.PAYMENT: 'PAYMENT',
};

const _$TransactionChannelEnumMap = {
  TransactionChannel.WEB: 'WEB',
  TransactionChannel.MOBILE: 'MOBILE',
  TransactionChannel.ATM: 'ATM',
  TransactionChannel.BRANCH: 'BRANCH',
};

const _$TransactionStatusEnumMap = {
  TransactionStatus.PENDING: 'PENDING',
  TransactionStatus.COMPLETED: 'COMPLETED',
  TransactionStatus.FAILED: 'FAILED',
  TransactionStatus.CANCELLED: 'CANCELLED',
};

_$JournalResponseImpl _$$JournalResponseImplFromJson(
  Map<String, dynamic> json,
) => _$JournalResponseImpl(
  id: (json['id'] as num?)?.toInt(),
  accountNumber: json['accountNumber'] as String?,
  amount: (json['amount'] as num?)?.toDouble(),
  type: json['type'] as String?,
  remarks: json['remarks'] as String?,
  createdAt: json['createdAt'] == null
      ? null
      : DateTime.parse(json['createdAt'] as String),
);

Map<String, dynamic> _$$JournalResponseImplToJson(
  _$JournalResponseImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'accountNumber': instance.accountNumber,
  'amount': instance.amount,
  'type': instance.type,
  'remarks': instance.remarks,
  'createdAt': instance.createdAt?.toIso8601String(),
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
  'request': instance.request,
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
  'response': instance.response,
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
