from collections import deque 
from itertools import permutations

def reverse_subsequence(lst, start, end):
    # Create a copy of the original list to avoid modifying the input list
    result = lst.copy()
    # Validate indices
    if start < 0 or end >= len(lst) or start > end:
        raise ValueError("Invalid start or end indices")
    # Reverse the subsequence in-place
    while start < end:
        result[start], result[end] = result[end], result[start]
        start += 1
        end -= 1
    return result


def encrypt(password):
    for line in data:
        instruction = line.split()
        match instruction:
            case ['swap', 'letter', a, 'with', 'letter', b]:
                i = password.index(a) 
                j = password.index(b) 
                password[i], password[j] = password[j], password[i]
            case ['move', 'position', x, 'to', 'position', y]:
                x = int(x)
                y = int(y)
                c = password.pop(x)
                password.insert(y, c)
            case ['reverse', 'positions', x, 'through', y]:
                x = int(x)
                y = int(y)
                password = reverse_subsequence(password, x, y)
            case ['rotate', 'based', 'on', 'position', 'of', 'letter', a]:
                i = password.index(a)
                d = deque(password)
                d.rotate(i + (2 if i >= 4 else 1))
                password = list(d)
            case ['swap', 'position', x, 'with', 'position', y]:
                x = int(x)
                y = int(y)
                password[x], password[y] = password[y], password[x]
            case ['rotate', 'right', x, 'steps']:
                d = deque(password)
                d.rotate(int(x))
                password = list(d)
            case ['rotate', 'left', x, 'steps']:
                d = deque(password)
                d.rotate(int(x) * -1)
                password = list(d)
            case _:
                print(instruction)
                assert False 

    return password

with open("day21.txt", "r") as infile:
    data = infile.read().splitlines()
    coll = list("abcdefgh")
    target = "fbgdceah"
    for pw in permutations(coll):
        e = encrypt(list(pw))
        if "".join(e) == target:
            print("".join(pw))


