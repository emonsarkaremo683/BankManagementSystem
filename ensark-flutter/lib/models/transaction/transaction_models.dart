// ignore_for_file: invalid_annotation_target
import 'package:freezed_annotation/freezed_annotation.dart';
import '../enums.dart';

part 'transaction_models.freezed.dart';
part 'transaction_models.g.dart';

@freezed
class TransactionRequest with _$TransactionRequest {
  const factory TransactionRequest({
    double? amount,
    String? remarks,
  }) = _TransactionRequest;

  factory TransactionRequest.fromJson(Map<String, dynamic> json) => _$TransactionRequestFromJson(json);
}

@freezed
class TransactionResponse with _$TransactionResponse {
  const factory TransactionResponse({
    String? transactionId,
    String? referenceNo,
    TransactionType? transactionType,
    TransactionChannel? channel,
    TransactionStatus? status,
    double? amount,
    double? chargeAmount,
    double? vatAmount,
    String? remarks,
    DateTime? createdAt,
    @Default([]) List<JournalResponse> journals,
  }) = _TransactionResponse;

  factory TransactionResponse.fromJson(Map<String, dynamic> json) => _$TransactionResponseFromJson(json);
}

@freezed
class JournalResponse with _$JournalResponse {
  const factory JournalResponse({
    int? id,
    String? accountNumber,
    double? amount,
    @JsonKey(name: 'entryType') String? type, // DEBIT/CREDIT
    String? remarks,
    @JsonKey(name: 'date') DateTime? createdAt,
    String? transactionId,
    String? particulars,
    String? counterpartyAccountNumber,
    String? counterpartyName,
  }) = _JournalResponse;

  factory JournalResponse.fromJson(Map<String, dynamic> json) => _$JournalResponseFromJson(json);
}

@freezed
class AccountTransactionRequest with _$AccountTransactionRequest {
  const factory AccountTransactionRequest({
    int? senderAccountId,
    int? receiverAccountId,
    String? receiverAccountNumber,
    String? receiverName,
    String? bankName,
    String? routingNumber,
    int? beneficiaryId,
    TransactionRequest? request,
  }) = _AccountTransactionRequest;

  factory AccountTransactionRequest.fromJson(Map<String, dynamic> json) => _$AccountTransactionRequestFromJson(json);
}

@freezed
class AccountTransactionResponse with _$AccountTransactionResponse {
  const factory AccountTransactionResponse({
    int? id,
    String? transactionId,
    String? senderAccountNumber,
    String? senderName,
    String? receiverAccountNumber,
    String? receiverName,
    String? bankName,
    String? direction,
    TransactionResponse? response,
  }) = _AccountTransactionResponse;

  factory AccountTransactionResponse.fromJson(Map<String, dynamic> json) => _$AccountTransactionResponseFromJson(json);
}

@freezed
class OtpInitiateResponse with _$OtpInitiateResponse {
  const factory OtpInitiateResponse({
    int? otpReferenceId,
    String? maskedEmail,
    DateTime? expiresAt,
  }) = _OtpInitiateResponse;

  factory OtpInitiateResponse.fromJson(Map<String, dynamic> json) => _$OtpInitiateResponseFromJson(json);
}

@freezed
class OtpVerifyRequest with _$OtpVerifyRequest {
  const factory OtpVerifyRequest({
    int? otpReferenceId,
    String? otpCode,
  }) = _OtpVerifyRequest;

  factory OtpVerifyRequest.fromJson(Map<String, dynamic> json) => _$OtpVerifyRequestFromJson(json);
}

@freezed
class BalanceCheckRequest with _$BalanceCheckRequest {
  const factory BalanceCheckRequest({
    String? cardNumber,
    String? pin,
  }) = _BalanceCheckRequest;

  factory BalanceCheckRequest.fromJson(Map<String, dynamic> json) => _$BalanceCheckRequestFromJson(json);
}
