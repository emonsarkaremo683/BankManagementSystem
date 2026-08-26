import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../providers/core_providers.dart';

part 'general_repository.g.dart';

class GeneralRepository {
  final Dio _dio;

  GeneralRepository(this._dio);

  Future<List<BranchResponse>> getAllBranches() async {
    final response = await _dio.get('api/branch/');
    return (response.data as List).map((e) => BranchResponse.fromJson(e)).toList();
  }

  Future<List<DivisionResponse>> getAllDivisions() async {
    final response = await _dio.get('api/division/all');
    return (response.data as List).map((e) => DivisionResponse.fromJson(e)).toList();
  }

  Future<List<DistrictResponse>> getDistrictsByDivision(int divisionId) async {
    final response = await _dio.get('api/district/division/$divisionId');
    return (response.data as List).map((e) => DistrictResponse.fromJson(e)).toList();
  }

  Future<List<PoliceStationResponse>> getPoliceStationsByDistrict(int districtId) async {
    final response = await _dio.get('api/policestation/district/$districtId');
    return (response.data as List).map((e) => PoliceStationResponse.fromJson(e)).toList();
  }

  Future<List<CurrencyResponse>> getAllCurrencies(String base) async {
    final response = await _dio.get('api/currency/', queryParameters: {'base': base});
    return (response.data as List).map((e) => CurrencyResponse.fromJson(e)).toList();
  }

  Future<Map<String, dynamic>> convertCurrency({
    required String from,
    required String to,
    required double amount,
  }) async {
    final response = await _dio.get(
      'api/currency/convert',
      queryParameters: {'from': from, 'to': to, 'amount': amount},
    );
    return Map<String, dynamic>.from(response.data);
  }
}

@riverpod
GeneralRepository generalRepository(Ref ref) {
  return GeneralRepository(ref.watch(dioProvider));
}
