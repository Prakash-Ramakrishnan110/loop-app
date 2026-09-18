# Loop – Brand Logo & Asset Documentation

This document provides complete documentation for the **Loop** brand logo, design guidelines, color specifications, and the exact paths of all logo assets across the Android codebase, web, and app store configurations.

---

## 🎨 Logo Symbolism & Design Breakdown

The **Loop** logo is built around the concept of **continuous reciprocal community exchange**:

1. **Infinity Ribbon Path (`#FFFFFF`)**:
   - Represents transparency, continuous mutual support, and seamless on-demand service flow between neighbors.
   - Smooth 6.5-unit stroke with rounded caps (`round`), designed without sharp breaks to convey reliability.

2. **Left Node: Favor Spark / Warm Orange (`#F4A261`)**:
   - Centered inside the left loop apex at `(38, 54)`.
   - Symbolizes human warmth, community kindness, urgent household help, and neighborly support.

3. **Right Node: Time Credit / Emerald Green (`#34A853`)**:
   - Centered inside the right loop apex at `(70, 54)`.
   - Symbolizes value generation, verified escrow transactions, and mutual reciprocity.

4. **Background Tile (`#2E75B6` to `#1F4E79`)**:
   - 315° linear gradient transitioning from **Trust Blue** (`#2E75B6`) to **Deep Harbor Navy** (`#1F4E79`).
   - Represents institutional credibility, security, and professional safety.
   - 24px squircle corner radius (standard Android 108×108 viewport).

5. **Concentric Community Orbit Rings**:
   - Centered at `(54, 54)` with radii `r=42` (12% opacity) and `r=28` (15% opacity).
   - Signifies hyperlocal geographic proximity zones (1 km / 3 km neighborhood radius).

---

## 🎨 Color Palette & Specifications

| Element | HEX Code | RGB | Purpose & Usage |
| :--- | :--- | :--- | :--- |
| **Trust Blue (Primary)** | `#2E75B6` | `rgb(46, 117, 182)` | Background gradient start; conveys reliability and safety |
| **Deep Harbor Navy** | `#1F4E79` | `rgb(31, 78, 121)` | Background gradient end; adds depth and elevation |
| **Favor Spark (Orange)** | `#F4A261` | `rgb(244, 162, 97)` | Left interior node token (human warmth & favor request) |
| **Time Credit (Green)** | `#34A853` | `rgb(52, 168, 83)` | Right interior node token (growth & verified exchange) |
| **Loop Ribbon (White)** | `#FFFFFF` | `rgb(255, 255, 255)` | Continuous infinity stroke (transparency & reciprocity) |
| **Community Rings** | `#FFFFFF` | `rgba(255, 255, 255, 0.12 - 0.15)` | Concentric proximity zones |

---

## 📁 Directory & File Paths of Logo Assets

### 1. Vector Files (Figma / Web / Design Tools)

| Asset File | Absolute Path | Description |
| :--- | :--- | :--- |
| **Root SVG** | `/loop_logo.svg` | Ready-to-use 512×512 SVG for Figma, Illustrator, Canva, or Web |
| **Android Asset SVG** | `/app/src/main/assets/loop_logo.svg` | Embedded in app assets for runtime web views or SVG loaders |

### 2. Android Vector Drawables (In-App & Adaptive Launcher)

| Asset File | Absolute Path | Usage |
| :--- | :--- | :--- |
| **In-App Vector Logo** | `/app/src/main/res/drawable/ic_loop_logo.xml` | Rendered on Splash Screen, Login/Auth screen headers, and profile headers |
| **Adaptive Icon Foreground** | `/app/src/main/res/drawable/ic_launcher_foreground.xml` | Vector foreground layer with infinity ribbon and orange/green tokens (108×108 dp) |
| **Adaptive Icon Background** | `/app/src/main/res/drawable/ic_launcher_background.xml` | Vector background layer with `#2E75B6` ➔ `#1F4E79` gradient and concentric rings |
| **Monochrome Themed Icon** | `/app/src/main/res/drawable/ic_launcher_monochrome.xml` | Android 13+ dynamic Material You themed launcher icon |
| **Adaptive XML Manifest** | `/app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml` | Adaptive icon definition linking background, foreground, and monochrome |
| **Adaptive Round XML** | `/app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml` | Adaptive round icon definition for Pixel & OEM launchers |

### 3. Product Launch & App Store PNG Assets

| Asset File | Absolute Path | Dimensions | Density |
| :--- | :--- | :--- | :--- |
| **Google Play Store Icon** | `/app/src/main/playstore-icon.png` | 512 × 512 px | 32-bit PNG (Play Console submission) |
| **Web Launcher Icon** | `/app/src/main/ic_launcher-web.png` | 512 × 512 px | 32-bit PNG |
| **Drawable Web Icon** | `/app/src/main/res/drawable/ic_launcher_web.png` | 512 × 512 px | 32-bit PNG |
| **Launcher Icon (xxxhdpi)** | `/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png` | 192 × 192 px | Square launcher icon |
| **Round Icon (xxxhdpi)** | `/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png` | 192 × 192 px | Circular launcher icon |
| **Launcher Icon (xxhdpi)** | `/app/src/main/res/mipmap-xxhdpi/ic_launcher.png` | 144 × 144 px | Square launcher icon |
| **Round Icon (xxhdpi)** | `/app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png` | 144 × 144 px | Circular launcher icon |
| **Launcher Icon (xhdpi)** | `/app/src/main/res/mipmap-xhdpi/ic_launcher.png` | 96 × 96 px | Square launcher icon |
| **Round Icon (xhdpi)** | `/app/src/main/res/mipmap-xhdpi/ic_launcher_round.png` | 96 × 96 px | Circular launcher icon |
| **Launcher Icon (hdpi)** | `/app/src/main/res/mipmap-hdpi/ic_launcher.png` | 72 × 72 px | Square launcher icon |
| **Round Icon (hdpi)** | `/app/src/main/res/mipmap-hdpi/ic_launcher_round.png` | 72 × 72 px | Circular launcher icon |
| **Launcher Icon (mdpi)** | `/app/src/main/res/mipmap-mdpi/ic_launcher.png` | 48 × 48 px | Square launcher icon |
| **Round Icon (mdpi)** | `/app/src/main/res/mipmap-mdpi/ic_launcher_round.png` | 48 × 48 px | Circular launcher icon |

---

## 📐 Geometry & Safe Zone Standards

- **Viewport**: `108 × 108` (standard Android adaptive icon grid).
- **Safe Zone**: The core infinity ribbon and nodes are centered strictly within the inner **`66 × 66 dp` safe circle** (spanning `X: 26 to 82` and `Y: 42 to 66`). This ensures zero clipping when manufacturers apply circular, squircle, or pebble masks.
- **Stroke Width**: `6.5` units with `stroke-linecap="round"` and `stroke-linejoin="round"`.
- **Node Geometry**:
  - Left Token: radius `4.5` units, centered at `(38, 54)`.
  - Right Token: radius `4.5` units, centered at `(70, 54)`.
- **Concentric Circles**: Centered at `(54, 54)` with radii of `28` and `42` units.

---

## 💻 How to Use in Code

### In Jetpack Compose:
```kotlin
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.R

Image(
    painter = painterResource(id = R.drawable.ic_loop_logo),
    contentDescription = "Loop Logo",
    modifier = Modifier.size(56.dp)
)
```

### In Web / HTML:
```html
<img src="/loop_logo.svg" alt="Loop Logo" width="64" height="64" />
```

### In Android Manifest (`AndroidManifest.xml`):
```xml
<application
    android:icon="@mipmap/ic_launcher"
    android:roundIcon="@mipmap/ic_launcher_round"
    ...>
```
