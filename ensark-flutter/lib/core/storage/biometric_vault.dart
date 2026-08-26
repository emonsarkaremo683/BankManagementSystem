import 'package:flutter/foundation.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:local_auth/local_auth.dart';

class BiometricVault {
  final FlutterSecureStorage _storage;
  final LocalAuthentication _auth;

  BiometricVault({
    FlutterSecureStorage? storage,
    LocalAuthentication? auth,
  })  : _storage = storage ?? const FlutterSecureStorage(),
        _auth = auth ?? LocalAuthentication();

  static const _emailKey = 'bio_email';
  static const _passwordKey = 'bio_password';
  static const _isEnabledKey = 'bio_enabled';

  Future<bool> canCheckBiometrics() async {
    if (kIsWeb) return false; // Biometrics not supported on Web for this app
    try {
      final bool canAuthenticateWithBiometrics = await _auth.canCheckBiometrics;
      final bool canAuthenticate = canAuthenticateWithBiometrics || await _auth.isDeviceSupported();
      return canAuthenticate;
    } catch (e) {
      return false;
    }
  }

  Future<bool> authenticate() async {
    if (kIsWeb) return false;
    try {
      return await _auth.authenticate(
        localizedReason: 'Please authenticate to sign in',
        options: const AuthenticationOptions(
          stickyAuth: true,
          biometricOnly: true,
        ),
      );
    } catch (e) {
      return false;
    }
  }

  Future<void> enableBiometrics(String email, String password) async {
    await _storage.write(key: _isEnabledKey, value: 'true');
    await _storage.write(key: _emailKey, value: email);
    await _storage.write(key: _passwordKey, value: password);
  }

  Future<void> disableBiometrics() async {
    await _storage.write(key: _isEnabledKey, value: 'false');
    await _storage.delete(key: _emailKey);
    await _storage.delete(key: _passwordKey);
  }

  Future<bool> isEnabled() async {
    final value = await _storage.read(key: _isEnabledKey);
    return value == 'true';
  }

  Future<Map<String, String>?> getCredentials() async {
    final isBioEnabled = await isEnabled();
    if (!isBioEnabled) return null;

    final authenticated = await authenticate();
    if (!authenticated) return null;

    final email = await _storage.read(key: _emailKey);
    final password = await _storage.read(key: _passwordKey);

    if (email != null && password != null) {
      return {'email': email, 'password': password};
    }
    return null;
  }
}
