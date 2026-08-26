import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/routing/app_router.dart';
import '../../../models/auth/auth_models.dart';
import '../../../repositories/auth_repository.dart';
import '../widgets/neon_button.dart';
import '../widgets/neon_text_field.dart';

class ResetPasswordScreen extends ConsumerStatefulWidget {
  const ResetPasswordScreen({super.key});

  @override
  ConsumerState<ResetPasswordScreen> createState() => _ResetPasswordScreenState();
}

class _ResetPasswordScreenState extends ConsumerState<ResetPasswordScreen> {
  final _tokenController = TextEditingController();
  final _passwordController = TextEditingController();
  bool _isLoading = false;

  void _handleSubmit() async {
    final messenger = ScaffoldMessenger.of(context);
    final router = GoRouter.of(context);
    setState(() => _isLoading = true);
    try {
      await ref.read(authRepositoryProvider).resetPassword(ResetPasswordRequest(
        token: _tokenController.text.trim(),
        newPassword: _passwordController.text,
      ));
      if (mounted) {
        messenger.showSnackBar(const SnackBar(content: Text('Password reset successful!')));
        router.go(AppRoutes.login);
      }
    } catch (e) {
      if (mounted) {
        messenger.showSnackBar(SnackBar(content: Text(e.toString())));
      }
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(backgroundColor: Colors.transparent),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          children: [
            NeonTextField(label: 'Reset Token', controller: _tokenController),
            const SizedBox(height: 20),
            NeonTextField(label: 'New Password', controller: _passwordController, isPassword: true),
            const SizedBox(height: 32),
            NeonButton(text: 'RESET PASSWORD', isLoading: _isLoading, onPressed: _handleSubmit),
          ],
        ),
      ),
    );
  }
}
