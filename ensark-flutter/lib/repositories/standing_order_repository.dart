import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../providers/core_providers.dart';

part 'standing_order_repository.g.dart';

class StandingOrderRepository {
  final Dio _dio;

  StandingOrderRepository(this._dio);

  Future<StandingOrderResponse> create(StandingOrderRequest request) async {
    final response = await _dio.post('api/standing-orders/', data: request.toJson());
    return StandingOrderResponse.fromJson(response.data);
  }

  Future<StandingOrderResponse> cancel(int id) async {
    final response = await _dio.put('api/standing-orders/$id/cancel');
    return StandingOrderResponse.fromJson(response.data);
  }

  Future<List<StandingOrderResponse>> findByAccountId(int accountId) async {
    final response = await _dio.get('api/standing-orders/account/$accountId');
    return (response.data as List).map((e) => StandingOrderResponse.fromJson(e)).toList();
  }
}

@riverpod
StandingOrderRepository standingOrderRepository(Ref ref) {
  return StandingOrderRepository(ref.watch(dioProvider));
}
