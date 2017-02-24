# sixfourdeploy

*Push Android APKs to your device over WIFI.*

### SETUP
1. Install & run on your Android device (the traditional way)
2. Note the _case-sensitive_ SHORTCODE printed by the app. This is used to identify your device.

### PUSHING APKs
```     ./send.py SHORTCODE APK```

That's it :-)

This only works across WIFI (or private networks) for now. The APK is not copied to the cloud, it
is streamed directly to your device over TCP.

### FEATURE REQUESTS / BUGS
- F1: start enabled
- F1: Launch app after install
- F1: Graceful failure on public networks
- F1: Graceful error messages in send.py for common connection failures
- F1: Custom Icon & Rebrand
- B2: Fails if port is busy (for example, duplicate work profile instance)
- F2: Public networks & NAT support w/ hole punching
- F2: UI (Snackbar?) to walk-through "Unknown Sources" Android Settings
- F2: AVD / Android Studio integration
- F2: installation history UI

### CHANGELOG
v0.03
- Ongoing notification when active.

v0.02
- P2P only (phone on WIFI, computer on same network).
- Shortcode connections.
- send.py to push from computer.
- On/Off switch.
