import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:intl/intl.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../models/transaction/transaction_models.dart';
import '../../../providers/statement_provider.dart';

class StatementScreen extends ConsumerStatefulWidget {
  const StatementScreen({super.key});

  @override
  ConsumerState<StatementScreen> createState() => _StatementScreenState();
}

class _StatementScreenState extends ConsumerState<StatementScreen> {
  DateTime _fromDate = DateTime.now().subtract(const Duration(days: 30));
  DateTime _toDate = DateTime.now();

  @override
  Widget build(BuildContext context) {
    final statementsAsync = ref.watch(statementProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Account Statement'),
        backgroundColor: Colors.transparent,
        actions: [
          IconButton(
            icon: const Icon(Icons.download, color: AppColors.neonCyan),
            onPressed: () => _showExportDialog(context),
          ),
        ],
      ),
      body: Column(
        children: [
          _DateFilterHeader(
            from: _fromDate,
            to: _toDate,
            onTap: _selectDateRange,
          ),
          Expanded(
            child: statementsAsync.when(
              data: (list) => list.isEmpty
                  ? const _EmptyState()
                  : ListView.builder(
                      padding: const EdgeInsets.all(24),
                      itemCount: list.length,
                      itemBuilder: (context, index) => _StatementTile(journal: list[index]),
                    ),
              loading: () => const Center(child: CircularProgressIndicator()),
              error: (e, s) => Center(child: Text('Error: $e')),
            ),
          ),
        ],
      ),
    );
  }

  Future<void> _selectDateRange() async {
    final range = await showDateRangePicker(
      context: context,
      firstDate: DateTime(2020),
      lastDate: DateTime.now(),
      initialDateRange: DateTimeRange(start: _fromDate, end: _toDate),
    );

    if (range != null) {
      setState(() {
        _fromDate = range.start;
        _toDate = range.end;
      });
      ref.read(statementProvider.notifier).filterByDate(range.start, range.end);
    }
  }

  void _showExportDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        backgroundColor: AppColors.darkSurface,
        title: const Text('Export Statement'),
        content: const Text('Choose format to download your account statement.'),
        actions: [
          TextButton(onPressed: () => _export('PDF'), child: const Text('PDF', style: TextStyle(color: AppColors.neonCyan))),
          TextButton(onPressed: () => _export('CSV'), child: const Text('CSV', style: TextStyle(color: AppColors.neonPink))),
        ],
      ),
    );
  }

  void _export(String format) async {
    Navigator.pop(context);
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Exporting as $format...')));
    // In a real app, we'd handle the byte download and file saving here.
  }
}

class _DateFilterHeader extends StatelessWidget {
  final DateTime from;
  final DateTime to;
  final VoidCallback onTap;

  const _DateFilterHeader({required this.from, required this.to, required this.onTap});

  @override
  Widget build(BuildContext context) {
    final format = DateFormat('MMM dd, yyyy');
    return GestureDetector(
      onTap: onTap,
      child: Container(
        margin: const EdgeInsets.all(24),
        padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 16),
        decoration: BoxDecoration(
          color: AppColors.darkSurface,
          borderRadius: BorderRadius.circular(16),
          boxShadow: AppShadows.embossed,
          border: Border.all(color: AppColors.neonCyan.withValues(alpha: 0.2)),
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text('DATE RANGE', style: TextStyle(fontSize: 10, color: AppColors.textSecondary, letterSpacing: 1)),
                const SizedBox(height: 4),
                Text('${format.format(from)} - ${format.format(to)}', style: const TextStyle(fontWeight: FontWeight.bold)),
              ],
            ),
            const Icon(Icons.calendar_month, color: AppColors.neonCyan),
          ],
        ),
      ),
    );
  }
}

class _StatementTile extends StatelessWidget {
  final JournalResponse journal;
  const _StatementTile({required this.journal});

  @override
  Widget build(BuildContext context) {
    final isCredit = journal.type?.toUpperCase() == 'CREDIT';
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
          Container(
            padding: const EdgeInsets.all(8),
            decoration: BoxDecoration(
              color: isCredit ? Colors.green.withValues(alpha: 0.1) : Colors.red.withValues(alpha: 0.1),
              shape: BoxShape.circle,
            ),
            child: Icon(
              isCredit ? Icons.add : Icons.remove,
              color: isCredit ? Colors.green : Colors.redAccent,
              size: 20,
            ),
          ),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(journal.remarks ?? 'No remarks', style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 14)),
                Text(
                  journal.createdAt != null ? DateFormat('MMM dd, hh:mm a').format(journal.createdAt!) : '',
                  style: const TextStyle(color: AppColors.textSecondary, fontSize: 12),
                ),
              ],
            ),
          ),
          Text(
            '${isCredit ? '+' : '-'}${NumberFormat.currency(symbol: '\$').format((journal.amount ?? 0).abs())}',
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: isCredit ? AppColors.neonGreen : Colors.white,
            ),
          ),
        ],
      ),
    );
  }
}

class _EmptyState extends StatelessWidget {
  const _EmptyState();
  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(Icons.history, size: 64, color: Colors.white10),
          SizedBox(height: 16),
          Text('No transactions for this period.', style: TextStyle(color: AppColors.textSecondary)),
        ],
      ),
    );
  }
}
