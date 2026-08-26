import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../providers/auth_provider.dart';
import '../widgets/neon_button.dart';

class BiometricSetupScreen extends ConsumerWidget {
  final String email;
  final String password;

  const BiometricSetupScreen({
    super.key,
    required this.email,
    required this.password,
  });

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(32.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.fingerprint, size: 80, color: AppColors.neonCyan),
            const SizedBox(height: 32),
            Text(
              'Enable Biometric Login',
              style: Theme.of(context).textTheme.displayLarge?.copyWith(fontSize: 24),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 16),
            const Text(
              'Use your fingerprint to sign in quickly and securely next time.',
              style: TextStyle(color: AppColors.textSecondary),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 48),
            NeonButton(
              text: 'ENABLE NOW',
              onPressed: () async {
                await ref.read(authProvider.notifier).enableBiometrics(email, password);
                if (context.mounted) {
                  context.pop();
                }
              },
            ),
            const SizedBox(height: 16),
            TextButton(
              onPressed: () => context.pop(),
              child: const Text('NOT NOW', style: TextStyle(color: AppColors.textSecondary)),
            ),
          ],
        ),
      ),
    );
  }
}
