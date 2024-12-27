def extrapolate(history):
    current = history
    xs = [history]
    while True:
        nxt = []
        for i in range(len(current) - 1):
            d = current[i + 1] - current[i]
            nxt.append(d)
        xs.append(nxt)
        current = nxt[:]
        if all(x == 0 for x in nxt):
            break

    xs[-1].append(0)
    for i in range(len(xs) - 2, -1, -1):
        xs[i].insert(0, xs[i + 1][0] + xs[i][1])
    return xs


with open("day9.txt", "r") as infile:
    data = infile.read().splitlines()
    total = 0
    for line in data:
        xs = [int(x) for x in line.split()]
        out = extrapolate(xs)
        total += out[0][-1]

    print(total)
