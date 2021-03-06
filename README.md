# sixfourdeploy

*Install Android APKs over WIFI.*

Works on any production device, no root required.*

### SETUP
1. Install & run on your Android device (the traditional way)
2. Note the _case-sensitive_ SHORTCODE printed by the app. This is used to identify your device.

### PUSHING APKs
```./push.py SHORTCODE APK```

That's it :-)

This only works across WIFI (or private networks) for now. The APK is not copied to the cloud, it
is streamed directly to your device over TCP.

### FEATURE REQUESTS / BUGS
- F1: Custom Icon & Rebrand
- F1: adb shell setprop persist.service.adb.tcp.port 5555  ??
- B2: Fails if port is busy (for example, duplicate work profile instance)
- F2: Public networks & NAT support w/ hole punching
- F2: UI (Snackbar?) to walk-through "Unknown Sources" Android Settings
- F2: AVD / Android Studio integration
- F2: installation history UI

### CHANGELOG
- Rename send.py to push.py and improve error handling
- Nicer UI when not on WIFI.

v0.4
- Auto-launch installed app.
- Start listening by default.
- Fix some reliability bugs.

v0.3
- Ongoing notification when active.

v0.2
- P2P only (phone on WIFI, computer on same network).
- Shortcode connections.
- send.py to push from computer.
- On/Off switch.
