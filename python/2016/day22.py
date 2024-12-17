import re
from dataclasses import dataclass


@dataclass
class Node:
    x: int
    y: int
    size: int
    used: int
    avail: int
    use_per: int


def find_pairs(x, y):
    n = nodes[(x, y)]
    if cur_node.used <= n.avail and (cur_node.x, cur_node.y) != (n.x, n.y):
        yield 1
    if (x, y) not in seen:
        seen.add((x, y))
        if (x + 1, y) in nodes:
            yield from find_pairs(x + 1, y)
        if (x - 1, y) in nodes:
            yield from find_pairs(x - 1, y)
        if (x, y + 1) in nodes:
            yield from find_pairs(x, y + 1)
        if (x, y - 1) in nodes:
            yield from find_pairs(x, y - 1)


with open("day22.txt", "r") as infile:
    data = infile.read().splitlines()
    nodes = {}
    for line in data:
        x, y, *attrs = [int(x) for x in re.findall(r"\d+", line)]
        nodes[(x, y)] = Node(x, y, *attrs)

    pairs = 0
    for k in nodes:
        seen = set()
        cur_node = nodes[k]
        if cur_node.used == 0:
            continue
        pairs += sum(find_pairs(cur_node.x, cur_node.y))

    print(pairs)
