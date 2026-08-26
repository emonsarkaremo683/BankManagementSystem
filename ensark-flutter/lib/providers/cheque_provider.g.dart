// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'cheque_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$chequeLeavesHash() => r'989d8ca872e012a3d556886fa9937980bac847f6';

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

/// See also [chequeLeaves].
@ProviderFor(chequeLeaves)
const chequeLeavesProvider = ChequeLeavesFamily();

/// See also [chequeLeaves].
class ChequeLeavesFamily extends Family<AsyncValue<List<ChequeLeafResponse>>> {
  /// See also [chequeLeaves].
  const ChequeLeavesFamily();

  /// See also [chequeLeaves].
  ChequeLeavesProvider call(int customerId, {String? status}) {
    return ChequeLeavesProvider(customerId, status: status);
  }

  @override
  ChequeLeavesProvider getProviderOverride(
    covariant ChequeLeavesProvider provider,
  ) {
    return call(provider.customerId, status: provider.status);
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'chequeLeavesProvider';
}

/// See also [chequeLeaves].
class ChequeLeavesProvider
    extends AutoDisposeFutureProvider<List<ChequeLeafResponse>> {
  /// See also [chequeLeaves].
  ChequeLeavesProvider(int customerId, {String? status})
    : this._internal(
        (ref) =>
            chequeLeaves(ref as ChequeLeavesRef, customerId, status: status),
        from: chequeLeavesProvider,
        name: r'chequeLeavesProvider',
        debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
            ? null
            : _$chequeLeavesHash,
        dependencies: ChequeLeavesFamily._dependencies,
        allTransitiveDependencies:
            ChequeLeavesFamily._allTransitiveDependencies,
        customerId: customerId,
        status: status,
      );

  ChequeLeavesProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.customerId,
    required this.status,
  }) : super.internal();

  final int customerId;
  final String? status;

  @override
  Override overrideWith(
    FutureOr<List<ChequeLeafResponse>> Function(ChequeLeavesRef provider)
    create,
  ) {
    return ProviderOverride(
      origin: this,
      override: ChequeLeavesProvider._internal(
        (ref) => create(ref as ChequeLeavesRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        customerId: customerId,
        status: status,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<List<ChequeLeafResponse>> createElement() {
    return _ChequeLeavesProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is ChequeLeavesProvider &&
        other.customerId == customerId &&
        other.status == status;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, customerId.hashCode);
    hash = _SystemHash.combine(hash, status.hashCode);

    return _SystemHash.finish(hash);
  }
}

@Deprecated('Will be removed in 3.0. Use Ref instead')
// ignore: unused_element
mixin ChequeLeavesRef
    on AutoDisposeFutureProviderRef<List<ChequeLeafResponse>> {
  /// The parameter `customerId` of this provider.
  int get customerId;

  /// The parameter `status` of this provider.
  String? get status;
}

class _ChequeLeavesProviderElement
    extends AutoDisposeFutureProviderElement<List<ChequeLeafResponse>>
    with ChequeLeavesRef {
  _ChequeLeavesProviderElement(super.provider);

  @override
  int get customerId => (origin as ChequeLeavesProvider).customerId;
  @override
  String? get status => (origin as ChequeLeavesProvider).status;
}

String _$chequeBooksHash() => r'83ef19645d7d1edb542d9559f635f7f2ee778dd1';

/// See also [ChequeBooks].
@ProviderFor(ChequeBooks)
final chequeBooksProvider =
    AutoDisposeAsyncNotifierProvider<
      ChequeBooks,
      List<ChequeBookResponse>
    >.internal(
      ChequeBooks.new,
      name: r'chequeBooksProvider',
      debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
          ? null
          : _$chequeBooksHash,
      dependencies: null,
      allTransitiveDependencies: null,
    );

typedef _$ChequeBooks = AutoDisposeAsyncNotifier<List<ChequeBookResponse>>;
String _$leafActionsHash() => r'f8cc074d4bf03e1a2183e06a7a2fcf059b7eff78';

/// See also [LeafActions].
@ProviderFor(LeafActions)
final leafActionsProvider =
    AutoDisposeNotifierProvider<LeafActions, AsyncValue<void>>.internal(
      LeafActions.new,
      name: r'leafActionsProvider',
      debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
          ? null
          : _$leafActionsHash,
      dependencies: null,
      allTransitiveDependencies: null,
    );

typedef _$LeafActions = AutoDisposeNotifier<AsyncValue<void>>;
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member, deprecated_member_use_from_same_package
