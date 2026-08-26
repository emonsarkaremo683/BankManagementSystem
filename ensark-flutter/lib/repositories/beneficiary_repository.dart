import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../providers/core_providers.dart';

part 'beneficiary_repository.g.dart';

class BeneficiaryRepository {
  final Dio _dio;

  BeneficiaryRepository(this._dio);

  Future<BeneficiaryResponse> add(BeneficiaryRequest request) async {
    final response = await _dio.post('api/beneficiary/', data: request.toJson());
    return BeneficiaryResponse.fromJson(response.data);
  }

  Future<List<BeneficiaryResponse>> getByCustomerEmail(String email) async {
    try {
      final response = await _dio.get('api/beneficiary/customer/email/$email');
      if (response.data == null) return [];
      return (response.data as List).map((e) => BeneficiaryResponse.fromJson(e)).toList();
    } catch (e) {
      return [];
    }
  }

  Future<Map<String, String>> initiateVerification(int id) async {
    final response = await _dio.post('api/beneficiary/$id/initiate-verify');
    return Map<String, String>.from(response.data);
  }

  Future<Map<String, String>> verify(int id, String otpCode) async {
    final response = await _dio.post('api/beneficiary/$id/verify', queryParameters: {'otpCode': otpCode});
    return Map<String, String>.from(response.data);
  }

  Future<void> delete(int id) async {
    await _dio.delete('api/beneficiary/$id');
  }
}

@riverpod
BeneficiaryRepository beneficiaryRepository(Ref ref) {
  return BeneficiaryRepository(ref.watch(dioProvider));
}
