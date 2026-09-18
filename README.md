# Loop – Hyper-Local Services Booking Platform

**Loop** is an all-in-one on-demand hyper-local services and event staffing application platform.

---

## 🌟 Platform Overview

The platform unites three core portals in an interconnected ecosystem:
1. **Customer Mobile App**: Explore local services, compare verified professionals within 5 km, book specialized home repairs (plumbers, electricians, painters, carpenters, AC technicians) or event/wedding staff (banquet waiters, helpers, cleaning crew), and pay securely via Razorpay UPI/cards.
2. **Service Provider Side (Loop Partner Pro)**: In-app professional workspace for accepting/declining job requests, managing availability (Online/Offline), updating real-time job milestones (*Requested → Confirmed → On the Way → In Progress → Completed*), and withdrawing earnings directly to UPI IDs with platform commission deductions.
3. **Admin Dashboard (Web Console)**: Centralized command center for managing customers, approving/rejecting service professionals with Aadhaar KYC verification, monitoring live bookings, configuring platform commission percentages (e.g. 15%), issuing coupons/discounts, and resolving customer support complaints.

---

## 🎨 Design System & Brand Logo

- **Primary Brand Color**: `#2E75B6` (Trust Blue)
- **Primary Dark**: `#1F4E79` (Deep Harbor Navy)
- **Favor Spark Orange**: `#F4A261` (Warm Community Token)
- **Time Credit Green**: `#34A853` (Verified Escrow Token)
- **Loop Ribbon**: `#FFFFFF` (Continuous Reciprocal Infinity)
- **Background**: `#F8FAFC` (Light) / `#0F172A` (Dark)
- **Cards**: `#FFFFFF` / `#1E293B`
- **Corner Radii**: 12dp – 16dp rounded cards with soft tonal elevations
- **Typography**: Responsive Material 3 Typography system with high contrast readability
- **Bilingual**: One-tap toggle between **English** and **Hindi (हिंदी)**

### 💎 Brand Logo & Asset Paths
Full brand guidelines and vector specifications are documented in **[LOGO_README.md](LOGO_README.md)**.

- **Standalone Vector SVG**: `/loop_logo.svg` & `/app/src/main/assets/loop_logo.svg`
- **Android In-App Vector**: `/app/src/main/res/drawable/ic_loop_logo.xml`
- **Adaptive Foreground**: `/app/src/main/res/drawable/ic_launcher_foreground.xml`
- **Adaptive Background**: `/app/src/main/res/drawable/ic_launcher_background.xml`
- **Monochrome Themed Icon**: `/app/src/main/res/drawable/ic_launcher_monochrome.xml`
- **Google Play Store Launch Icon (512x512)**: `/app/src/main/playstore-icon.png` & `/app/src/main/ic_launcher-web.png`
- **Mipmap Density Icons**: `/app/src/main/res/mipmap-{mdpi,hdpi,xhdpi,xxhdpi,xxxhdpi}/ic_launcher.png`

---

## 🚀 Key Functional Modules

### 1. Customer Application
- **Onboarding & Auth**: Multi-step visual walkthrough and 4-digit OTP phone authentication.
- **Location Selector & Radius**: Hyper-local discovery centered around Indiranagar, Bengaluru (5 km radius).
- **Service Categories Grid**: Plumbers, Electricians, Deep Cleaners, Carpenters, Painters, AC Techs, and Event/Marriage Staffing.
- **Special Feature – Event & Marriage Staff Booking**:
  - Purpose-built flow for booking banquet waiters, kitchen/catering helpers, supplier/runner boys, and post-event cleanup crew for grand weddings and private functions.
  - Interactive crew count (number of people) and duration (hours) stepper controls with instant billing calculations.
- **Search & Live Filters**: Instant query parsing matching professionals, categories, and specific tasks.
- **Professional Profiles**: Ratings, review breakdowns, completed jobs counter, Aadhaar verification badges, distance, bio, and catalog rate cards.
- **Booking & Checkout**: Date/time slot picker, service address, custom repair instructions, promo coupon application (`LOOP50`, `EVENT15`), GST 18% calculation, and real-time total breakdown.
- **Razorpay Payment Gateway**: Simulated multi-mode payment dialog supporting UPI (Google Pay, PhonePe, Paytm, BHIM), Credit/Debit Cards (Visa, MasterCard, RuPay), and Net Banking.
- **Live Status Tracker**: Real-time progress timeline (*Requested → Confirmed → On the Way → In Progress → Completed*).
- **In-App Live Chat**: Real-time messaging between customer and assigned professional.
- **Rating & Reviews**: Interactive 5-star rating and customer review submission.
- **Loop Wallet**: Real-time cash balance tracking.
- **Support & Ticketing**: 24x7 help desk with ticket creation and admin resolution tracking.

### 2. Service Provider Portal (Loop Partner Pro)
- **Partner Registration Form**: Service category selection, hourly rate definition, experience years, Aadhaar number + document verification upload simulation, and payout UPI ID setup.
- **Availability Toggle**: Real-time Online / Offline status switch.
- **Job Dispatch Terminal**: Instant Accept / Reject cards for incoming bookings.
- **Milestone Actions**:
  - *Accept Job* → transitions to Confirmed
  - *Start Trip* → transitions to On the Way
  - *Start Work* → transitions to In Progress
  - *Mark Completed* → triggers payment settlement
- **Provider Wallet & UPI Payout**: Real-time earnings summary minus platform commission with one-tap IMPS UPI withdrawal.

### 3. Admin Command Center (Web Console)
- **Overview Analytics**: Total registered users, verified professionals, active/completed bookings, gross merchandise value (GMV), and net commission earned.
- **Users Management**: Customer directory with account status and Block/Unblock actions.
- **Professionals & KYC Management**: Identity verification, Aadhaar document check, and instant approval/rejection toggle.
- **Bookings Management**: Real-time monitoring of all bookings with cancellation and Razorpay refund triggers.
- **Coupons Engine**: Create discount codes with percentage discounts, maximum savings limits, and minimum order values.
- **Support Complaints**: View, reply to, and resolve customer grievances.
- **Commission Engine**: Live configuration of platform commission percentages.

---

## 📱 Role Switching in App

A dedicated **LOOP MODES** bar is present at the top of the interface, allowing immediate switching between:
- **Customer**: Explores and books home services or wedding crew.
- **Provider**: Terminal for accepting bookings, updating statuses, and requesting payouts.
- **Admin Web**: Complete administrator management dashboard.

---

## 🛠️ Tech Stack & Standards

- **Language**: Kotlin 2.0+
- **UI Framework**: Jetpack Compose with Material Design 3 (M3)
- **State Management**: Kotlin Coroutines `StateFlow` reactive repository pattern
- **Image & Icon Loading**: Coil Compose & Material Icons Extended
- **Architecture**: Clean MVVM Architecture with single source of truth (`LoopRepository`)
- **Theme**: Centralized `LoopTheme` supporting light and dark modes
- **Packaging**: Android Gradle Plugin with unique `applicationId: com.aistudio.loop.vnbk`
