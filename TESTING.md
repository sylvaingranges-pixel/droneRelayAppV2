# Testing Guide

## What to Test

Once you have the app installed and running, here's what to verify:

### 1. SDK Registration ✅
- **Expected:** Green text saying "DJI SDK registered successfully!"
- **If Failed:** Red text with error message
  - Check API key in AndroidManifest.xml
  - Verify internet connection
  - Confirm package name matches in DJI Developer Portal

### 2. USB Connection ✅
- **Test:** Connect RC-N2 to phone via USB
- **Expected:** App auto-launches (or permission dialog appears)
- **Grant:** USB permission when prompted
- **Check:** No "USB Permission Denied" errors

### 3. Product Connection ✅
- **Prerequisites:**
  - Drone powered ON
  - Controller powered ON  
  - USB cable connected
  - USB permission granted
- **Expected:** Connection status shows "Product Connected: MINI_4_PRO" in green
- **If Failed:**
  - Power cycle drone and controller
  - Reconnect USB cable
  - Check using correct controller (RC-N2, not RC 2)

### 4. Drone Information Display ✅
- **Expected:** Drone Info card appears with:
  - Model name (e.g., MINI_4_PRO)
  - Firmware version
  - Serial number
  - Battery level
- **Note:** Some fields may show "Unknown" - this is normal for basic sample

## Testing with Real Hardware

### Required Equipment
1. **Android Device:**
   - Android 7.0 or higher
   - USB OTG support (most modern phones have this)
   - ARM64 processor

2. **DJI Equipment:**
   - DJI Mini 4 Pro drone (or other supported model)
   - DJI RC-N2 remote controller
   - Fully charged batteries
   - USB cable (USB-C or micro-USB depending on your phone)

### Testing Procedure

1. **Initial Setup (One-time)**
   ```
   ✓ Install app on Android device
   ✓ Grant all requested permissions
   ✓ Verify API key is configured
   ```

2. **Connection Test**
   ```
   ✓ Power on drone
   ✓ Power on controller
   ✓ Wait for controller to connect to drone (beep sound)
   ✓ Connect phone to controller via USB
   ✓ Grant USB permission
   ✓ App launches automatically
   ```

3. **SDK Registration Test**
   ```
   ✓ Check "SDK Status" section
   ✓ Should show green "registered successfully"
   ✓ If orange "in progress", wait 5-10 seconds
   ✓ If red "failed", check API key and internet
   ```

4. **Product Detection Test**
   ```
   ✓ Check "Connection Status" section
   ✓ Should show green "Product Connected: MINI_4_PRO"
   ✓ Drone info card should be visible
   ✓ Model name should match your drone
   ```

## Automated Testing

Currently, this is a basic sample app without automated tests. However, the project structure supports adding tests:

### Unit Tests Location
```
app/src/test/java/com/pixel/dronerelayapp/
```

### Instrumented Tests Location
```
app/src/androidTest/java/com/pixel/dronerelayapp/
```

### Running Tests (when added)
```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires connected device)
./gradlew connectedAndroidTest
```

## Common Issues and Solutions

### Issue: App Crashes on Launch
- **Check:** Logcat for error messages (`adb logcat`)
- **Try:** Clear app data: Settings → Apps → DJI MSDK Sample → Clear Data
- **Verify:** All permissions granted

### Issue: Drone Not Detected
- **Check:** USB cable is data-capable (not charge-only)
- **Check:** Controller firmware is up to date
- **Check:** Drone firmware is compatible
- **Try:** Different USB cable
- **Try:** Reboot phone, controller, and drone

### Issue: Intermittent Connection
- **Check:** USB cable quality
- **Check:** USB port not loose
- **Avoid:** Moving USB cable while connected
- **Try:** Use shorter USB cable

## Build Verification

Before testing on device, verify the build:

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Expected output
BUILD SUCCESSFUL in Xs
```

If build fails:
- Check internet connection (first build downloads dependencies)
- Check Android SDK installed correctly
- Check Gradle version compatibility
- See error message for specific issue

## Debugging

### View Logs
```bash
# View all logs
adb logcat

# Filter for DJI app only
adb logcat | grep "com.pixel.dronerelayapp"

# Filter for DJI SDK
adb logcat | grep "DJI"

# Save logs to file
adb logcat > dji_logs.txt
```

### Key Log Tags to Watch
- `DJIApplication` - SDK initialization
- `MainActivity` - App lifecycle and UI
- `UsbAttachActivity` - USB connection events
- `SDKManager` - DJI SDK operations

## Performance Testing

### Memory Usage
- Expected: ~100-200MB when idle
- Expected: ~200-400MB when connected to drone
- Monitor in Android Studio: View → Tool Windows → Profiler

### Battery Usage
- App should not drain battery when idle
- Normal drain when actively connected (due to USB communication)
- Test: Leave app running for 10 minutes, check battery usage in Settings

## Compatibility Testing

### Tested Devices
This app has been designed for:
- Android 7.0+ (API 24+)
- ARM64 architecture
- USB OTG support

### Tested Drones
According to DJI MSDK v5.16.0 documentation:
- ✅ DJI Mini 4 Pro (Primary)
- ✅ DJI Mini 3 Pro
- ✅ DJI Mini 3
- ✅ Mavic 3 series
- ✅ Matrice 30 series
- ✅ Matrice 300 RTK
- ✅ And more...

## Test Checklist

Use this checklist for each test session:

```
□ Build successful
□ App installs without errors
□ App launches without crashing
□ Permissions requested and granted
□ SDK registers successfully (green status)
□ USB connection detected
□ USB permission granted
□ Controller connects to drone
□ App detects connected product
□ Product name displays correctly
□ Drone info card appears
□ App remains stable for 5+ minutes
□ App handles disconnect gracefully
□ App handles reconnect correctly
□ No memory leaks observed
□ No excessive battery drain
```

## Reporting Issues

If you find bugs or issues:

1. **Collect Information:**
   - Android version
   - Phone model
   - Drone model
   - Controller model
   - App logs (adb logcat)
   - Screenshots

2. **Check Existing Issues:**
   - GitHub Issues for this repo
   - DJI Developer Forum

3. **Create Detailed Report:**
   - What you expected to happen
   - What actually happened
   - Steps to reproduce
   - Log files
   - Screenshots/videos

## Next Steps After Testing

Once basic functionality is verified:
1. Explore the code to understand the implementation
2. Try modifying the UI
3. Add new features (camera feed, flight control, etc.)
4. Refer to DJI MSDK documentation for advanced features

Happy testing! 🚁
