from os import walk


DESIGN_NUM = 1358


def is_wall(x, y):
    check = x * x + 3 * x + 2 * x * y + y + y * y
    check += DESIGN_NUM
    b_str = bin(check)[2:]
    return b_str.count("1") % 2 != 0


def neighbors(x, y):
    out = []
    for i, j in [(1, 0), (-1, 0), (0, 1), (0, -1)]:
        m, n = x + i, j + y
        if m < 0 or n < 0:
            continue
        if is_wall(m, n):
            continue
        if (m, n) in seen:
            continue
        out.append((m, n))
    return out


seen = set()
queue = [(1, 1)]
GOAL = (31, 39)
steps = 0
while steps < 51:
    seen.update(queue)
    queue = [n for p in queue for n in neighbors(p[0], p[1])]
    steps += 1

print(len(seen))
