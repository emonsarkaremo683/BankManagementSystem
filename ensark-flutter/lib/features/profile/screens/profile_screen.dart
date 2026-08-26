import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../core/routing/app_router.dart';
import '../../../providers/auth_provider.dart';
import '../../../providers/core_providers.dart';

class ProfileScreen extends ConsumerWidget {
  const ProfileScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final user = ref.watch(authProvider).value?.user;
    final baseUrl = ref.watch(baseUrlProvider);
    final bioVault = ref.watch(biometricVaultProvider);

    String? getImageUrl(String? path) {
      if (path == null) return null;
      if (path.startsWith('http')) return path;
      // Remove leading slash if present
      final cleanPath = path.startsWith('/') ? path.substring(1) : path;
      return '${baseUrl}images/$cleanPath';
    }

    return Scaffold(
      appBar: AppBar(title: const Text('Profile'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Column(
          children: [
            Center(
              child: Container(
                width: 120,
                height: 120,
                decoration: BoxDecoration(
                  shape: BoxShape.circle,
                  color: AppColors.darkSurface,
                  boxShadow: AppShadows.embossed,
                  border: Border.all(color: AppColors.neonCyan, width: 2),
                ),
                child: ClipOval(
                  child: user?.profile != null
                      ? Image.network(
                          getImageUrl(user!.profile!)!,
                          fit: BoxFit.cover,
                          errorBuilder: (context, error, stackTrace) => const Icon(Icons.person, size: 60, color: AppColors.textSecondary),
                        )
                      : const Icon(Icons.person, size: 60, color: AppColors.textSecondary),
                ),
              ),
            ),
            const SizedBox(height: 24),
            Text(user?.name ?? 'User Name', style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold)),
            Text(user?.email ?? 'email@example.com', style: const TextStyle(color: AppColors.textSecondary)),
            const SizedBox(height: 40),
            _ProfileMenuItem(
              icon: Icons.person_outline,
              title: 'Edit Profile',
              onTap: () => context.push(AppRoutes.editProfile),
            ),
            _ProfileMenuItem(
              icon: Icons.lock_outline,
              title: 'Change Password',
              onTap: () => context.push(AppRoutes.changePassword),
            ),
            FutureBuilder<bool>(
              future: bioVault.isEnabled(),
              builder: (context, snapshot) {
                final isEnabled = snapshot.data ?? false;
                return _ProfileMenuItem(
                  icon: Icons.fingerprint,
                  title: 'Biometric Login',
                  subtitle: isEnabled ? 'Enabled' : 'Disabled (Log in again to enable)',
                  onTap: () async {
                    if (isEnabled) {
                      await ref.read(authProvider.notifier).disableBiometrics();
                      if (context.mounted) {
                        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Biometrics disabled')));
                      }
                    } else {
                      showDialog(
                        context: context,
                        builder: (context) => AlertDialog(
                          backgroundColor: AppColors.darkSurface,
                          title: const Text('Enable Biometrics', style: TextStyle(color: AppColors.neonCyan)),
                          content: const Text('To enable biometric login, please log out and sign in manually once. You will be prompted to enable it after a successful login.'),
                          actions: [
                            TextButton(
                              onPressed: () => Navigator.pop(context),
                              child: const Text('OK'),
                            ),
                          ],
                        ),
                      );
                    }
                  },
                );
              },
            ),
            _ProfileMenuItem(
              icon: Icons.verified_user_outlined,
              title: 'KYC Verification',
              subtitle: user?.kycStatus?.name ?? 'NOT UPLOADED',
              onTap: () => context.push(AppRoutes.kycUpload),
            ),
            const SizedBox(height: 40),
            _ProfileMenuItem(
              icon: Icons.logout,
              title: 'Logout',
              color: Colors.redAccent,
              onTap: () => ref.read(authProvider.notifier).logout(),
            ),
          ],
        ),
      ),
    );
  }
}

class _ProfileMenuItem extends StatelessWidget {
  final IconData icon;
  final String title;
  final String? subtitle;
  final VoidCallback onTap;
  final Color? color;

  const _ProfileMenuItem({
    required this.icon,
    required this.title,
    this.subtitle,
    required this.onTap,
    this.color,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      decoration: BoxDecoration(
        color: AppColors.darkBackground,
        borderRadius: BorderRadius.circular(12),
        boxShadow: AppShadows.debossed,
      ),
      child: ListTile(
        leading: Icon(icon, color: color ?? AppColors.neonCyan),
        title: Text(title, style: TextStyle(color: color)),
        subtitle: subtitle != null ? Text(subtitle!, style: const TextStyle(color: AppColors.textSecondary, fontSize: 12)) : null,
        trailing: const Icon(Icons.chevron_right, color: AppColors.textSecondary),
        onTap: onTap,
      ),
    );
  }
}
