from collections import defaultdict, Counter
from string import ascii_lowercase

LIMIT = 1000000


def run_signal(start):
    registers = defaultdict(int)
    registers["a"] = start
    pos = 0
    signals = []

    while 0 <= pos < len(data) and len(signals) < LIMIT:
        op = data[pos]
        match op:
            case ["cpy", x, y]:
                registers[y] = registers[x] if x in ascii_lowercase else int(x)
                pos += 1
            case ["inc", a]:
                registers[a] += 1
                pos += 1
            case ["dec", a]:
                registers[a] -= 1
                pos += 1
            case ["jnz", x, y]:
                p = registers[x] if x in ascii_lowercase else int(x)
                if p != 0:
                    pos += int(y)
                else:
                    pos += 1
            case ["out", x]:
                i = registers[x] if x in ascii_lowercase else int(x)
                signals.append(i)
            case _:
                assert False
    return signals


with open("day25.txt", "r") as infile:
    data = [s.split() for s in infile.read().splitlines()]
    for i in range(1000):
        result = run_signal(i)
        c = Counter(result)
        if c[0] < LIMIT and c[1] < LIMIT:
            print(i)
