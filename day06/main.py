from os import environ

def get_solution_part1(init_fish_state, days):
    return simulate_fish_expansion(init_fish_state, days)

def get_solution_part2(init_fish_state, days):
    return simulate_fish_expansion(init_fish_state, days)

def simulate_fish_expansion(init_fish_state, days):
    init_fish_state = [int(i) for i in init_fish_state]
    fishes = [0] * 10
    for fish in init_fish_state:
        fishes[fish] += 1
    for _ in range(1, days):
        for idx, fish in enumerate(fishes):
            if (fish > 0):
                fishes[idx - 1] = fishes[idx]
                fishes[idx] = 0
        if (fishes[0] > 0):
            fishes[7] = fishes[7] + fishes[0]
            fishes[9] = fishes[0]
            fishes[0] = 0
    return sum(fishes)

if __name__ == '__main__':
    print('Python')
    splitlines = open('input.txt').read().split(",")
    print(get_solution_part2(splitlines, days=256) if environ.get('part') == 'part2' else get_solution_part1(splitlines, days=80))