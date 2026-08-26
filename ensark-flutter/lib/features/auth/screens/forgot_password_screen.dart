import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/routing/app_router.dart';
import '../../../repositories/auth_repository.dart';
import '../widgets/neon_button.dart';
import '../widgets/neon_text_field.dart';

class ForgotPasswordScreen extends ConsumerStatefulWidget {
  const ForgotPasswordScreen({super.key});

  @override
  ConsumerState<ForgotPasswordScreen> createState() => _ForgotPasswordScreenState();
}

class _ForgotPasswordScreenState extends ConsumerState<ForgotPasswordScreen> {
  final _emailController = TextEditingController();
  bool _isLoading = false;

  void _handleSubmit() async {
    final messenger = ScaffoldMessenger.of(context);
    final router = GoRouter.of(context);
    setState(() => _isLoading = true);
    try {
      await ref.read(authRepositoryProvider).forgotPassword(_emailController.text.trim());
      if (mounted) {
        messenger.showSnackBar(const SnackBar(content: Text('Reset link sent to your email.')));
        router.push(AppRoutes.resetPassword);
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
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Forgot Password', style: Theme.of(context).textTheme.displayLarge),
            const SizedBox(height: 16),
            const Text('Enter your email to receive a reset link.', style: TextStyle(color: AppColors.textSecondary)),
            const SizedBox(height: 48),
            NeonTextField(label: 'Email', controller: _emailController, keyboardType: TextInputType.emailAddress),
            const SizedBox(height: 32),
            NeonButton(text: 'SEND LINK', isLoading: _isLoading, onPressed: _handleSubmit),
          ],
        ),
      ),
    );
  }
}
