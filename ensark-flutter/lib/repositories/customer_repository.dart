import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/customer/customer_models.dart';
import '../models/other/other_models.dart';
import '../providers/core_providers.dart';

part 'customer_repository.g.dart';

class CustomerRepository {
  final Dio _dio;

  CustomerRepository(this._dio);

  Future<CustomerResponse> findByEmail(String email) async {
    final response = await _dio.get('api/customer/email/$email');
    return CustomerResponse.fromJson(response.data);
  }

  Future<CustomerResponse> findById(int id) async {
    final response = await _dio.get('api/customer/$id');
    return CustomerResponse.fromJson(response.data);
  }

  Future<CustomerDashboardResponse> getDashboard() async {
    final response = await _dio.get('api/customer/state');
    return CustomerDashboardResponse.fromJson(response.data);
  }

  Future<CustomerResponse> updatePassword(int id, String oldPass, String newPass) async {
    final response = await _dio.patch(
      'api/customer/$id/password',
      queryParameters: {'oldPass': oldPass, 'newPass': newPass},
    );
    return CustomerResponse.fromJson(response.data);
  }
}

@riverpod
CustomerRepository customerRepository(Ref ref) {
  return CustomerRepository(ref.watch(dioProvider));
}
