<div align="center">

<img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Backend-Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black"/>
<img src="https://img.shields.io/badge/Architecture-MVVM-4285F4?style=for-the-badge&logo=google&logoColor=white"/>
<img src="https://img.shields.io/badge/Min%20SDK-API%2024-brightgreen?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Target%20SDK-API%2034-blue?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Status-Complete-success?style=for-the-badge"/>

<br/><br/>

# Daily Groceries

### A native Android grocery shopping app built with Kotlin and Firebase

Browse products · Manage your cart in real time · Confirm orders · Track purchase history

<br/>

</div>

---

## Table of Contents

1. [Overview](#-overview)
2. [Features](#-features)
3. [Architecture](#-architecture)
4. [Tech Stack](#-tech-stack)
5. [Project Structure](#-project-structure)
6. [Getting Started](#-getting-started)
7. [Firebase Setup](#-firebase-setup)
8. [App Navigation](#-app-navigation)
9. [Screen Reference](#-screen-reference)
10. [Design System](#-design-system)
11. [App Lifecycle](#-app-lifecycle)
12. [Known Limitations](#-known-limitations)
13. [Developer](#-developer)

---

## 🌟 Overview

**Daily Groceries** is a fully functional, production-grade Android application that simulates a real grocery shopping platform. The entire backend is powered by **Firebase**, providing real-time data sync, secure authentication, and scalable cloud storage with zero server management.

The app was designed around three core principles:

- **Speed** — every screen loads within 2 seconds; cart updates reflect instantly via Firebase Realtime Database
- **Accessibility** — built for users aged 18–60, including those with limited mobile experience; all text uses `sp` units to honour system font size settings
- **Security** — Firebase Authentication handles all credential management; all traffic runs over HTTPS; passwords are never stored in plain text

---

## ✨ Features

### Authentication
- Email and password **registration and login** via Firebase Authentication
- **Persistent sessions** — users remain signed in after closing the app
- Full **input validation** on all fields with descriptive inline error messages
- Secure **logout** from the profile screen

### Home Screen
- Prominent **search bar** for instant product discovery
- Horizontally scrollable **discounted items** shelf with percentage labels
- **Category quick-access grid** — Vegetables, Fruits, Snacks, Sweets, Beverages
- **Recently viewed items** row for fast re-access to previously browsed products

### Category Browsing
- Products displayed in a responsive **grid layout** showing image, name, and price
- Selecting a category filters the entire product catalogue instantly
- Clean card-style UI with consistent spacing across all product tiles

### Search
- **Real-time search** — results update as the user types, with no submit button required
- Displays a friendly **"No results found"** message for unmatched queries
- Results presented in a scrollable list with product image and price visible

### Product Detail
- Full-screen product view with a **large image**, name, price, and description
- **"Add to Cart"** button adds the item and confirms with a toast notification
- **"Go to Cart"** button provides a direct shortcut to the cart screen

### Cart Management
- Lists every item with its **image, name, price, and quantity**
- **Live +/− quantity controls** per item — total price recalculates instantly
- **Remove individual items** or **clear the entire cart** in one tap
- Total is prominently displayed above the checkout button at all times
- Cart state is **synced to Firebase Realtime Database** so it persists across sessions

### Order Confirmation
- Pre-checkout **order summary screen** showing:
  - Customer name, delivery address, and phone number
  - Itemised product list with quantities
  - Grand total
- **"Confirm Order"** finalises the purchase and writes the order to Firebase
- Instant **toast notification**: *"Order confirmed! Thank you for your purchase."*

### User Profile
- Displays account details: **name, email, address, and phone number**
- **Logout** button with a single tap
- **"View Purchase History"** navigates to the full order log

### Purchase History
- Complete chronological list of all past orders
- Each order shows: **transaction ID, date and time, all items with quantities and individual prices, and the grand total**
- Handles the empty state gracefully: *"No Purchase History Available"*

---

## 🏗️ Architecture

The app is built on the **MVVM (Model–View–ViewModel)** pattern using Android Jetpack components. This keeps the codebase modular, testable, and easy to maintain.

```
┌─────────────────────────────────────────────────────────────┐
│                         UI Layer                            │
│                                                             │
│   Activities          XML Layouts         RecyclerView      │
│   (one per screen)    (res/layout/)       Adapters          │
└──────────────────────────────┬──────────────────────────────┘
                               │
                    Observes LiveData /
                    Calls ViewModel methods
                               │
┌──────────────────────────────▼──────────────────────────────┐
│                      ViewModel Layer                        │
│                                                             │
│   Holds and exposes UI state via LiveData                   │
│   Contains all business logic                               │
│   Survives configuration changes (rotation, etc.)           │
└──────────────────────────────┬──────────────────────────────┘
                               │
                    Reads from / writes to
                               │
┌──────────────────────────────▼──────────────────────────────┐
│                       Data Layer                            │
│                                                             │
│   Firebase Authentication    Firebase Realtime Database     │
│   (user sessions)            (products, cart, orders)       │
│                                                             │
│   Firebase Cloud Messaging                                  │
│   (order notifications)                                     │
└─────────────────────────────────────────────────────────────┘
```

### Why MVVM?

| Concern | How MVVM addresses it |
|---|---|
| UI ↔ Logic separation | Activities only observe data — no business logic in XML controllers |
| Configuration changes | ViewModel survives rotation; no data loss on screen flip |
| Testability | ViewModels can be unit tested without an Android device or emulator |
| Scalability | Adding a new screen means a new Activity + ViewModel pair — no refactoring of existing code |

---

## 🛠️ Tech Stack

| Category | Technology | Purpose |
|---|---|---|
| **Language** | Kotlin | Primary development language |
| **UI** | XML Layouts | Declarative screen design |
| **Layouts** | ConstraintLayout, GridLayout | Responsive, density-independent layouts |
| **Lists** | RecyclerView | Efficient scrollable product and order lists |
| **Architecture** | ViewModel + LiveData | MVVM state management |
| **Authentication** | Firebase Authentication | Secure email/password login and registration |
| **Database** | Firebase Realtime Database | Real-time cart, product, and order sync |
| **Notifications** | Firebase Cloud Messaging | Order confirmation push notifications |
| **Build System** | Gradle (Kotlin DSL) | Dependency and build management via `.kts` files |
| **Min SDK** | API 24 (Android 7.0) | Covers ~94% of active Android devices |
| **Target SDK** | API 34 (Android 14) | Latest Android features and security patches |

---

## 📂 Project Structure

```
Grocery-Shopping-App-Kotlin/
│
├── app/
│   ├── google-services.json              ← Firebase config (not committed — add manually)
│   └── src/
│       ├── main/
│       │   ├── java/com/<package>/
│       │   │   │
│       │   │   ├── activities/           ← One Activity per screen
│       │   │   │   ├── SplashActivity.kt
│       │   │   │   ├── LoginActivity.kt
│       │   │   │   ├── SignUpActivity.kt
│       │   │   │   ├── HomeActivity.kt
│       │   │   │   ├── CategoryItemsActivity.kt
│       │   │   │   ├── ItemInfoActivity.kt
│       │   │   │   ├── CartActivity.kt
│       │   │   │   ├── CheckoutActivity.kt
│       │   │   │   ├── ProfileActivity.kt
│       │   │   │   └── PurchaseHistoryActivity.kt
│       │   │   │
│       │   │   ├── adapters/             ← RecyclerView adapters
│       │   │   │   ├── ProductAdapter.kt
│       │   │   │   ├── CartAdapter.kt
│       │   │   │   ├── CategoryAdapter.kt
│       │   │   │   └── OrderHistoryAdapter.kt
│       │   │   │
│       │   │   ├── models/               ← Kotlin data classes
│       │   │   │   ├── User.kt
│       │   │   │   ├── Product.kt
│       │   │   │   ├── CartItem.kt
│       │   │   │   └── Order.kt
│       │   │   │
│       │   │   ├── viewmodels/           ← MVVM ViewModels
│       │   │   │   ├── HomeViewModel.kt
│       │   │   │   ├── CartViewModel.kt
│       │   │   │   └── ProfileViewModel.kt
│       │   │   │
│       │   │   └── utils/               ← Helpers and extensions
│       │   │       ├── FirebaseUtils.kt
│       │   │       └── Extensions.kt
│       │   │
│       │   ├── res/
│       │   │   ├── layout/              ← Default (portrait) XML layouts
│       │   │   ├── layout-land/         ← Landscape layout overrides
│       │   │   ├── layout-sw600dp/      ← Tablet-specific layouts
│       │   │   ├── drawable/            ← Vector icons and assets
│       │   │   ├── mipmap-mdpi/         ← App icon — medium density
│       │   │   ├── mipmap-hdpi/         ← App icon — high density
│       │   │   ├── mipmap-xhdpi/        ← App icon — extra high density
│       │   │   ├── mipmap-xxhdpi/       ← App icon — extra extra high density
│       │   │   ├── mipmap-xxxhdpi/      ← App icon — highest density
│       │   │   └── values/
│       │   │       ├── colors.xml       ← Colour palette
│       │   │       ├── strings.xml      ← All UI strings (internationalisation-ready)
│       │   │       └── themes.xml       ← App theme and style overrides
│       │   │
│       │   └── AndroidManifest.xml      ← App permissions, activities, and metadata
│       │
│       └── test/                        ← Unit tests
│
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
│
├── build.gradle.kts                     ← Project-level build configuration
├── settings.gradle.kts                  ← Module declarations
├── gradle.properties                    ← JVM and Gradle flags
├── gradlew                              ← Unix build script
├── gradlew.bat                          ← Windows build script
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

Make sure the following are installed before cloning:

| Tool | Required Version | Download |
|---|---|---|
| Android Studio | Hedgehog (2023.1.1) or later | [developer.android.com](https://developer.android.com/studio) |
| JDK | 17 or later | Bundled with Android Studio |
| Android SDK | API 34 | Android Studio SDK Manager |
| Emulator or device | API 24+ (Android 7.0+) | Android Studio AVD Manager |

### Clone the Repository

```bash
git clone https://github.com/yaseensharaf/Grocery-Shopping-App-Kotlin.git
cd Grocery-Shopping-App-Kotlin
```

### Open in Android Studio

```
File → Open → select the Grocery-Shopping-App-Kotlin folder → OK
```

Wait for the Gradle sync to complete. You will see a build error until `google-services.json` is added — this is expected.

### Build

```bash
# Command line
./gradlew assembleDebug

# Or press the green ▶ Run button in Android Studio
```

> ⚠️ **Important:** The project will not build without `google-services.json`. Complete the Firebase setup below first.

---

## 🔥 Firebase Setup

### Step 1 — Create a Firebase Project

1. Go to [console.firebase.google.com](https://console.firebase.google.com)
2. Click **"Add project"** → name it (e.g. `daily-groceries`) → continue
3. Disable Google Analytics if not needed → **Create project**

### Step 2 — Register the Android App

1. In your Firebase project, click the **Android icon** (Add app)
2. Enter your **package name** — found at the top of `AndroidManifest.xml`:
   ```xml
   <manifest xmlns:android="..."
       package="com.yourname.groceries">   ← copy this value
   ```
3. Enter an app nickname (optional) → **Register app**
4. Download **`google-services.json`**
5. Place it here — exact location matters:
   ```
   Grocery-Shopping-App-Kotlin/
   └── app/
       └── google-services.json    ← must be here
   ```

### Step 3 — Enable Authentication

1. Firebase Console → **Authentication** → **Get started**
2. **Sign-in method** tab → **Email/Password** → Enable → **Save**

### Step 4 — Enable Realtime Database

1. Firebase Console → **Realtime Database** → **Create database**
2. Choose a region → **Start in test mode** (for development)
3. Click **Enable**

### Step 5 — Set Security Rules

For development, test mode allows open access. Before releasing, replace the rules with:

```json
{
  "rules": {
    "users": {
      "$uid": {
        ".read":  "$uid === auth.uid",
        ".write": "$uid === auth.uid"
      }
    },
    "products": {
      ".read":  "auth != null",
      ".write": false
    },
    "orders": {
      "$uid": {
        ".read":  "$uid === auth.uid",
        ".write": "$uid === auth.uid"
      }
    }
  }
}
```

### Step 6 — Sync and Run

1. Return to Android Studio
2. **File → Sync Project with Gradle Files**
3. Press **▶ Run** — the app should build and launch

---

## 🧭 App Navigation

The app uses a **bottom navigation bar** that is persistent across all main screens, giving users one-tap access to Home, Search, Cart, and Profile at any time.

```
┌─────────────────────────────────────────────────────────────┐
│  ENTRY                                                      │
│                                                             │
│  Splash Screen                                              │
│       │                                                     │
│       ├── Logged in? ──────────────────▶ Home Screen        │
│       └── Not logged in? ──────────────▶ Login Screen       │
│                                              │              │
│                                         Sign Up Screen      │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│  MAIN APP  (persistent bottom navigation)                   │
│                                                             │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────────┐   │
│  │  Home   │  │ Search  │  │  Cart   │  │   Profile   │   │
│  └────┬────┘  └────┬────┘  └────┬────┘  └──────┬──────┘   │
│       │            │            │               │           │
│  Category     Search        Checkout       Purchase         │
│  Screen       Results       Screen         History          │
│       │            │            │                           │
│  Product      Product       Order                           │
│  Detail       Detail        Confirmed ✓                     │
│       │            │                                        │
│  Add to Cart ◀─────┘                                        │
└─────────────────────────────────────────────────────────────┘
```

---

## 📋 Screen Reference

A quick reference for every screen in the app — what it does, what it reads from Firebase, and what it writes.

| Screen | Description | Firebase Read | Firebase Write |
|---|---|---|---|
| **Splash** | Entry point — checks auth state, routes to login or home | Auth state | — |
| **Login** | Email + password authentication | — | Auth session |
| **Sign Up** | Creates account and stores user profile | — | Auth + `users/{uid}` |
| **Home** | Displays categories, discounts, recently viewed | `products/`, `users/{uid}/recentlyViewed` | `users/{uid}/recentlyViewed` |
| **Category** | Filtered product grid | `products/{category}` | — |
| **Product Detail** | Full product info with add-to-cart | `products/{id}` | `cart/{uid}/{productId}` |
| **Search** | Real-time keyword product search | `products/` | — |
| **Cart** | Quantity management and checkout entry | `cart/{uid}` | `cart/{uid}` |
| **Order Confirmation** | Pre-checkout summary and confirm action | `cart/{uid}`, `users/{uid}` | `orders/{uid}/{orderId}` |
| **Profile** | User details and logout | `users/{uid}` | Auth (logout) |
| **Purchase History** | Full order log | `orders/{uid}` | — |

---

## 🎨 Design System

### Colour Palette

| Name | Hex | RGB | Usage |
|---|---|---|---|
| Primary Purple | `#6200EE` | `(98, 0, 238)` | Buttons, top bar, active nav icons, accents |
| Secondary Teal | `#018786` | `(1, 135, 134)` | Secondary buttons, highlights |
| White | `#FFFFFF` | `(255, 255, 255)` | All screen backgrounds |
| Black | `#000000` | `(0, 0, 0)` | Body text and headings |
| Surface Gray | `#EEEEEE` | `(238, 238, 238)` | Cards, dividers, input backgrounds |

### Typography

| Element | Unit | Notes |
|---|---|---|
| All font sizes | `sp` (scale-independent pixels) | Respects the user's system font size setting |
| Headings | `20sp` bold | Screen titles and section headers |
| Body | `16sp` regular | Product names, descriptions, list items |
| Captions | `13sp` regular | Prices, metadata, timestamps |

### Dimensions and Spacing

| Measurement | Unit | Reason |
|---|---|---|
| Margins and padding | `dp` (density-independent pixels) | Consistent physical size across screen densities |
| Icons | Vector drawables | Crisp rendering at any resolution, small APK size |
| App icon | Adaptive Icon | Supports all Android launcher shapes (circle, squircle, etc.) |

### Responsive Layouts

| Device type | Qualifier | Approach |
|---|---|---|
| Phone portrait | *(default)* | Vertical single-column ConstraintLayout |
| Phone landscape | `layout-land/` | Adjusted proportions, wider content areas |
| Tablet (600dp+) | `layout-sw600dp/` | Wider grids (more columns), split-view on some screens |

---

## ♻️ App Lifecycle

Android activities go through defined lifecycle states. Here is how the app handles each one:

| State | Trigger | App Behaviour |
|---|---|---|
| **Created** | App launch | Firebase initialised, splash screen shown, auth state checked, deep links processed |
| **Started** | Activity becomes visible | LiveData observers registered, UI populated from ViewModel |
| **Resumed** | Activity in foreground | Animations run, real-time Firebase listeners active, cart synced |
| **Paused** | Another activity partially covers this one | Animations paused, transient state written to SharedPreferences |
| **Stopped** | Activity fully hidden | Non-critical resources released, order and cart data persisted to Firebase |
| **Destroyed** | Back pressed or system kills process | Firebase listeners removed, database connections closed, incomplete data saved |

### Handling System Events

| Event | Handling strategy |
|---|---|
| **Screen rotation** | `ViewModel` retains all data; `onSaveInstanceState` saves scroll position and UI state |
| **Low memory warning** | Image caches cleared, non-visible adapter data released |
| **App killed by OS** | SharedPreferences and Firebase ensure no cart or profile data is lost on next launch |

---



## 👨‍💻 Developer

<div align="center">

Built entirely by **Yaseen Sharaf**

[![GitHub](https://img.shields.io/badge/GitHub-yaseensharaf-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/yaseensharaf)

*Academic project — UFCF7H-15-3 Mobile App Design*

</div>

---

## 📄 License

```
Copyright © 2025 Yaseen Sharaf
All rights reserved.

This project was developed for academic purposes.
Unauthorised copying, redistribution, or commercial use is not permitted.
```
