import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:intl/intl.dart';
import '../../../models/account/account_models.dart';
import '../../../models/enums.dart';
import '../../../models/other/other_models.dart';
import '../../../providers/dashboard_provider.dart';
import '../../../providers/standing_order_provider.dart';
import '../../auth/widgets/neon_button.dart';
import '../../auth/widgets/neon_text_field.dart';

class StandingOrderFormScreen extends ConsumerStatefulWidget {
  const StandingOrderFormScreen({super.key});

  @override
  ConsumerState<StandingOrderFormScreen> createState() => _StandingOrderFormScreenState();
}

class _StandingOrderFormScreenState extends ConsumerState<StandingOrderFormScreen> {
  final _formKey = GlobalKey<FormState>();
  final _amountController = TextEditingController();
  final _targetAccController = TextEditingController();
  final _targetNameController = TextEditingController();
  final _startController = TextEditingController();
  final _endController = TextEditingController();

  AccountResponse? _selectedSource;
  StandingOrderFrequency _frequency = StandingOrderFrequency.MONTHLY;

  @override
  Widget build(BuildContext context) {
    final dashboard = ref.watch(dashboardProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('New Standing Order'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              dashboard.when(
                data: (data) => DropdownButtonFormField<AccountResponse>(
                  initialValue: _selectedSource,
                  items: (data.accounts ?? []).map((acc) => DropdownMenuItem(value: acc, child: Text(acc.accountNumber ?? ''))).toList(),
                  onChanged: (v) => setState(() => _selectedSource = v),
                  decoration: const InputDecoration(labelText: 'Source Account'),
                ),
                loading: () => const LinearProgressIndicator(),
                error: (err, stack) => const Text('Error loading accounts'),
              ),
              const SizedBox(height: 24),
              NeonTextField(label: 'Target Account Number', controller: _targetAccController),
              const SizedBox(height: 16),
              NeonTextField(label: 'Target Account Name', controller: _targetNameController),
              const SizedBox(height: 16),
              NeonTextField(label: 'Amount', controller: _amountController, keyboardType: TextInputType.number),
              const SizedBox(height: 16),
              DropdownButtonFormField<StandingOrderFrequency>(
                initialValue: _frequency,
                items: StandingOrderFrequency.values.map((f) => DropdownMenuItem(value: f, child: Text(f.name))).toList(),
                onChanged: (v) => setState(() => _frequency = v!),
                decoration: const InputDecoration(labelText: 'Frequency'),
              ),
              const SizedBox(height: 16),
              Row(
                children: [
                  Expanded(
                    child: GestureDetector(
                      onTap: () => _selectDate(true),
                      child: AbsorbPointer(child: NeonTextField(label: 'Start Date', controller: _startController)),
                    ),
                  ),
                  const SizedBox(width: 16),
                  Expanded(
                    child: GestureDetector(
                      onTap: () => _selectDate(false),
                      child: AbsorbPointer(child: NeonTextField(label: 'End Date', controller: _endController)),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 48),
              NeonButton(text: 'CREATE ORDER', onPressed: _handleSubmit),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _selectDate(bool isStart) async {
    final date = await showDatePicker(
      context: context,
      initialDate: DateTime.now().add(const Duration(days: 1)),
      firstDate: DateTime.now(),
      lastDate: DateTime.now().add(const Duration(days: 365 * 5)),
    );
    if (date != null) {
      setState(() {
        if (isStart) {
          _startController.text = DateFormat('yyyy-MM-dd').format(date);
        } else {
          _endController.text = DateFormat('yyyy-MM-dd').format(date);
        }
      });
    }
  }

  void _handleSubmit() async {
    if (_formKey.currentState!.validate()) {
      if (_selectedSource == null) return;

      final request = StandingOrderRequest(
        sourceAccountId: _selectedSource!.id,
        targetAccountNumber: _targetAccController.text,
        targetAccountName: _targetNameController.text,
        amount: double.tryParse(_amountController.text),
        frequency: _frequency,
        startDate: _startController.text,
        endDate: _endController.text,
      );

      await ref.read(standingOrdersProvider(_selectedSource!.id!).notifier).create(request);
      if (mounted) Navigator.pop(context);
    }
  }
}
