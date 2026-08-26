import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:qr_flutter/qr_flutter.dart';
import '../../../models/auth/auth_models.dart';
import '../../../models/customer/customer_models.dart';
import '../../../repositories/auth_repository.dart';
import '../widgets/neon_button.dart';
import '../widgets/neon_text_field.dart';

class MfaSetupScreen extends ConsumerStatefulWidget {
  final String email;
  const MfaSetupScreen({super.key, required this.email});

  @override
  ConsumerState<MfaSetupScreen> createState() => _MfaSetupScreenState();
}

class _MfaSetupScreenState extends ConsumerState<MfaSetupScreen> {
  LoginResponse<CustomerResponse>? _mfaData;
  final _codeController = TextEditingController();
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _loadMfaSetup();
  }

  void _loadMfaSetup() async {
    setState(() => _isLoading = true);
    try {
      final data = await ref.read(authRepositoryProvider).setupMfa(widget.email);
      setState(() => _mfaData = data);
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(e.toString())));
      }
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  void _confirmMfa() async {
    if (_codeController.text.length == 6) {
      setState(() => _isLoading = true);
      try {
        await ref.read(authRepositoryProvider).confirmMfa(widget.email, _codeController.text);
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('MFA setup confirmed!')));
          Navigator.pop(context);
        }
      } catch (e) {
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(e.toString())));
        }
      } finally {
        if (mounted) setState(() => _isLoading = false);
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Setup MFA'), backgroundColor: Colors.transparent),
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),
          child: Column(
            children: [
              if (_mfaData?.mfaQrCode != null) ...[
                const Text('Scan this QR code with your authenticator app', textAlign: TextAlign.center),
                const SizedBox(height: 20),
                QrImageView(
                  data: _mfaData!.mfaQrCode!,
                  version: QrVersions.auto,
                  size: 200.0,
                  backgroundColor: Colors.white,
                ),
                const SizedBox(height: 20),
                Text('Secret: ${_mfaData!.mfaSecret ?? ''}', style: const TextStyle(fontFamily: 'monospace')),
                const SizedBox(height: 40),
                NeonTextField(label: 'Verification Code', controller: _codeController, keyboardType: TextInputType.number),
                const SizedBox(height: 20),
                NeonButton(text: 'CONFIRM MFA', isLoading: _isLoading, onPressed: _confirmMfa),
              ] else if (_isLoading)
                const CircularProgressIndicator()
              else
                const Text('Failed to load MFA setup.'),
            ],
          ),
        ),
      ),
    );
  }
}
