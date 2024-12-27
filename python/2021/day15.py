import networkx as nx

LIM = 499

with open("day15.txt", "r") as infile:
    data = infile.read().splitlines()
    G = nx.Graph()
    for i, line in enumerate(data):
        for j, c in enumerate(line):
            if not (0 <= i <= LIM and 0 <= j <= LIM):
                continue
            for x, y in [(0, 1), (0, -1), (1, 0), (-1, 0)]:
                m, n = x + i, j + y
                if 0 <= m <= LIM and 0 <= n <= LIM:
                    G.add_edge(
                        (i, j),
                        (m, n),
                        weight=int(data[i % 100][j % 100])
                        + int(data[m % 100][n % 100]),
                    )

    p = nx.shortest_path(G, (0, 0), (LIM, LIM), weight="weight")
    score = 0
    for x, y in p[1:]:
        score += int(data[x][y])
    print(score)
