# Bank Management System

A full-stack, multi-channel banking platform consisting of a **Spring Boot REST API**, an **Angular web SPA**, and a **native Android app**. The system covers core retail/branch banking: customer onboarding & KYC, accounts, cards, loans, transfers, beneficiaries, cheques, standing orders, ATMs, branches, HR/employee management, fraud detection, accounting/ledger, and financial reporting — all behind a JWT + MFA secured API.

---

## Repository Structure

```
BankManagementSystem/
├── ensark/                 # Backend — Spring Boot REST API (Java 21)
├── ensark-frontend/        # Web client — Angular 22 SPA (TypeScript)
└── ensarkbank-android/     # Mobile client — native Android app (Java)
```

| Module | Stack | Default URL / Port | Package |
|--------|-------|--------------------|---------|
| `ensark` | Spring Boot 4.0.7, Java 21, Spring Security, JPA/Hibernate, MySQL | `http://localhost:8085` | `com.elitetech_inc.ensarkbank` |
| `ensark-frontend` | Angular 22.1, TailwindCSS 4, RxJS, Chart.js, STOMP/SockJS | `http://localhost:4200` | `app.*` |
| `ensarkbank-android` | Android (Java 11), Retrofit 2, OkHttp, Navigation Component, ViewBinding | installable APK | `com.ensark.ensarkbank` |

---

## Tech Stack

### Backend (`ensark`)
- **Framework:** Spring Boot 4.0.7 (WebMvc, Data JPA, Validation, Security, Mail, Thymeleaf, WebSocket)
- **Language:** Java 21
- **Database:** MySQL 8 (JPA/Hibernate, `ddl-auto=update`)
- **Auth/Security:** Spring Security, JWT (`jjwt` 0.12.7), BCrypt, TOTP MFA (`dev.samstevens.totp`)
- **Mapping:** MapStruct 1.6.3 + Lombok 1.18.34
- **Docs:** springdoc-openapi (`/swagger-ui.html`)
- **Other:** OpenHTMLToPDF (statements/PDF), Apache POI + OpenCSV (Excel/CSV export), ZXing (QR), Spring Retry (optimistic-lock retries), WebSocket notifications

### Frontend (`ensark-frontend`)
- **Framework:** Angular 22.1, standalone components, Angular Router
- **Styling:** TailwindCSS 4, Lucide icons, Chart.js
- **Comms:** RxJS, `HttpClient`, `@stomp/stompjs` + `sockjs-client` (live notifications)
- **Utils:** `crypto-js` (client-side hashing), `jsPDF` + `jspdf-autotable` (exports)
- **Tests:** Vitest

### Android (`ensarkbank-android`)
- **Min/Target SDK:** 28 / 37 (compileSdk 37), Java 11
- **Network:** Retrofit 2 + Gson converter, OkHttp (logging + auth interceptors)
- **UI:** Material Design, Navigation Component, ViewBinding, Glide (images)
- **State:** `SessionManager` (SharedPreferences JWT store) + `AuthInterceptor` (Bearer injection)

---

## Feature Modules (Backend)

| Domain | Highlights |
|--------|-----------|
| `auth_management` | Login, MFA setup/verify/confirm/disable, logout, token refresh, email verification, forgot/reset password, **token validation**, rate-limited login, JWT blacklist |
| `customer_management` | Customer CRUD, KYC submission/review, beneficiaries, customer dashboard |
| `account_management` | Accounts, account holders, transactions, cards, cheques, credit accounts, loans, holds, nominee, cashier transactions, interest scheduler |
| `atm_management` | ATM registry + ATM transactions |
| `accounting_system` | Journal, ledger, transactions (double-entry accounting) |
| `branch_management` | Branches |
| `currency_management` | Currency rates (external API scheduler) + conversion |
| `common` | Address (division/district/police-station), email, notifications (WebSocket), security, enums, exceptions |
| `dashboard` | Aggregated dashboard data |
| `fraud_detection` | Fraud flagging + review |
| `human_resource_management` | Employees + seeder |
| `public_pages` | Public/branch/location info |
| `report_management` | Trial balance, ledger, profit & loss, balance sheet |
| `standing_order` | Recurring standing orders (scheduler) |

---

## Prerequisites

- **JDK 21** (backend)
- **Node.js 20+ / npm** (frontend)
- **MySQL 8** server running locally (`localhost:3306`, schema `elitebank`)
- **Android SDK 37** + Android Studio (mobile)
- **Maven** (or the bundled `mvnw`)
- A Gmail app-password (or any SMTP credential) for email features

---

## Getting Started

### 1. Backend (`ensark`)
```bash
cd ensark
# Configure environment (see below), then:
./mvnw spring-boot:run        # or: mvn spring-boot:run
```
- Runs on **http://localhost:8085**
- Swagger UI: **http://localhost:8085/swagger-ui.html**
- Static uploads served from `/uploads/**`

### 2. Frontend (`ensark-frontend`)
```bash
cd ensark-frontend
npm install
npm start                    # ng serve -> http://localhost:4200
```
- API base URL is configured in `src/environments/environment.ts` (`apiUrl: 'http://localhost:8085/api/'`).
- Build: `npm run build` (outputs to `dist/`).

### 3. Android (`ensarkbank-android`)
```bash
cd ensarkbank-android
./gradlew assembleDebug       # or open in Android Studio and Run
```
- Base URL is hardcoded in `api/ApiClient.java` → `BASE_URL = "http://192.168.0.102:8085/"`.
  > For an emulator use `http://10.0.2.2:8085/`; for a physical device use your dev machine's LAN IP. Update `ApiClient.BASE_URL` accordingly.
- Requires `android.permission.INTERNET` and `usesCleartextTraffic="true"` (already set in the manifest).

---

## Environment Configuration (Backend)

All sensitive values are read from environment variables (with dev fallbacks in `application.properties`):

| Variable | Purpose | Default (dev) |
|----------|---------|---------------|
| `DB_USERNAME` / `DB_PASSWORD` | MySQL credentials | `root` / `1234` |
| `JWT_SECRET` | JWT signing key (length/entropy validated at startup) | *(must be set)* |
| `SMTP_USERNAME` / `SMTP_PASSWORD` | Email SMTP credentials | Gmail dev account |
| `FRONTEND_URL` | Allowed CORS / WebSocket / email-link origin | `http://localhost:4200` |
| `UPLOAD_DIR` | File upload root | `D:/ensarkbank/uploads` |

JWT expiry windows are configurable: `jwt.expiration` (15m access), `jwt.refresh-expiration`, `jwt.emp-expiration` (8h staff), `jwt.verification-expiration` (1h), `jwt.reset-expiration` (15m).

---

## API Overview

All endpoints are prefixed with `/api`. Public auth endpoints live under `/api/auth/**` (permitAll); everything else requires a `Authorization: Bearer <token>` header.

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/auth/login` | Authenticate (rate-limited); may require MFA |
| POST | `/api/auth/verify-mfa` | Verify TOTP and complete login |
| POST | `/api/auth/setup-mfa` | Begin MFA enrollment (returns secret + QR) |
| POST | `/api/auth/confirm-mfa` | Confirm MFA setup |
| POST | `/api/auth/disable-mfa` | Disable MFA |
| POST | `/api/auth/logout` | Invalidate token (blacklist) |
| POST | `/api/auth/refresh` | Exchange refresh token for new access token |
| POST | `/api/auth/register` | Customer self-registration (multipart + KYC docs) |
| GET | `/api/auth/verify-email` | Email verification callback |
| POST | `/api/auth/send-verification` | Resend verification email |
| POST | `/api/auth/forgot-password` | Request password reset link |
| POST | `/api/auth/reset-password` | Reset password |
| POST | `/api/auth/validate` | Validate a session token (signature/expiry/blacklist) |

Other domains follow the same `/api/<domain>/...` convention (accounts, transactions, cards, loans, beneficiaries, cheques, standing-orders, atms, branches, employees, reports, currency, fraud, notifications over `/ws/**`).

### Authentication & Security
- **Stateless JWT** sessions (`JwtAuthFilter` ahead of Spring Security), BCrypt password hashing.
- **MFA** via TOTP (Google Authenticator compatible) for sensitive accounts.
- **Rate limiting** on login attempts (`RateLimitConfig`).
- **CORS** restricted to `FRONTEND_URL`; CSRF disabled (stateless); security headers (HSTS, CSP, XSS, frame-deny) enforced.
- **Token validation** mirrors `JwtAuthFilter` checks: signature, expiry, blacklist, and access-token purpose.

---

## Current Situation / Status

**Backend (`ensark`)** — *Mature / actively implemented.*
- All major banking domains have controllers, services, entities, repositories, and DTOs (MapStruct mappers).
- Auth is fully featured (MFA, refresh, email verification, reset, validation, rate limiting).
- Cross-cutting infra present: security, CORS, scheduling, retry, rate-limit, async, WebSocket notifications, OpenAPI docs.
- Recent addition: `POST /api/auth/validate` endpoint + `AuthService.validateToken` + `TokenValidationResponse`.

**Frontend (`ensark-frontend`)** — *Comprehensive SPA.*
- Three role areas implemented: **public** (home, login, register, MFA verify, forgot/reset password, branches, ATMs, services), **customer** (dashboard, accounts, transfers, beneficiaries, cards, cheques, loans, KYC, standing orders, transactions, currency converter, profile), and **staff** (customers, accounts, loans, cards, cheques, atms, branches, employees, divisions/districts/police-stations, fraud review, reports, cashier/standing-order transactions).
- Live notifications via WebSocket, charts via Chart.js, PDF/CSV export, lazy routing.

**Android (`ensarkbank-android`)** — *In progress / feature-complete skeleton.*
- Activities wired for: splash, login, register, MFA/Otp, forgot/reset password, main dashboard, accounts, transfer, cards, loans, profile, history, KYC, beneficiary, standing orders, cheques, currency converter, account opening, card application, loan application.
- Retrofit API services exist for Auth, Account, Transaction, StandingOrder, Loan, Kyc, General, Customer, Cheque, Card, Beneficiary — kept in sync with the backend.
- **Latest change:** added `AuthApiService.validateToken(...)` and the `TokenValidationResponse` model to mirror the new backend `/api/auth/validate` endpoint.
- **To do / notes:**
  - `ApiClient.BASE_URL` is hardcoded to a LAN IP (`192.168.0.102`) — switch to `10.0.2.2` for emulator or externalize via `local.properties`/`BuildConfig`.
  - Several screens may still be stubbed; verify each activity's binding before release.
  - `release` build has R8/optimization disabled (`enable false`) — enable and test before production.

**Cross-cutting**
- Git history is early-stage: `Initial commit` → `add frontend` → `added backend` → `android`. Recommended: adopt conventional commits and per-feature branches going forward.
- Each client currently targets `localhost:8085` (web) / LAN IP (Android). Align these via environment/config for shared deployments.

---

## Useful Commands

```bash
# Backend
cd ensark && ./mvnw test                 # run unit/integration tests
cd ensark && ./mvnw package              # build runnable jar

# Frontend
cd ensark-frontend && npm test           # vitest
cd ensark-frontend && npm run build      # production build

# Android
cd ensarkbank-android && ./gradlew lint  # static analysis
cd ensarkbank-android && ./gradlew assembleRelease
```

---

## Notes & Next Steps
- Externalize Android `BASE_URL` and backend secrets (don't commit real `JWT_SECRET`/SMTP passwords).
- Add integration tests for the Android layer (Retrofit + MockWebServer).
- Centralize API contract definitions (e.g., an OpenAPI spec) shared by web + mobile clients.
- Implement CI (build/lint/test) for all three modules.
