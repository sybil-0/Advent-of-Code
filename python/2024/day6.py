from collections import defaultdict
from copy import deepcopy


UP = 1
RIGHT = 2
DOWN = 3
LEFT = 4


class Guard:
    def __init__(self, grid) -> None:
        self.facing = UP
        self.x = 77
        self.y = 59
        self.grid = grid

    def move(self) -> None:
        if self.facing == UP:
            if self.grid[(self.x - 1), self.y] == "#":
                self.facing = RIGHT
            else:
                self.x -= 1
        elif self.facing == RIGHT:
            if self.grid[(self.x, self.y + 1)] == "#":
                self.facing = DOWN
            else:
                self.y += 1
        elif self.facing == DOWN:
            if self.grid[(self.x + 1, self.y)] == "#":
                self.facing = LEFT
            else:
                self.x += 1
        elif self.facing == LEFT:
            if self.grid[(self.x, self.y - 1)] == "#":
                self.facing = UP
            else:
                self.y -= 1


with open("day6.txt", "r") as infile:
    data = infile.read().splitlines()
    grid = defaultdict(lambda: "?")
    seen = set()
    loop = set()

    for i, row in enumerate(data):
        for j, c in enumerate(row):
            grid[(i, j)] = c

    guard = Guard(grid)
    while grid[(guard.x, guard.y)] != "?":
        guard.move()
        seen.add((guard.x, guard.y))

    # part 1
    print(len(seen))

    # part 2
    loop_count = 0

    for p in seen:
        loop = set()
        g = deepcopy(grid)
        g[p] = "#"
        guard = Guard(g)
        while grid[(guard.x, guard.y)] != "?":
            if (guard.x, guard.y, guard.facing) in loop:
                loop_count += 1
                break
            loop.add((guard.x, guard.y, guard.facing))
            guard.move()

    print(loop_count)
