import 'dart:io';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:image_picker/image_picker.dart';
import '../../../core/theme/app_colors.dart';
import '../../../models/account/account_models.dart';
import '../../../models/loan/loan_models.dart';
import '../../../providers/dashboard_provider.dart';
import '../../../providers/loan_provider.dart';
import '../../auth/widgets/neon_button.dart';
import '../../auth/widgets/neon_text_field.dart';

class LoanApplicationScreen extends ConsumerStatefulWidget {
  const LoanApplicationScreen({super.key});

  @override
  ConsumerState<LoanApplicationScreen> createState() => _LoanApplicationScreenState();
}

class _LoanApplicationScreenState extends ConsumerState<LoanApplicationScreen> {
  final _formKey = GlobalKey<FormState>();
  
  final _amountController = TextEditingController();
  final _tenureController = TextEditingController();
  final _interestController = TextEditingController(text: '12.5'); // Default
  
  final _guarantorNameController = TextEditingController();
  final _guarantorPhoneController = TextEditingController();
  final _guarantorNidController = TextEditingController();
  final _guarantorAddressController = TextEditingController();
  final _relationController = TextEditingController();

  AccountResponse? _selectedAccount;
  File? _guarantorPhoto;
  final List<File> _documents = [];

  Future<void> _pickGuarantorPhoto() async {
    final file = await ImagePicker().pickImage(source: ImageSource.gallery);
    if (file != null) setState(() => _guarantorPhoto = File(file.path));
  }

  Future<void> _pickDocument() async {
    final file = await ImagePicker().pickImage(source: ImageSource.gallery);
    if (file != null) setState(() => _documents.add(File(file.path)));
  }

  void _handleSubmit() async {
    if (_formKey.currentState!.validate()) {
      if (_selectedAccount == null) {
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Please select an account')));
        return;
      }
      if (_guarantorPhoto == null) {
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Guarantor photo is required')));
        return;
      }

      final request = LoanApplicationRequest(
        accountId: _selectedAccount!.id,
        principalAmount: double.tryParse(_amountController.text),
        annualInterestRate: double.tryParse(_interestController.text),
        tenureMonths: int.tryParse(_tenureController.text),
        guarantor: GuarantorRequest(
          name: _guarantorNameController.text,
          phone: _guarantorPhoneController.text,
          nidNumber: _guarantorNidController.text,
          address: _guarantorAddressController.text,
          relation: _relationController.text,
        ),
      );

      final Map<String, List<int>> files = {
        'guarantorPhoto': await _guarantorPhoto!.readAsBytes(),
      };
      
      // For backend multipart names of documents, assuming indexed list
      for (int i = 0; i < _documents.length; i++) {
        files['documents[$i]'] = await _documents[i].readAsBytes();
      }

      await ref.read(loansProvider.notifier).apply(request, files);
      
      if (mounted && !ref.read(loansProvider).hasError) {
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Loan application submitted!')));
        context.pop();
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final dashboard = ref.watch(dashboardProvider);
    final loanState = ref.watch(loansProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Loan Application'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text('Loan Details', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: AppColors.neonCyan)),
              const SizedBox(height: 16),
              dashboard.when(
                data: (data) => DropdownButtonFormField<AccountResponse>(
                  initialValue: _selectedAccount,
                  items: (data.accounts ?? []).map((acc) => DropdownMenuItem(
                    value: acc,
                    child: Text('${acc.accountType?.name} - ${acc.accountNumber}'),
                  )).toList(),
                  onChanged: (v) => setState(() => _selectedAccount = v),
                  decoration: const InputDecoration(hintText: 'Select Account'),
                ),
                loading: () => const LinearProgressIndicator(),
                error: (err, stack) => const Text('Error loading accounts'),
              ),
              const SizedBox(height: 16),
              NeonTextField(label: 'Principal Amount', controller: _amountController, keyboardType: TextInputType.number),
              const SizedBox(height: 16),
              NeonTextField(label: 'Tenure (Months)', controller: _tenureController, keyboardType: TextInputType.number),
              const SizedBox(height: 16),
              NeonTextField(label: 'Annual Interest Rate (%)', controller: _interestController, keyboardType: TextInputType.number),
              
              const SizedBox(height: 32),
              const Text('Guarantor Info', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: AppColors.neonPink)),
              const SizedBox(height: 16),
              NeonTextField(label: 'Full Name', controller: _guarantorNameController),
              const SizedBox(height: 16),
              NeonTextField(label: 'Phone Number', controller: _guarantorPhoneController, keyboardType: TextInputType.phone),
              const SizedBox(height: 16),
              NeonTextField(label: 'NID Number', controller: _guarantorNidController),
              const SizedBox(height: 16),
              NeonTextField(label: 'Relation to Applicant', controller: _relationController),
              const SizedBox(height: 16),
              _ImagePickerTile(
                label: 'Guarantor Photo',
                file: _guarantorPhoto,
                onTap: _pickGuarantorPhoto,
              ),

              const SizedBox(height: 32),
              const Text('Documents', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: AppColors.neonCyan)),
              const SizedBox(height: 16),
              ListView.builder(
                shrinkWrap: true,
                physics: const NeverScrollableScrollPhysics(),
                itemCount: _documents.length + 1,
                itemBuilder: (context, index) {
                  if (index == _documents.length) {
                    return ListTile(
                      leading: const Icon(Icons.add_a_photo, color: AppColors.neonCyan),
                      title: const Text('Add Document (Bank Statement, etc.)'),
                      onTap: _pickDocument,
                    );
                  }
                  return ListTile(
                    leading: const Icon(Icons.description, color: AppColors.textSecondary),
                    title: Text('Document ${index + 1}'),
                    trailing: IconButton(
                      icon: const Icon(Icons.delete, color: Colors.redAccent),
                      onPressed: () => setState(() => _documents.removeAt(index)),
                    ),
                  );
                },
              ),

              const SizedBox(height: 48),
              NeonButton(text: 'SUBMIT APPLICATION', isLoading: loanState.isLoading, onPressed: _handleSubmit),
              const SizedBox(height: 40),
            ],
          ),
        ),
      ),
    );
  }
}

class _ImagePickerTile extends StatelessWidget {
  final String label;
  final File? file;
  final VoidCallback onTap;

  const _ImagePickerTile({required this.label, this.file, required this.onTap});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      contentPadding: EdgeInsets.zero,
      title: Text(label, style: const TextStyle(fontWeight: FontWeight.bold)),
      subtitle: Text(file != null ? 'Selected' : 'Not selected'),
      trailing: IconButton(icon: const Icon(Icons.camera_alt, color: AppColors.neonCyan), onPressed: onTap),
    );
  }
}
