from os import environ

def getSolutionPart1(data):
    x, y = 0, 0
    for position in data:
        if position[0] == 'f': y += int(position[-1])
        elif position[0] == 'd': x += int(position[-1])
        else: x -= int(position[-1])
    return x * y

def getSolutionPart2(data):
    x, y, a = 0, 0, 0
    for position in data:
        if position[0] == 'f':
            y += int(position[-1])
            x += int(position[-1]) * a
        elif position[0] == 'd': a += int(position[-1])
        else: a -= int(position[-1])
    return x * y

print('Python')
print(getSolutionPart2(open('input.txt').read().splitlines()) if environ.get('part') == 'part2' else getSolutionPart1(open('input.txt').read().splitlines()))