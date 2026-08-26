import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../core/routing/app_router.dart';
import '../../../models/card/card_models.dart';
import '../../../providers/card_provider.dart';

class CardListScreen extends ConsumerWidget {
  const CardListScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final cardsAsync = ref.watch(cardsProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('My Cards'),
        backgroundColor: Colors.transparent,
        actions: [
          IconButton(
            icon: const Icon(Icons.add_card, color: AppColors.neonCyan),
            onPressed: () => _showApplyCardDialog(context, ref),
          ),
        ],
      ),
      body: cardsAsync.when(
        data: (cards) => cards.isEmpty
            ? const Center(child: Text('No cards found.', style: TextStyle(color: AppColors.textSecondary)))
            : RefreshIndicator(
                onRefresh: () => ref.read(cardsProvider.notifier).refresh(),
                child: ListView.builder(
                  padding: const EdgeInsets.symmetric(vertical: 24),
                  itemCount: cards.length,
                  itemBuilder: (context, index) {
                    final card = cards[index];
                    return Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                      child: GestureDetector(
                        onTap: () => context.push(AppRoutes.cardDetails, extra: card),
                        child: _NeonCreditCard(card: card),
                      ),
                    );
                  },
                ),
              ),
        loading: () => const Center(child: CircularProgressIndicator()),
        error: (e, s) => Center(child: Text('Error: $e')),
      ),
    );
  }

  void _showApplyCardDialog(BuildContext context, WidgetRef ref) {
    // Implementation for applying a new card
    ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Apply card feature coming soon!')));
  }
}

class _NeonCreditCard extends StatelessWidget {
  final CardResponse card;
  const _NeonCreditCard({required this.card});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 200,
      width: double.infinity,
      decoration: BoxDecoration(
        color: AppColors.darkSurface,
        borderRadius: BorderRadius.circular(20),
        boxShadow: [
          AppShadows.neonGlow(card.cardNetwork?.name == 'VISA' ? AppColors.neonCyan : AppColors.neonPink),
          ...AppShadows.embossed,
        ],
        gradient: LinearGradient(
          colors: [
            AppColors.darkSurface,
            AppColors.darkSurface.withValues(alpha: 0.8),
          ],
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
        ),
      ),
      child: Stack(
        children: [
          Positioned(
            right: -20,
            bottom: -20,
            child: Icon(
              Icons.credit_card,
              size: 150,
              color: Colors.white.withValues(alpha: 0.05),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(24.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(
                      card.cardType?.name ?? 'DEBIT',
                      style: const TextStyle(fontWeight: FontWeight.bold, letterSpacing: 2, color: AppColors.textSecondary),
                    ),
                    Text(
                      card.cardNetwork?.name ?? 'VISA',
                      style: TextStyle(
                        fontSize: 24,
                        fontWeight: FontWeight.w900,
                        fontStyle: FontStyle.italic,
                        color: card.cardNetwork?.name == 'VISA' ? AppColors.neonCyan : AppColors.neonPink,
                      ),
                    ),
                  ],
                ),
                Text(
                  card.cardNumber?.replaceAllMapped(RegExp(r".{4}"), (match) => "${match.group(0)} ") ?? '**** **** **** ****',
                  style: const TextStyle(fontSize: 22, letterSpacing: 4, fontFamily: 'monospace'),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text('CARD HOLDER', style: TextStyle(fontSize: 10, color: AppColors.textSecondary)),
                        Text(card.cardHolderName ?? 'USER NAME', style: const TextStyle(fontWeight: FontWeight.bold)),
                      ],
                    ),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text('EXPIRES', style: TextStyle(fontSize: 10, color: AppColors.textSecondary)),
                        Text(
                          card.expiryDate != null
                              ? "${card.expiryDate!.month.toString().padLeft(2, '0')}/${card.expiryDate!.year.toString().substring(2)}"
                              : 'MM/YY',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                      ],
                    ),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
