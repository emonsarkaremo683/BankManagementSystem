import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:intl/intl.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../models/card/card_models.dart';
import '../../../providers/card_provider.dart';
import '../../auth/widgets/neon_button.dart';
import '../../auth/widgets/neon_text_field.dart';
import '../../../models/enums.dart';

class CardDetailsScreen extends ConsumerStatefulWidget {
  final CardResponse card;
  const CardDetailsScreen({super.key, required this.card});

  @override
  ConsumerState<CardDetailsScreen> createState() => _CardDetailsScreenState();
}

class _CardDetailsScreenState extends ConsumerState<CardDetailsScreen> {
  late double _dailyLimit;
  late double _monthlyLimit;

  @override
  void initState() {
    super.initState();
    _dailyLimit = widget.card.dailyLimit ?? 1000;
    _monthlyLimit = widget.card.monthlyLimit ?? 5000;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Card Settings'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _InfoSection(
              title: 'Card Limits',
              children: [
                _LimitDisplay(
                  label: 'Daily Limit',
                  value: _dailyLimit,
                  max: 5000,
                ),
                const SizedBox(height: 24),
                _LimitDisplay(
                  label: 'Monthly Limit',
                  value: _monthlyLimit,
                  max: 20000,
                ),
                const SizedBox(height: 16),
                const Text(
                  '* Limits are set by the bank. Please contact support to request a limit change.',
                  style: TextStyle(fontSize: 10, color: AppColors.textSecondary, fontStyle: FontStyle.italic),
                ),
              ],
            ),
            const SizedBox(height: 32),
            _InfoSection(
              title: 'Card Actions',
              children: [
                _SecurityTile(
                  icon: Icons.upgrade,
                  title: 'Request Card Upgrade',
                  onTap: () => _showUpgradeDialog(context),
                ),
                _SecurityTile(
                  icon: Icons.lock_reset,
                  title: 'Change PIN',
                  onTap: () => _showChangePinDialog(context),
                ),
                _SecurityTile(
                  icon: Icons.block,
                  title: 'Report Lost or Stolen',
                  color: Colors.redAccent,
                  onTap: () => _showReportLostDialog(context),
                ),
              ],
            ),
            const SizedBox(height: 32),
            const Text('Card Status', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: AppColors.neonCyan)),
            const SizedBox(height: 16),
            _StatusTile(label: 'Network', value: widget.card.cardNetwork?.name ?? 'N/A'),
            _StatusTile(label: 'Type', value: widget.card.cardType?.name ?? 'N/A'),
            _StatusTile(label: 'Status', value: widget.card.status?.name ?? 'ACTIVE', isHighlight: true),
          ],
        ),
      ),
    );
  }

  void _showUpgradeDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => _UpgradeCardDialog(cardId: widget.card.cardId!, currentType: widget.card.cardType ?? CardType.DEBIT),
    );
  }

  void _showChangePinDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => _ChangePinDialog(cardId: widget.card.cardId!),
    );
  }

  void _showReportLostDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        backgroundColor: AppColors.darkSurface,
        title: const Text('Report Lost/Stolen'),
        content: const Text('Are you sure you want to block this card permanently?'),
        actions: [
          TextButton(onPressed: () => Navigator.pop(context), child: const Text('CANCEL')),
          TextButton(
            onPressed: () async {
            await ref.read(cardsProvider.notifier).reportLost(widget.card.cardId!, 'User reported lost');
            if (context.mounted) {
              Navigator.pop(context);
              Navigator.pop(context);
            }
          },
            child: const Text('BLOCK CARD', style: TextStyle(color: Colors.redAccent)),
          ),
        ],
      ),
    );
  }
}

class _InfoSection extends StatelessWidget {
  final String title;
  final List<Widget> children;

  const _InfoSection({required this.title, required this.children});

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          title,
          style: const TextStyle(
            fontSize: 18,
            fontWeight: FontWeight.bold,
            color: AppColors.neonCyan,
          ),
        ),
        const SizedBox(height: 16),
        Container(
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(
            color: AppColors.darkSurface,
            borderRadius: BorderRadius.circular(16),
            border: Border.all(color: AppColors.neonCyan.withOpacity(0.1)),
          ),
          child: Column(children: children),
        ),
      ],
    );
  }
}

class _LimitDisplay extends StatelessWidget {
  final String label;
  final double value;
  final double max;

  const _LimitDisplay({required this.label, required this.value, required this.max});

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(label, style: const TextStyle(color: AppColors.textSecondary)),
            Text(NumberFormat.currency(symbol: '\$').format(value), style: const TextStyle(fontWeight: FontWeight.bold, color: AppColors.neonCyan)),
          ],
        ),
        const SizedBox(height: 8),
        ClipRRect(
          borderRadius: BorderRadius.circular(4),
          child: LinearProgressIndicator(
            value: value / max,
            backgroundColor: AppColors.darkBackground,
            valueColor: const AlwaysStoppedAnimation<Color>(AppColors.neonCyan),
            minHeight: 8,
          ),
        ),
      ],
    );
  }
}

class _UpgradeCardDialog extends ConsumerStatefulWidget {
  final int cardId;
  final CardType currentType;
  const _UpgradeCardDialog({required this.cardId, required this.currentType});

  @override
  ConsumerState<_UpgradeCardDialog> createState() => _UpgradeCardDialogState();
}

class _UpgradeCardDialogState extends ConsumerState<_UpgradeCardDialog> {
  late CardType _selectedType;
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _selectedType = widget.currentType;
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      backgroundColor: AppColors.darkSurface,
      title: const Text('Request Card Upgrade'),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          const Text('Select the card type you wish to apply for:', style: TextStyle(fontSize: 12, color: AppColors.textSecondary)),
          const SizedBox(height: 16),
          ...CardType.values.map((type) => RadioListTile<CardType>(
                title: Text(type.name, style: const TextStyle(color: Colors.white)),
                value: type,
                groupValue: _selectedType,
                activeColor: AppColors.neonCyan,
                onChanged: (v) => setState(() => _selectedType = v!),
              )),
        ],
      ),
      actions: [
        TextButton(onPressed: () => Navigator.pop(context), child: const Text('CANCEL')),
        TextButton(
          onPressed: _isLoading ? null : () async {
            setState(() => _isLoading = true);
            try {
              await ref.read(cardsProvider.notifier).requestCardTypeChange(widget.cardId, _selectedType);
              if (context.mounted) {
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Request submitted successfully!')));
              }
            } catch (e) {
              if (context.mounted) ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(e.toString())));
            } finally {
              if (mounted) setState(() => _isLoading = false);
            }
          },
          child: const Text('SUBMIT', style: TextStyle(color: AppColors.neonCyan)),
        ),
      ],
    );
  }
}

class _SecurityTile extends StatelessWidget {
  final IconData icon;
  final String title;
  final VoidCallback onTap;
  final Color? color;

  const _SecurityTile({required this.icon, required this.title, required this.onTap, this.color});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      contentPadding: EdgeInsets.zero,
      leading: Icon(icon, color: color ?? AppColors.neonCyan),
      title: Text(title, style: TextStyle(color: color)),
      trailing: const Icon(Icons.chevron_right, color: AppColors.textSecondary),
      onTap: onTap,
    );
  }
}

class _StatusTile extends StatelessWidget {
  final String label;
  final String value;
  final bool isHighlight;

  const _StatusTile({required this.label, required this.value, this.isHighlight = false});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(label, style: const TextStyle(color: AppColors.textSecondary)),
          Text(
            value,
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: isHighlight ? AppColors.neonGreen : Colors.white,
            ),
          ),
        ],
      ),
    );
  }
}

class _ChangePinDialog extends ConsumerStatefulWidget {
  final int cardId;
  const _ChangePinDialog({required this.cardId});

  @override
  ConsumerState<_ChangePinDialog> createState() => _ChangePinDialogState();
}

class _ChangePinDialogState extends ConsumerState<_ChangePinDialog> {
  final _oldController = TextEditingController();
  final _newController = TextEditingController();
  bool _isLoading = false;

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      backgroundColor: AppColors.darkSurface,
      title: const Text('Change PIN'),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          NeonTextField(label: 'Old PIN', controller: _oldController, isPassword: true, keyboardType: TextInputType.number),
          const SizedBox(height: 16),
          NeonTextField(label: 'New PIN', controller: _newController, isPassword: true, keyboardType: TextInputType.number),
        ],
      ),
      actions: [
        TextButton(onPressed: () => Navigator.pop(context), child: const Text('CANCEL')),
        TextButton(
          onPressed: _isLoading ? null : () async {
            setState(() => _isLoading = true);
            try {
              await ref.read(cardsProvider.notifier).updatePin(widget.cardId, _oldController.text, _newController.text);
              if (context.mounted) Navigator.pop(context);
            } catch (e) {
              if (context.mounted) ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(e.toString())));
            } finally {
              if (mounted) setState(() => _isLoading = false);
            }
          },
          child: const Text('CHANGE', style: TextStyle(color: AppColors.neonCyan)),
        ),
      ],
    );
  }
}
