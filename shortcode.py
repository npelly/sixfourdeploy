SHORTCODE_MAP = [
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
]

def decodeShortcode(s):
    code = 0
    for i in range(len(s)):
        code *= len(SHORTCODE_MAP)
        code += SHORTCODE_MAP.index(s[i])

    ipClass, code = popBits(code, 1)

    if (ipClass == 1):
        ip = [192, 168, 0, 0]
        ip[3], code = popBits(code, 8)
        ip[2], code = popBits(code, 8)
    else:
        ip = [10, 0, 0, 0]
        ip[3], code = popBits(code, 8)
        ip[2], code = popBits(code, 8)
        ip[1], code = popBits(code, 8)

    port, _ = popBits(code, 2)
    port += 12364

    ipString = str(ip[0]) + '.' + str(ip[1]) + '.' + str(ip[2]) + '.' + str(ip[3])

    return ipString, port

def popBits(code, bitCount):
    mask = (1 << bitCount) - 1
    bits = code & mask
    code >>= bitCount
    return bits, code

assert ("192.168.0.0", 12364) == decodeShortcode("1")
assert ("192.168.4.179", 12364) == decodeShortcode("00cp")
assert ("192.168.4.179", 12366) == decodeShortcode("16ox")
assert ("10.0.0.1", 12364) == decodeShortcode("00002")
