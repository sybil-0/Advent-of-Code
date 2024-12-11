from collections import deque

CHALL_INPUT = "1,2,3"


def reverse_section(xs, pos, l):
    xs.rotate(-pos)
    xs = list(xs)
    subs = xs[:l]
    subs = subs[::-1]
    xs = deque(subs + xs[l:])
    xs.rotate(pos)
    return xs


d = deque(range(0, 256))
input_lengths = [ord(i) for i in CHALL_INPUT] + [17, 31, 73, 47, 23]
pos = 0
skip_size = 0

for _ in range(64):
    lengths = input_lengths[:]
    while lengths:
        x = lengths.pop(0)
        d = reverse_section(d, pos, x)
        pos += (x + skip_size) % len(d)
        skip_size += 1

print(d)
