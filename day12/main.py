import re
from os import environ

def get_solution_part1(input):
    return 1

def get_solution_part2(input):
    return 2

if __name__ == '__main__':
    input = open('input.txt').read()
    print(get_solution_part2(input) if environ.get('part') == 'part2' else get_solution_part1(input))