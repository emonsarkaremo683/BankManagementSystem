import 'package:flutter/material.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';

class NeonButton extends StatefulWidget {
  final String text;
  final VoidCallback? onPressed;
  final bool isLoading;
  final Color color;

  const NeonButton({
    super.key,
    required this.text,
    this.onPressed,
    this.isLoading = false,
    this.color = AppColors.neonCyan,
  });

  @override
  State<NeonButton> createState() => _NeonButtonState();
}

class _NeonButtonState extends State<NeonButton> {
  bool _isPressed = false;

  @override
  Widget build(BuildContext context) {
    final bool isEnabled = widget.onPressed != null && !widget.isLoading;

    return GestureDetector(
      onTapDown: isEnabled ? (_) => setState(() => _isPressed = true) : null,
      onTapUp: isEnabled ? (_) => setState(() => _isPressed = false) : null,
      onTapCancel: isEnabled ? () => setState(() => _isPressed = false) : null,
      onTap: isEnabled ? widget.onPressed : null,
      child: AnimatedContainer(
        duration: const Duration(milliseconds: 150),
        width: double.infinity,
        height: 55,
        decoration: BoxDecoration(
          color: AppColors.darkBackground,
          borderRadius: BorderRadius.circular(27.5),
          boxShadow: isEnabled 
            ? (_isPressed ? AppShadows.debossed : AppShadows.embossed)
            : [],
        ),
        child: Center(
          child: widget.isLoading
            ? const SizedBox(
                height: 20,
                width: 20,
                child: CircularProgressIndicator(strokeWidth: 2, color: AppColors.neonCyan),
              )
            : Text(
                widget.text,
                style: TextStyle(
                  color: isEnabled ? widget.color : Colors.white24,
                  fontWeight: FontWeight.bold,
                  fontSize: 16,
                  shadows: isEnabled ? [Shadow(color: widget.color.withValues(alpha: 0.5), blurRadius: 10)] : [],
                ),
              ),
        ),
      ),
    );
  }
}
