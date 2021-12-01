from os import environ


def getSolutionPart1(data):
    count = 0
    for i in range(1, len(data)):
        if data[i] > data[i-1]: count += 1
    return count


def getSolutionPart2(data):
    count = 0
    for i in range(1, len(data)):
        if sum(data[i:i+3]) > sum(data[i-1:i+2]): count += 1
    print(count)
    return count

with open('input.txt') as f:
    file_input = [int(x) for x in f.readlines()]

print('Python')
part = environ.get('part')

if part == 'part2':
    print(getSolutionPart2(file_input))
else:
    print(getSolutionPart1(file_input))


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    getSolutionPart2(file_input)