import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../providers/core_providers.dart';

part 'kyc_repository.g.dart';

class KycRepository {
  final Dio _dio;

  KycRepository(this._dio);

  Future<void> uploadMyDocuments(Map<String, List<int>> multipartFiles) async {
    final formData = FormData();
    for (var entry in multipartFiles.entries) {
      formData.files.add(MapEntry(
        entry.key,
        MultipartFile.fromBytes(entry.value, filename: '${entry.key}.jpg'),
      ));
    }
    await _dio.post('api/kyc/my-documents', data: formData);
  }

  Future<Map<String, dynamic>> getMyKycStatus() async {
    final response = await _dio.get('api/kyc/my-status');
    return Map<String, dynamic>.from(response.data);
  }
}

@riverpod
KycRepository kycRepository(Ref ref) {
  return KycRepository(ref.watch(dioProvider));
}
