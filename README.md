# DJI MSDK v5 Sample Application - DroneRelayAppV2

This is an Android application built with **DJI Mobile SDK (MSDK) v5.16.0**, designed to work with **DJI Mini 4 Pro** and other compatible DJI aircraft.

## Overview

This application provides a working sample of DJI MSDK v5 integration, demonstrating:
- SDK registration and initialization
- Product connection detection
- Basic drone information display
- USB connection handling with DJI RC-N2 controller

## Supported Devices

### Drones
- **DJI Mini 4 Pro** (Primary target)
- DJI Mini 3 Pro
- DJI Mini 3
- Mavic 3 Enterprise Series
- Matrice 30 Series
- Matrice 300 RTK
- Matrice 350 RTK
- Matrice 400 Series
- And other MSDK v5 compatible aircraft

### Controllers
- **DJI RC-N2** (Required for Mini 4 Pro)
- Other compatible DJI remote controllers

**Important:** The DJI RC 2 controller with built-in screen does NOT support third-party apps.

## Requirements

### Development Environment
- **Android Studio** (Latest version recommended, minimum: Android Studio Flamingo)
- **JDK 8 or higher**
- **Android SDK API Level 24+** (Android 7.0 Nougat or higher)
- **Gradle 7.4+**

### Hardware
- Android device or phone with:
  - Android 7.0 (API 24) or higher
  - USB OTG support
  - ARM64 processor (arm64-v8a)
- DJI Remote Controller (RC-N2 for Mini 4 Pro)
- DJI Aircraft (e.g., Mini 4 Pro)
- USB cable to connect phone to remote controller

### DJI Developer Account
You must register for a DJI Developer account and create an app to get an API key.

## Setup Instructions

### 1. Get DJI API Key

1. Visit [DJI Developer Portal](https://developer.dji.com/)
2. Register for a developer account (if you don't have one)
3. Go to [My Apps](https://developer.dji.com/user/apps/)
4. Click "Create App"
5. Fill in the application details:
   - **App Name:** Your app name
   - **SDK:** Mobile SDK
   - **Package Name:** `com.pixel.dronerelayapp` (must match exactly)
   - **Category:** Select appropriate category
6. Submit and get your **App Key**

### 2. Configure API Key

Open `app/src/main/AndroidManifest.xml` and replace the placeholder with your API key:

```xml
<meta-data
    android:name="com.dji.sdk.API_KEY"
    android:value="YOUR_API_KEY_HERE" />
```

**Important:** Replace `PLEASE_ADD_YOUR_API_KEY_HERE` with your actual DJI API key.

### 3. Build the Application

#### Using Android Studio:

1. Clone this repository:
   ```bash
   git clone https://github.com/sylvaingranges-pixel/droneRelayAppV2.git
   cd droneRelayAppV2
   ```

2. Open Android Studio

3. Select **File → Open** and navigate to the cloned directory

4. Wait for Gradle sync to complete (this may take a few minutes)

5. Connect your Android device via USB or set up an emulator (Note: USB features require a physical device)

6. Click **Run** (green play button) or press `Shift + F10`

#### Using Command Line:

```bash
# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Or build and install in one step
./gradlew installDebug
```

The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### 4. Install and Test

1. **Enable Developer Options** on your Android device:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Go back to Settings → Developer Options
   - Enable "USB Debugging"

2. **Install the app** on your Android device (use the APK or install via Android Studio)

3. **Connect your hardware:**
   - Power on your DJI Mini 4 Pro
   - Power on your DJI RC-N2 controller
   - Connect your Android device to the RC-N2 controller via USB cable
   - Grant USB permissions when prompted

4. **Launch the app:**
   - The app should auto-launch when USB is connected
   - Or manually launch "DJI MSDK Sample" from your app drawer

5. **Verify connection:**
   - SDK Status should show "DJI SDK registered successfully!"
   - Connection Status should show your connected product (e.g., "Product Connected: MINI_4_PRO")

## Project Structure

```
droneRelayAppV2/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/pixel/dronerelayapp/
│   │   │   │   ├── DJIApplication.kt          # SDK initialization
│   │   │   │   ├── MainActivity.kt             # Main UI and logic
│   │   │   │   └── UsbAttachActivity.kt        # USB connection handler
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml       # Main UI layout
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml             # String resources
│   │   │   │   │   ├── colors.xml              # Color definitions
│   │   │   │   │   └── themes.xml              # App theme
│   │   │   │   └── xml/
│   │   │   │       ├── accessory_filter.xml    # USB accessory filter
│   │   │   │       └── file_paths.xml          # File provider paths
│   │   │   └── AndroidManifest.xml             # App manifest with permissions
│   │   └── build.gradle                         # App module build config
│   └── proguard-rules.pro                       # ProGuard rules
├── gradle/                                       # Gradle wrapper
├── build.gradle                                  # Project build config
├── settings.gradle                               # Project settings
├── gradle.properties                             # Gradle properties
├── .gitignore                                    # Git ignore rules
└── README.md                                     # This file
```

## Features

### Current Features
- ✅ DJI MSDK v5.16.0 integration
- ✅ SDK registration and initialization
- ✅ Product connection detection
- ✅ USB connection handling
- ✅ Basic UI with connection status
- ✅ Permission management
- ✅ Support for DJI Mini 4 Pro

### Planned Features
- ⏳ Live camera feed display
- ⏳ Flight control (takeoff, landing, movement)
- ⏳ Gimbal control
- ⏳ Waypoint missions
- ⏳ Media download
- ⏳ Telemetry data display (battery, GPS, altitude, etc.)

## Troubleshooting

### SDK Registration Failed
- **Check API Key:** Ensure your API key is correctly set in `AndroidManifest.xml`
- **Check Package Name:** Package name in DJI Developer Portal must match `com.pixel.dronerelayapp`
- **Check Internet:** SDK registration requires internet connection
- **Check Expiry:** API keys may have expiration dates

### Product Not Connecting
- **Check USB Cable:** Use a good quality USB OTG cable
- **Check USB Permissions:** Grant USB permissions when prompted
- **Power Cycle:** Turn off and on both the drone and controller
- **Firmware:** Ensure your drone firmware is up to date
- **Controller:** Make sure you're using RC-N2, not RC 2

### App Crashes
- **Check Permissions:** Ensure all runtime permissions are granted
- **Check Logs:** Use `adb logcat` to view detailed error messages
- **Clear Cache:** Try clearing app data and cache

### Build Errors
- **Gradle Sync:** Try File → Invalidate Caches → Invalidate and Restart
- **Clean Build:** Run `./gradlew clean` then rebuild
- **Update SDK:** Ensure Android SDK Build Tools are up to date

## Technical Details

### SDK Version
- **MSDK Version:** 5.16.0 (Released October 2024)
- **Minimum Android Version:** API 24 (Android 7.0)
- **Target Android Version:** API 34 (Android 14)
- **Compile SDK Version:** API 34

### Dependencies
- DJI MSDK v5 Aircraft: 5.16.0
- AndroidX Core: 1.12.0
- AndroidX AppCompat: 1.6.1
- Material Components: 1.11.0
- Lifecycle: 2.7.0
- RxJava 3: 3.0.2
- Kotlin: 1.9.20

### Architecture
- **Language:** Kotlin
- **Build System:** Gradle 8.0 / AGP 8.1.4
- **Architecture Components:** ViewModel, LiveData
- **Reactive Programming:** RxJava 3
- **Minimum SDK:** 24 (Android 7.0)

## Resources

### Official Documentation
- [DJI Mobile SDK Tutorial](https://developer.dji.com/doc/mobile-sdk-tutorial/en/)
- [Run Sample Application Guide](https://developer.dji.com/doc/mobile-sdk-tutorial/en/quick-start/run-sample.html)
- [DJI Developer Portal](https://developer.dji.com/)
- [MSDK v5 GitHub Repository](https://github.com/dji-sdk/Mobile-SDK-Android-V5)

### Support
- [DJI Developer Forum](https://forum.dji.com/forum-139-1.html)
- [GitHub Issues](https://github.com/dji-sdk/Mobile-SDK-Android-V5/issues)

## License

This project is based on the DJI Mobile SDK v5 sample code, which is provided under the MIT License.

The DJI Android SDK is dynamically linked with unmodified libraries of FFmpeg licensed under the LGPLv2.1.

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

## Author

Sylvain Granges - Pixel

## Acknowledgments

- DJI for providing the Mobile SDK
- DJI SDK team for comprehensive documentation and samples
