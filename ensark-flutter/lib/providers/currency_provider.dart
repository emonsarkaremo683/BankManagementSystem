import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../repositories/general_repository.dart';

part 'currency_provider.g.dart';

@riverpod
Future<List<CurrencyResponse>> currencies(Ref ref, {String base = 'BDT'}) async {
  return ref.watch(generalRepositoryProvider).getAllCurrencies(base);
}

@riverpod
class CurrencyConverter extends _$CurrencyConverter {
  @override
  double build() => 0.0;

  Future<double> convert(String from, String to, double amount) async {
    try {
      final result = await ref.read(generalRepositoryProvider).convertCurrency(
        from: from,
        to: to,
        amount: amount,
      );
      // Backend returns Map<String, Object> - assume 'convertedAmount' key
      return (result['convertedAmount'] as num).toDouble();
    } catch (e) {
      return 0.0;
    }
  }
}
