import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/routing/app_router.dart';
import '../../../models/account/account_models.dart';
import '../../../models/transaction/transaction_models.dart';
import '../../../models/other/other_models.dart';
import '../../../providers/dashboard_provider.dart';
import '../../../providers/transfer_provider.dart';
import '../../auth/widgets/neon_button.dart';
import '../../auth/widgets/neon_text_field.dart';

class TransferScreen extends ConsumerStatefulWidget {
  const TransferScreen({super.key});

  @override
  ConsumerState<TransferScreen> createState() => _TransferScreenState();
}

class _TransferScreenState extends ConsumerState<TransferScreen> {
  final _formKey = GlobalKey<FormState>();
  final _amountController = TextEditingController();
  final _remarksController = TextEditingController();
  final _receiverAccController = TextEditingController();
  final _receiverNameController = TextEditingController();

  AccountResponse? _selectedSource;
  BeneficiaryResponse? _selectedBeneficiary;
  bool _isManual = true;

  @override
  Widget build(BuildContext context) {
    final dashboard = ref.watch(dashboardProvider);
    final beneficiaries = ref.watch(beneficiariesProvider);
    final transferState = ref.watch(transferProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Transfer Money'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text('Source Account', style: TextStyle(fontWeight: FontWeight.bold, color: AppColors.neonCyan)),
              const SizedBox(height: 12),
              dashboard.when(
                data: (data) => DropdownButtonFormField<AccountResponse>(
                  initialValue: _selectedSource,
                  items: (data.accounts ?? []).map((acc) => DropdownMenuItem(
                    value: acc,
                    child: Text('${acc.accountType?.name} - ${acc.accountNumber}'),
                  )).toList(),
                  onChanged: (v) => setState(() => _selectedSource = v),
                  decoration: const InputDecoration(hintText: 'Select Source'),
                ),
                loading: () => const CircularProgressIndicator(),
                error: (err, stack) => const Text('Error loading accounts'),
              ),
              const SizedBox(height: 32),
              
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text('Receiver Details', style: TextStyle(fontWeight: FontWeight.bold, color: AppColors.neonPink)),
                  Switch(
                    value: _isManual,
                    onChanged: (v) => setState(() {
                      _isManual = v;
                      _selectedBeneficiary = null;
                    }),
                    activeThumbColor: AppColors.neonPink,
                  ),
                ],
              ),
              Text(_isManual ? 'Manual Entry' : 'Select Beneficiary', style: const TextStyle(fontSize: 12, color: AppColors.textSecondary)),
              const SizedBox(height: 12),

              if (!_isManual)
                beneficiaries.when(
                  data: (list) => list.isEmpty
                      ? Center(
                          child: Column(
                            children: [
                              const Text('No beneficiaries found', style: TextStyle(color: AppColors.textSecondary)),
                              TextButton.icon(
                                icon: const Icon(Icons.add, color: AppColors.neonCyan),
                                label: const Text('ADD NEW', style: TextStyle(color: AppColors.neonCyan)),
                                onPressed: () => context.push(AppRoutes.beneficiaries),
                              ),
                            ],
                          ),
                        )
                      : DropdownButtonFormField<BeneficiaryResponse>(
                          initialValue: _selectedBeneficiary,
                          items: list
                              .map((b) => DropdownMenuItem(
                                    value: b,
                                    child: Text('${b.name} (${b.accNumber})'),
                                  ))
                              .toList(),
                          onChanged: (v) => setState(() {
                            _selectedBeneficiary = v;
                            if (v != null) {
                              _receiverAccController.text = v.accNumber ?? '';
                              _receiverNameController.text = v.name ?? '';
                            }
                          }),
                          decoration: const InputDecoration(hintText: 'Select Beneficiary'),
                        ),
                  loading: () => const Center(child: CircularProgressIndicator()),
                  error: (err, stack) => const Text('Error loading beneficiaries'),
                )
              else ...[
                NeonTextField(label: 'Receiver Account Number', controller: _receiverAccController),
                const SizedBox(height: 16),
                NeonTextField(label: 'Receiver Name', controller: _receiverNameController),
              ],

              const SizedBox(height: 32),
              const Text('Transaction Info', style: TextStyle(fontWeight: FontWeight.bold, color: AppColors.neonCyan)),
              const SizedBox(height: 12),
              NeonTextField(label: 'Amount', controller: _amountController, keyboardType: TextInputType.number),
              const SizedBox(height: 16),
              NeonTextField(label: 'Remarks', controller: _remarksController),
              
              const SizedBox(height: 48),
              NeonButton(
                text: 'INITIATE TRANSFER',
                isLoading: transferState.isLoading,
                onPressed: _handleInitiate,
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _handleInitiate() async {
    if (_formKey.currentState!.validate()) {
      final messenger = ScaffoldMessenger.of(context);
      final router = GoRouter.of(context);

      if (_selectedSource == null) {
        messenger.showSnackBar(const SnackBar(content: Text('Please select source account')));
        return;
      }

      if (_receiverAccController.text.isEmpty) {
        messenger.showSnackBar(const SnackBar(content: Text('Please enter receiver account number')));
        return;
      }

      final amount = double.tryParse(_amountController.text);
      if (amount == null || amount <= 0) {
        messenger.showSnackBar(const SnackBar(content: Text('Please enter a valid positive amount')));
        return;
      }

      final request = AccountTransactionRequest(
        senderAccountId: _selectedSource!.id,
        receiverAccountNumber: _receiverAccController.text,
        receiverName: _receiverNameController.text,
        beneficiaryId: _selectedBeneficiary?.id,
        bankName: _selectedBeneficiary?.provider, // Map bank name from beneficiary
        routingNumber: _selectedBeneficiary?.routingNumber, // Map routing number
        request: TransactionRequest(
          amount: amount,
          remarks: _remarksController.text,
        ),
      );

      try {
        final response = await ref.read(transferProvider.notifier).initiate(request);
        
        if (!mounted) return;

        if (response != null) {
          router.push(AppRoutes.otpVerify, extra: {
            'otpReferenceId': response.otpReferenceId,
            'maskedEmail': response.maskedEmail,
          });
        } else {
          final error = ref.read(transferProvider).error;
          messenger.showSnackBar(SnackBar(
            content: Text('Transfer failed: ${error?.toString() ?? 'Unknown error'}'),
            backgroundColor: Colors.redAccent,
          ));
        }
      } catch (e) {
        if (mounted) {
          messenger.showSnackBar(SnackBar(
            content: Text('Error: $e'),
            backgroundColor: Colors.redAccent,
          ));
        }
      }
    }
  }
}
