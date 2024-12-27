def parse_nodes(xs, score=False):
    global total_sum
    children = xs.pop(0)
    metadata = xs.pop(0)

    if children == 0:
        for _ in range(metadata):
            total_sum += xs.pop(0)
    else:
        for i in range(1, children):
            parse_nodes(xs)
        for _ in range(metadata):
            total_sum += xs.pop(0)


with open("day8.txt", "r") as infile:
    data = [int(x) for x in infile.read().split()]
    total_sum = 0
    parse_nodes(data)
    print(total_sum)
    print(data)
