import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/account/account_models.dart';
import '../providers/core_providers.dart';

part 'account_repository.g.dart';

class AccountRepository {
  final Dio _dio;

  AccountRepository(this._dio);

  Future<AccountResponse> create(AccountRequest request, Map<String, List<int>> multipartFiles) async {
    final formData = FormData.fromMap({
      'data': MultipartFile.fromString(
        // Assuming JSON string as per earlier findings
        request.toJson().toString(),
        contentType: DioMediaType.parse('application/json'),
      ),
    });

    for (var entry in multipartFiles.entries) {
      if (entry.key == 'signatures') {
        // Handle list of signatures if needed
      } else {
        formData.files.add(MapEntry(
          entry.key,
          MultipartFile.fromBytes(entry.value, filename: '${entry.key}.jpg'),
        ));
      }
    }

    final response = await _dio.post('api/account/create', data: formData);
    return AccountResponse.fromJson(response.data);
  }

  Future<AccountResponse> findById(int id) async {
    final response = await _dio.get('api/account/$id');
    return AccountResponse.fromJson(response.data);
  }

  Future<List<AccountResponse>> findByCustomerEmail(String email) async {
    final response = await _dio.get('api/account/email/$email');
    return (response.data as List).map((e) => AccountResponse.fromJson(e)).toList();
  }

  Future<AccountResponse> findByAccountNumber(String accountNumber) async {
    final response = await _dio.get('api/account/number/$accountNumber');
    return AccountResponse.fromJson(response.data);
  }

  Future<double> getBalance(String accountNumber) async {
    final response = await _dio.get('api/account/$accountNumber/balance');
    return (response.data as num).toDouble();
  }
}

@riverpod
AccountRepository accountRepository(Ref ref) {
  return AccountRepository(ref.watch(dioProvider));
}
