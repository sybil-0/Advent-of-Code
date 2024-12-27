from dataclasses import dataclass
from string import ascii_uppercase

UP, RIGHT, DOWN, LEFT = 0, 1, 2, 3


@dataclass
class Packet:
    x: int
    y: int
    dir: int


with open("day19.txt", "r") as infile:
    data = infile.read().splitlines()
    G = {}
    output = []

    for x, line in enumerate(data):
        for y, c in enumerate(line):
            if c != " ":
                G[(x, y)] = c

    p = Packet(0, 153, DOWN)
    steps = 0
    while True:
        if G[(p.x, p.y)] in ascii_uppercase:
            output.append(G[(p.x, p.y)])
        if p.dir == DOWN and (p.x + 1, p.y) in G:
            p.x += 1
            steps += 1
        elif p.dir == DOWN and (p.x, p.y + 1) in G:
            p.dir = RIGHT
        elif p.dir == DOWN and (p.x, p.y - 1) in G:
            p.dir = LEFT
        elif p.dir == RIGHT and (p.x, p.y + 1) in G:
            p.y += 1
            steps += 1
        elif p.dir == RIGHT and (p.x + 1, p.y) in G:
            p.dir = DOWN
        elif p.dir == RIGHT and (p.x - 1, p.y) in G:
            p.dir = UP
        elif p.dir == UP and (p.x - 1, p.y) in G:
            p.x -= 1
            steps += 1
        elif p.dir == UP and (p.x, p.y + 1) in G:
            p.dir = RIGHT
        elif p.dir == UP and (p.x, p.y - 1) in G:
            p.dir = LEFT
        elif p.dir == LEFT and (p.x, p.y - 1) in G:
            p.y -= 1
            steps += 1
        elif p.dir == LEFT and (p.x + 1, p.y) in G:
            p.dir = DOWN
        elif p.dir == LEFT and (p.x - 1, p.y) in G:
            p.dir = UP
        else:
            break
    print("".join(output))
    print(steps + 1)
