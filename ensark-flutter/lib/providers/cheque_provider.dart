import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../repositories/cheque_repository.dart';
import 'auth_provider.dart';

part 'cheque_provider.g.dart';

@riverpod
class ChequeBooks extends _$ChequeBooks {
  @override
  FutureOr<List<ChequeBookResponse>> build() async {
    final user = ref.watch(authProvider).value?.user;
    if (user == null) return [];
    return ref.watch(chequeRepositoryProvider).findByCustomerEmail(user.email);
  }

  Future<void> refresh() async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      final user = ref.read(authProvider).value?.user;
      return ref.read(chequeRepositoryProvider).findByCustomerEmail(user!.email);
    });
  }

  Future<void> apply(ChequeBookRequest request) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(chequeRepositoryProvider).apply(request);
      final user = ref.read(authProvider).value?.user;
      return ref.read(chequeRepositoryProvider).findByCustomerEmail(user!.email);
    });
  }
}

@riverpod
Future<List<ChequeLeafResponse>> chequeLeaves(Ref ref, int customerId, {String? status}) async {
  return ref.watch(chequeRepositoryProvider).getLeavesByCustomerId(customerId, status: status);
}

@riverpod
class LeafActions extends _$LeafActions {
  @override
  AsyncValue<void> build() => const AsyncValue.data(null);

  Future<void> stopPayment(int leafId, String remarks) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() => ref.read(chequeRepositoryProvider).stopPayment(leafId, remarks));
  }
}
