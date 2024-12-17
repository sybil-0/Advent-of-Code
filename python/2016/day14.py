SALT = "ngcjuoqr"
import hashlib


def has_triplet(s):
    for i in range(len(s) - 2):
        if s[i] == s[i + 1] == s[i + 2]:
            return s[i : i + 3]
    return False


keys = []
candidates = []
i = 0

while len(keys) < 64:
    s = SALT + str(i)
    md5 = hashlib.md5(s.encode("utf-8")).hexdigest()
    print(md5)
    for t, lim, idx in candidates:
        if t[0] * 5 in md5 and idx + 1 <= i <= lim:
            keys.append(idx)

    if t := has_triplet(md5):
        print(t)
        candidates.append((t, i + 1000, i))

    i += 1

print(keys)
