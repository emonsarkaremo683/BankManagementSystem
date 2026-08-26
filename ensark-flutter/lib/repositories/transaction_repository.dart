import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/transaction/transaction_models.dart';
import '../models/card/card_models.dart';
import '../providers/core_providers.dart';

part 'transaction_repository.g.dart';

class TransactionRepository {
  final Dio _dio;

  TransactionRepository(this._dio);

  Future<OtpInitiateResponse> initiateOnlineTransaction(AccountTransactionRequest request) async {
    final response = await _dio.post('api/account-transaction/online/initiate', data: request.toJson());
    return OtpInitiateResponse.fromJson(response.data);
  }

  Future<AccountTransactionResponse> verifyOnlineTransaction(OtpVerifyRequest request) async {
    final response = await _dio.post('api/account-transaction/online/verify', data: request.toJson());
    return AccountTransactionResponse.fromJson(response.data);
  }

  Future<AccountTransactionResponse> getTransactionById(int id) async {
    final response = await _dio.get('api/account-transaction/$id');
    return AccountTransactionResponse.fromJson(response.data);
  }

  Future<List<AccountTransactionResponse>> findTransactionsByAccountNumber(String accountNumber) async {
    final response = await _dio.get('api/account-transaction/accountNumber/$accountNumber');
    return (response.data as List).map((e) => AccountTransactionResponse.fromJson(e)).toList();
  }

  Future<List<JournalResponse>> findJournalsByCustomerEmail(String email) async {
    final response = await _dio.get('api/journal/customer/$email');
    return (response.data as List).map((e) => JournalResponse.fromJson(e)).toList();
  }

  Future<List<JournalResponse>> findJournalsByEmailAndSpan(String email, String from, String to) async {
    final response = await _dio.get('api/journal/customer/$email/span', queryParameters: {'from': from, 'to': to});
    return (response.data as List).map((e) => JournalResponse.fromJson(e)).toList();
  }

  Future<List<int>> exportStatement(String accountNumber, String from, String to, String format) async {
    final response = await _dio.get(
      'api/journal/account/$accountNumber/export',
      queryParameters: {'from': from, 'to': to, 'format': format},
      options: Options(responseType: ResponseType.bytes),
    );
    return response.data as List<int>;
  }

  Future<double> checkAtmBalance(BalanceCheckRequest request) async {
    final response = await _dio.post('api/atm-transactions/balance', data: request.toJson());
    return (response.data as num).toDouble();
  }

  Future<ATMTransactionResponse> atmTransaction(ATMTransactionRequest request) async {
    final response = await _dio.post('api/atm-transactions', data: request.toJson());
    return ATMTransactionResponse.fromJson(response.data);
  }
}

@riverpod
TransactionRepository transactionRepository(Ref ref) {
  return TransactionRepository(ref.watch(dioProvider));
}
