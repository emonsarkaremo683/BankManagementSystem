import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../models/transaction/transaction_models.dart';
import '../../../providers/transfer_provider.dart';
import '../../auth/widgets/neon_button.dart';
import '../../auth/widgets/neon_text_field.dart';

class OtpVerificationScreen extends ConsumerStatefulWidget {
  final int otpReferenceId;
  final String maskedEmail;

  const OtpVerificationScreen({
    super.key,
    required this.otpReferenceId,
    required this.maskedEmail,
  });

  @override
  ConsumerState<OtpVerificationScreen> createState() => _OtpVerificationScreenState();
}

class _OtpVerificationScreenState extends ConsumerState<OtpVerificationScreen> {
  final _codeController = TextEditingController();

  void _handleVerify() async {
    if (_codeController.text.length == 6) {
      final messenger = ScaffoldMessenger.of(context);
      final router = GoRouter.of(context);

      final request = OtpVerifyRequest(
        otpReferenceId: widget.otpReferenceId,
        otpCode: _codeController.text,
      );
      
      try {
        final response = await ref.read(transferProvider.notifier).verify(request);
        if (response != null && mounted) {
          messenger.showSnackBar(const SnackBar(
            content: Text('Transaction Successful!'),
            backgroundColor: Colors.green,
          ));
          router.pop(); // Back to transfer or dashboard
          router.pop(); 
        } else if (mounted) {
          final error = ref.read(transferProvider).error;
          messenger.showSnackBar(SnackBar(
            content: Text('Verification failed: ${error?.toString() ?? 'Invalid code'}'),
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
    } else {
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Please enter 6-digit OTP')));
    }
  }

  @override
  Widget build(BuildContext context) {
    final transferState = ref.watch(transferProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Verify Transaction'), backgroundColor: Colors.transparent),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Verification Required', style: Theme.of(context).textTheme.displayLarge?.copyWith(fontSize: 24)),
            const SizedBox(height: 16),
            Text(
              'An OTP has been sent to ${widget.maskedEmail}. Please enter it below to complete the transfer.',
              style: const TextStyle(color: AppColors.textSecondary),
            ),
            const SizedBox(height: 48),
            NeonTextField(
              label: '6-Digit OTP',
              controller: _codeController,
              keyboardType: TextInputType.number,
            ),
            const SizedBox(height: 32),
            NeonButton(
              text: 'VERIFY & SEND',
              isLoading: transferState.isLoading,
              onPressed: _handleVerify,
            ),
          ],
        ),
      ),
    );
  }
}
