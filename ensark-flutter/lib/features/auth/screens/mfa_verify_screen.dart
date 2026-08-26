import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../providers/auth_provider.dart';
import '../widgets/neon_button.dart';
import '../widgets/neon_text_field.dart';

class MfaVerifyScreen extends ConsumerStatefulWidget {
  final String email;
  const MfaVerifyScreen({super.key, required this.email});

  @override
  ConsumerState<MfaVerifyScreen> createState() => _MfaVerifyScreenState();
}

class _MfaVerifyScreenState extends ConsumerState<MfaVerifyScreen> {
  final _codeController = TextEditingController();

  void _handleVerify() async {
    if (_codeController.text.length == 6) {
      final router = GoRouter.of(context);
      
      await ref.read(authProvider.notifier).verifyMfa(widget.email, _codeController.text);
      
      if (!mounted) return;

      if (!ref.read(authProvider).hasError) {
        router.pop(); 
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final authState = ref.watch(authProvider);

    return Scaffold(
      appBar: AppBar(backgroundColor: Colors.transparent, elevation: 0),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Verify MFA',
              style: Theme.of(context).textTheme.displayLarge,
            ),
            const SizedBox(height: 16),
            const Text(
              'Enter the 6-digit code from your authenticator app.',
              style: TextStyle(color: AppColors.textSecondary),
            ),
            const SizedBox(height: 48),
            NeonTextField(
              label: 'Verification Code',
              controller: _codeController,
              keyboardType: TextInputType.number,
            ),
            const SizedBox(height: 32),
            NeonButton(
              text: 'VERIFY',
              isLoading: authState.isLoading,
              onPressed: _handleVerify,
            ),
          ],
        ),
      ),
    );
  }
}
