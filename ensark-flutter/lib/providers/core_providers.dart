import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../core/api/dio_client.dart';
import '../core/api/auth_interceptor.dart';
import '../core/storage/secure_vault.dart';
import '../core/storage/biometric_vault.dart';

part 'core_providers.g.dart';

@Riverpod(keepAlive: true)
class ServerIp extends _$ServerIp {
  static const String defaultIp = '192.168.0.104';

  @override
  FutureOr<String> build() async {
    final vault = ref.watch(secureVaultProvider);
    final savedIp = await vault.getServerIp();
    if (savedIp != null && savedIp.trim().isNotEmpty) {
      return savedIp.trim();
    }
    return defaultIp;
  }

  Future<void> setIp(String newIp) async {
    final trimmed = newIp.trim();
    final vault = ref.read(secureVaultProvider);
    await vault.saveServerIp(trimmed);
    state = AsyncValue.data(trimmed.isEmpty ? defaultIp : trimmed);
  }
}

String formatBaseUrl(String raw) {
  var input = raw.trim();
  if (input.isEmpty) return 'http://192.168.0.102:8085/';

  if (!input.startsWith('http://') && !input.startsWith('https://')) {
    input = 'http://$input';
  }

  final uri = Uri.tryParse(input);
  if (uri != null) {
    if (!uri.hasPort && !input.contains(':', input.indexOf('://') + 3)) {
      input = '$input:8085';
    }
  }

  if (!input.endsWith('/')) {
    input = '$input/';
  }

  return input;
}

@riverpod
String baseUrl(Ref ref) {
  const String envUrl = String.fromEnvironment('BASE_URL');
  if (envUrl.isNotEmpty) return envUrl;

  final ipAsync = ref.watch(serverIpProvider);
  final rawIp = ipAsync.value ?? ServerIp.defaultIp;

  return formatBaseUrl(rawIp);
}

@riverpod
SecureVault secureVault(Ref ref) {
  return SecureVault();
}

@riverpod
BiometricVault biometricVault(Ref ref) {
  return BiometricVault();
}

@riverpod
Dio dio(Ref ref) {
  final baseUrl = ref.watch(baseUrlProvider);
  final vault = ref.watch(secureVaultProvider);
  
  final dioClient = DioClient(baseUrl: baseUrl);
  
  dioClient.dio.interceptors.add(AuthInterceptor(vault, dioClient.dio, ref));
  
  dioClient.dio.interceptors.add(LogInterceptor(
    requestHeader: true, // Enable header logging
    requestBody: true,
    responseHeader: true,
    responseBody: true,
  ));
  
  return dioClient.dio;
}
