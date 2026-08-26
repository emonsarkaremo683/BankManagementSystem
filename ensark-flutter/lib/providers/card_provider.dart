import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/card/card_models.dart';
import '../repositories/card_repository.dart';
import 'auth_provider.dart';

part 'card_provider.g.dart';

@riverpod
class Cards extends _$Cards {
  @override
  FutureOr<List<CardResponse>> build() async {
    final user = ref.watch(authProvider).value?.user;
    if (user == null) return [];
    return ref.watch(cardRepositoryProvider).findByCustomerEmail(user.email);
  }

  Future<void> refresh() async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      final user = ref.read(authProvider).value?.user;
      return ref.read(cardRepositoryProvider).findByCustomerEmail(user!.email);
    });
  }

  Future<void> apply(CardRequest request) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(cardRepositoryProvider).apply(request);
      final user = ref.read(authProvider).value?.user;
      return ref.read(cardRepositoryProvider).findByCustomerEmail(user!.email);
    });
  }

  Future<void> updatePin(int cardId, String oldPin, String newPin) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(cardRepositoryProvider).updatePin(cardId, PinChangeRequest(oldPin: oldPin, newPin: newPin));
      final user = ref.read(authProvider).value?.user;
      return ref.read(cardRepositoryProvider).findByCustomerEmail(user!.email);
    });
  }

  Future<void> updateLimits(int cardId, double dailyLimit, double monthlyLimit) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(cardRepositoryProvider).setTransactionLimit(cardId, dailyLimit, monthlyLimit);
      final user = ref.read(authProvider).value?.user;
      return ref.read(cardRepositoryProvider).findByCustomerEmail(user!.email);
    });
  }

  Future<void> reportLost(int cardId, String reason) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(cardRepositoryProvider).reportLostOrStolen(cardId, reason);
      final user = ref.read(authProvider).value?.user;
      return ref.read(cardRepositoryProvider).findByCustomerEmail(user!.email);
    });
  }
}
