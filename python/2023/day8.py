DIRECTIONS = "LLLRRLRRRLLRRLRRLLRLRRLRRRLRRLRRLRRRLRLRRLRLRRLRRLLRRLRLLRRLLLRRRLRRLRLRLRRRLRLLRRLRRRLRRLRRLRRLRLLRLLRRLRRRLRRLRLRRLRRRLRRLLRLLRRLRRRLLRRRLRLRRRLLRLRRLRRLLRRLRRLLLRRRLRLRRRLRRLLRLRRLRLLRRRLRLRLLRLRRRLRLRRRLRRLRLRLLRLRRRLRRLRRRLRRRLRLRRRLRRRLLLLRLRLRRRLLLRLRRRLRRLRLRRLLRLLRRRR"


L = len(DIRECTIONS)


def walk(start):
    pos = start
    steps = 0
    while not pos.endswith("Z"):
        dir = DIRECTIONS[steps % L]
        if dir == "L":
            pos = G[pos][0]
        elif dir == "R":
            pos = G[pos][1]
        steps += 1
    return steps


with open("day8.txt", "r") as infile:
    data = infile.read().splitlines()
    G = {}
    starts = []

    for line in data:
        k, vs = line.split(" = ")
        if k.endswith("A"):
            starts.append(k)
        G[k.strip()] = vs[1:-1].split(", ")

    # part 1
    print(walk("AAA"))
    # part 2
    for p in starts:
        print(walk(p), end=",")
