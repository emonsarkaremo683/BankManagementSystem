// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'card_models.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$CardRequestImpl _$$CardRequestImplFromJson(Map<String, dynamic> json) =>
    _$CardRequestImpl(
      accountId: (json['accountId'] as num?)?.toInt(),
      cardNetwork: $enumDecodeNullable(
        _$CardNetworkEnumMap,
        json['cardNetwork'],
      ),
      cardType: $enumDecodeNullable(_$CardTypeEnumMap, json['cardType']),
      pin: json['pin'] as String?,
      isInternationalEnabled: json['isInternationalEnabled'] as bool? ?? false,
      isOnlineTransactionEnabled:
          json['isOnlineTransactionEnabled'] as bool? ?? false,
    );

Map<String, dynamic> _$$CardRequestImplToJson(_$CardRequestImpl instance) =>
    <String, dynamic>{
      'accountId': instance.accountId,
      'cardNetwork': _$CardNetworkEnumMap[instance.cardNetwork],
      'cardType': _$CardTypeEnumMap[instance.cardType],
      'pin': instance.pin,
      'isInternationalEnabled': instance.isInternationalEnabled,
      'isOnlineTransactionEnabled': instance.isOnlineTransactionEnabled,
    };

const _$CardNetworkEnumMap = {
  CardNetwork.VISA: 'VISA',
  CardNetwork.MASTERCARD: 'MASTERCARD',
  CardNetwork.AMEX: 'AMEX',
  CardNetwork.DISCOVER: 'DISCOVER',
};

const _$CardTypeEnumMap = {
  CardType.DEBIT: 'DEBIT',
  CardType.CREDIT: 'CREDIT',
  CardType.PREPAID: 'PREPAID',
};

_$CardResponseImpl _$$CardResponseImplFromJson(Map<String, dynamic> json) =>
    _$CardResponseImpl(
      cardId: (json['cardId'] as num?)?.toInt(),
      cardNumber: json['cardNumber'] as String?,
      cardHolderName: json['cardHolderName'] as String?,
      cardNetwork: $enumDecodeNullable(
        _$CardNetworkEnumMap,
        json['cardNetwork'],
      ),
      cardType: $enumDecodeNullable(_$CardTypeEnumMap, json['cardType']),
      status: $enumDecodeNullable(_$CardStatusEnumMap, json['status']),
      expiryDate: json['expiryDate'] == null
          ? null
          : DateTime.parse(json['expiryDate'] as String),
      dailyLimit: (json['dailyLimit'] as num?)?.toDouble(),
      monthlyLimit: (json['monthlyLimit'] as num?)?.toDouble(),
      accountNumber: json['accountNumber'] as String?,
      isInternationalEnabled: json['isInternationalEnabled'] as bool? ?? false,
      isOnlineTransactionEnabled:
          json['isOnlineTransactionEnabled'] as bool? ?? false,
      createdAt: json['createdAt'] == null
          ? null
          : DateTime.parse(json['createdAt'] as String),
    );

Map<String, dynamic> _$$CardResponseImplToJson(_$CardResponseImpl instance) =>
    <String, dynamic>{
      'cardId': instance.cardId,
      'cardNumber': instance.cardNumber,
      'cardHolderName': instance.cardHolderName,
      'cardNetwork': _$CardNetworkEnumMap[instance.cardNetwork],
      'cardType': _$CardTypeEnumMap[instance.cardType],
      'status': _$CardStatusEnumMap[instance.status],
      'expiryDate': instance.expiryDate?.toIso8601String(),
      'dailyLimit': instance.dailyLimit,
      'monthlyLimit': instance.monthlyLimit,
      'accountNumber': instance.accountNumber,
      'isInternationalEnabled': instance.isInternationalEnabled,
      'isOnlineTransactionEnabled': instance.isOnlineTransactionEnabled,
      'createdAt': instance.createdAt?.toIso8601String(),
    };

const _$CardStatusEnumMap = {
  CardStatus.ACTIVE: 'ACTIVE',
  CardStatus.INACTIVE: 'INACTIVE',
  CardStatus.BLOCKED: 'BLOCKED',
  CardStatus.EXPIRED: 'EXPIRED',
  CardStatus.PENDING: 'PENDING',
};

_$PinChangeRequestImpl _$$PinChangeRequestImplFromJson(
  Map<String, dynamic> json,
) => _$PinChangeRequestImpl(
  oldPin: json['oldPin'] as String?,
  newPin: json['newPin'] as String?,
);

Map<String, dynamic> _$$PinChangeRequestImplToJson(
  _$PinChangeRequestImpl instance,
) => <String, dynamic>{'oldPin': instance.oldPin, 'newPin': instance.newPin};

_$CardUsageResponseImpl _$$CardUsageResponseImplFromJson(
  Map<String, dynamic> json,
) => _$CardUsageResponseImpl(
  cardId: (json['cardId'] as num?)?.toInt(),
  cardNumber: json['cardNumber'] as String?,
  dailyLimit: (json['dailyLimit'] as num?)?.toDouble(),
  monthlyLimit: (json['monthlyLimit'] as num?)?.toDouble(),
  currentDailyUsage: (json['currentDailyUsage'] as num?)?.toDouble(),
  currentMonthlyUsage: (json['currentMonthlyUsage'] as num?)?.toDouble(),
  dailyRemaining: (json['dailyRemaining'] as num?)?.toDouble(),
  monthlyRemaining: (json['monthlyRemaining'] as num?)?.toDouble(),
);

Map<String, dynamic> _$$CardUsageResponseImplToJson(
  _$CardUsageResponseImpl instance,
) => <String, dynamic>{
  'cardId': instance.cardId,
  'cardNumber': instance.cardNumber,
  'dailyLimit': instance.dailyLimit,
  'monthlyLimit': instance.monthlyLimit,
  'currentDailyUsage': instance.currentDailyUsage,
  'currentMonthlyUsage': instance.currentMonthlyUsage,
  'dailyRemaining': instance.dailyRemaining,
  'monthlyRemaining': instance.monthlyRemaining,
};

_$CardSettingsRequestImpl _$$CardSettingsRequestImplFromJson(
  Map<String, dynamic> json,
) => _$CardSettingsRequestImpl(
  id: (json['id'] as num?)?.toInt(),
  cardId: (json['cardId'] as num?)?.toInt(),
  requestType: $enumDecodeNullable(
    _$CardSettingsRequestTypeEnumMap,
    json['requestType'],
  ),
  requestedValue: json['requestedValue'] as bool?,
  requestedCardType: $enumDecodeNullable(
    _$CardTypeEnumMap,
    json['requestedCardType'],
  ),
  status: $enumDecodeNullable(_$RequestStatusEnumMap, json['status']),
  rejectionReason: json['rejectionReason'] as String?,
  requestedById: (json['requestedById'] as num?)?.toInt(),
);

Map<String, dynamic> _$$CardSettingsRequestImplToJson(
  _$CardSettingsRequestImpl instance,
) => <String, dynamic>{
  'id': instance.id,
  'cardId': instance.cardId,
  'requestType': _$CardSettingsRequestTypeEnumMap[instance.requestType],
  'requestedValue': instance.requestedValue,
  'requestedCardType': _$CardTypeEnumMap[instance.requestedCardType],
  'status': _$RequestStatusEnumMap[instance.status],
  'rejectionReason': instance.rejectionReason,
  'requestedById': instance.requestedById,
};

const _$CardSettingsRequestTypeEnumMap = {
  CardSettingsRequestType.INTERNATIONAL_ENABLED: 'INTERNATIONAL_ENABLED',
  CardSettingsRequestType.ONLINE_TRANSACTION_ENABLED:
      'ONLINE_TRANSACTION_ENABLED',
  CardSettingsRequestType.CARD_TYPE_CHANGE: 'CARD_TYPE_CHANGE',
};

const _$RequestStatusEnumMap = {
  RequestStatus.PENDING: 'PENDING',
  RequestStatus.APPROVED: 'APPROVED',
  RequestStatus.REJECTED: 'REJECTED',
};

_$ATMTransactionRequestImpl _$$ATMTransactionRequestImplFromJson(
  Map<String, dynamic> json,
) => _$ATMTransactionRequestImpl(
  atmId: (json['atmId'] as num?)?.toInt(),
  cardNumber: json['cardNumber'] as String?,
  transactionType: $enumDecodeNullable(
    _$ATMTransactionTypeEnumMap,
    json['transactionType'],
  ),
  pin: json['pin'] as String?,
  transactionRequest: json['transactionRequest'] == null
      ? null
      : TransactionRequest.fromJson(
          json['transactionRequest'] as Map<String, dynamic>,
        ),
);

Map<String, dynamic> _$$ATMTransactionRequestImplToJson(
  _$ATMTransactionRequestImpl instance,
) => <String, dynamic>{
  'atmId': instance.atmId,
  'cardNumber': instance.cardNumber,
  'transactionType': _$ATMTransactionTypeEnumMap[instance.transactionType],
  'pin': instance.pin,
  'transactionRequest': instance.transactionRequest,
};

const _$ATMTransactionTypeEnumMap = {
  ATMTransactionType.WITHDRAWAL: 'WITHDRAWAL',
  ATMTransactionType.DEPOSIT: 'DEPOSIT',
  ATMTransactionType.BALANCE_INQUIRY: 'BALANCE_INQUIRY',
  ATMTransactionType.PIN_CHANGE: 'PIN_CHANGE',
};

_$ATMTransactionResponseImpl _$$ATMTransactionResponseImplFromJson(
  Map<String, dynamic> json,
) => _$ATMTransactionResponseImpl(
  atmTransactionId: (json['ATMTransactionId'] as num?)?.toInt(),
  transactionType: $enumDecodeNullable(
    _$ATMTransactionTypeEnumMap,
    json['transactionType'],
  ),
  cardNumber: json['cardNumber'] as String?,
  address: json['address'] as String?,
  transactionResponse: json['transactionResponse'] == null
      ? null
      : TransactionResponse.fromJson(
          json['transactionResponse'] as Map<String, dynamic>,
        ),
);

Map<String, dynamic> _$$ATMTransactionResponseImplToJson(
  _$ATMTransactionResponseImpl instance,
) => <String, dynamic>{
  'ATMTransactionId': instance.atmTransactionId,
  'transactionType': _$ATMTransactionTypeEnumMap[instance.transactionType],
  'cardNumber': instance.cardNumber,
  'address': instance.address,
  'transactionResponse': instance.transactionResponse,
};
