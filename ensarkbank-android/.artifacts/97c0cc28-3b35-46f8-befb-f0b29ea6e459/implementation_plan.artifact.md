# Implementation Plan - EnSark Bank (Neon-Neumorphic Fintech App)

Build a production-level, customer-facing banking app using Java, Retrofit, and ViewBinding, following the "Electric Embossed" Neon-Neumorphic design language.

## User Review Required

> [!IMPORTANT]
> The app will transition to a **Single-Activity Navigation Pattern** for the main authenticated experience to better support neumorphic transitions and shared state.
>
> We will use **ViewBinding** as specified in the `build.gradle`.

## Proposed Changes

### 1. UI Infrastructure & Styling
Set up the foundational colors, themes, and neumorphic drawable systems.

#### [NEW] [colors.xml](file:///F:/BankManagementSystem/ensarkbank-android/app/src/main/res/values/colors.xml)
- Define the "Electric Embossed" palette (`#0D1117`, `#161B22`, `#00F2FE`, etc.).

#### [NEW] [themes.xml](file:///F:/BankManagementSystem/ensarkbank-android/app/src/main/res/values/themes.xml)
- Configure the dark theme with no action bar and custom window backgrounds.

#### [NEW] Neumorphic Drawables
- Create reusable XML shapes for "Raised" and "Recessed" surfaces using layered drawables to simulate dual shadows.

---

### 2. Base Architecture
Implement the core classes to handle common UI logic and Navigation.

#### [NEW] [BaseActivity.java](file:///F:/BankManagementSystem/ensarkbank-android/app/src/main/java/com/ensark/ensarkbank/ui/base/BaseActivity.java)
- Common setup for ViewBinding and Session management.

#### [NEW] [NavHostActivity.java](file:///F:/BankManagementSystem/ensarkbank-android/app/src/main/java/com/ensark/ensarkbank/ui/NavHostActivity.java)
- The main entry point hosting the Navigation Graph.

---

### 3. Feature: Onboarding & Authentication
Implement the secure entry flow.

#### [NEW] [SplashActivity.java](file:///F:/BankManagementSystem/ensarkbank-android/app/src/main/java/com/ensark/ensarkbank/ui/auth/SplashActivity.java)
- Logo animation and session validation.

#### [NEW] [LoginActivity.java](file:///F:/BankManagementSystem/ensarkbank-android/app/src/main/java/com/ensark/ensarkbank/ui/auth/LoginActivity.java)
- Recessed input fields with Cyan focus glow.

#### [NEW] [RegisterActivity.java](file:///F:/BankManagementSystem/ensarkbank-android/app/src/main/java/com/ensark/ensarkbank/ui/auth/RegisterActivity.java)
- Multi-step registration wizard.

---

### 4. Feature: Core Banking Hub (Dashboard)
The main interface for account overview.

#### [NEW] [DashboardFragment.java](file:///F:/BankManagementSystem/ensarkbank-android/app/src/main/java/com/ensark/ensarkbank/ui/dashboard/DashboardFragment.java)
- Hero Credit Card (Glassmorphic).
- Quick Action buttons (Pops-out on tap).
- Transaction summary.

---

### 5. Feature: Transactions & Money Movement
#### [NEW] [TransferFragment.java](file:///F:/BankManagementSystem/ensarkbank-android/app/src/main/java/com/ensark/ensarkbank/ui/transfer/TransferFragment.java)
- Beneficiary selection (ID-based dropdown).
- Amount entry with real-time balance validation.

## Verification Plan

### Automated Tests
- `gradlew build` to ensure UI and Backend components link correctly.
- Unit tests for `SessionManager` and `AuthRepository`.

### Manual Verification
- Verify shadow physics (Top-Left Light source) on physical devices/emulators.
- Test "Glow" states on input focus and button presses.
- Verify JWT token persistence and auto-injection in `AuthInterceptor`.
