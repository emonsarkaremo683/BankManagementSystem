import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../providers/auth_provider.dart';
import '../../../repositories/customer_repository.dart';
import '../../auth/widgets/neon_button.dart';
import '../../auth/widgets/neon_text_field.dart';

class ChangePasswordScreen extends ConsumerStatefulWidget {
  const ChangePasswordScreen({super.key});

  @override
  ConsumerState<ChangePasswordScreen> createState() => _ChangePasswordScreenState();
}

class _ChangePasswordScreenState extends ConsumerState<ChangePasswordScreen> {
  final _oldPassController = TextEditingController();
  final _newPassController = TextEditingController();
  final _confirmPassController = TextEditingController();
  bool _isLoading = false;

  void _handleChange() async {
    if (_newPassController.text != _confirmPassController.text) {
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Passwords do not match')));
      return;
    }

    setState(() => _isLoading = true);
    try {
      final user = ref.read(authProvider).value?.user;
      if (user != null) {
        await ref.read(customerRepositoryProvider).updatePassword(
          user.id,
          _oldPassController.text,
          _newPassController.text,
        );
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Password updated successfully!')));
          context.pop();
        }
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
      appBar: AppBar(title: const Text('Change Password'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Column(
          children: [
            NeonTextField(label: 'Current Password', controller: _oldPassController, isPassword: true),
            const SizedBox(height: 20),
            NeonTextField(label: 'New Password', controller: _newPassController, isPassword: true),
            const SizedBox(height: 20),
            NeonTextField(label: 'Confirm New Password', controller: _confirmPassController, isPassword: true),
            const SizedBox(height: 48),
            NeonButton(text: 'UPDATE PASSWORD', isLoading: _isLoading, onPressed: _handleChange),
          ],
        ),
      ),
    );
  }
}
