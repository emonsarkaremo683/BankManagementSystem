// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'standing_order_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$standingOrdersHash() => r'6c3e6aa6f056aea66a2d793b406fc52c02b999b3';

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

abstract class _$StandingOrders
    extends BuildlessAutoDisposeAsyncNotifier<List<StandingOrderResponse>> {
  late final int accountId;

  FutureOr<List<StandingOrderResponse>> build(int accountId);
}

/// See also [StandingOrders].
@ProviderFor(StandingOrders)
const standingOrdersProvider = StandingOrdersFamily();

/// See also [StandingOrders].
class StandingOrdersFamily
    extends Family<AsyncValue<List<StandingOrderResponse>>> {
  /// See also [StandingOrders].
  const StandingOrdersFamily();

  /// See also [StandingOrders].
  StandingOrdersProvider call(int accountId) {
    return StandingOrdersProvider(accountId);
  }

  @override
  StandingOrdersProvider getProviderOverride(
    covariant StandingOrdersProvider provider,
  ) {
    return call(provider.accountId);
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'standingOrdersProvider';
}

/// See also [StandingOrders].
class StandingOrdersProvider
    extends
        AutoDisposeAsyncNotifierProviderImpl<
          StandingOrders,
          List<StandingOrderResponse>
        > {
  /// See also [StandingOrders].
  StandingOrdersProvider(int accountId)
    : this._internal(
        () => StandingOrders()..accountId = accountId,
        from: standingOrdersProvider,
        name: r'standingOrdersProvider',
        debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
            ? null
            : _$standingOrdersHash,
        dependencies: StandingOrdersFamily._dependencies,
        allTransitiveDependencies:
            StandingOrdersFamily._allTransitiveDependencies,
        accountId: accountId,
      );

  StandingOrdersProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.accountId,
  }) : super.internal();

  final int accountId;

  @override
  FutureOr<List<StandingOrderResponse>> runNotifierBuild(
    covariant StandingOrders notifier,
  ) {
    return notifier.build(accountId);
  }

  @override
  Override overrideWith(StandingOrders Function() create) {
    return ProviderOverride(
      origin: this,
      override: StandingOrdersProvider._internal(
        () => create()..accountId = accountId,
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        accountId: accountId,
      ),
    );
  }

  @override
  AutoDisposeAsyncNotifierProviderElement<
    StandingOrders,
    List<StandingOrderResponse>
  >
  createElement() {
    return _StandingOrdersProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is StandingOrdersProvider && other.accountId == accountId;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, accountId.hashCode);

    return _SystemHash.finish(hash);
  }
}

@Deprecated('Will be removed in 3.0. Use Ref instead')
// ignore: unused_element
mixin StandingOrdersRef
    on AutoDisposeAsyncNotifierProviderRef<List<StandingOrderResponse>> {
  /// The parameter `accountId` of this provider.
  int get accountId;
}

class _StandingOrdersProviderElement
    extends
        AutoDisposeAsyncNotifierProviderElement<
          StandingOrders,
          List<StandingOrderResponse>
        >
    with StandingOrdersRef {
  _StandingOrdersProviderElement(super.provider);

  @override
  int get accountId => (origin as StandingOrdersProvider).accountId;
}

// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member, deprecated_member_use_from_same_package
