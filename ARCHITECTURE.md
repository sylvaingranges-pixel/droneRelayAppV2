# Architecture Overview

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      Android Device                          │
│                                                               │
│  ┌──────────────────────────────────────────────────────┐   │
│  │              DJI MSDK Sample App                      │   │
│  │                                                        │   │
│  │  ┌──────────────────────────────────────────────┐   │   │
│  │  │         MainActivity (UI Layer)               │   │   │
│  │  │  • Display SDK status                         │   │   │
│  │  │  • Show connection status                     │   │   │
│  │  │  • Display drone information                  │   │   │
│  │  │  • Handle user permissions                    │   │   │
│  │  └──────────────────┬────────────────────────────┘   │   │
│  │                     │                                 │   │
│  │  ┌──────────────────▼────────────────────────────┐   │   │
│  │  │      DJIApplication (SDK Layer)               │   │   │
│  │  │  • Initialize DJI SDK                         │   │   │
│  │  │  • Register with DJI servers                  │   │   │
│  │  │  • Manage SDK lifecycle                       │   │   │
│  │  └──────────────────┬────────────────────────────┘   │   │
│  │                     │                                 │   │
│  │  ┌──────────────────▼────────────────────────────┐   │   │
│  │  │         DJI MSDK v5.16.0                      │   │   │
│  │  │  • Product management                         │   │   │
│  │  │  • USB communication                          │   │   │
│  │  │  • Command/control interface                  │   │   │
│  │  └──────────────────┬────────────────────────────┘   │   │
│  └────────────────────┼──────────────────────────────────┘   │
│                       │                                       │
│  ┌────────────────────▼────────────────────────────┐         │
│  │         USB OTG Connection                      │         │
│  └────────────────────┬────────────────────────────┘         │
└───────────────────────┼───────────────────────────────────────┘
                        │
                   USB Cable
                        │
┌───────────────────────▼───────────────────────────────────────┐
│                 DJI RC-N2 Controller                          │
│  • Receives commands from phone                               │
│  • Communicates with drone                                    │
│  • Forwards telemetry to phone                                │
└───────────────────────┬───────────────────────────────────────┘
                        │
                 Radio Link (OcuSync)
                        │
┌───────────────────────▼───────────────────────────────────────┐
│                   DJI Mini 4 Pro                              │
│  • Flight controller                                          │
│  • Camera system                                              │
│  • Sensors & telemetry                                        │
└───────────────────────────────────────────────────────────────┘
```

## Component Flow

### 1. App Initialization
```
Android System
    └─> Application.onCreate()
        └─> DJIApplication.onCreate()
            └─> SDKManager.init()
                ├─> Register with DJI (requires internet + API key)
                ├─> Load SDK modules
                └─> Setup callbacks
```

### 2. USB Connection
```
User connects USB cable
    └─> Android detects USB device
        └─> Matches accessory_filter.xml (manufacturer: DJI)
            └─> UsbAttachActivity triggered
                └─> Launch MainActivity
                    └─> Request USB permission
                        └─> SDK detects product
```

### 3. Product Connection
```
SDK detects product
    └─> SDKManagerCallback.onProductConnect()
        └─> MainActivity.setupProductConnectionListener()
            └─> Query product type (KeyManager)
                ├─> Update UI (connection status)
                ├─> Show drone info card
                └─> Enable features
```

## Class Diagram

```
┌─────────────────────────┐
│   Application           │
│   (Android System)      │
└──────────┬──────────────┘
           │ extends
           │
┌──────────▼──────────────┐
│   DJIApplication        │
│  ────────────────────   │
│  - initSDK()            │
│  - isSDKRegistered()    │
│  - setCallback()        │
└──────────┬──────────────┘
           │ initializes
           │
┌──────────▼──────────────┐
│   SDKManager            │
│   (DJI MSDK)            │
│  ────────────────────   │
│  + init()               │
│  + getProduct()         │
│  + getKeyManager()      │
└──────────┬──────────────┘
           │ used by
           │
┌──────────▼──────────────┐     ┌─────────────────────────┐
│   MainActivity          │────▶│  ActivityMainBinding    │
│  ────────────────────   │     │  (View Binding)         │
│  - checkPermissions()   │     └─────────────────────────┘
│  - setupListeners()     │
│  - updateStatus()       │
│  - updateDroneInfo()    │
└─────────────────────────┘

┌─────────────────────────┐
│   UsbAttachActivity     │
│  ────────────────────   │
│  - onCreate()           │
│  - launchMainActivity() │
└─────────────────────────┘
```

## Data Flow

### Registration Flow
```
1. App Launch
   └─> DJIApplication.onCreate()
       └─> SDKManager.init()
           └─> HTTP Request to DJI Server
               ├─ Send: API Key, Package Name, Device Info
               └─ Receive: Registration Result
                   ├─ Success → isSDKRegistered = true
                   └─ Failure → Show error message
```

### Connection Status Flow
```
1. USB Connected
   └─> Hardware Detection
       └─> USB Permission Granted
           └─> SDK Detects Product
               └─> Product Type Query
                   └─> UI Update
                       ├─ Status: Connected
                       ├─ Model: MINI_4_PRO
                       └─ Show Info Card
```

### Command Flow (Future Implementation)
```
User Action (e.g., "Take Off")
    └─> MainActivity.onTakeOffClick()
        └─> FlightController.takeOff()
            └─> SDK Command
                └─> USB → Controller → Radio → Drone
                    └─> Drone executes command
                        └─> Telemetry feedback
                            └─> Update UI
```

## Thread Model

```
┌─────────────────────────────────────────────────────────┐
│                     Main Thread (UI)                    │
│  • MainActivity                                          │
│  • UI updates                                            │
│  • User interactions                                     │
└────────────────────┬────────────────────────────────────┘
                     │
                     │ SDK Callbacks
                     ▼
┌─────────────────────────────────────────────────────────┐
│                   Background Thread                     │
│  • SDK initialization                                    │
│  • Network requests (registration)                       │
│  • USB communication                                     │
│  • Product detection                                     │
└─────────────────────────────────────────────────────────┘

Note: Use runOnUiThread() to update UI from callbacks
```

## Permissions Flow

```
App Launch
    └─> MainActivity.onCreate()
        └─> checkAndRequestPermissions()
            ├─> Location (FINE, COARSE)
            ├─> Storage (READ_EXTERNAL_STORAGE / READ_MEDIA_*)
            ├─> Audio (RECORD_AUDIO)
            └─> Bluetooth (for future features)
                └─> User grants/denies
                    └─> onRequestPermissionsResult()
                        ├─ Granted → Continue
                        └─ Denied → Show warning
```

## Error Handling

```
┌─────────────────────────────────────────────────────────┐
│                    Error Sources                        │
├─────────────────────────────────────────────────────────┤
│  1. SDK Registration Failed                             │
│     → Show error in SDK Status card (red)               │
│     → Check: API key, internet, package name            │
│                                                          │
│  2. USB Permission Denied                               │
│     → Show error message                                 │
│     → Prompt user to grant permission                    │
│                                                          │
│  3. Product Not Found                                   │
│     → Show "No Product Connected" (red)                 │
│     → Check: USB cable, controller power, drone power    │
│                                                          │
│  4. Network Error (Registration)                        │
│     → Retry registration                                 │
│     → Show error with details                            │
│                                                          │
│  5. Runtime Exception                                   │
│     → Log error                                          │
│     → Show generic error message                         │
│     → Don't crash app                                    │
└─────────────────────────────────────────────────────────┘
```

## State Machine

```
┌──────────────┐
│   App Start  │
└──────┬───────┘
       │
       ▼
┌──────────────────┐
│  SDK Registering │ ◄────────┐
└──────┬───────────┘          │
       │                      │ Retry
       │ Success              │
       ▼                      │
┌──────────────────┐          │
│  SDK Registered  │ ─────────┘
└──────┬───────────┘    Failure
       │
       │ USB Connected
       ▼
┌──────────────────┐
│  Waiting Product │
└──────┬───────────┘
       │
       │ Product Detected
       ▼
┌──────────────────┐
│     Connected    │ ◄──┐
└──────┬───────────┘    │
       │                │ Reconnect
       │ Disconnect     │
       ▼                │
┌──────────────────┐    │
│   Disconnected   │────┘
└──────────────────┘
```

## Key Technologies

- **Language:** Kotlin 1.9.20
- **SDK:** DJI MSDK v5.16.0
- **UI:** View Binding + Material Components
- **Async:** RxJava 3
- **Build:** Gradle 8.0 / AGP 8.1.4
- **Platform:** Android 7.0+ (API 24+)

## Design Patterns Used

1. **Application Singleton**
   - `DJIApplication` - Single instance for SDK initialization

2. **Observer Pattern**
   - `SDKManagerCallback` - Observes SDK events
   - Product connection listeners

3. **View Binding**
   - Type-safe view access
   - No findViewById() needed

4. **Callback Pattern**
   - SDK registration callback
   - Permission request callback
   - USB permission callback

## Future Architecture Additions

When adding new features, follow this pattern:

```
Feature (e.g., Camera Control)
    └─> CameraViewModel
        └─> Use KeyManager to access camera keys
            └─> Update UI via LiveData
                └─> Handle errors gracefully
```

## Related Documentation

- [KEYFILES.md](KEYFILES.md) - Important files explained
- [README.md](README.md) - Complete documentation
- [DJI MSDK Docs](https://developer.dji.com/doc/mobile-sdk-tutorial/en/) - Official SDK documentation
