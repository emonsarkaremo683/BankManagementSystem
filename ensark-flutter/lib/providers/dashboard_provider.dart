import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../repositories/customer_repository.dart';

part 'dashboard_provider.g.dart';

@riverpod
class Dashboard extends _$Dashboard {
  @override
  FutureOr<CustomerDashboardResponse> build() async {
    return ref.watch(customerRepositoryProvider).getDashboard();
  }

  Future<void> refresh() async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() => ref.read(customerRepositoryProvider).getDashboard());
  }
}
