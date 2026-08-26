import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/loan/loan_models.dart';
import '../repositories/loan_repository.dart';
import 'auth_provider.dart';

part 'loan_provider.g.dart';

@riverpod
class Loans extends _$Loans {
  @override
  FutureOr<List<LoanApplicationResponse>> build() async {
    final user = ref.watch(authProvider).value?.user;
    if (user == null) return [];
    return ref.watch(loanRepositoryProvider).findByCustomerEmail(user.email);
  }

  Future<void> refresh() async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      final user = ref.read(authProvider).value?.user;
      return ref.read(loanRepositoryProvider).findByCustomerEmail(user!.email);
    });
  }

  Future<void> apply(LoanApplicationRequest request, Map<String, List<int>> files) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(loanRepositoryProvider).apply(request, files);
      final user = ref.read(authProvider).value?.user;
      return ref.read(loanRepositoryProvider).findByCustomerEmail(user!.email);
    });
  }

  Future<void> payInstallment(int repaymentId) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(loanRepositoryProvider).payInstallment(repaymentId);
      final user = ref.read(authProvider).value?.user;
      return ref.read(loanRepositoryProvider).findByCustomerEmail(user!.email);
    });
  }
}

@riverpod
Future<List<LoanScheduleResponse>> loanSchedule(Ref ref, int loanId) async {
  return ref.watch(loanRepositoryProvider).getSchedule(loanId);
}
