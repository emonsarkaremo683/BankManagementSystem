import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/card/card_models.dart';
import '../providers/core_providers.dart';

part 'card_repository.g.dart';

class CardRepository {
  final Dio _dio;

  CardRepository(this._dio);

  Future<CardResponse> apply(CardRequest request) async {
    final response = await _dio.post('api/card/apply', data: request.toJson());
    return CardResponse.fromJson(response.data);
  }

  Future<List<CardResponse>> findByCustomerEmail(String email) async {
    final response = await _dio.get('api/card/customer/$email');
    return (response.data as List).map((e) => CardResponse.fromJson(e)).toList();
  }

  Future<CardResponse> updatePin(int id, PinChangeRequest request) async {
    final response = await _dio.patch('api/card/$id/pin', data: request.toJson());
    return CardResponse.fromJson(response.data);
  }

  Future<CardUsageResponse> getUsage(int id) async {
    final response = await _dio.get('api/card/$id/usage');
    return CardUsageResponse.fromJson(response.data);
  }

  Future<CardResponse> setTransactionLimit(int id, double dailyLimit, double monthlyLimit) async {
    final response = await _dio.patch(
      'api/card/$id/limit',
      queryParameters: {'dailyLimit': dailyLimit, 'monthlyLimit': monthlyLimit},
    );
    return CardResponse.fromJson(response.data);
  }

  Future<CardResponse> reportLostOrStolen(int id, String reason) async {
    final response = await _dio.post(
      'api/card/$id/report-lost-stolen',
      queryParameters: {'reason': reason},
    );
    return CardResponse.fromJson(response.data);
  }

  Future<CardSettingsRequest> createSettingsRequest(Map<String, dynamic> body) async {
    final response = await _dio.post('api/card-settings-requests/', data: body);
    return CardSettingsRequest.fromJson(response.data);
  }
}

@riverpod
CardRepository cardRepository(Ref ref) {
  return CardRepository(ref.watch(dioProvider));
}
