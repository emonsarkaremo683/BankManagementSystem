import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/loan/loan_models.dart';
import '../providers/core_providers.dart';

part 'loan_repository.g.dart';

class LoanRepository {
  final Dio _dio;

  LoanRepository(this._dio);

  Future<LoanApplicationResponse> apply(LoanApplicationRequest request, Map<String, List<int>> multipartFiles) async {
    final formData = FormData.fromMap({
      'data': MultipartFile.fromString(
        request.toJson().toString(),
        contentType: DioMediaType.parse('application/json'),
      ),
    });

    for (var entry in multipartFiles.entries) {
      formData.files.add(MapEntry(
        entry.key,
        MultipartFile.fromBytes(entry.value, filename: '${entry.key}.jpg'),
      ));
    }

    final response = await _dio.post('api/loan/apply', data: formData);
    return LoanApplicationResponse.fromJson(response.data);
  }

  Future<List<LoanApplicationResponse>> findByCustomerEmail(String email) async {
    final response = await _dio.get('api/loan/customer/$email');
    return (response.data as List).map((e) => LoanApplicationResponse.fromJson(e)).toList();
  }

  Future<List<LoanScheduleResponse>> getSchedule(int loanId) async {
    final response = await _dio.get('api/loan/$loanId/schedule');
    return (response.data as List).map((e) => LoanScheduleResponse.fromJson(e)).toList();
  }

  Future<LoanRepaymentResponse> payInstallment(int repaymentId) async {
    final response = await _dio.post('api/loan/repayments/$repaymentId/pay');
    return LoanRepaymentResponse.fromJson(response.data);
  }
}

@riverpod
LoanRepository loanRepository(Ref ref) {
  return LoanRepository(ref.watch(dioProvider));
}
