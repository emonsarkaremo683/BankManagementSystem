import 'package:dio/dio.dart';
import 'package:ensarkbank_flutter/providers/dashboard_provider.dart';
import 'package:ensarkbank_flutter/providers/notification_provider.dart';
import 'package:ensarkbank_flutter/providers/transfer_provider.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/auth/auth_models.dart';
import '../models/customer/customer_models.dart';
import '../repositories/auth_repository.dart';
import '../repositories/customer_repository.dart';
import 'core_providers.dart';

part 'auth_provider.freezed.dart';
part 'auth_provider.g.dart';

@freezed
class AuthState with _$AuthState {
  const factory AuthState({
    CustomerResponse? user,
    @Default(false) bool isLoading,
    String? error,
    @Default(false) bool mfaRequired,
    String? mfaEmail,
  }) = _AuthState;
}

@Riverpod(keepAlive: true)
class Auth extends _$Auth {
  @override
  FutureOr<AuthState> build() async {
    final vault = ref.watch(secureVaultProvider);
    final token = await vault.getToken();
    
    if (token != null) {
      try {
        final validation = await ref.read(authRepositoryProvider).validateToken();
        if (validation.valid && validation.email != null) {
          if (validation.role != 'CUSTOMER') {
             await vault.clearAll();
             return const AuthState();
          }
          final user = await ref.read(customerRepositoryProvider).findByEmail(validation.email!);
          return AuthState(user: user);
        }
      } catch (e) {
        await vault.clearAll();
      }
    }
    
    return const AuthState();
  }

  Future<void> login(String email, String password) async {
    state = const AsyncValue.loading();
    try {
      final repository = ref.read(authRepositoryProvider);
      final response = await repository.login(LoginRequest(email: email, password: password));
      
      if (response.mfaRequired) {
        state = AsyncValue.data(AuthState(mfaRequired: true, mfaEmail: email));
        return;
      }

      if (response.token != null) {
        if (response.user?.role != Role.CUSTOMER) {
          throw Exception('Unauthorized: Only customers can log in to this app.');
        }

        final vault = ref.read(secureVaultProvider);
        await vault.saveToken(response.token!);
        if (response.refreshToken != null) {
          await vault.saveRefreshToken(response.refreshToken!);
        }
        
        state = AsyncValue.data(AuthState(user: response.user));

        // Invalidate providers to force a refetch with the new token
        ref.invalidate(dashboardProvider);
        ref.invalidate(notificationsProvider);
        ref.invalidate(unreadCountProvider);
        ref.invalidate(beneficiariesProvider);
      }
    } catch (e) {
      String errorMessage = e.toString();
      if (e is DioException && e.response?.data != null) {
        final data = e.response!.data;
        if (data is Map && data.containsKey('message')) {
          errorMessage = data['message'];
        }
      }
      state = AsyncValue.error(errorMessage, StackTrace.current);
    }
  }

  Future<void> verifyMfa(String email, String totpCode) async {
    state = const AsyncValue.loading();
    try {
      final repository = ref.read(authRepositoryProvider);
      final response = await repository.verifyMfa(email, totpCode);
      
      if (response.token != null) {
        if (response.user?.role != Role.CUSTOMER) {
          throw Exception('Unauthorized: Only customers can log in to this app.');
        }

        final vault = ref.read(secureVaultProvider);
        await vault.saveToken(response.token!);
        if (response.refreshToken != null) {
          await vault.saveRefreshToken(response.refreshToken!);
        }
        
        state = AsyncValue.data(AuthState(user: response.user));

        // Invalidate providers to force a refetch with the new token
        ref.invalidate(dashboardProvider);
        ref.invalidate(notificationsProvider);
        ref.invalidate(unreadCountProvider);
        ref.invalidate(beneficiariesProvider);
      }
    } catch (e) {
      String errorMessage = e.toString();
      if (e is DioException && e.response?.data != null) {
        final data = e.response!.data;
        if (data is Map && data.containsKey('message')) {
          errorMessage = data['message'];
        }
      }
      state = AsyncValue.error(errorMessage, StackTrace.current);
    }
  }

  Future<void> logout() async {
    try {
      await ref.read(authRepositoryProvider).logout();
    } catch (_) {}
    await ref.read(secureVaultProvider).clearAll();
    state = const AsyncValue.data(AuthState());
  }

  Future<void> register(CustomerRequest request, Map<String, List<int>> files) async {
    state = const AsyncValue.loading();
    try {
      await ref.read(authRepositoryProvider).register(request, files);
      // After registration, usually login or redirect to verification
      state = const AsyncValue.data(AuthState());
    } catch (e) {
      String errorMessage = e.toString();
      if (e is DioException && e.response?.data != null) {
        final data = e.response!.data;
        if (data is Map && data.containsKey('message')) {
          errorMessage = data['message'];
        }
      }
      state = AsyncValue.error(errorMessage, StackTrace.current);
    }
  }

  Future<void> biometricLogin() async {
    final bioVault = ref.read(biometricVaultProvider);
    final creds = await bioVault.getCredentials();
    if (creds != null) {
      await login(creds['email']!, creds['password']!);
    }
  }

  Future<void> enableBiometrics(String email, String password) async {
    await ref.read(biometricVaultProvider).enableBiometrics(email, password);
  }

  Future<void> disableBiometrics() async {
    await ref.read(biometricVaultProvider).disableBiometrics();
  }

  Future<void> refreshUser() async {
    final currentUser = state.value?.user;
    if (currentUser != null) {
      try {
        final updatedUser = await ref.read(customerRepositoryProvider).findByEmail(currentUser.email);
        state = AsyncValue.data(state.value!.copyWith(user: updatedUser));
      } catch (_) {}
    }
  }
}
