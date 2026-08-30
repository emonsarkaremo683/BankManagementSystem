// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'transfer_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$transferHash() => r'992090e4b60e0dd3a696d5232adbedd87e9594bb';

/// See also [Transfer].
@ProviderFor(Transfer)
final transferProvider =
    AutoDisposeNotifierProvider<Transfer, AsyncValue<void>>.internal(
      Transfer.new,
      name: r'transferProvider',
      debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
          ? null
          : _$transferHash,
      dependencies: null,
      allTransitiveDependencies: null,
    );

typedef _$Transfer = AutoDisposeNotifier<AsyncValue<void>>;
String _$beneficiariesHash() => r'b35796f7399e8515d143dc794192dbf60fc0d264';

/// See also [Beneficiaries].
@ProviderFor(Beneficiaries)
final beneficiariesProvider =
    AutoDisposeAsyncNotifierProvider<
      Beneficiaries,
      List<BeneficiaryResponse>
    >.internal(
      Beneficiaries.new,
      name: r'beneficiariesProvider',
      debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
          ? null
          : _$beneficiariesHash,
      dependencies: null,
      allTransitiveDependencies: null,
    );

typedef _$Beneficiaries = AutoDisposeAsyncNotifier<List<BeneficiaryResponse>>;
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member, deprecated_member_use_from_same_package
