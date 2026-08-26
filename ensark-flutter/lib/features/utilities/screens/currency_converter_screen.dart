import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../models/enums.dart';
import '../../../providers/currency_provider.dart';
import '../../auth/widgets/neon_button.dart';
import '../../auth/widgets/neon_text_field.dart';

class CurrencyConverterScreen extends ConsumerStatefulWidget {
  const CurrencyConverterScreen({super.key});

  @override
  ConsumerState<CurrencyConverterScreen> createState() => _CurrencyConverterScreenState();
}

class _CurrencyConverterScreenState extends ConsumerState<CurrencyConverterScreen> {
  final _amountController = TextEditingController(text: '1.0');
  String _from = 'USD';
  String _to = 'BDT';
  double _result = 0.0;
  bool _isLoading = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Currency Converter'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Column(
          children: [
            Container(
              padding: const EdgeInsets.all(32),
              decoration: BoxDecoration(
                color: AppColors.darkSurface,
                borderRadius: BorderRadius.circular(24),
                boxShadow: AppShadows.embossed,
              ),
              child: Column(
                children: [
                  const Text('Converted Amount', style: TextStyle(color: AppColors.textSecondary)),
                  const SizedBox(height: 16),
                  Text(
                    _result.toStringAsFixed(2),
                    style: const TextStyle(
                      fontSize: 48,
                      fontWeight: FontWeight.bold,
                      color: AppColors.neonGreen,
                      shadows: [Shadow(color: AppColors.neonGreen, blurRadius: 20)],
                    ),
                  ),
                  Text(_to, style: const TextStyle(color: AppColors.textSecondary, letterSpacing: 2)),
                ],
              ),
            ),
            const SizedBox(height: 48),
            NeonTextField(label: 'Amount to Convert', controller: _amountController, keyboardType: TextInputType.number),
            const SizedBox(height: 24),
            Row(
              children: [
                Expanded(child: _CurrencyPicker(label: 'From', value: _from, onChanged: (v) => setState(() => _from = v!))),
                const SizedBox(width: 16),
                const Icon(Icons.compare_arrows, color: AppColors.neonCyan),
                const SizedBox(width: 16),
                Expanded(child: _CurrencyPicker(label: 'To', value: _to, onChanged: (v) => setState(() => _to = v!))),
              ],
            ),
            const SizedBox(height: 48),
            NeonButton(text: 'CONVERT', isLoading: _isLoading, onPressed: _handleConvert),
          ],
        ),
      ),
    );
  }

  void _handleConvert() async {
    setState(() => _isLoading = true);
    final amount = double.tryParse(_amountController.text) ?? 0.0;
    final res = await ref.read(currencyConverterProvider.notifier).convert(_from, _to, amount);
    setState(() {
      _result = res;
      _isLoading = false;
    });
  }
}

class _CurrencyPicker extends StatelessWidget {
  final String label;
  final String value;
  final ValueChanged<String?> onChanged;

  const _CurrencyPicker({required this.label, required this.value, required this.onChanged});

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(label, style: const TextStyle(fontSize: 12, color: AppColors.textSecondary)),
        const SizedBox(height: 8),
        DropdownButtonFormField<String>(
          initialValue: value,
          items: Currency.values.map((c) => DropdownMenuItem(value: c.name, child: Text(c.name))).toList(),
          onChanged: onChanged,
          decoration: const InputDecoration(border: OutlineInputBorder()),
        ),
      ],
    );
  }
}
