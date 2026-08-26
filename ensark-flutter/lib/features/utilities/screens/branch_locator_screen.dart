import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../models/other/other_models.dart';
import '../../../providers/reference_data_provider.dart';
import '../../auth/widgets/neon_text_field.dart';

class BranchLocatorScreen extends ConsumerStatefulWidget {
  const BranchLocatorScreen({super.key});

  @override
  ConsumerState<BranchLocatorScreen> createState() => _BranchLocatorScreenState();
}

class _BranchLocatorScreenState extends ConsumerState<BranchLocatorScreen> {
  final _searchController = TextEditingController();
  String _searchQuery = '';

  @override
  Widget build(BuildContext context) {
    final branchesAsync = ref.watch(branchesProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Branches & ATMs'), backgroundColor: Colors.transparent),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(24),
            child: NeonTextField(
              label: 'Search by name or city',
              controller: _searchController,
              validator: (v) {
                setState(() => _searchQuery = v ?? '');
                return null;
              },
            ),
          ),
          Expanded(
            child: branchesAsync.when(
              data: (list) {
                final filtered = list.where((b) {
                  final name = b.name?.toLowerCase() ?? '';
                  final address = b.address?.toLowerCase() ?? '';
                  final query = _searchQuery.toLowerCase();
                  return name.contains(query) || address.contains(query);
                }).toList();

                return ListView.builder(
                  padding: const EdgeInsets.symmetric(horizontal: 24),
                  itemCount: filtered.length,
                  itemBuilder: (context, index) => _BranchTile(branch: filtered[index]),
                );
              },
              loading: () => const Center(child: CircularProgressIndicator()),
              error: (e, s) => Center(child: Text('Error: $e')),
            ),
          ),
        ],
      ),
    );
  }
}

class _BranchTile extends StatelessWidget {
  final BranchResponse branch;
  const _BranchTile({required this.branch});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: AppColors.darkSurface,
        borderRadius: BorderRadius.circular(16),
        boxShadow: AppShadows.debossed,
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(branch.name ?? 'Branch', style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16, color: Colors.white)),
              _TypeBadge(type: branch.type?.name ?? 'BRANCH'),
            ],
          ),
          const SizedBox(height: 8),
          Text(branch.address ?? '', style: const TextStyle(color: AppColors.textSecondary, fontSize: 13)),
          const Divider(height: 24, color: Colors.white10),
          Row(
            children: [
              const Icon(Icons.phone_outlined, size: 14, color: AppColors.neonCyan),
              const SizedBox(width: 8),
              Text(branch.phoneNumber ?? 'N/A', style: const TextStyle(fontSize: 12)),
              const SizedBox(width: 24),
              const Icon(Icons.map_outlined, size: 14, color: AppColors.neonPink),
              const SizedBox(width: 8),
              const Text('View on Map', style: TextStyle(fontSize: 12, color: AppColors.neonPink)),
            ],
          ),
        ],
      ),
    );
  }
}

class _TypeBadge extends StatelessWidget {
  final String type;
  const _TypeBadge({required this.type});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
      decoration: BoxDecoration(
        color: AppColors.neonCyan.withValues(alpha: 0.1),
        borderRadius: BorderRadius.circular(8),
      ),
      child: Text(type, style: const TextStyle(color: AppColors.neonCyan, fontSize: 10, fontWeight: FontWeight.bold)),
    );
  }
}
