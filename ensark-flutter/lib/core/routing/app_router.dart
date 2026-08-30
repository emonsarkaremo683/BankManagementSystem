import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../../features/auth/screens/login_screen.dart';
import '../../features/auth/screens/register_screen.dart';
import '../../features/auth/screens/forgot_password_screen.dart';
import '../../features/auth/screens/reset_password_screen.dart';
import '../../features/auth/screens/mfa_verify_screen.dart';
import '../../features/auth/screens/biometric_setup_screen.dart';
import '../../features/dashboard/screens/dashboard_screen.dart';
import '../../features/accounts/screens/account_details_screen.dart';
import '../../features/profile/screens/profile_screen.dart';
import '../../features/profile/screens/edit_profile_screen.dart';
import '../../features/profile/screens/change_password_screen.dart';
import '../../features/profile/screens/kyc_upload_screen.dart';
import '../../features/transfer/screens/transfer_screen.dart';
import '../../features/transfer/screens/otp_verification_screen.dart';
import '../../features/transfer/screens/beneficiary_list_screen.dart';
import '../../features/cards/screens/card_list_screen.dart';
import '../../features/cards/screens/card_details_screen.dart';
import '../../features/statements/screens/statement_screen.dart';
import '../../features/loans/screens/loan_list_screen.dart';
import '../../features/loans/screens/loan_application_screen.dart';
import '../../features/loans/screens/loan_details_screen.dart';
import '../../features/cheques/screens/cheque_book_list_screen.dart';
import '../../features/cheques/screens/cheque_leaf_list_screen.dart';
import '../../features/standing_orders/screens/standing_order_list_screen.dart';
import '../../features/standing_orders/screens/standing_order_form_screen.dart';
import '../../features/notifications/screens/notification_list_screen.dart';
import '../../features/utilities/screens/currency_converter_screen.dart';
import '../../features/utilities/screens/branch_locator_screen.dart';
import '../../providers/auth_provider.dart';
import '../../models/account/account_models.dart';
import '../../models/card/card_models.dart';
import '../../models/loan/loan_models.dart';
import '../../models/other/other_models.dart';

part 'app_router.g.dart';

class AppRoutes {
  static const String login = '/login';
  static const String register = '/register';
  static const String forgotPassword = '/forgot-password';
  static const String resetPassword = '/reset-password';
  static const String mfaVerify = '/mfa-verify';
  static const String biometricSetup = '/biometric-setup';
  static const String dashboard = '/';
  static const String accountDetails = '/account-details';
  static const String profile = '/profile';
  static const String editProfile = '/profile/edit';
  static const String changePassword = '/profile/change-password';
  static const String kycUpload = '/profile/kyc';
  static const String transfer = '/transfer';
  static const String otpVerify = '/otp-verify';
  static const String beneficiaries = '/beneficiaries';
  static const String cards = '/cards';
  static const String cardDetails = '/card-details';
  static const String statements = '/statements';
  static const String loanList = '/loans';
  static const String loanApplication = '/loans/apply';
  static const String loanDetails = '/loans/details';
  static const String chequeBookList = '/cheques';
  static const String chequeLeafList = '/cheques/leaves';
  static const String standingOrderList = '/standing-orders';
  static const String standingOrderForm = '/standing-orders/new';
  static const String notifications = '/notifications';
  static const String currencyConverter = '/currency-converter';
  static const String branchLocator = '/branch-locator';
}

@riverpod
GoRouter router(Ref ref) {
  final refreshListenable = _RouterRefreshListenable(ref);
  
  ref.onDispose(() {
    refreshListenable.dispose();
  });

  return GoRouter(
    initialLocation: AppRoutes.dashboard,
    refreshListenable: refreshListenable,
    redirect: (context, state) {
      final authState = ref.read(authProvider);
      final isAuth = authState.value?.user != null;
      final isLoggingIn = state.matchedLocation == AppRoutes.login;
      final isRegistering = state.matchedLocation == AppRoutes.register;
      final isForgot = state.matchedLocation == AppRoutes.forgotPassword;

      if (authState.isLoading) return null;

      if (!isAuth) {
        if (isLoggingIn || isRegistering || isForgot) return null;
        return AppRoutes.login;
      }

      if (isLoggingIn) return AppRoutes.dashboard;

      return null;
    },
    routes: [
      GoRoute(
        path: AppRoutes.login,
        builder: (context, state) => const LoginScreen(),
      ),
      GoRoute(
        path: AppRoutes.register,
        builder: (context, state) => const RegisterScreen(),
      ),
      GoRoute(
        path: AppRoutes.forgotPassword,
        builder: (context, state) => const ForgotPasswordScreen(),
      ),
      GoRoute(
        path: AppRoutes.resetPassword,
        builder: (context, state) => const ResetPasswordScreen(),
      ),
      GoRoute(
        path: AppRoutes.mfaVerify,
        builder: (context, state) {
          final email = state.extra as String;
          return MfaVerifyScreen(email: email);
        },
      ),
      GoRoute(
        path: AppRoutes.biometricSetup,
        builder: (context, state) {
          final extra = state.extra as Map<String, String>;
          return BiometricSetupScreen(
            email: extra['email']!,
            password: extra['password']!,
          );
        },
      ),
      GoRoute(
        path: AppRoutes.dashboard,
        builder: (context, state) => const DashboardScreen(),
      ),
      GoRoute(
        path: AppRoutes.transfer,
        builder: (context, state) => const TransferScreen(),
      ),
      GoRoute(
        path: AppRoutes.otpVerify,
        builder: (context, state) {
          final extra = state.extra as Map<String, dynamic>;
          return OtpVerificationScreen(
            otpReferenceId: extra['otpReferenceId'] as int,
            maskedEmail: extra['maskedEmail'] as String,
          );
        },
      ),
      GoRoute(
        path: AppRoutes.beneficiaries,
        builder: (context, state) => const BeneficiaryListScreen(),
      ),
      GoRoute(
        path: AppRoutes.cards,
        builder: (context, state) => const CardListScreen(),
      ),
      GoRoute(
        path: AppRoutes.cardDetails,
        builder: (context, state) {
          final card = state.extra as CardResponse;
          return CardDetailsScreen(card: card);
        },
      ),
      GoRoute(
        path: AppRoutes.statements,
        builder: (context, state) => const StatementScreen(),
      ),
      GoRoute(
        path: AppRoutes.loanList,
        builder: (context, state) => const LoanListScreen(),
      ),
      GoRoute(
        path: AppRoutes.loanApplication,
        builder: (context, state) => const LoanApplicationScreen(),
      ),
      GoRoute(
        path: AppRoutes.loanDetails,
        builder: (context, state) {
          final loan = state.extra as LoanApplicationResponse;
          return LoanDetailsScreen(loan: loan);
        },
      ),
      GoRoute(
        path: AppRoutes.chequeBookList,
        builder: (context, state) => const ChequeBookListScreen(),
      ),
      GoRoute(
        path: AppRoutes.chequeLeafList,
        builder: (context, state) {
          final book = state.extra as ChequeBookResponse;
          return ChequeLeafListScreen(book: book);
        },
      ),
      GoRoute(
        path: AppRoutes.standingOrderList,
        builder: (context, state) => const StandingOrderListScreen(),
      ),
      GoRoute(
        path: AppRoutes.standingOrderForm,
        builder: (context, state) => const StandingOrderFormScreen(),
      ),
      GoRoute(
        path: AppRoutes.notifications,
        builder: (context, state) => const NotificationListScreen(),
      ),
      GoRoute(
        path: AppRoutes.currencyConverter,
        builder: (context, state) => const CurrencyConverterScreen(),
      ),
      GoRoute(
        path: AppRoutes.branchLocator,
        builder: (context, state) => const BranchLocatorScreen(),
      ),
      GoRoute(
        path: AppRoutes.accountDetails,
        builder: (context, state) {
          final account = state.extra as AccountResponse;
          return AccountDetailsScreen(account: account);
        },
      ),
      GoRoute(
        path: AppRoutes.profile,
        builder: (context, state) => const ProfileScreen(),
        routes: [
          GoRoute(
            path: 'edit',
            builder: (context, state) => const EditProfileScreen(),
          ),
          GoRoute(
            path: 'change-password',
            builder: (context, state) => const ChangePasswordScreen(),
          ),
          GoRoute(
            path: 'kyc',
            builder: (context, state) => const KycUploadScreen(),
          ),
        ],
      ),
    ],
  );
}

class _RouterRefreshListenable extends ChangeNotifier {
  _RouterRefreshListenable(Ref ref) {
    ref.listen(authProvider, (previous, next) {
      if (previous?.value?.user != next.value?.user) {
        notifyListeners();
      }
    });
  }
}
