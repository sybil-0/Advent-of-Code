from collections import Counter
from functools import cmp_to_key


CARDS = "AKQJT98765432"

def score(hand):
    c = sorted(list(Counter(hand).values()))
    match c:
        case [5]:
            return 0
        case [1, 4]:
            return 1 
        case [2, 3]:
            return 2 
        case [1, 1, 3]:
            return 3 
        case [1, 2, 2]:
            return 4 
        case [1, 1, 1, 2]:
            return 5 
        case [1, 1, 1, 1, 1]:
            return 6
        case _:
            print(hand)
            assert False

def compare_hands(a: str, b: str) -> int:
    x = score(a)
    y = score(b)
    if x < y:
        return 1 
    elif y < x:
        return -1 
    else:
        for m, n in zip(a, b):
            if CARDS.index(m) < CARDS.index(n):
                return 1
            elif CARDS.index(n) < CARDS.index(m):
                return -1

    print("invalid state")
    assert False


with open("day7.txt", "r") as infile:
    data = infile.read().splitlines()
    hands = {}
    for line in data:
        hand, bid = line.split()
        hands[hand] = int(bid)

    ks = list(hands.keys())
    ks.sort(key=cmp_to_key(compare_hands))
    total = 0
    for i, hand in enumerate(ks, start=1):
        total += i * hands[hand]
    print(total)


