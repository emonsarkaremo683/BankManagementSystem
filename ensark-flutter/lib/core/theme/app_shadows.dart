import 'package:flutter/material.dart';
import 'app_colors.dart';

class AppShadows {
  static List<BoxShadow> embossed = [
    const BoxShadow(
      color: AppColors.shadowDark,
      offset: Offset(4, 4),
      blurRadius: 8,
    ),
    const BoxShadow(
      color: AppColors.shadowLight,
      offset: Offset(-4, -4),
      blurRadius: 8,
    ),
  ];

  static List<BoxShadow> debossed = [
    BoxShadow(
      color: AppColors.shadowDark.withValues(alpha: 0.5),
      offset: const Offset(2, 2),
      blurRadius: 4,
      spreadRadius: -1,
    ),
    BoxShadow(
      color: AppColors.shadowLight.withValues(alpha: 0.5),
      offset: const Offset(-2, -2),
      blurRadius: 4,
      spreadRadius: -1,
    ),
  ];

  static BoxShadow neonGlow(Color color) => BoxShadow(
    color: color.withValues(alpha: 0.3),
    blurRadius: 10,
    spreadRadius: 2,
  );
}
