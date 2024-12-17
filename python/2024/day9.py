with open("day9.txt", "r") as infile:
    data = [int(x) for x in infile.read().strip()]


blocks = []
for i, x in enumerate(data):
    if x == 0:
        continue
    if i % 2 == 0:
        blocks.append((x, i // 2))
    else:
        blocks.append((x, -1))


# part 2
files = [b for b in blocks if b[1] != -1]

while files:
    x, y = files.pop()
    idx = blocks.index((x, y))
    updates = []
    for k, (i, j) in enumerate(blocks):
        if j == -1 and i >= x and k < idx:
            updates = [(k, (x, y)), (k + 1, (i - x, -1)), (idx + 1, (x, -1))]
            break

    if updates:
        p, b = updates[0]
        p2, b2 = updates[1]
        p3, b3 = updates[2]
        blocks[p] = b
        blocks.insert(p2, b2)
        blocks[p3] = b3

checksum = 0
result = []
for x, y in blocks:
    result.extend(x * [y])

for i, x in enumerate(result):
    if x == -1:
        continue
    checksum += i * x

print(checksum)
