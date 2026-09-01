import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SecureVault {
  final FlutterSecureStorage _storage;

  SecureVault([this._storage = const FlutterSecureStorage()]);

  static const _tokenKey = 'auth_token';
  static const _refreshTokenKey = 'refresh_token';
  static const _serverIpKey = 'server_ip';

  Future<void> saveToken(String token) async {
    await _storage.write(key: _tokenKey, value: token);
  }

  Future<String?> getToken() async {
    return await _storage.read(key: _tokenKey);
  }

  Future<void> saveRefreshToken(String token) async {
    await _storage.write(key: _refreshTokenKey, value: token);
  }

  Future<String?> getRefreshToken() async {
    return await _storage.read(key: _refreshTokenKey);
  }

  Future<void> saveServerIp(String ip) async {
    await _storage.write(key: _serverIpKey, value: ip);
  }

  Future<String?> getServerIp() async {
    return await _storage.read(key: _serverIpKey);
  }

  Future<void> clearAll() async {
    await _storage.delete(key: _tokenKey);
    await _storage.delete(key: _refreshTokenKey);
  }
}
