import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../core/routing/app_router.dart';
import '../../../providers/auth_provider.dart';
import '../../../providers/core_providers.dart';
import '../widgets/neon_button.dart';
import '../widgets/neon_text_field.dart';

class LoginScreen extends ConsumerStatefulWidget {
  const LoginScreen({super.key});

  @override
  ConsumerState<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends ConsumerState<LoginScreen> {
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _ipController = TextEditingController();
  final _formKey = GlobalKey<FormState>();

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    _ipController.dispose();
    super.dispose();
  }

  void _saveServerIp() async {
    final newIp = _ipController.text.trim();
    if (newIp.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Please enter a valid IP address or Host')),
      );
      return;
    }

    await ref.read(serverIpProvider.notifier).setIp(newIp);
    final currentBaseUrl = ref.read(baseUrlProvider);

    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Server URL set to: $currentBaseUrl'),
          backgroundColor: AppColors.neonCyan,
        ),
      );
    }
  }

  void _handleLogin() async {
    if (_formKey.currentState!.validate()) {
      final email = _emailController.text.trim();
      final password = _passwordController.text;
      
      final router = GoRouter.of(context);
      
      await ref.read(authProvider.notifier).login(email, password);
      
      if (!mounted) return;

      final state = ref.read(authProvider).value;
      if (state?.user != null) {
        final bioVault = ref.read(biometricVaultProvider);
        if (!(await bioVault.isEnabled()) && await bioVault.canCheckBiometrics()) {
          router.push(AppRoutes.biometricSetup, extra: {'email': email, 'password': password});
        }
      }
    }
  }

  void _handleBiometricLogin() async {
    final bioVault = ref.read(biometricVaultProvider);
    final canCheck = await bioVault.canCheckBiometrics();
    
    if (!canCheck) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Biometrics not supported on this device')),
        );
      }
      return;
    }

    if (await bioVault.isEnabled()) {
      await ref.read(authProvider.notifier).biometricLogin();
    } else {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Please log in with password first to enable biometrics')),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final authState = ref.watch(authProvider);

    ref.listen(authProvider, (previous, next) {
      if (!mounted) return;
      
      next.whenData((state) {
        if (state.mfaRequired && state.mfaEmail != null) {
          context.push(AppRoutes.mfaVerify, extra: state.mfaEmail);
        }
      });
      
      if (next.hasError) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(next.error.toString())),
        );
      }
    });

    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 40),
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  'Welcome Back',
                  style: Theme.of(context).textTheme.displayLarge,
                ),
                const SizedBox(height: 8),
                const Text(
                  'Sign in to your EnsarkBank account',
                  style: TextStyle(color: AppColors.textSecondary),
                ),
                const SizedBox(height: 24),
                Consumer(
                  builder: (context, ref, child) {
                    final serverIpAsync = ref.watch(serverIpProvider);
                    final currentBaseUrl = ref.watch(baseUrlProvider);

                    serverIpAsync.whenData((ip) {
                      if (_ipController.text.isEmpty) {
                        _ipController.text = ip;
                      }
                    });

                    return Container(
                      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                      decoration: BoxDecoration(
                        color: AppColors.darkBackground,
                        borderRadius: BorderRadius.circular(16),
                        boxShadow: AppShadows.debossed,
                        border: Border.all(color: AppColors.neonCyan.withValues(alpha: 0.3)),
                      ),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Row(
                            children: [
                              const Icon(Icons.lan, color: AppColors.neonCyan, size: 20),
                              const SizedBox(width: 8),
                              Expanded(
                                child: TextField(
                                  controller: _ipController,
                                  style: const TextStyle(color: Colors.white, fontSize: 13),
                                  keyboardType: TextInputType.url,
                                  decoration: const InputDecoration(
                                    hintText: 'Server IP / Host (e.g. 192.168.0.104)',
                                    hintStyle: TextStyle(color: AppColors.textSecondary, fontSize: 12),
                                    border: InputBorder.none,
                                    isDense: true,
                                  ),
                                ),
                              ),
                              IconButton(
                                icon: const Icon(Icons.save, color: AppColors.neonCyan),
                                tooltip: 'Save Server IP',
                                onPressed: _saveServerIp,
                              ),
                            ],
                          ),
                          Padding(
                            padding: const EdgeInsets.only(left: 28, bottom: 4),
                            child: Text(
                              'Base URL: $currentBaseUrl',
                              style: const TextStyle(
                                color: AppColors.textSecondary,
                                fontSize: 11,
                                fontStyle: FontStyle.italic,
                              ),
                            ),
                          ),
                        ],
                      ),
                    );
                  },
                ),
                const SizedBox(height: 32),
                NeonTextField(
                  label: 'Email',
                  controller: _emailController,
                  keyboardType: TextInputType.emailAddress,
                  validator: (value) {
                    if (value == null || value.isEmpty) return 'Email is required';
                    final emailRegex = RegExp(r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$');
                    if (!emailRegex.hasMatch(value)) return 'Enter a valid email address';
                    return null;
                  },
                ),
                const SizedBox(height: 24),
                NeonTextField(
                  label: 'Password',
                  controller: _passwordController,
                  isPassword: true,
                  validator: (value) => value == null || value.isEmpty ? 'Password is required' : null,
                ),
                const SizedBox(height: 16),
                Align(
                  alignment: Alignment.centerRight,
                  child: TextButton(
                    onPressed: () => context.push(AppRoutes.forgotPassword),
                    child: const Text('Forgot Password?', style: TextStyle(color: AppColors.neonCyan)),
                  ),
                ),
                const SizedBox(height: 32),
                Row(
                  children: [
                    Expanded(
                      child: NeonButton(
                        text: 'LOGIN',
                        isLoading: authState.isLoading,
                        onPressed: _handleLogin,
                      ),
                    ),
                    if (!kIsWeb) ...[
                      const SizedBox(width: 20),
                      GestureDetector(
                        onTap: authState.isLoading ? null : _handleBiometricLogin,
                        child: Container(
                          width: 55,
                          height: 55,
                          decoration: BoxDecoration(
                            color: AppColors.darkBackground,
                            shape: BoxShape.circle,
                            boxShadow: AppShadows.embossed,
                          ),
                          child: const Icon(Icons.fingerprint, color: AppColors.neonCyan, size: 30),
                        ),
                      ),
                    ],
                  ],
                ),
                const SizedBox(height: 40),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Text('Don\'t have an account? ', style: TextStyle(color: AppColors.textSecondary)),
                    GestureDetector(
                      onTap: () => context.push(AppRoutes.register),
                      child: const Text(
                        'Register',
                        style: TextStyle(color: AppColors.neonPink, fontWeight: FontWeight.bold),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
