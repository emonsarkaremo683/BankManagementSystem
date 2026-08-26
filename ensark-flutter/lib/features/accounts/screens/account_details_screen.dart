import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../models/account/account_models.dart';

class AccountDetailsScreen extends StatelessWidget {
  final AccountResponse account;
  const AccountDetailsScreen({super.key, required this.account});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Account Details'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _DetailCard(
              child: Column(
                children: [
                  Text(account.accountType?.name ?? 'Account', style: const TextStyle(color: AppColors.textSecondary)),
                  const SizedBox(height: 8),
                  Text(
                    NumberFormat.currency(symbol: '\$').format(account.availableBalance ?? 0),
                    style: const TextStyle(fontSize: 32, fontWeight: FontWeight.bold, color: AppColors.neonCyan),
                  ),
                  const SizedBox(height: 16),
                  Text('Acc No: ${account.accountNumber}', style: const TextStyle(fontFamily: 'monospace')),
                ],
              ),
            ),
            const SizedBox(height: 32),
            const Text('Details', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
            const SizedBox(height: 16),
            _InfoRow(label: 'Branch', value: account.branchName ?? 'N/A'),
            _InfoRow(label: 'Routing Number', value: account.branchRoutingNumber ?? 'N/A'),
            _InfoRow(label: 'Status', value: account.accountStatus?.name ?? 'N/A'),
            _InfoRow(label: 'Current Balance', value: NumberFormat.currency(symbol: '\$').format(account.currentBalance ?? 0)),
            const SizedBox(height: 32),
            const Text('Nominee', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
            const SizedBox(height: 16),
            _InfoRow(label: 'Name', value: account.nName ?? 'N/A'),
            _InfoRow(label: 'Relation', value: account.relation?.name ?? 'N/A'),
            _InfoRow(label: 'Phone', value: account.nPhone ?? 'N/A'),
          ],
        ),
      ),
    );
  }
}

class _DetailCard extends StatelessWidget {
  final Widget child;
  const _DetailCard({required this.child});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(24),
      decoration: BoxDecoration(
        color: AppColors.darkSurface,
        borderRadius: BorderRadius.circular(20),
        boxShadow: AppShadows.embossed,
      ),
      child: child,
    );
  }
}

class _InfoRow extends StatelessWidget {
  final String label;
  final String value;
  const _InfoRow({required this.label, required this.value});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(label, style: const TextStyle(color: AppColors.textSecondary)),
          Text(value, style: const TextStyle(fontWeight: FontWeight.bold)),
        ],
      ),
    );
  }
}
