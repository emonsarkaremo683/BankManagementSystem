import 'dart:io';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:image_picker/image_picker.dart';
import '../../../core/theme/app_colors.dart';
import '../../../repositories/kyc_repository.dart';
import '../../../providers/auth_provider.dart';
import '../../auth/widgets/neon_button.dart';

class KycUploadScreen extends ConsumerStatefulWidget {
  const KycUploadScreen({super.key});

  @override
  ConsumerState<KycUploadScreen> createState() => _KycUploadScreenState();
}

class _KycUploadScreenState extends ConsumerState<KycUploadScreen> {
  final Map<String, File?> _files = {
    'NID': null,
    'PASSPORT': null,
    'DRIVING_LICENSE': null,
    'BIRTH_CERTIFICATE': null,
  };
  bool _isLoading = false;

  Future<void> _pickImage(String key) async {
    final pickedFile = await ImagePicker().pickImage(source: ImageSource.gallery);
    if (pickedFile != null) {
      setState(() => _files[key] = File(pickedFile.path));
    }
  }

  void _handleUpload() async {
    final selectedFiles = _files.entries.where((e) => e.value != null).toList();
    if (selectedFiles.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Select at least one document')));
      return;
    }

    setState(() => _isLoading = true);
    try {
      final Map<String, List<int>> fileBytes = {};
      for (var entry in selectedFiles) {
        fileBytes[entry.key] = await entry.value!.readAsBytes();
      }

      await ref.read(kycRepositoryProvider).uploadMyDocuments(fileBytes);
      await ref.read(authProvider.notifier).refreshUser();
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Documents uploaded successfully!')));
        context.pop();
      }
    } catch (e) {
      if (mounted) ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(e.toString())));
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('KYC Verification'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Column(
          children: [
            const Text(
              'Upload your identification documents for verification.',
              style: TextStyle(color: AppColors.textSecondary),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 32),
            _DocumentTile(label: 'National ID (NID)', file: _files['NID'], onPick: () => _pickImage('NID')),
            _DocumentTile(label: 'Passport', file: _files['PASSPORT'], onPick: () => _pickImage('PASSPORT')),
            _DocumentTile(label: 'Driving License', file: _files['DRIVING_LICENSE'], onPick: () => _pickImage('DRIVING_LICENSE')),
            _DocumentTile(label: 'Birth Certificate', file: _files['BIRTH_CERTIFICATE'], onPick: () => _pickImage('BIRTH_CERTIFICATE')),
            const SizedBox(height: 48),
            NeonButton(text: 'UPLOAD ALL', isLoading: _isLoading, onPressed: _handleUpload),
          ],
        ),
      ),
    );
  }
}

class _DocumentTile extends StatelessWidget {
  final String label;
  final File? file;
  final VoidCallback onPick;

  const _DocumentTile({required this.label, this.file, required this.onPick});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      decoration: BoxDecoration(
        color: AppColors.darkBackground,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: file != null ? AppColors.neonCyan : Colors.transparent),
      ),
      child: ListTile(
        contentPadding: EdgeInsets.zero,
        title: Text(label, style: const TextStyle(fontWeight: FontWeight.bold)),
        subtitle: Text(file != null ? 'Selected' : 'Not selected', style: TextStyle(color: file != null ? AppColors.neonCyan : AppColors.textSecondary)),
        trailing: IconButton(icon: Icon(Icons.add_a_photo, color: file != null ? AppColors.neonCyan : AppColors.textSecondary), onPressed: onPick),
      ),
    );
  }
}
