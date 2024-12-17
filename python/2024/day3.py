import re 


with open("out.txt", "r") as infile:
    data = infile.read()
    data = re.sub(r"don't\(\)[\S\s]+?do\(\)", "", data)
    print(data)
    # part 1 
    total = 0
    results = re.findall(r"mul\(\d+,\d+\)", data)
    for s in results:
        a, b = re.findall(r"\d+", s)
        total += int(a) * int(b)

    print(total)

