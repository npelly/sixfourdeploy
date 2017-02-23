#!/usr/bin/python

import socket
import sys
import shortcode

if len(sys.argv) <= 2:
    print "USAGE"
    print "  send.py TARGET_SHORTCODE APKNAME"
    sys.exit(-1);

s = sys.argv[1]
ip, port = shortcode.decodeShortcode(s)

chunksize = 8192

file = open(sys.argv[2], "rb")

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print "connecting to " + str(ip) + ":" + str(port)
s.connect((ip, port))

try:
    while 1:
        chunk = file.read(chunksize)
        if chunk:
            print "."
            s.send(chunk)
        else:
            break
finally:
    file.close()

#s.sendall(b'Hello, world')
#data = s.recv(1024)


s.close()
print('Sent')
