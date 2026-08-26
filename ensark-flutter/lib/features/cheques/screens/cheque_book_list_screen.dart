import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../core/routing/app_router.dart';
import '../../../models/account/account_models.dart';
import '../../../models/other/other_models.dart';
import '../../../providers/dashboard_provider.dart';
import '../../../providers/cheque_provider.dart';
import '../../auth/widgets/neon_button.dart';

class ChequeBookListScreen extends ConsumerWidget {
  const ChequeBookListScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final booksAsync = ref.watch(chequeBooksProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Cheque Books'),
        backgroundColor: Colors.transparent,
        actions: [
          IconButton(
            icon: const Icon(Icons.add_box_outlined, color: AppColors.neonCyan),
            onPressed: () => _showApplySheet(context, ref),
          ),
        ],
      ),
      body: booksAsync.when(
        data: (books) => books.isEmpty
            ? const _EmptyCheques()
            : RefreshIndicator(
                onRefresh: () => ref.read(chequeBooksProvider.notifier).refresh(),
                child: ListView.builder(
                  padding: const EdgeInsets.all(24),
                  itemCount: books.length,
                  itemBuilder: (context, index) => _ChequeBookCard(book: books[index]),
                ),
              ),
        loading: () => const Center(child: CircularProgressIndicator()),
        error: (e, s) => Center(child: Text('Error: $e')),
      ),
    );
  }

  void _showApplySheet(BuildContext context, WidgetRef ref) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: AppColors.darkSurface,
      shape: const RoundedRectangleBorder(borderRadius: BorderRadius.vertical(top: Radius.circular(24))),
      builder: (context) => const _ApplyChequeSheet(),
    );
  }
}

class _ChequeBookCard extends StatelessWidget {
  final ChequeBookResponse book;
  const _ChequeBookCard({required this.book});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 20),
      decoration: BoxDecoration(
        color: AppColors.darkSurface,
        borderRadius: BorderRadius.circular(20),
        boxShadow: AppShadows.embossed,
      ),
      child: InkWell(
        onTap: () => context.push(AppRoutes.chequeLeafList, extra: book),
        borderRadius: BorderRadius.circular(20),
        child: Padding(
          padding: const EdgeInsets.all(24),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text('CHEQUE BOOK', style: TextStyle(color: AppColors.textSecondary, letterSpacing: 1.5, fontSize: 10)),
                  _StatusChip(status: book.status?.name ?? 'ACTIVE'),
                ],
              ),
              const SizedBox(height: 12),
              Text(
                book.bookSerialNumber ?? 'N/A',
                style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold, fontFamily: 'monospace'),
              ),
              const SizedBox(height: 16),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  _InfoItem(label: 'LEAVES', value: '${book.numberOfLeaves}'),
                  _InfoItem(label: 'RANGE', value: '${book.startLeafNumber} - ${book.endLeafNumber}'),
                  const Icon(Icons.chevron_right, color: AppColors.neonCyan),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class _InfoItem extends StatelessWidget {
  final String label;
  final String value;
  const _InfoItem({required this.label, required this.value});

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(label, style: const TextStyle(color: AppColors.textSecondary, fontSize: 10)),
        Text(value, style: const TextStyle(fontWeight: FontWeight.bold)),
      ],
    );
  }
}

class _StatusChip extends StatelessWidget {
  final String status;
  const _StatusChip({required this.status});

  @override
  Widget build(BuildContext context) {
    final Color color = status == 'ACTIVATED' || status == 'DELIVERED' ? Colors.green : Colors.orange;
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
      decoration: BoxDecoration(color: color.withValues(alpha: 0.1), borderRadius: BorderRadius.circular(8)),
      child: Text(status, style: TextStyle(color: color, fontSize: 10, fontWeight: FontWeight.bold)),
    );
  }
}

class _EmptyCheques extends StatelessWidget {
  const _EmptyCheques();
  @override
  Widget build(BuildContext context) {
    return const Center(child: Text('No cheque books found.', style: TextStyle(color: AppColors.textSecondary)));
  }
}

class _ApplyChequeSheet extends ConsumerStatefulWidget {
  const _ApplyChequeSheet();
  @override
  ConsumerState<_ApplyChequeSheet> createState() => _ApplyChequeSheetState();
}

class _ApplyChequeSheetState extends ConsumerState<_ApplyChequeSheet> {
  AccountResponse? _selectedAccount;
  int _leaves = 25;

  @override
  Widget build(BuildContext context) {
    final dashboard = ref.watch(dashboardProvider);

    return Padding(
      padding: const EdgeInsets.all(32),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          const Text('Apply for Cheque Book', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: AppColors.neonCyan)),
          const SizedBox(height: 24),
          dashboard.when(
            data: (data) => DropdownButtonFormField<AccountResponse>(
              initialValue: _selectedAccount,
              items: (data.accounts ?? []).map((acc) => DropdownMenuItem(value: acc, child: Text(acc.accountNumber ?? ''))).toList(),
              onChanged: (v) => setState(() => _selectedAccount = v),
              decoration: const InputDecoration(labelText: 'Select Account'),
            ),
            loading: () => const LinearProgressIndicator(),
            error: (err, stack) => const Text('Error loading accounts'),
          ),
          const SizedBox(height: 24),
          DropdownButtonFormField<int>(
            initialValue: _leaves,
            items: [10, 25, 50, 100].map((l) => DropdownMenuItem(value: l, child: Text('$l Leaves'))).toList(),
            onChanged: (v) => setState(() => _leaves = v!),
            decoration: const InputDecoration(labelText: 'Book Size'),
          ),
          const SizedBox(height: 48),
          NeonButton(
            text: 'SUBMIT APPLICATION',
            onPressed: () async {
              if (_selectedAccount != null) {
                await ref.read(chequeBooksProvider.notifier).apply(ChequeBookRequest(
                  accountId: _selectedAccount!.id,
                  numberOfLeaves: _leaves,
                ));
                if (context.mounted) Navigator.pop(context);
              }
            },
          ),
        ],
      ),
    );
  }
}
