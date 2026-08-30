// ignore_for_file: invalid_annotation_target
import 'package:freezed_annotation/freezed_annotation.dart';
import '../enums.dart';

part 'loan_models.freezed.dart';
part 'loan_models.g.dart';

@freezed
class LoanApplicationRequest with _$LoanApplicationRequest {
  const factory LoanApplicationRequest({
    int? accountId,
    double? principalAmount,
    double? annualInterestRate,
    int? tenureMonths,
    GuarantorRequest? guarantor,
  }) = _LoanApplicationRequest;

  factory LoanApplicationRequest.fromJson(Map<String, dynamic> json) => _$LoanApplicationRequestFromJson(json);
}

@freezed
class GuarantorRequest with _$GuarantorRequest {
  const factory GuarantorRequest({
    String? name,
    String? phone,
    String? address,
    String? nidNumber,
    String? relation,
  }) = _GuarantorRequest;

  factory GuarantorRequest.fromJson(Map<String, dynamic> json) => _$GuarantorRequestFromJson(json);
}

@freezed
class LoanApplicationResponse with _$LoanApplicationResponse {
  const factory LoanApplicationResponse({
    int? loanId,
    int? accountId,
    String? accountNumber,
    double? principalAmount,
    double? annualInterestRate,
    int? tenureMonths,
    double? emiAmount,
    double? totalPayable,
    double? outstandingBalance,
    double? disbursementCharge,
    LoanStatus? status,
    DateTime? applicationDate,
    DateTime? approvalDate,
    DateTime? disbursementDate,
    DateTime? nextDueDate,
    String? rejectionReason,
    String? disbursementTransactionRef,
    List<GuarantorResponse>? guarantors,
    List<DocumentResponse>? documents,
  }) = _LoanApplicationResponse;

  factory LoanApplicationResponse.fromJson(Map<String, dynamic> json) => _$LoanApplicationResponseFromJson(json);
}

@freezed
class GuarantorResponse with _$GuarantorResponse {
  const factory GuarantorResponse({
    int? id,
    String? name,
    String? phone,
    String? address,
    String? nidNumber,
    String? relation,
    String? photoPath,
  }) = _GuarantorResponse;

  factory GuarantorResponse.fromJson(Map<String, dynamic> json) => _$GuarantorResponseFromJson(json);
}

@freezed
class DocumentResponse with _$DocumentResponse {
  const factory DocumentResponse({
    int? id,
    String? fileName,
    String? originalFileName,
    String? contentType,
    int? fileSize,
  }) = _DocumentResponse;

  factory DocumentResponse.fromJson(Map<String, dynamic> json) => _$DocumentResponseFromJson(json);
}

@freezed
class LoanRepaymentResponse with _$LoanRepaymentResponse {
  const factory LoanRepaymentResponse({
    int? id,
    int? loanId,
    int? installmentNumber,
    DateTime? dueDate,
    double? principalComponent,
    double? interestComponent,
    double? emiAmount,
    double? remainingBalanceAfter,
    RepaymentStatus? status,
    DateTime? paidDate,
    String? transactionRef,
  }) = _LoanRepaymentResponse;

  factory LoanRepaymentResponse.fromJson(Map<String, dynamic> json) => _$LoanRepaymentResponseFromJson(json);
}

@freezed
class LoanScheduleResponse with _$LoanScheduleResponse {
  const factory LoanScheduleResponse({
    int? repaymentId,
    int? installmentNumber,
    DateTime? dueDate,
    double? principalComponent,
    double? interestComponent,
    double? emiAmount,
    double? remainingBalanceAfter,
    RepaymentStatus? status,
    DateTime? paidDate,
    String? transactionRef,
  }) = _LoanScheduleResponse;

  factory LoanScheduleResponse.fromJson(Map<String, dynamic> json) => _$LoanScheduleResponseFromJson(json);
}
