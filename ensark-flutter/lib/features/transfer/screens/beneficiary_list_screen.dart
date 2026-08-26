import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../models/enums.dart';
import '../../../models/other/other_models.dart';
import '../../../providers/transfer_provider.dart';
import '../../auth/widgets/neon_button.dart';
import '../../auth/widgets/neon_text_field.dart';

class BeneficiaryListScreen extends ConsumerWidget {
  const BeneficiaryListScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final beneficiariesAsync = ref.watch(beneficiariesProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Beneficiaries'),
        backgroundColor: Colors.transparent,
        actions: [
          IconButton(
            icon: const Icon(Icons.add, color: AppColors.neonCyan),
            onPressed: () => _showAddBeneficiaryDialog(context, ref),
          ),
        ],
      ),
      body: beneficiariesAsync.when(
        data: (list) => list.isEmpty
            ? const Center(child: Text('No beneficiaries added yet.', style: TextStyle(color: AppColors.textSecondary)))
            : ListView.builder(
                padding: const EdgeInsets.all(24),
                itemCount: list.length,
                itemBuilder: (context, index) {
                  final b = list[index];
                  return _BeneficiaryCard(beneficiary: b);
                },
              ),
        loading: () => const Center(child: CircularProgressIndicator()),
        error: (e, s) => Center(child: Text('Error: $e')),
      ),
    );
  }

  void _showAddBeneficiaryDialog(BuildContext context, WidgetRef ref) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: AppColors.darkSurface,
      shape: const RoundedRectangleBorder(borderRadius: BorderRadius.vertical(top: Radius.circular(24))),
      builder: (context) => const _AddBeneficiarySheet(),
    );
  }
}

class _BeneficiaryCard extends ConsumerWidget {
  final BeneficiaryResponse beneficiary;
  const _BeneficiaryCard({required this.beneficiary});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: AppColors.darkBackground,
        borderRadius: BorderRadius.circular(16),
        boxShadow: AppShadows.debossed,
      ),
      child: Row(
        children: [
          Container(
            padding: const EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: AppColors.darkSurface,
              shape: BoxShape.circle,
              boxShadow: AppShadows.embossed,
            ),
            child: const Icon(Icons.person, color: AppColors.neonCyan),
          ),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(beneficiary.name ?? 'Unknown', style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
                Text(beneficiary.accNumber ?? '', style: const TextStyle(color: AppColors.textSecondary, fontSize: 13)),
                Text(beneficiary.provider ?? '', style: const TextStyle(color: AppColors.neonPink, fontSize: 11)),
              ],
            ),
          ),
          IconButton(
            icon: const Icon(Icons.delete_outline, color: Colors.redAccent),
            onPressed: () => ref.read(beneficiariesProvider.notifier).delete(beneficiary.id!),
          ),
        ],
      ),
    );
  }
}

class _AddBeneficiarySheet extends ConsumerStatefulWidget {
  const _AddBeneficiarySheet();

  @override
  ConsumerState<_AddBeneficiarySheet> createState() => _AddBeneficiarySheetState();
}

class _AddBeneficiarySheetState extends ConsumerState<_AddBeneficiarySheet> {
  final _nameController = TextEditingController();
  final _accController = TextEditingController();
  final _providerController = TextEditingController();
  BeneficiaryType _type = BeneficiaryType.INTERNAL;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        bottom: MediaQuery.of(context).viewInsets.bottom,
        left: 24,
        right: 24,
        top: 32,
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text('Add Beneficiary', style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: AppColors.neonCyan)),
          const SizedBox(height: 24),
          NeonTextField(label: 'Full Name', controller: _nameController),
          const SizedBox(height: 16),
          NeonTextField(label: 'Account Number', controller: _accController),
          const SizedBox(height: 16),
          NeonTextField(label: 'Bank/Provider Name', controller: _providerController),
          const SizedBox(height: 16),
          DropdownButtonFormField<BeneficiaryType>(
            initialValue: _type,
            items: BeneficiaryType.values.map((t) => DropdownMenuItem(value: t, child: Text(t.name))).toList(),
            onChanged: (v) => setState(() => _type = v!),
            decoration: const InputDecoration(labelText: 'Beneficiary Type'),
          ),
          const SizedBox(height: 32),
          NeonButton(
            text: 'SAVE BENEFICIARY',
            onPressed: () async {
              final request = BeneficiaryRequest(
                name: _nameController.text,
                accNumber: _accController.text,
                provider: _providerController.text,
                beneficiaryType: _type,
              );
              await ref.read(beneficiariesProvider.notifier).add(request);
              if (context.mounted) Navigator.pop(context);
            },
          ),
          const SizedBox(height: 32),
        ],
      ),
    );
  }
}
