// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'currency_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$currenciesHash() => r'59f80d25898624ec3d719f95e3155f7805c9d462';

/// Copied from Dart SDK
class _SystemHash {
  _SystemHash._();

  static int combine(int hash, int value) {
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + value);
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + ((0x0007ffff & hash) << 10));
    return hash ^ (hash >> 6);
  }

  static int finish(int hash) {
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + ((0x03ffffff & hash) << 3));
    // ignore: parameter_assignments
    hash = hash ^ (hash >> 11);
    return 0x1fffffff & (hash + ((0x00003fff & hash) << 15));
  }
}

/// See also [currencies].
@ProviderFor(currencies)
const currenciesProvider = CurrenciesFamily();

/// See also [currencies].
class CurrenciesFamily extends Family<AsyncValue<List<CurrencyResponse>>> {
  /// See also [currencies].
  const CurrenciesFamily();

  /// See also [currencies].
  CurrenciesProvider call({String base = 'BDT'}) {
    return CurrenciesProvider(base: base);
  }

  @override
  CurrenciesProvider getProviderOverride(
    covariant CurrenciesProvider provider,
  ) {
    return call(base: provider.base);
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'currenciesProvider';
}

/// See also [currencies].
class CurrenciesProvider
    extends AutoDisposeFutureProvider<List<CurrencyResponse>> {
  /// See also [currencies].
  CurrenciesProvider({String base = 'BDT'})
    : this._internal(
        (ref) => currencies(ref as CurrenciesRef, base: base),
        from: currenciesProvider,
        name: r'currenciesProvider',
        debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
            ? null
            : _$currenciesHash,
        dependencies: CurrenciesFamily._dependencies,
        allTransitiveDependencies: CurrenciesFamily._allTransitiveDependencies,
        base: base,
      );

  CurrenciesProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.base,
  }) : super.internal();

  final String base;

  @override
  Override overrideWith(
    FutureOr<List<CurrencyResponse>> Function(CurrenciesRef provider) create,
  ) {
    return ProviderOverride(
      origin: this,
      override: CurrenciesProvider._internal(
        (ref) => create(ref as CurrenciesRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        base: base,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<List<CurrencyResponse>> createElement() {
    return _CurrenciesProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is CurrenciesProvider && other.base == base;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, base.hashCode);

    return _SystemHash.finish(hash);
  }
}

@Deprecated('Will be removed in 3.0. Use Ref instead')
// ignore: unused_element
mixin CurrenciesRef on AutoDisposeFutureProviderRef<List<CurrencyResponse>> {
  /// The parameter `base` of this provider.
  String get base;
}

class _CurrenciesProviderElement
    extends AutoDisposeFutureProviderElement<List<CurrencyResponse>>
    with CurrenciesRef {
  _CurrenciesProviderElement(super.provider);

  @override
  String get base => (origin as CurrenciesProvider).base;
}

String _$currencyConverterHash() => r'6a70fd7f42b2996a704c9fdfcd608992abebf484';

/// See also [CurrencyConverter].
@ProviderFor(CurrencyConverter)
final currencyConverterProvider =
    AutoDisposeNotifierProvider<CurrencyConverter, double>.internal(
      CurrencyConverter.new,
      name: r'currencyConverterProvider',
      debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
          ? null
          : _$currencyConverterHash,
      dependencies: null,
      allTransitiveDependencies: null,
    );

typedef _$CurrencyConverter = AutoDisposeNotifier<double>;
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member, deprecated_member_use_from_same_package
