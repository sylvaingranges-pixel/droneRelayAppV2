# Quick Setup Guide - DJI MSDK Sample App

This guide will help you get the DJI MSDK sample app running on your Android device in just a few minutes.

## Before You Start

Make sure you have:
- ✅ Android device with Android 7.0+ and USB OTG support
- ✅ DJI Mini 4 Pro drone
- ✅ DJI RC-N2 controller (NOT RC 2 with built-in screen)
- ✅ USB cable to connect phone to controller
- ✅ Android Studio installed on your computer

## Step-by-Step Setup

### 1. Get Your DJI API Key (5 minutes)

1. Go to https://developer.dji.com/
2. Click "Sign In" or "Register" (create account if needed)
3. Once logged in, go to https://developer.dji.com/user/apps/
4. Click "Create App" button
5. Fill in:
   - **App Name:** My DJI Test App (or any name)
   - **SDK:** Select "Mobile SDK"
   - **Package Name:** `com.pixel.dronerelayapp` (⚠️ MUST be exact)
   - **Category:** Choose any category
6. Click Submit
7. Copy your **App Key** (looks like: `1234567890abcdef1234567890abcdef1234567890`)

### 2. Configure the App (1 minute)

1. Open the project in Android Studio
2. Open `app/src/main/AndroidManifest.xml`
3. Find line 47 that says:
   ```xml
   android:value="PLEASE_ADD_YOUR_API_KEY_HERE"
   ```
4. Replace `PLEASE_ADD_YOUR_API_KEY_HERE` with your actual API key
5. Save the file

### 3. Build and Install (5 minutes)

#### Option A: Using Android Studio (Recommended)

1. Connect your Android phone to computer via USB
2. Enable Developer Mode on phone:
   - Settings → About Phone → Tap "Build Number" 7 times
   - Go back → Developer Options → Enable "USB Debugging"
3. In Android Studio:
   - Click the green ▶️ "Run" button (or press Shift + F10)
   - Select your device from the list
   - Wait for build and installation to complete

#### Option B: Using Command Line

```bash
# Navigate to project folder
cd droneRelayAppV2

# Build and install
./gradlew installDebug

# Or just build APK
./gradlew assembleDebug
# APK will be at: app/build/outputs/apk/debug/app-debug.apk
```

### 4. Connect Hardware and Test (2 minutes)

1. **Power ON** your DJI Mini 4 Pro drone
2. **Power ON** your DJI RC-N2 controller
3. **Connect** your Android phone to RC-N2 controller using USB cable
4. When prompted on phone, grant **USB permission**
5. The app should auto-launch! If not, open "DJI MSDK Sample" from app drawer

### 5. Verify It Works ✅

You should see:
- ✅ SDK Status: "DJI SDK registered successfully!" in green
- ✅ Connection Status: "Product Connected: MINI_4_PRO" in green
- ✅ Drone Info card showing your drone model

## Troubleshooting

### Problem: "SDK Registration Failed"

**Solution:**
- Check that your API key is correct in AndroidManifest.xml
- Make sure package name in DJI Developer Portal is exactly `com.pixel.dronerelayapp`
- Ensure your phone has internet connection (needed for first registration)

### Problem: "Product Disconnected"

**Solution:**
- Check USB cable is properly connected
- Make sure you're using DJI RC-N2 controller (NOT RC 2)
- Grant USB permission when prompted
- Try unplugging and replugging the USB cable
- Power cycle both drone and controller

### Problem: "USB Permission Denied"

**Solution:**
- When you connect USB, Android will show a dialog asking for permission
- Make sure to check "Always allow" and tap "OK"
- If you missed it, disconnect and reconnect USB

### Problem: Build Errors in Android Studio

**Solution:**
- Go to File → Invalidate Caches → Invalidate and Restart
- Try: `./gradlew clean` then rebuild
- Check you have internet connection (downloads dependencies)
- Update Android SDK in Tools → SDK Manager

## Next Steps

Once you have the app running, you can:

1. **View the source code** in Android Studio to learn how it works
2. **Modify the UI** in `app/src/main/res/layout/activity_main.xml`
3. **Add features** in `app/src/main/java/com/pixel/dronerelayapp/MainActivity.kt`
4. **Explore DJI SDK docs** at https://developer.dji.com/doc/mobile-sdk-tutorial/en/

## Common Questions

**Q: Can I use DJI RC 2 controller?**
A: No, RC 2 with built-in screen doesn't support third-party apps. Use RC-N2.

**Q: Does it work with other DJI drones?**
A: Yes! This app supports many DJI drones including Mini 3, Mavic 3, Matrice series, etc.

**Q: Can I change the package name?**
A: Yes, but you must also update it in DJI Developer Portal to get a new API key.

**Q: Is iOS supported?**
A: Not with MSDK v5. Only Android is supported.

**Q: The build fails with network errors**
A: Make sure you have internet connection. First build downloads ~200MB of dependencies.

## Need Help?

- 📖 [Full README](README.md) - Complete documentation
- 🌐 [DJI Developer Docs](https://developer.dji.com/doc/mobile-sdk-tutorial/en/)
- 💬 [DJI Forum](https://forum.dji.com/forum-139-1.html)
- 🐛 [GitHub Issues](https://github.com/dji-sdk/Mobile-SDK-Android-V5/issues)

## Success! 🎉

If you see your drone connected in the app, congratulations! You now have a working DJI MSDK v5 application. You can start building your own drone features from here.

Happy coding! 🚁
