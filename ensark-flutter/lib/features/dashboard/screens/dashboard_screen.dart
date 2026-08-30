import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../core/routing/app_router.dart';
import '../../../providers/auth_provider.dart';
import '../../../providers/dashboard_provider.dart';
import '../../../providers/notification_provider.dart';

class DashboardScreen extends ConsumerWidget {
  const DashboardScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final dashboardAsync = ref.watch(dashboardProvider);
    final unreadCountAsync = ref.watch(unreadCountProvider);
    final user = ref.watch(authProvider).value?.user;

    return Scaffold(
      appBar: AppBar(
        title: const Text('EnsarkBank'),
        actions: [
          unreadCountAsync.when(
            data: (count) => Stack(
              children: [
                IconButton(
                  icon: const Icon(Icons.notifications_none),
                  onPressed: () => context.push(AppRoutes.notifications),
                ),
                if (count > 0)
                  Positioned(
                    right: 8,
                    top: 8,
                    child: Container(
                      padding: const EdgeInsets.all(2),
                      decoration: BoxDecoration(color: AppColors.neonPink, borderRadius: BorderRadius.circular(10)),
                      constraints: const BoxConstraints(minWidth: 16, minHeight: 16),
                      child: Text('$count', style: const TextStyle(color: Colors.white, fontSize: 10), textAlign: TextAlign.center),
                    ),
                  ),
              ],
            ),
            loading: () => IconButton(icon: const Icon(Icons.notifications_none), onPressed: null),
            error: (err, stack) => IconButton(icon: const Icon(Icons.notifications_none), onPressed: null),
          ),
          IconButton(
            icon: const Icon(Icons.person_outline),
            onPressed: () => context.push(AppRoutes.profile),
          ),
        ],
      ),
      body: RefreshIndicator(
        onRefresh: () => ref.read(dashboardProvider.notifier).refresh(),
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Hello, ${user?.name?.split(' ').first ?? 'User'}!',
                style: const TextStyle(fontSize: 16, color: AppColors.textSecondary),
              ),
              const SizedBox(height: 8),
              const Text(
                'Welcome Back',
                style: TextStyle(fontSize: 28, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 32),

              // Balance Card
              dashboardAsync.when(
                data: (data) => _BalanceCard(totalBalance: data.balance ?? 0),
                loading: () => _SkeletonCard(),
                error: (e, s) => Text('Error: $e'),
              ),
              const SizedBox(height: 32),

              // Quick Actions
              const Text('Quick Actions', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: AppColors.textSecondary)),
              const SizedBox(height: 16),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  _QuickAction(icon: Icons.send, label: 'Transfer', route: AppRoutes.transfer),
                  _QuickAction(icon: Icons.person_add_outlined, label: 'Add Beneficiary', route: AppRoutes.beneficiaries),
                  _QuickAction(icon: Icons.credit_card, label: 'Cards', route: AppRoutes.cards),
                  _QuickAction(icon: Icons.history, label: 'Statement', route: AppRoutes.statements),
                ],
              ),
              const SizedBox(height: 32),

              // Extra Services
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  _QuickAction(icon: Icons.receipt_long, label: 'Loans', route: AppRoutes.loanList),
                  _QuickAction(icon: Icons.menu_book, label: 'Cheques', route: AppRoutes.chequeBookList),
                  _QuickAction(icon: Icons.timer, label: 'Recurring', route: AppRoutes.standingOrderList),
                  _QuickAction(icon: Icons.currency_exchange, label: 'Converter', route: AppRoutes.currencyConverter),
                ],
              ),
              const SizedBox(height: 40),

              // Accounts Section
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text('My Accounts', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
                  TextButton(onPressed: () {}, child: const Text('See All', style: TextStyle(color: AppColors.neonCyan))),
                ],
              ),
              const SizedBox(height: 16),
              dashboardAsync.when(
                data: (data) => Column(
                  children: (data.accounts ?? [])
                      .map((acc) => GestureDetector(
                            onTap: () => context.push(AppRoutes.accountDetails, extra: acc),
                            child: _AccountTile(
                              accountNumber: (acc.accountNumber == null || acc.accountNumber!.isEmpty) ? 'PENDING' : acc.accountNumber!,
                              balance: acc.availableBalance ?? 0,
                              type: acc.accountType?.name ?? '',
                              status: acc.accountStatus?.name ?? 'PENDING',
                            ),
                          ))
                      .toList(),
                ),
                loading: () => const Center(child: SpinKitPulse(color: AppColors.neonCyan)),
                error: (e, s) => const SizedBox(),
              ),
              const SizedBox(height: 40),

              // Transactions Section
              const Text('Recent Activity', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
              const SizedBox(height: 16),
              dashboardAsync.when(
                data: (data) => Column(
                  children: (data.recentTransactions ?? [])
                      .map((tx) => _TransactionTile(
                            title: tx.remarks ?? 'Transaction',
                            amount: tx.amount ?? 0,
                            type: tx.type ?? 'DEBIT',
                            date: tx.createdAt ?? DateTime.now(),
                          ))
                      .toList(),
                ),
                loading: () => const Center(child: SpinKitPulse(color: AppColors.neonPink)),
                error: (e, s) => const SizedBox(),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class _BalanceCard extends StatelessWidget {
  final double totalBalance;
  const _BalanceCard({required this.totalBalance});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(32),
      decoration: BoxDecoration(
        color: AppColors.darkSurface,
        borderRadius: BorderRadius.circular(24),
        boxShadow: AppShadows.embossed,
        border: Border.all(color: AppColors.neonCyan.withValues(alpha: 0.1)),
      ),
      child: Column(
        children: [
          const Text('Total Balance', style: TextStyle(color: AppColors.textSecondary, fontSize: 16)),
          const SizedBox(height: 12),
          Text(
            NumberFormat.currency(symbol: '\$').format(totalBalance),
            style: const TextStyle(
              fontSize: 36,
              fontWeight: FontWeight.bold,
              color: AppColors.neonCyan,
              shadows: [Shadow(color: AppColors.neonCyan, blurRadius: 20)],
            ),
          ),
        ],
      ),
    );
  }
}

class _AccountTile extends StatelessWidget {
  final String accountNumber;
  final double balance;
  final String type;
  final String status;

  const _AccountTile({
    required this.accountNumber,
    required this.balance,
    required this.type,
    required this.status,
  });

  @override
  Widget build(BuildContext context) {
    final bool isPending = status == 'PENDING';

    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: AppColors.darkBackground,
        borderRadius: BorderRadius.circular(16),
        boxShadow: AppShadows.debossed,
        border: isPending ? Border.all(color: Colors.orangeAccent.withValues(alpha: 0.3)) : null,
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  children: [
                    Text(type, style: const TextStyle(fontWeight: FontWeight.bold, color: Colors.white)),
                    if (isPending) ...[
                      const SizedBox(width: 8),
                      Container(
                        padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                        decoration: BoxDecoration(
                          color: Colors.orangeAccent.withValues(alpha: 0.1),
                          borderRadius: BorderRadius.circular(4),
                        ),
                        child: const Text('PENDING', style: TextStyle(color: Colors.orangeAccent, fontSize: 8, fontWeight: FontWeight.bold)),
                      ),
                    ],
                  ],
                ),
                Text(accountNumber, style: const TextStyle(color: AppColors.textSecondary, fontSize: 12)),
              ],
            ),
          ),
          Text(
            NumberFormat.currency(symbol: '\$').format(balance),
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: isPending ? AppColors.textSecondary : AppColors.neonGreen,
            ),
          ),
        ],
      ),
    );
  }
}

class _TransactionTile extends StatelessWidget {
  final String title;
  final double amount;
  final String type;
  final DateTime date;

  const _TransactionTile({
    required this.title,
    required this.amount,
    required this.type,
    required this.date,
  });

  @override
  Widget build(BuildContext context) {
    final isCredit = type.toUpperCase() == 'CREDIT';

    return ListTile(
      contentPadding: EdgeInsets.zero,
      leading: Container(
        padding: const EdgeInsets.all(10),
        decoration: BoxDecoration(color: AppColors.darkSurface, shape: BoxShape.circle, boxShadow: AppShadows.embossed),
        child: Icon(
          isCredit ? Icons.arrow_downward : Icons.arrow_upward,
          color: isCredit ? Colors.green : Colors.red,
        ),
      ),
      title: Text(title, style: const TextStyle(fontWeight: FontWeight.w500)),
      subtitle: Text(DateFormat('MMM dd, yyyy').format(date), style: const TextStyle(color: AppColors.textSecondary, fontSize: 12)),
      trailing: Text(
        '${isCredit ? '+' : '-'}${NumberFormat.currency(symbol: '\$').format(amount.abs())}',
        style: TextStyle(
          fontWeight: FontWeight.bold,
          color: isCredit ? AppColors.neonGreen : Colors.white,
        ),
      ),
    );
  }
}

class _SkeletonCard extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 150,
      width: double.infinity,
      decoration: BoxDecoration(color: AppColors.darkSurface, borderRadius: BorderRadius.circular(24), boxShadow: AppShadows.embossed),
      child: const Center(child: SpinKitThreeBounce(color: AppColors.neonCyan, size: 30)),
    );
  }
}

class _QuickAction extends StatelessWidget {
  final IconData icon;
  final String label;
  final String route;

  const _QuickAction({required this.icon, required this.label, required this.route});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () => context.push(route),
      child: Column(
        children: [
          Container(
            width: 60,
            height: 60,
            decoration: BoxDecoration(
              color: AppColors.darkSurface,
              borderRadius: BorderRadius.circular(16),
              boxShadow: AppShadows.embossed,
            ),
            child: Icon(icon, color: AppColors.neonCyan),
          ),
          const SizedBox(height: 8),
          Text(label, style: const TextStyle(fontSize: 10, color: AppColors.textSecondary, fontWeight: FontWeight.bold)),
        ],
      ),
    );
  }
}
