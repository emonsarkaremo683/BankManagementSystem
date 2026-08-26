import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../core/routing/app_router.dart';
import '../../../models/account/account_models.dart';
import '../../../providers/dashboard_provider.dart';
import '../../../providers/standing_order_provider.dart';

class StandingOrderListScreen extends ConsumerStatefulWidget {
  const StandingOrderListScreen({super.key});

  @override
  ConsumerState<StandingOrderListScreen> createState() => _StandingOrderListScreenState();
}

class _StandingOrderListScreenState extends ConsumerState<StandingOrderListScreen> {
  AccountResponse? _selectedAccount;

  @override
  Widget build(BuildContext context) {
    final dashboard = ref.watch(dashboardProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Standing Orders'), backgroundColor: Colors.transparent),
      floatingActionButton: FloatingActionButton(
        onPressed: () => context.push(AppRoutes.standingOrderForm),
        backgroundColor: AppColors.neonCyan,
        child: const Icon(Icons.add, color: Colors.black),
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(24),
            child: dashboard.when(
              data: (data) => DropdownButtonFormField<AccountResponse>(
                initialValue: _selectedAccount,
                items: (data.accounts ?? []).map((acc) => DropdownMenuItem(value: acc, child: Text(acc.accountNumber ?? ''))).toList(),
                onChanged: (v) => setState(() => _selectedAccount = v),
                decoration: const InputDecoration(labelText: 'Select Account to view orders'),
              ),
              loading: () => const LinearProgressIndicator(),
              error: (err, stack) => const Text('Error loading accounts'),
            ),
          ),
          if (_selectedAccount != null)
            Expanded(
              child: ref.watch(standingOrdersProvider(_selectedAccount!.id!)).when(
                    data: (list) => list.isEmpty
                        ? const Center(child: Text('No standing orders for this account.'))
                        : ListView.builder(
                            padding: const EdgeInsets.symmetric(horizontal: 24),
                            itemCount: list.length,
                            itemBuilder: (context, index) => _OrderCard(order: list[index], accountId: _selectedAccount!.id!),
                          ),
                    loading: () => const Center(child: CircularProgressIndicator()),
                    error: (e, s) => Center(child: Text('Error: $e')),
                  ),
            )
          else
            const Expanded(child: Center(child: Text('Please select an account.'))),
        ],
      ),
    );
  }
}

class _OrderCard extends ConsumerWidget {
  final dynamic order; // StandingOrderResponse
  final int accountId;
  const _OrderCard({required this.order, required this.accountId});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final format = NumberFormat.currency(symbol: '\$');
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(color: AppColors.darkSurface, borderRadius: BorderRadius.circular(16), boxShadow: AppShadows.embossed),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(order.targetAccountName ?? 'Recurring Transfer', style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
              Text(format.format(order.amount ?? 0), style: const TextStyle(color: AppColors.neonCyan, fontWeight: FontWeight.bold)),
            ],
          ),
          const SizedBox(height: 8),
          Text('Frequency: ${order.frequency?.name}', style: const TextStyle(color: AppColors.textSecondary, fontSize: 12)),
          const SizedBox(height: 16),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text('Status: ${order.status?.name}', style: TextStyle(color: order.status?.name == 'ACTIVE' ? AppColors.neonGreen : Colors.orange, fontSize: 10, fontWeight: FontWeight.bold)),
              if (order.status?.name != 'CANCELLED')
                IconButton(
                  icon: const Icon(Icons.cancel_outlined, color: Colors.redAccent, size: 20),
                  onPressed: () => ref.read(standingOrdersProvider(accountId).notifier).cancel(order.id!, accountId),
                ),
            ],
          ),
        ],
      ),
    );
  }
}
