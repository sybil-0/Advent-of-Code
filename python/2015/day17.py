def find_combinations(buckets, target):
    def find_combinations_helper(index, remaining, current_combination):
        if remaining == 0:
            result_combinations.append(tuple(current_combination))
            return
        if remaining < 0 or index >= len(buckets):
            return
            
        # Include current bucket
        current_combination.append(buckets[index])
        find_combinations_helper(index + 1, remaining - buckets[index], current_combination)
        current_combination.pop()
        
        # Skip current bucket
        find_combinations_helper(index + 1, remaining, current_combination)
    
    result_combinations = []
    find_combinations_helper(0, target, [])
    return result_combinations

# Test the function with the given example
buckets = [33, 14, 18, 20, 45, 35, 16, 35, 1, 13, 18, 13, 50, 44, 48, 6, 24, 41, 30, 42 ]
target = 150

combinations = find_combinations(buckets, target)
min_len = min([len(c) for c in combinations])
count = 0
for c in combinations:
    if len(c) == min_len:
        count += 1
print(count)
