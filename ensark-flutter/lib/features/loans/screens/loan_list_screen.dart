import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../core/routing/app_router.dart';
import '../../../models/loan/loan_models.dart';
import '../../../providers/loan_provider.dart';
import '../../auth/widgets/neon_button.dart';

class LoanListScreen extends ConsumerWidget {
  const LoanListScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final loansAsync = ref.watch(loansProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('My Loans'),
        backgroundColor: Colors.transparent,
        actions: [
          IconButton(
            icon: const Icon(Icons.add_circle_outline, color: AppColors.neonCyan),
            onPressed: () => context.push(AppRoutes.loanApplication),
          ),
        ],
      ),
      body: loansAsync.when(
        data: (loans) => loans.isEmpty
            ? const _EmptyLoans()
            : RefreshIndicator(
                onRefresh: () => ref.read(loansProvider.notifier).refresh(),
                child: ListView.builder(
                  padding: const EdgeInsets.all(24),
                  itemCount: loans.length,
                  itemBuilder: (context, index) {
                    final loan = loans[index];
                    return _LoanCard(loan: loan);
                  },
                ),
              ),
        loading: () => const Center(child: CircularProgressIndicator()),
        error: (e, s) => Center(child: Text('Error: $e')),
      ),
    );
  }
}

class _LoanCard extends StatelessWidget {
  final LoanApplicationResponse loan;
  const _LoanCard({required this.loan});

  @override
  Widget build(BuildContext context) {
    final format = NumberFormat.currency(symbol: '\$');
    return Container(
      margin: const EdgeInsets.only(bottom: 20),
      decoration: BoxDecoration(
        color: AppColors.darkSurface,
        borderRadius: BorderRadius.circular(20),
        boxShadow: AppShadows.embossed,
      ),
      child: InkWell(
        onTap: () => context.push(AppRoutes.loanDetails, extra: loan),
        borderRadius: BorderRadius.circular(20),
        child: Padding(
          padding: const EdgeInsets.all(24),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    'Loan #${loan.loanId}',
                    style: const TextStyle(color: AppColors.textSecondary, fontWeight: FontWeight.bold),
                  ),
                  _StatusChip(status: loan.status?.name ?? 'PENDING'),
                ],
              ),
              const SizedBox(height: 16),
              Text(
                format.format(loan.principalAmount ?? 0),
                style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: Colors.white),
              ),
              const SizedBox(height: 8),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    'EMI: ${format.format(loan.emiAmount ?? 0)}',
                    style: const TextStyle(color: AppColors.neonCyan, fontSize: 14),
                  ),
                  Text(
                    'Tenure: ${loan.tenureMonths} Months',
                    style: const TextStyle(color: AppColors.textSecondary, fontSize: 14),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class _StatusChip extends StatelessWidget {
  final String status;
  const _StatusChip({required this.status});

  @override
  Widget build(BuildContext context) {
    Color color;
    switch (status) {
      case 'APPROVED':
      case 'DISBURSED':
        color = Colors.green;
        break;
      case 'REJECTED':
        color = Colors.redAccent;
        break;
      default:
        color = Colors.orangeAccent;
    }

    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 4),
      decoration: BoxDecoration(
        color: color.withValues(alpha: 0.1),
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: color.withValues(alpha: 0.5)),
      ),
      child: Text(
        status,
        style: TextStyle(color: color, fontSize: 10, fontWeight: FontWeight.bold),
      ),
    );
  }
}

class _EmptyLoans extends StatelessWidget {
  const _EmptyLoans();
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Icon(Icons.account_balance_wallet_outlined, size: 80, color: Colors.white10),
          const SizedBox(height: 24),
          const Text('No active loans found', style: TextStyle(color: AppColors.textSecondary, fontSize: 18)),
          const SizedBox(height: 32),
          SizedBox(
            width: 200,
            child: NeonButton(
              text: 'APPLY NOW',
              onPressed: () => context.push(AppRoutes.loanApplication),
            ),
          ),
        ],
      ),
    );
  }
}
