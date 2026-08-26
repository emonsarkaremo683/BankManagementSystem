// ignore_for_file: invalid_annotation_target, constant_identifier_names
import 'package:freezed_annotation/freezed_annotation.dart';
import '../enums.dart';
import '../transaction/transaction_models.dart';

part 'card_models.freezed.dart';
part 'card_models.g.dart';

@freezed
class CardRequest with _$CardRequest {
  const factory CardRequest({
    int? accountId,
    CardNetwork? cardNetwork,
    CardType? cardType,
    String? pin,
    @Default(false) bool isInternationalEnabled,
    @Default(false) bool isOnlineTransactionEnabled,
  }) = _CardRequest;

  factory CardRequest.fromJson(Map<String, dynamic> json) => _$CardRequestFromJson(json);
}

@freezed
class CardResponse with _$CardResponse {
  const factory CardResponse({
    int? cardId,
    String? cardNumber,
    String? cardHolderName,
    CardNetwork? cardNetwork,
    CardType? cardType,
    CardStatus? status,
    DateTime? expiryDate,
    double? dailyLimit,
    double? monthlyLimit,
    String? accountNumber,
    @Default(false) bool isInternationalEnabled,
    @Default(false) bool isOnlineTransactionEnabled,
    DateTime? createdAt,
  }) = _CardResponse;

  factory CardResponse.fromJson(Map<String, dynamic> json) => _$CardResponseFromJson(json);
}

@freezed
class PinChangeRequest with _$PinChangeRequest {
  const factory PinChangeRequest({
    String? oldPin,
    String? newPin,
  }) = _PinChangeRequest;

  factory PinChangeRequest.fromJson(Map<String, dynamic> json) => _$PinChangeRequestFromJson(json);
}

@freezed
class CardUsageResponse with _$CardUsageResponse {
  const factory CardUsageResponse({
    int? cardId,
    String? cardNumber,
    double? dailyLimit,
    double? monthlyLimit,
    double? currentDailyUsage,
    double? currentMonthlyUsage,
    double? dailyRemaining,
    double? monthlyRemaining,
  }) = _CardUsageResponse;

  factory CardUsageResponse.fromJson(Map<String, dynamic> json) => _$CardUsageResponseFromJson(json);
}

@freezed
class CardSettingsRequest with _$CardSettingsRequest {
  const factory CardSettingsRequest({
    int? id,
    int? cardId,
    CardSettingsRequestType? requestType,
    bool? requestedValue,
    CardType? requestedCardType,
    RequestStatus? status,
    String? rejectionReason,
    int? requestedById,
  }) = _CardSettingsRequest;

  factory CardSettingsRequest.fromJson(Map<String, dynamic> json) => _$CardSettingsRequestFromJson(json);
}

enum CardSettingsRequestType {
  INTERNATIONAL_ENABLED,
  ONLINE_TRANSACTION_ENABLED,
  CARD_TYPE_CHANGE
}

@freezed
class ATMTransactionRequest with _$ATMTransactionRequest {
  const factory ATMTransactionRequest({
    int? atmId,
    String? cardNumber,
    ATMTransactionType? transactionType,
    String? pin,
    TransactionRequest? transactionRequest,
  }) = _ATMTransactionRequest;

  factory ATMTransactionRequest.fromJson(Map<String, dynamic> json) => _$ATMTransactionRequestFromJson(json);
}

@freezed
class ATMTransactionResponse with _$ATMTransactionResponse {
  const factory ATMTransactionResponse({
    @JsonKey(name: 'ATMTransactionId') int? atmTransactionId,
    ATMTransactionType? transactionType,
    String? cardNumber,
    String? address,
    TransactionResponse? transactionResponse,
  }) = _ATMTransactionResponse;

  factory ATMTransactionResponse.fromJson(Map<String, dynamic> json) => _$ATMTransactionResponseFromJson(json);
}
