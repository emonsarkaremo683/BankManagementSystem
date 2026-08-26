import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/transaction/transaction_models.dart';
import '../models/other/other_models.dart';
import '../repositories/transaction_repository.dart';
import '../repositories/beneficiary_repository.dart';
import 'auth_provider.dart';

part 'transfer_provider.g.dart';

@riverpod
class Transfer extends _$Transfer {
  @override
  AsyncValue<void> build() => const AsyncValue.data(null);

  Future<OtpInitiateResponse?> initiate(AccountTransactionRequest request) async {
    state = const AsyncValue.loading();
    try {
      final response = await ref.read(transactionRepositoryProvider).initiateOnlineTransaction(request);
      state = const AsyncValue.data(null);
      return response;
    } catch (e, s) {
      state = AsyncValue.error(e, s);
      return null;
    }
  }

  Future<AccountTransactionResponse?> verify(OtpVerifyRequest request) async {
    state = const AsyncValue.loading();
    try {
      final response = await ref.read(transactionRepositoryProvider).verifyOnlineTransaction(request);
      state = const AsyncValue.data(null);
      return response;
    } catch (e, s) {
      state = AsyncValue.error(e, s);
      return null;
    }
  }
}

@riverpod
class Beneficiaries extends _$Beneficiaries {
  @override
  FutureOr<List<BeneficiaryResponse>> build() async {
    final user = ref.watch(authProvider).value?.user;
    if (user == null) {
      return [];
    }
    return ref.watch(beneficiaryRepositoryProvider).getByCustomerEmail(user.email);
  }

  Future<void> refresh() async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      final user = ref.read(authProvider).value?.user;
      if (user == null) return [];
      return ref.read(beneficiaryRepositoryProvider).getByCustomerEmail(user.email);
    });
  }

  Future<void> add(BeneficiaryRequest request) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(beneficiaryRepositoryProvider).add(request);
      final user = ref.read(authProvider).value?.user;
      if (user == null) return [];
      return ref.read(beneficiaryRepositoryProvider).getByCustomerEmail(user.email);
    });
  }

  Future<void> delete(int id) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(beneficiaryRepositoryProvider).delete(id);
      final user = ref.read(authProvider).value?.user;
      if (user == null) return [];
      return ref.read(beneficiaryRepositoryProvider).getByCustomerEmail(user.email);
    });
  }
}
