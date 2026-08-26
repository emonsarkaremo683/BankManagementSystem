// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'reference_data_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$divisionsHash() => r'dced510a713744ebb7257f1850ae6e7d7114f19b';

/// See also [divisions].
@ProviderFor(divisions)
final divisionsProvider =
    AutoDisposeFutureProvider<List<DivisionResponse>>.internal(
      divisions,
      name: r'divisionsProvider',
      debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
          ? null
          : _$divisionsHash,
      dependencies: null,
      allTransitiveDependencies: null,
    );

@Deprecated('Will be removed in 3.0. Use Ref instead')
// ignore: unused_element
typedef DivisionsRef = AutoDisposeFutureProviderRef<List<DivisionResponse>>;
String _$districtsHash() => r'b178b2bf081b39c86c582b03a9757a651bf7305a';

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

/// See also [districts].
@ProviderFor(districts)
const districtsProvider = DistrictsFamily();

/// See also [districts].
class DistrictsFamily extends Family<AsyncValue<List<DistrictResponse>>> {
  /// See also [districts].
  const DistrictsFamily();

  /// See also [districts].
  DistrictsProvider call(int divisionId) {
    return DistrictsProvider(divisionId);
  }

  @override
  DistrictsProvider getProviderOverride(covariant DistrictsProvider provider) {
    return call(provider.divisionId);
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'districtsProvider';
}

/// See also [districts].
class DistrictsProvider
    extends AutoDisposeFutureProvider<List<DistrictResponse>> {
  /// See also [districts].
  DistrictsProvider(int divisionId)
    : this._internal(
        (ref) => districts(ref as DistrictsRef, divisionId),
        from: districtsProvider,
        name: r'districtsProvider',
        debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
            ? null
            : _$districtsHash,
        dependencies: DistrictsFamily._dependencies,
        allTransitiveDependencies: DistrictsFamily._allTransitiveDependencies,
        divisionId: divisionId,
      );

  DistrictsProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.divisionId,
  }) : super.internal();

  final int divisionId;

  @override
  Override overrideWith(
    FutureOr<List<DistrictResponse>> Function(DistrictsRef provider) create,
  ) {
    return ProviderOverride(
      origin: this,
      override: DistrictsProvider._internal(
        (ref) => create(ref as DistrictsRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        divisionId: divisionId,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<List<DistrictResponse>> createElement() {
    return _DistrictsProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is DistrictsProvider && other.divisionId == divisionId;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, divisionId.hashCode);

    return _SystemHash.finish(hash);
  }
}

@Deprecated('Will be removed in 3.0. Use Ref instead')
// ignore: unused_element
mixin DistrictsRef on AutoDisposeFutureProviderRef<List<DistrictResponse>> {
  /// The parameter `divisionId` of this provider.
  int get divisionId;
}

class _DistrictsProviderElement
    extends AutoDisposeFutureProviderElement<List<DistrictResponse>>
    with DistrictsRef {
  _DistrictsProviderElement(super.provider);

  @override
  int get divisionId => (origin as DistrictsProvider).divisionId;
}

String _$policeStationsHash() => r'78b2fa426463fefc5e0e12c8b72bf4e0c4541362';

/// See also [policeStations].
@ProviderFor(policeStations)
const policeStationsProvider = PoliceStationsFamily();

/// See also [policeStations].
class PoliceStationsFamily
    extends Family<AsyncValue<List<PoliceStationResponse>>> {
  /// See also [policeStations].
  const PoliceStationsFamily();

  /// See also [policeStations].
  PoliceStationsProvider call(int districtId) {
    return PoliceStationsProvider(districtId);
  }

  @override
  PoliceStationsProvider getProviderOverride(
    covariant PoliceStationsProvider provider,
  ) {
    return call(provider.districtId);
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'policeStationsProvider';
}

/// See also [policeStations].
class PoliceStationsProvider
    extends AutoDisposeFutureProvider<List<PoliceStationResponse>> {
  /// See also [policeStations].
  PoliceStationsProvider(int districtId)
    : this._internal(
        (ref) => policeStations(ref as PoliceStationsRef, districtId),
        from: policeStationsProvider,
        name: r'policeStationsProvider',
        debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
            ? null
            : _$policeStationsHash,
        dependencies: PoliceStationsFamily._dependencies,
        allTransitiveDependencies:
            PoliceStationsFamily._allTransitiveDependencies,
        districtId: districtId,
      );

  PoliceStationsProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.districtId,
  }) : super.internal();

  final int districtId;

  @override
  Override overrideWith(
    FutureOr<List<PoliceStationResponse>> Function(PoliceStationsRef provider)
    create,
  ) {
    return ProviderOverride(
      origin: this,
      override: PoliceStationsProvider._internal(
        (ref) => create(ref as PoliceStationsRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        districtId: districtId,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<List<PoliceStationResponse>>
  createElement() {
    return _PoliceStationsProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is PoliceStationsProvider && other.districtId == districtId;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, districtId.hashCode);

    return _SystemHash.finish(hash);
  }
}

@Deprecated('Will be removed in 3.0. Use Ref instead')
// ignore: unused_element
mixin PoliceStationsRef
    on AutoDisposeFutureProviderRef<List<PoliceStationResponse>> {
  /// The parameter `districtId` of this provider.
  int get districtId;
}

class _PoliceStationsProviderElement
    extends AutoDisposeFutureProviderElement<List<PoliceStationResponse>>
    with PoliceStationsRef {
  _PoliceStationsProviderElement(super.provider);

  @override
  int get districtId => (origin as PoliceStationsProvider).districtId;
}

String _$branchesHash() => r'483acaa67c54eb34f463958f4a357ed4481998dc';

/// See also [branches].
@ProviderFor(branches)
final branchesProvider =
    AutoDisposeFutureProvider<List<BranchResponse>>.internal(
      branches,
      name: r'branchesProvider',
      debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
          ? null
          : _$branchesHash,
      dependencies: null,
      allTransitiveDependencies: null,
    );

@Deprecated('Will be removed in 3.0. Use Ref instead')
// ignore: unused_element
typedef BranchesRef = AutoDisposeFutureProviderRef<List<BranchResponse>>;
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member, deprecated_member_use_from_same_package
