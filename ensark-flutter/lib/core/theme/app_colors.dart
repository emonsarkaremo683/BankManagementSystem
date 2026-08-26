import 'package:flutter/material.dart';

class AppColors {
  static const Color darkBackground = Color(0xFF1E1E26);
  static const Color darkSurface = Color(0xFF23232E);
  
  static const Color neonCyan = Color(0xFF00E5FF);
  static const Color neonGreen = Color(0xFF39FF14);
  static const Color neonPink = Color(0xFFBD00FF);
  
  static const Color shadowLight = Color(0xFF2A2A36);
  static const Color shadowDark = Color(0xFF15151C);
  
  static const Color textPrimary = Colors.white;
  static const Color textSecondary = Color(0xFF8E8E93);
  
  static const LinearGradient neonGradient = LinearGradient(
    colors: [neonCyan, neonPink],
    begin: Alignment.topLeft,
    end: Alignment.bottomRight,
  );
}
