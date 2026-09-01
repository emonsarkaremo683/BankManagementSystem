import 'package:dio/dio.dart';

class DioClient {
  final String baseUrl;
  late final Dio dio;

  DioClient({required this.baseUrl}) {
    dio = Dio(
      BaseOptions(
        baseUrl: baseUrl,
        connectTimeout: const Duration(seconds: 25),
        receiveTimeout: const Duration(seconds: 25),
        contentType: 'application/json',
      ),
    );
    // LogInterceptor moved out of here to be controlled in the provider
  }
}
