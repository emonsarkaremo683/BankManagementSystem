// ignore_for_file: invalid_annotation_target
import 'package:freezed_annotation/freezed_annotation.dart';
import '../enums.dart';
import '../account/account_models.dart';
import '../transaction/transaction_models.dart';
import '../card/card_models.dart';

part 'other_models.freezed.dart';
part 'other_models.g.dart';

@freezed
class ChequeBookRequest with _$ChequeBookRequest {
  const factory ChequeBookRequest({
    int? accountId,
    int? numberOfLeaves,
  }) = _ChequeBookRequest;

  factory ChequeBookRequest.fromJson(Map<String, dynamic> json) => _$ChequeBookRequestFromJson(json);
}

@freezed
class ChequeBookResponse with _$ChequeBookResponse {
  const factory ChequeBookResponse({
    int? chequeBookId,
    String? bookSerialNumber,
    int? numberOfLeaves,
    int? startLeafNumber,
    int? endLeafNumber,
    @JsonKey(unknownEnumValue: ChequeBookStatus.REQUESTED) ChequeBookStatus? status,
    int? accountId,
    String? accountNumber,
    DateTime? applicationDate,
    DateTime? approvalDate,
    DateTime? deliveryDate,
    DateTime? activationDate,
    DateTime? expiryDate,
    String? rejectionReason,
    List<ChequeLeafResponse>? leaves,
  }) = _ChequeBookResponse;

  factory ChequeBookResponse.fromJson(Map<String, dynamic> json) => _$ChequeBookResponseFromJson(json);
}

@freezed
class ChequeLeafResponse with _$ChequeLeafResponse {
  const factory ChequeLeafResponse({
    int? leafId,
    int? leafNumber,
    String? chequeNumber,
    double? amount,
    String? payeeName,
    String? remarks,
    @JsonKey(unknownEnumValue: ChequeLeafStatus.UNUSED) ChequeLeafStatus? status,
    DateTime? issueDate,
    DateTime? clearanceDate,
    DateTime? expiryDate,
    String? bounceReason,
    String? transactionReference,
    int? chequeBookId,
    String? bookSerialNumber,
  }) = _ChequeLeafResponse;

  factory ChequeLeafResponse.fromJson(Map<String, dynamic> json) => _$ChequeLeafResponseFromJson(json);
}

@freezed
class StandingOrderRequest with _$StandingOrderRequest {
  const factory StandingOrderRequest({
    int? sourceAccountId,
    String? targetAccountNumber,
    String? targetAccountName,
    double? amount,
    StandingOrderFrequency? frequency,
    String? startDate,
    String? endDate,
    int? maxExecutions,
    String? description,
  }) = _StandingOrderRequest;

  factory StandingOrderRequest.fromJson(Map<String, dynamic> json) => _$StandingOrderRequestFromJson(json);
}

@freezed
class StandingOrderResponse with _$StandingOrderResponse {
  const factory StandingOrderResponse({
    int? id,
    String? sourceAccountNumber,
    String? targetAccountNumber,
    String? targetAccountName,
    double? amount,
    StandingOrderFrequency? frequency,
    StandingOrderStatus? status,
    String? startDate,
    String? endDate,
    String? nextExecutionDate,
    String? lastExecutionDate,
    int? executionCount,
    int? maxExecutions,
    String? description,
  }) = _StandingOrderResponse;

  factory StandingOrderResponse.fromJson(Map<String, dynamic> json) => _$StandingOrderResponseFromJson(json);
}

@freezed
class BeneficiaryRequest with _$BeneficiaryRequest {
  const factory BeneficiaryRequest({
    String? accNumber,
    String? name,
    String? provider,
    String? routingNumber,
    BeneficiaryType? beneficiaryType,
    int? customerId,
  }) = _BeneficiaryRequest;

  factory BeneficiaryRequest.fromJson(Map<String, dynamic> json) => _$BeneficiaryRequestFromJson(json);
}

@freezed
class BeneficiaryResponse with _$BeneficiaryResponse {
  const factory BeneficiaryResponse({
    int? id,
    String? accNumber,
    String? name,
    String? provider,
    String? routingNumber,
    BeneficiaryType? beneficiaryType,
    int? customerId,
    String? customerName,
    bool? isVerified,
    bool? isBlocked,
    String? blockReason,
  }) = _BeneficiaryResponse;

  factory BeneficiaryResponse.fromJson(Map<String, dynamic> json) => _$BeneficiaryResponseFromJson(json);
}

@freezed
class NotificationResponse with _$NotificationResponse {
  const factory NotificationResponse({
    int? id,
    @JsonKey(unknownEnumValue: NotificationType.GENERAL) NotificationType? type,
    String? title,
    String? message,
    bool? read,
    String? referenceId,
    String? referenceType,
    String? createdAt,
  }) = _NotificationResponse;

  factory NotificationResponse.fromJson(Map<String, dynamic> json) => _$NotificationResponseFromJson(json);
}

@freezed
class BranchResponse with _$BranchResponse {
  const factory BranchResponse({
    int? id,
    String? name,
    String? address,
    String? routingNumber,
    String? branchCode,
    String? email,
    String? phoneNumber,
    BranchType? type,
    BranchStatus? status,
  }) = _BranchResponse;

  factory BranchResponse.fromJson(Map<String, dynamic> json) => _$BranchResponseFromJson(json);
}

@freezed
class DivisionResponse with _$DivisionResponse {
  const factory DivisionResponse({
    int? id,
    String? name,
  }) = _DivisionResponse;

  factory DivisionResponse.fromJson(Map<String, dynamic> json) => _$DivisionResponseFromJson(json);
}

@freezed
class DistrictResponse with _$DistrictResponse {
  const factory DistrictResponse({
    int? id,
    String? name,
  }) = _DistrictResponse;

  factory DistrictResponse.fromJson(Map<String, dynamic> json) => _$DistrictResponseFromJson(json);
}

@freezed
class PoliceStationResponse with _$PoliceStationResponse {
  const factory PoliceStationResponse({
    int? id,
    String? name,
  }) = _PoliceStationResponse;

  factory PoliceStationResponse.fromJson(Map<String, dynamic> json) => _$PoliceStationResponseFromJson(json);
}

@freezed
class CurrencyResponse with _$CurrencyResponse {
  const factory CurrencyResponse({
    Currency? currency,
    double? rate,
  }) = _CurrencyResponse;

  factory CurrencyResponse.fromJson(Map<String, dynamic> json) => _$CurrencyResponseFromJson(json);
}

@freezed
class CustomerDashboardResponse with _$CustomerDashboardResponse {
  const factory CustomerDashboardResponse({
    double? balance,
    double? totalCredit,
    double? totalDebit,
    double? totalLoan,
    int? totalCard,
    int? totalTransaction,
    int? totalBeneficiary,
    int? totalAccount,
    List<CardResponse>? cards,
    List<AccountResponse>? accounts,
    List<JournalResponse>? last30DaysTransactions,
    List<JournalResponse>? recentTransactions,
  }) = _CustomerDashboardResponse;

  factory CustomerDashboardResponse.fromJson(Map<String, dynamic> json) => _$CustomerDashboardResponseFromJson(json);
}
