import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../providers/core_providers.dart';

part 'cheque_repository.g.dart';

class ChequeRepository {
  final Dio _dio;

  ChequeRepository(this._dio);

  Future<ChequeBookResponse> apply(ChequeBookRequest request) async {
    final response = await _dio.post('api/cheque/apply', data: request.toJson());
    return ChequeBookResponse.fromJson(response.data);
  }

  Future<List<ChequeBookResponse>> findByCustomerEmail(String email) async {
    final response = await _dio.get('api/cheque/customer/email/$email');
    return (response.data as List).map((e) => ChequeBookResponse.fromJson(e)).toList();
  }

  Future<ChequeLeafResponse> stopPayment(int leafId, String remarks) async {
    final response = await _dio.post('api/cheque/leaves/$leafId/stop-payment', queryParameters: {'remarks': remarks});
    return ChequeLeafResponse.fromJson(response.data);
  }

  Future<List<ChequeLeafResponse>> getLeavesByCustomerId(int customerId, {String? status}) async {
    final response = await _dio.get('api/cheque/customer/$customerId/leaves', queryParameters: status != null ? {'status': status} : null);
    return (response.data as List).map((e) => ChequeLeafResponse.fromJson(e)).toList();
  }
}

@riverpod
ChequeRepository chequeRepository(Ref ref) {
  return ChequeRepository(ref.watch(dioProvider));
}
