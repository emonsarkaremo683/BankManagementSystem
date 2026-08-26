// ignore_for_file: invalid_annotation_target, constant_identifier_names
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:intl/intl.dart';

part 'customer_models.freezed.dart';
part 'customer_models.g.dart';

enum AddressType { PERMANENT, PRESENT }
enum Gender { MALE, FEMALE, OTHER }
enum DocumentType { NID, PASSPORT, DRIVING_LICENSE, BIRTH_CERTIFICATE }
enum KYCStatus { PENDING, UNDER_REVIEW, VERIFIED, REJECTED, EXPIRED }
enum CustomerStatus { ACTIVE, INACTIVE, BLOCKED, PENDING }
enum Role { CUSTOMER, ADMIN }

enum CustomerOccupation {
  STUDENT, SERVICE_HOLDER, GOVERNMENT_EMPLOYEE, BUSINESS_OWNER, SELF_EMPLOYED,
  FREELANCER, DOCTOR, ENGINEER, TEACHER, LAWYER, ACCOUNTANT, ARCHITECT, CONSULTANT, FARMER,
  LABORER, DRIVER, MECHANIC, ELECTRICIAN, PLUMBER, POLICE, MILITARY, CIVIL_SERVANT, BANKER,
  NGO_EMPLOYEE, RETIRED, HOMEMAKER, UNEMPLOYED, FOREIGN_EMPLOYEE, EXPATRIATE, POLITICIAN,
  JOURNALIST, ARTIST, WRITER, ACTOR, MUSICIAN, RELIGIOUS_LEADER, OTHERS
}

class IsoDateConverter implements JsonConverter<DateTime?, String?> {
  const IsoDateConverter();

  @override
  DateTime? fromJson(String? json) => json == null ? null : DateTime.parse(json);

  @override
  String? toJson(DateTime? object) => object == null ? null : DateFormat('yyyy-MM-dd').format(object);
}

@freezed
class AddressRequest with _$AddressRequest {
  const factory AddressRequest({
    String? holdingNo,
    String? area,
    String? postalCode,
    required AddressType addressType,
    required int policeStationId,
  }) = _AddressRequest;

  factory AddressRequest.fromJson(Map<String, dynamic> json) => _$AddressRequestFromJson(json);
}

// Custom converter for AddressRequest to handle nested policeStation object
class AddressRequestConverter {
  static Map<String, dynamic> toJson(AddressRequest address) {
    return {
      'holdingNo': address.holdingNo,
      'area': address.area,
      'postalCode': address.postalCode,
      'addressType': address.addressType.name,
      'policeStation': {'id': address.policeStationId},
    };
  }
}

@freezed
class AddressResponse with _$AddressResponse {
  const factory AddressResponse({
    required int id,
    String? holdingNo,
    String? area,
    String? postalCode,
    required AddressType addressType,
    int? policeStationId,
    String? policeStationName,
    int? districtId,
    String? districtName,
    int? divisionId,
    String? divisionName,
  }) = _AddressResponse;

  factory AddressResponse.fromJson(Map<String, dynamic> json) => _$AddressResponseFromJson(json);
}

@freezed
class KycRequest with _$KycRequest {
  const factory KycRequest({
    int? id,
    String? path,
    @JsonKey(name: 'doc_type') required DocumentType docType,
  }) = _KycRequest;

  factory KycRequest.fromJson(Map<String, dynamic> json) => _$KycRequestFromJson(json);
}

@freezed
class CustomerRequest with _$CustomerRequest {
  const factory CustomerRequest({
    required String email,
    required String password,
    String? name,
    Gender? gender,
    String? phone,
    CustomerOccupation? occupation,
    @IsoDateConverter() DateTime? dob,
    String? profile,
    required List<AddressRequest> addresses,
    List<KycRequest>? kycRequests,
  }) = _CustomerRequest;

  factory CustomerRequest.fromJson(Map<String, dynamic> json) => _$CustomerRequestFromJson(json);
}

@freezed
class CustomerResponse with _$CustomerResponse {
  const factory CustomerResponse({
    required int id,
    required String email,
    required Role role,
    @JsonKey(name: 'isEmailVerified') required bool isEmailVerified,
    required bool active,
    String? name,
    Gender? gender,
    String? phone,
    CustomerOccupation? occupation,
    @IsoDateConverter() DateTime? dob,
    String? profile,
    required List<AddressResponse> addresses,
    List<KycRequest>? documents,
    KYCStatus? kycStatus,
    CustomerStatus? status,
  }) = _CustomerResponse;

  factory CustomerResponse.fromJson(Map<String, dynamic> json) => _$CustomerResponseFromJson(json);
}
