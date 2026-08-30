import 'dart:io';
import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../core/api/dio_client.dart';
import '../core/api/auth_interceptor.dart';
import '../core/storage/secure_vault.dart';
import '../core/storage/biometric_vault.dart';

part 'core_providers.g.dart';

@riverpod
String baseUrl(Ref ref) {
  const String envUrl = String.fromEnvironment('BASE_URL');
  if (envUrl.isNotEmpty) return envUrl;

  if (kIsWeb) return 'http://localhost:8085/';
  try {
    if (Platform.isAndroid) return 'http://192.168.0.104:8085/';
  } catch (_) {}
  
  return 'http://localhost:8085/';
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
