import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../repositories/standing_order_repository.dart';

part 'standing_order_provider.g.dart';

@riverpod
class StandingOrders extends _$StandingOrders {
  @override
  FutureOr<List<StandingOrderResponse>> build(int accountId) async {
    return ref.watch(standingOrderRepositoryProvider).findByAccountId(accountId);
  }

  Future<void> create(StandingOrderRequest request) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(standingOrderRepositoryProvider).create(request);
      return ref.read(standingOrderRepositoryProvider).findByAccountId(request.sourceAccountId!);
    });
  }

  Future<void> cancel(int id, int accountId) async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await ref.read(standingOrderRepositoryProvider).cancel(id);
      return ref.read(standingOrderRepositoryProvider).findByAccountId(accountId);
    });
  }
}
