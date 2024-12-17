from collections import defaultdict


with open("./day12.txt", "r") as infile:
    data = [s.split() for s in infile.read().splitlines()]
    registers = defaultdict(int)
    registers["c"] = 1
    pos = 0

    while 0 <= pos < len(data):
        op = data[pos]
        match op:
            case ["cpy", x, y]:
                registers[y] = int(x) if x.isnumeric() else registers[x]
                pos += 1
            case ["inc", a]:
                registers[a] += 1
                pos += 1
            case ["dec", a]:
                registers[a] -= 1
                pos += 1
            case ["jnz", x, y]:
                p = int(x) if x.isnumeric() else registers[x]
                if p != 0:
                    pos += int(y)
                else:
                    pos += 1
            case _:
                assert False

    print(registers)
