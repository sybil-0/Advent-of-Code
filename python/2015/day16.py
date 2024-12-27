with open("day16.txt", "r") as infile:
    data = infile.read().splitlines()
    sues = {}
    real = {"children": 3, "cats": 7, "samoyeds": 2, "pomeranians": 3, "akitas": 0, "vizslas": 0, "goldfish": 5,
            "trees": 3, "cars": 2, "perfumes": 1}
    for line in data:
        sue, *more = line.split(":")
        more = "".join(more).split(",")
        d = {}
        for s in more:
            a, b = s.split()
            d[a.strip()] = int(b)
        sues[sue] = d

candidates = []
for sue, m in sues.items():
    is_real = True
    for k, v in m.items():
        if k in ["cats", "trees"]:
            if v <= real[k]:
                is_real = False
        elif k in ["pomeranians", "goldfish"]:
            if v >= real[k]:
                is_real = False
        else:
            if real[k] != v:
                is_real = False
    if is_real:
        candidates.append(sue)

print(candidates)
        
        
