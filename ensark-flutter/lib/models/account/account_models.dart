// ignore_for_file: invalid_annotation_target
import 'package:freezed_annotation/freezed_annotation.dart';
import '../enums.dart';

part 'account_models.freezed.dart';
part 'account_models.g.dart';

@freezed
class AccountRequest with _$AccountRequest {
  const factory AccountRequest({
    AccountType? accountType,
    double? availableBalance,
    int? branchId,
    @JsonKey(name: 'n_name') String? nName,
    @JsonKey(name: 'n_email') String? nEmail,
    @JsonKey(name: 'n_phone') String? nPhone,
    NomineeRelation? relation,
    @JsonKey(name: 'n_photo') String? nPhoto,
    @JsonKey(name: 'n_nid_front') String? nNidFront,
    @JsonKey(name: 'n_nid_back') String? nNidBack,
    List<AccountHolderRequest>? accountHolders,
  }) = _AccountRequest;

  factory AccountRequest.fromJson(Map<String, dynamic> json) => _$AccountRequestFromJson(json);
}

@freezed
class AccountResponse with _$AccountResponse {
  const factory AccountResponse({
    int? id,
    String? accountNumber,
    AccountType? accountType,
    AccountStatus? accountStatus,
    double? availableBalance,
    double? currentBalance,
    double? holdBalance,
    String? branchName,
    String? branchRoutingNumber,
    @JsonKey(name: 'n_name') String? nName,
    @JsonKey(name: 'n_email') String? nEmail,
    NomineeRelation? relation,
    @JsonKey(name: 'n_phone') String? nPhone,
    @JsonKey(name: 'n_photo') String? nPhoto,
    @JsonKey(name: 'n_nid_front') String? nNidFront,
    @JsonKey(name: 'n_nid_back') String? nNidBack,
    @Default([]) List<AccountHolderResponse> holderResponses,
  }) = _AccountResponse;

  factory AccountResponse.fromJson(Map<String, dynamic> json) => _$AccountResponseFromJson(json);
}

@freezed
class AccountHolderRequest with _$AccountHolderRequest {
  const factory AccountHolderRequest({
    HolderType? holderType,
    bool? canWithdraw,
    bool? canDeposit,
    bool? canApproveTransaction,
    String? signature,
    int? customerId,
  }) = _AccountHolderRequest;

  factory AccountHolderRequest.fromJson(Map<String, dynamic> json) => _$AccountHolderRequestFromJson(json);
}

@freezed
class AccountHolderResponse with _$AccountHolderResponse {
  const factory AccountHolderResponse({
    int? id,
    String? accountHolderName,
    HolderType? holderType,
    bool? canWithdraw,
    bool? canDeposit,
    String? signature,
    bool? canApproveTransaction,
  }) = _AccountHolderResponse;

  factory AccountHolderResponse.fromJson(Map<String, dynamic> json) => _$AccountHolderResponseFromJson(json);
}
