import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../repositories/general_repository.dart';

part 'reference_data_provider.g.dart';

@riverpod
Future<List<DivisionResponse>> divisions(Ref ref) async {
  return ref.watch(generalRepositoryProvider).getAllDivisions();
}

@riverpod
Future<List<DistrictResponse>> districts(Ref ref, int divisionId) async {
  return ref.watch(generalRepositoryProvider).getDistrictsByDivision(divisionId);
}

@riverpod
Future<List<PoliceStationResponse>> policeStations(Ref ref, int districtId) async {
  return ref.watch(generalRepositoryProvider).getPoliceStationsByDistrict(districtId);
}

@riverpod
Future<List<BranchResponse>> branches(Ref ref) async {
  return ref.watch(generalRepositoryProvider).getAllBranches();
}
