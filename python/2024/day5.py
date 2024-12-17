from functools import cmp_to_key


def comp(x, y):
    if [x, y] in orderings: #type: ignore
        return -1 
    elif [y, x] in orderings: #type: ignore
        return 1 
    else:
        return 0

with open("day5.txt", "r") as infile:
    data = infile.read()
    orderings, pages = data.split("\n\n")
    orderings = [[int(x) for x in s.split()] for s in orderings.splitlines()]
    pages = [[int(x) for x in s.split(",")] for s in pages.splitlines()]
    total = 0
    total_p2 = 0

    for entry in pages:
        entry_sorted = sorted(entry, key=cmp_to_key(comp))
        if entry_sorted == entry:
            total += entry[len(entry) // 2]
        else:
            total_p2 += entry_sorted[len(entry) // 2]


    print(total)
    print(total_p2)


