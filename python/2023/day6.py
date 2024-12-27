races = [(46689866, 358105418071080)]

total = 1
for t, d in races:
    wins = 0
    for i in range(t):
        wins += (t - i) * i > d
    print(wins)
    total *= wins

print(total)

