import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:intl/intl.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../models/loan/loan_models.dart';
import '../../../providers/loan_provider.dart';

class LoanDetailsScreen extends ConsumerWidget {
  final LoanApplicationResponse loan;
  const LoanDetailsScreen({super.key, required this.loan});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final scheduleAsync = ref.watch(loanScheduleProvider(loan.loanId!));

    return Scaffold(
      appBar: AppBar(title: const Text('Loan Details'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _SummaryCard(loan: loan),
            const SizedBox(height: 32),
            const Text('Repayment Schedule', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: AppColors.neonCyan)),
            const SizedBox(height: 16),
            scheduleAsync.when(
              data: (list) => Column(
                children: list.map((s) => _ScheduleTile(schedule: s)).toList(),
              ),
              loading: () => const Center(child: CircularProgressIndicator()),
              error: (e, s) => Text('Error: $e'),
            ),
          ],
        ),
      ),
    );
  }
}

class _SummaryCard extends StatelessWidget {
  final LoanApplicationResponse loan;
  const _SummaryCard({required this.loan});

  @override
  Widget build(BuildContext context) {
    final format = NumberFormat.currency(symbol: '\$');
    return Container(
      padding: const EdgeInsets.all(24),
      decoration: BoxDecoration(
        color: AppColors.darkSurface,
        borderRadius: BorderRadius.circular(20),
        boxShadow: AppShadows.embossed,
        border: Border.all(color: AppColors.neonCyan.withValues(alpha: 0.1)),
      ),
      child: Column(
        children: [
          _RowItem(label: 'Principal Amount', value: format.format(loan.principalAmount ?? 0)),
          _RowItem(label: 'Interest Rate', value: '${loan.annualInterestRate}%'),
          _RowItem(label: 'EMI Amount', value: format.format(loan.emiAmount ?? 0), isNeon: true),
          _RowItem(label: 'Total Payable', value: format.format(loan.totalPayable ?? 0)),
          const Divider(color: Colors.white10, height: 32),
          _RowItem(label: 'Outstanding', value: format.format(loan.outstandingBalance ?? 0), isPink: true),
          _RowItem(label: 'Next Due Date', value: loan.nextDueDate != null ? DateFormat('MMM dd, yyyy').format(loan.nextDueDate!) : 'N/A'),
        ],
      ),
    );
  }
}

class _RowItem extends StatelessWidget {
  final String label;
  final String value;
  final bool isNeon;
  final bool isPink;

  const _RowItem({required this.label, required this.value, this.isNeon = false, this.isPink = false});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(label, style: const TextStyle(color: AppColors.textSecondary)),
          Text(
            value,
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: isNeon ? AppColors.neonCyan : (isPink ? AppColors.neonPink : Colors.white),
            ),
          ),
        ],
      ),
    );
  }
}

class _ScheduleTile extends ConsumerWidget {
  final LoanScheduleResponse schedule;
  const _ScheduleTile({required this.schedule});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final format = NumberFormat.currency(symbol: '\$');
    final isPaid = schedule.status?.name == 'PAID';

    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: AppColors.darkBackground,
        borderRadius: BorderRadius.circular(12),
        boxShadow: AppShadows.debossed,
      ),
      child: Row(
        children: [
          CircleAvatar(
            backgroundColor: isPaid ? Colors.green.withValues(alpha: 0.1) : Colors.orange.withValues(alpha: 0.1),
            child: Text(
              '#${schedule.installmentNumber}',
              style: TextStyle(color: isPaid ? Colors.green : Colors.orange, fontWeight: FontWeight.bold),
            ),
          ),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(DateFormat('MMM dd, yyyy').format(schedule.dueDate!), style: const TextStyle(fontWeight: FontWeight.bold)),
                Text(format.format(schedule.emiAmount ?? 0), style: const TextStyle(color: AppColors.textSecondary, fontSize: 12)),
              ],
            ),
          ),
          if (!isPaid)
            TextButton(
              onPressed: () => _handlePayment(context, ref),
              child: const Text('PAY', style: TextStyle(color: AppColors.neonCyan)),
            )
          else
            const Icon(Icons.check_circle, color: Colors.green),
        ],
      ),
    );
  }

  void _handlePayment(BuildContext context, WidgetRef ref) async {
    await ref.read(loansProvider.notifier).payInstallment(schedule.repaymentId!);
    if (context.mounted) {
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Payment successful!')));
    }
  }
}
