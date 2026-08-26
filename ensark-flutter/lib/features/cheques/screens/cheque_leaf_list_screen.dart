import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../models/other/other_models.dart';
import '../../../providers/auth_provider.dart';
import '../../../providers/cheque_provider.dart';
import '../../auth/widgets/neon_text_field.dart';

class ChequeLeafListScreen extends ConsumerWidget {
  final ChequeBookResponse book;
  const ChequeLeafListScreen({super.key, required this.book});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final customerId = ref.watch(authProvider).value?.user?.id;
    final leavesAsync = ref.watch(chequeLeavesProvider(customerId ?? 0));

    return Scaffold(
      appBar: AppBar(title: Text('Leaves: ${book.bookSerialNumber}'), backgroundColor: Colors.transparent),
      body: leavesAsync.when(
        data: (list) {
          // Filter leaves that belong to this book (if needed, or assume backend filters by customer)
          // The API findByCustomerId usually returns all leaves. 
          // Let's filter by chequeBookId if present in response.
          final filtered = list.where((l) => l.chequeBookId == book.chequeBookId).toList();

          return GridView.builder(
            padding: const EdgeInsets.all(24),
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 3,
              crossAxisSpacing: 16,
              mainAxisSpacing: 16,
              childAspectRatio: 0.8,
            ),
            itemCount: filtered.length,
            itemBuilder: (context, index) => _LeafItem(leaf: filtered[index]),
          );
        },
        loading: () => const Center(child: CircularProgressIndicator()),
        error: (e, s) => Center(child: Text('Error: $e')),
      ),
    );
  }
}

class _LeafItem extends StatelessWidget {
  final ChequeLeafResponse leaf;
  const _LeafItem({required this.leaf});

  @override
  Widget build(BuildContext context) {
    final isUsed = leaf.status?.name == 'USED';
    final isStopped = leaf.status?.name == 'STOPPED';

    return GestureDetector(
      onTap: (!isUsed && !isStopped) ? () => _showStopPaymentDialog(context) : null,
      child: Container(
        decoration: BoxDecoration(
          color: isUsed ? Colors.white.withValues(alpha: 0.05) : AppColors.darkSurface,
          borderRadius: BorderRadius.circular(12),
          boxShadow: isUsed ? [] : AppShadows.embossed,
          border: Border.all(
            color: isStopped ? Colors.redAccent.withValues(alpha: 0.3) : Colors.transparent,
          ),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              '${leaf.leafNumber}',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
                color: isUsed ? Colors.white24 : (isStopped ? Colors.redAccent : AppColors.neonCyan),
              ),
            ),
            const SizedBox(height: 4),
            Text(
              leaf.status?.name ?? 'UNUSED',
              style: TextStyle(fontSize: 8, color: isUsed ? Colors.white10 : AppColors.textSecondary),
            ),
          ],
        ),
      ),
    );
  }

  void _showStopPaymentDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => _StopPaymentDialog(leafId: leaf.leafId!),
    );
  }
}

class _StopPaymentDialog extends ConsumerStatefulWidget {
  final int leafId;
  const _StopPaymentDialog({required this.leafId});

  @override
  ConsumerState<_StopPaymentDialog> createState() => _StopPaymentDialogState();
}

class _StopPaymentDialogState extends ConsumerState<_StopPaymentDialog> {
  final _remarksController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      backgroundColor: AppColors.darkSurface,
      title: const Text('Stop Payment'),
      content: NeonTextField(label: 'Reason/Remarks', controller: _remarksController),
      actions: [
        TextButton(onPressed: () => Navigator.pop(context), child: const Text('CANCEL')),
        TextButton(
          onPressed: () async {
            await ref.read(leafActionsProvider.notifier).stopPayment(widget.leafId, _remarksController.text);
            if (context.mounted) Navigator.pop(context);
          },
          child: const Text('STOP PAYMENT', style: TextStyle(color: Colors.redAccent)),
        ),
      ],
    );
  }
}
