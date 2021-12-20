import re
from os import environ

def get_solution_part1(input):
    count, input_, output_numbers = 0, [], []
    for line in input[1:]: input_.append(line.split("\n", 1)[0])
    for line in input_: output_numbers.append(line.split(" "))
    for numbers in output_numbers:
        for number in numbers:
            if(len(number) == 2 or len(number) == 3 or len(number) == 7 or len(number) == 4): count += 1
    return count

def get_solution_part2(input):
    signal_patterns, output_codes, answers = [input[0].split()], [], []
    segment_maps = {}
    for line in input[1:]: output_codes.append(line.split()[:4])
    for line in input[1:-1]: signal_patterns.append(line.split("\n", 1)[1].split())
    for i, patterns in enumerate(signal_patterns):
        segment_map = {1: "", 2: "", 3: "", 4: "", 5: "", 6: "", 7: "", 8: ""}
        for pattern in patterns:
            if len(pattern) == 2: segment_map[1] = pattern
            elif len(pattern) == 3: segment_map[7] = pattern
            elif len(pattern) == 4: segment_map[4] = pattern
            elif len(pattern) == 7: segment_map[8] = pattern
        segment_maps[i] = segment_map

    for i, codes in enumerate(output_codes):
        segment_map = segment_maps[i]
        answer = ""
        for code in codes:
            if len(code) == 2: answer = answer + "1"
            elif len(code) == 3: answer = answer + "7"
            elif len(code) == 4: answer = answer + "4"
            elif len(code) == 7: answer = answer + "8"
            elif len(code) == 6 and containsAll(code, segment_map[4]): answer = answer + "9"
            elif len(code) == 6 and (containsAll(code, segment_map[1]) or containsAll(code, segment_map[7])): answer = answer + "0"
            elif len(code) == 6: answer = answer + "6"
            elif len(code) == 5 and (containsAll(code, segment_map[1]) or containsAll(code, segment_map[7])): answer = answer + "3"
            elif len(code) == 5 and (countContainingLetter(code, segment_map[4]) == 3): answer = answer + "5"
            else: answer = answer + "2"
        answers.append(answer)
    return sum(map(int, answers))

def containsAll(output_code, signal_pattern):
    return all(c in output_code for c in signal_pattern)

def countContainingLetter(output_code, signal_pattern):
    count = 0
    for c in output_code:
        if c in signal_pattern:
            count += 1
    return count

if __name__ == '__main__':
    print('Python')
    input = open('input.txt').read().split("|")
    print(get_solution_part2(input) if environ.get('part') == 'part2' else get_solution_part1(input))