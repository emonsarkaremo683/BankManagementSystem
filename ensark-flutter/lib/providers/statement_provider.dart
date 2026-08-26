import 'package:riverpod_annotation/riverpod_annotation.dart';
import 'package:intl/intl.dart';
import '../models/transaction/transaction_models.dart';
import '../repositories/transaction_repository.dart';
import 'auth_provider.dart';

part 'statement_provider.g.dart';

@riverpod
class Statement extends _$Statement {
  @override
  FutureOr<List<JournalResponse>> build() async {
    final user = ref.watch(authProvider).value?.user;
    if (user == null) return [];
    return ref.watch(transactionRepositoryProvider).findJournalsByCustomerEmail(user.email);
  }

  Future<void> filterByDate(DateTime from, DateTime to) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      final user = ref.read(authProvider).value?.user;
      final formatter = DateFormat('yyyy-MM-dd');
      return ref.read(transactionRepositoryProvider).findJournalsByEmailAndSpan(
            user!.email,
            formatter.format(from),
            formatter.format(to),
          );
    });
  }

  Future<List<int>?> export(String accountNumber, DateTime from, DateTime to, String format) async {
    try {
      final formatter = DateFormat('yyyy-MM-dd');
      return await ref.read(transactionRepositoryProvider).exportStatement(
            accountNumber,
            formatter.format(from),
            formatter.format(to),
            format,
          );
    } catch (e) {
      return null;
    }
  }
}
