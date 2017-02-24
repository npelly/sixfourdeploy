#!/usr/bin/python

import socket
import sys
import shortcode

if len(sys.argv) <= 2:
    print "Push APK to Android Device running SixFourDeploy"
    print ""
    print "USAGE"
    print "./push.py SHORTCODE APK"
    print "    SHORTCODE is from the SixFourDeploy app. CASE SENSITIVE"
    print "    APKNAME is a local path"
    print ""
    print "Troubleshooting"
    print "* SHORTCODE is CASE SENSITIVE, for example \"xFbD\"."
    print "* Android Device must be on the same private network. Try and ping the IP above."
    print "* SixFourDeploy must be active on the Android Device - look for the SixFourDeploy notification."
    print ""
    sys.exit(-1)

s = sys.argv[1]
ip, port = shortcode.decodeShortcode(s)

chunksize = 8192

file = open(sys.argv[2], "rb")

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print "connecting to " + str(ip) + ":" + str(port)

s.settimeout(5)
try:
    s.connect((ip, port))
except socket.timeout as e:
    print e
    print ""
    print "HINT: SHORTCODE is CaSe SENSiTiVE, for example \"xFbD\"."
    print "HINT: Android Device must be on the same network. Does \"ping " + ip + "\" work?"
    print ""
    sys.exit(-1)
except socket.error as e:
    print e
    print ""
    print "HINT: Is SixFourDeploy active on the Android Device? Look for the notification."
    print ""
    sys.exit(-1)

sys.stdout.write("sending")
count = 0
try:
    while 1:
        chunk = file.read(chunksize)
        if chunk:
            sys.stdout.write('.')
            sys.stdout.flush()
            count += len(chunk)
            s.send(chunk)
        else:
            break
finally:
    print ""
    file.close()


s.close()
print "complete (" + str(count) + " bytes)"
