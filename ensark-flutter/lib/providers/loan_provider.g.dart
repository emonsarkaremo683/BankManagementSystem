// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'loan_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$loanScheduleHash() => r'0118e5817db811d14086536cc887e01bd7cb3c10';

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

/// See also [loanSchedule].
@ProviderFor(loanSchedule)
const loanScheduleProvider = LoanScheduleFamily();

/// See also [loanSchedule].
class LoanScheduleFamily
    extends Family<AsyncValue<List<LoanScheduleResponse>>> {
  /// See also [loanSchedule].
  const LoanScheduleFamily();

  /// See also [loanSchedule].
  LoanScheduleProvider call(int loanId) {
    return LoanScheduleProvider(loanId);
  }

  @override
  LoanScheduleProvider getProviderOverride(
    covariant LoanScheduleProvider provider,
  ) {
    return call(provider.loanId);
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'loanScheduleProvider';
}

/// See also [loanSchedule].
class LoanScheduleProvider
    extends AutoDisposeFutureProvider<List<LoanScheduleResponse>> {
  /// See also [loanSchedule].
  LoanScheduleProvider(int loanId)
    : this._internal(
        (ref) => loanSchedule(ref as LoanScheduleRef, loanId),
        from: loanScheduleProvider,
        name: r'loanScheduleProvider',
        debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
            ? null
            : _$loanScheduleHash,
        dependencies: LoanScheduleFamily._dependencies,
        allTransitiveDependencies:
            LoanScheduleFamily._allTransitiveDependencies,
        loanId: loanId,
      );

  LoanScheduleProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.loanId,
  }) : super.internal();

  final int loanId;

  @override
  Override overrideWith(
    FutureOr<List<LoanScheduleResponse>> Function(LoanScheduleRef provider)
    create,
  ) {
    return ProviderOverride(
      origin: this,
      override: LoanScheduleProvider._internal(
        (ref) => create(ref as LoanScheduleRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        loanId: loanId,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<List<LoanScheduleResponse>> createElement() {
    return _LoanScheduleProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is LoanScheduleProvider && other.loanId == loanId;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, loanId.hashCode);

    return _SystemHash.finish(hash);
  }
}

@Deprecated('Will be removed in 3.0. Use Ref instead')
// ignore: unused_element
mixin LoanScheduleRef
    on AutoDisposeFutureProviderRef<List<LoanScheduleResponse>> {
  /// The parameter `loanId` of this provider.
  int get loanId;
}

class _LoanScheduleProviderElement
    extends AutoDisposeFutureProviderElement<List<LoanScheduleResponse>>
    with LoanScheduleRef {
  _LoanScheduleProviderElement(super.provider);

  @override
  int get loanId => (origin as LoanScheduleProvider).loanId;
}

String _$loansHash() => r'f4c3ece720826510533923b612ed95dec2d52de5';

/// See also [Loans].
@ProviderFor(Loans)
final loansProvider =
    AutoDisposeAsyncNotifierProvider<
      Loans,
      List<LoanApplicationResponse>
    >.internal(
      Loans.new,
      name: r'loansProvider',
      debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
          ? null
          : _$loansHash,
      dependencies: null,
      allTransitiveDependencies: null,
    );

typedef _$Loans = AutoDisposeAsyncNotifier<List<LoanApplicationResponse>>;
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member, deprecated_member_use_from_same_package
