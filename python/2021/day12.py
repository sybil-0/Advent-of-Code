from collections import defaultdict


def all_paths(n, end, seen, twice=True):
    if n == end:
        yield seen + ["end"]
    for x in G[n]:
        if x.isupper():
            yield from all_paths(x, end, seen + [n], twice)
        else:
            if x not in seen:
                yield from all_paths(x, end, seen + [n], twice)
            elif twice is True and x != "start" and x != "end":
                yield from all_paths(x, end, seen + [n], False)


with open("day12.txt", "r") as infile:
    data = infile.read().splitlines()
    G = defaultdict(list)
    for line in data:
        k, v = line.split("-")
        G[k].append(v)
        G[v].append(k)

    result = all_paths("start", "end", ["start"])
    print(len(list(result)))
