from dataclasses import dataclass

STRAIGHT, LEFT, RIGHT = 0, 1, 2
UP, RIGHT, DOWN, LEFT = 0, 1, 2, 3

@dataclass
class Cart:
    x: int 
    y: int
    turn: int
    facing: int

with open("day13.txt", "r") as infile:
    data = infile.read().splitlines()
    grid = {}
    start = []
    for i, line in enumerate(data):
        for j, c in enumerate(line):
            print(i, j, c)
