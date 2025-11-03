# Key Files Reference

This document explains the most important files in the project and what they do.

## Configuration Files

### `app/src/main/AndroidManifest.xml`
**What it does:** Main configuration file for the Android app
**Key contents:**
- App package name (`com.pixel.dronerelayapp`)
- DJI SDK API Key (⚠️ YOU MUST EDIT THIS)
- All permissions (USB, Location, Storage, Camera, etc.)
- Activity declarations
- USB accessory filter

**You need to edit:**
Line 47: Replace `PLEASE_ADD_YOUR_API_KEY_HERE` with your DJI API key

### `app/build.gradle`
**What it does:** Defines app dependencies and build settings
**Key contents:**
- DJI MSDK v5.16.0 dependency
- AndroidX libraries
- Kotlin configuration
- Build types (debug/release)
- ProGuard rules

**Important:** MSDK version is set here (`5.16.0`)

### `build.gradle` (root)
**What it does:** Project-level build configuration
**Key contents:**
- Gradle plugin versions
- Repository URLs (including DJI Maven)
- Kotlin version

### `gradle.properties`
**What it does:** Gradle build properties
**Key contents:**
- Memory allocation for build
- AndroidX enablement
- Build optimization settings

## Source Code Files

### `app/src/main/java/com/pixel/dronerelayapp/DJIApplication.kt`
**What it does:** Application entry point, initializes DJI SDK
**Key functionality:**
- Extends Android `Application` class
- Calls `SDKManager.getInstance().init()` to register SDK
- Handles SDK registration callbacks
- Provides registration status to other components

**Important methods:**
- `onCreate()` - Called when app starts
- `initSDK()` - Initializes DJI SDK
- `isSDKRegistered()` - Returns registration status

### `app/src/main/java/com/pixel/dronerelayapp/MainActivity.kt`
**What it does:** Main UI screen, shows connection status
**Key functionality:**
- Requests runtime permissions (location, storage, etc.)
- Displays SDK registration status
- Detects and displays connected drone
- Shows drone information

**Important methods:**
- `onCreate()` - Sets up UI
- `checkAndRequestPermissions()` - Handles permissions
- `setupProductConnectionListener()` - Monitors drone connection
- `updateConnectionStatus()` - Updates UI with connection info

### `app/src/main/java/com/pixel/dronerelayapp/UsbAttachActivity.kt`
**What it does:** Handles USB connection events
**Key functionality:**
- Triggered when USB device (DJI controller) is connected
- Launches MainActivity
- Transparent activity (user doesn't see it)

## Resource Files

### `app/src/main/res/layout/activity_main.xml`
**What it does:** Defines the main UI layout
**Key contents:**
- SDK Status card
- Connection Status card
- Drone Information card
- Setup Instructions card

**To customize UI:** Edit this file in Android Studio's Layout Editor

### `app/src/main/res/values/strings.xml`
**What it does:** Contains all text strings used in the app
**Why important:** Makes text easy to translate and modify

### `app/src/main/res/values/colors.xml`
**What it does:** Defines color palette
**Key colors:**
- `dji_blue` - Primary brand color
- `status_green` - Success messages
- `status_red` - Error messages
- `status_orange` - Warning messages

### `app/src/main/res/values/themes.xml`
**What it does:** Defines app theme and styling
**Based on:** Material Components Dark theme

### `app/src/main/res/xml/accessory_filter.xml`
**What it does:** USB device filter for DJI controllers
**Key contents:** Matches USB accessories with manufacturer "DJI"

### `app/src/main/res/xml/file_paths.xml`
**What it does:** FileProvider configuration for sharing files
**Used for:** Sharing logs, photos, videos from app

## Icon Files

### `app/src/main/res/mipmap-*/ic_launcher.png`
**What it does:** App launcher icons for different screen densities
**Sizes:**
- `mipmap-mdpi` - 48x48 px (medium)
- `mipmap-hdpi` - 72x72 px (high)
- `mipmap-xhdpi` - 96x96 px (extra high)
- `mipmap-xxhdpi` - 144x144 px (extra extra high)
- `mipmap-xxxhdpi` - 192x192 px (extra extra extra high)

**To change:** Replace these files with your own icon

## Build Files

### `.gitignore`
**What it does:** Tells Git which files to ignore
**Ignores:**
- Build outputs (`build/`, `*.apk`)
- IDE files (`.idea/`, `*.iml`)
- Local configuration (`local.properties`)

### `settings.gradle`
**What it does:** Defines which modules are in the project
**Current modules:** `:app`

### `gradle/wrapper/`
**What it does:** Contains Gradle wrapper for consistent builds
**Don't modify:** These ensure everyone uses same Gradle version

### `gradlew` and `gradlew.bat`
**What they do:** Gradle wrapper scripts
- `gradlew` - For Linux/Mac
- `gradlew.bat` - For Windows

**Usage:** `./gradlew build` (instead of `gradle build`)

## Documentation Files

### `README.md`
**What it does:** Main project documentation
**Contents:**
- Overview and features
- Requirements
- Setup instructions
- Project structure
- Troubleshooting
- Resources

### `QUICKSTART.md`
**What it does:** Fast setup guide
**Contents:**
- Step-by-step setup (5 steps)
- Common problems and solutions
- Quick reference

### `TESTING.md`
**What it does:** Testing guide
**Contents:**
- What to test
- Testing procedures
- Common issues
- Debugging tips

### `KEYFILES.md` (this file)
**What it does:** Explains all important files
**Purpose:** Help developers understand the project structure

## File Edit Priority

When customizing the app, edit files in this order:

### 1. Must Edit (to make app work)
- ✅ `AndroidManifest.xml` - Add your DJI API key

### 2. Often Edit (for customization)
- `MainActivity.kt` - Add your app logic
- `activity_main.xml` - Customize UI
- `strings.xml` - Change text
- `colors.xml` - Change colors
- Icon files - Change app icon

### 3. Sometimes Edit (for advanced features)
- `app/build.gradle` - Add dependencies
- `DJIApplication.kt` - Change SDK initialization
- `themes.xml` - Change app styling

### 4. Rarely Edit (build configuration)
- `build.gradle` (root)
- `gradle.properties`
- `settings.gradle`
- ProGuard rules

### 5. Don't Edit (auto-generated or system files)
- `gradle/wrapper/` contents
- `.gitignore` (unless adding new patterns)
- `.idea/` files (if they appear)

## Quick File Locations

```
AndroidManifest.xml → app/src/main/AndroidManifest.xml
MainActivity → app/src/main/java/com/pixel/dronerelayapp/MainActivity.kt
Main Layout → app/src/main/res/layout/activity_main.xml
Strings → app/src/main/res/values/strings.xml
Colors → app/src/main/res/values/colors.xml
App Icon → app/src/main/res/mipmap-*/ic_launcher.png
Build Config → app/build.gradle
Dependencies → app/build.gradle (dependencies section)
API Key → AndroidManifest.xml (line 47)
```

## File Size Reference

Approximate sizes:
- Source code files (`.kt`): 2-10 KB each
- Layout files (`.xml`): 2-10 KB each
- Icons (`.png`): 5-50 KB each
- Build files (`.gradle`): 1-5 KB each
- Documentation (`.md`): 5-20 KB each
- Gradle wrapper (`.jar`): ~60 KB

Total project size (without build outputs): ~150-200 KB

After first build: ~500 MB (includes downloaded dependencies)

## Need Help?

- Can't find a file? Use Android Studio's search: `Cmd/Ctrl + Shift + F`
- Don't understand a file? Check the comments inside it
- Want to add a new file? Right-click folder → New → [File Type]

## Related Documentation

- [README.md](README.md) - Full documentation
- [QUICKSTART.md](QUICKSTART.md) - Fast setup
- [TESTING.md](TESTING.md) - Testing guide
