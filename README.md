# sixfourdeploy

Push Android APK to your device over WIFI.

1. Install & run SixFourDeploy onto your Android device (the traditional way).
2. Note the SHORTCODE used to identify your device.
3. Push APKs to your WIFI connected device:
    ./send.py SHORTCODE APK

That's it :-)

BUILD SixFourDeploy
./gradlew build

FEATURE REQUESTS & BUGS
    - B1: Install fails if activity was swiped away.
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

CHANGELOG
v0.03
 New features
    - Ongoing notification when active.

v0.02
  New features
    - P2P only (phone on WIFI, computer on same network).
    - Shortcode connections.
    - send.py to push from computer.
    - On/Off switch.
