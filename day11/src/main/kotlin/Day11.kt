import java.io.File

fun main() {
    val input = File("input.txt").readLines()
        .mapIndexed { y, row ->
        row.mapIndexed  { x, energy -> Octopus(energy.digitToInt(), x, y) }
            .toTypedArray() }
        .toTypedArray()

    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

private fun solutionPart1(grid: Array<Array<Octopus>>): Int {
    val yMax = grid.size
    val xMax = grid[0].size
    var flasherCount = 0

    for (i in 0 until 100) {
        val hasFlashed = mutableSetOf<Octopus>()
        for (y in 0 until yMax) {
            for (x in 0 until xMax) {
                val octopus = grid[y][x]
                if (!hasFlashed.contains(octopus)) {
                    octopus.energyLevel += 1
                }
                flasherCount += incNeighborsAndCount(octopus, grid, hasFlashed)
            }
        }
    }
    return flasherCount
}

fun incNeighborsAndCount(octupus: Octopus, grid: Array<Array<Octopus>>, hasFlashed: MutableSet<Octopus>): Int {
    if (octupus.energyLevel > 9) {
        val neighbors = octupus.getNeighbors(grid)
        octupus.energyLevel = 0
        hasFlashed.add(octupus)
        //count flashers
        return 1 + neighbors
            .filter { !hasFlashed.contains(it) }
            .map { it.incEnergyLevel() }
            .map { incNeighborsAndCount(it, grid, hasFlashed)}
            .sum()
    }
    return 0 //no flash, return 0
}

private fun solutionPart2(grid: Array<Array<Octopus>>): Int {
    val yMax = grid.size
    val xMax = grid[0].size
    val numberOfOctopuses = yMax * xMax
    var hasFlashed = mutableSetOf<Octopus>()
    var stepCounter = 0

    while (hasFlashed.size != numberOfOctopuses) {
        hasFlashed = mutableSetOf()
        for (y in 0 until yMax) {
            for (x in 0 until xMax) {
                val octopus = grid[y][x]
                if (!hasFlashed.contains(octopus)) {
                    octopus.energyLevel += 1
                }
                incNeighborsAndCount(octopus, grid, hasFlashed)
            }
        }
        stepCounter += 1
    }
    return stepCounter
}

data class Octopus(var energyLevel: Int, val x: Int, val y: Int) {

    fun incEnergyLevel(): Octopus {
        this.energyLevel += 1
        return this
    }
    fun getNeighbors(grid: Array<Array<Octopus>>): List<Octopus> {
        val yMax = grid.size - 1
        val xMax = grid[0].size - 1

        var neighbors = cornerNeighbors(grid, yMax, xMax)
        if (neighbors.isEmpty()) {
            neighbors = borderNeighbors(grid, yMax, xMax)
        }
        if (neighbors.isEmpty()) {
            neighbors = normalNeighbors(grid)
        }

        return neighbors
    }

    private fun cornerNeighbors(grid: Array<Array<Octopus>>, yMax: Int, xMax: Int): List<Octopus> {
        if (this == grid[0][0]) {
            return mutableListOf(grid[y][x+1], grid[y+1][x], grid[y+1][x+1])
        } else if (this == grid[0][xMax]) {
            return mutableListOf(grid[y][x-1], grid[y+1][x], grid[y+1][x-1])
        } else if (this == grid[yMax][0]) {
            return mutableListOf(grid[y][x+1], grid[y-1][x], grid[y-1][x+1])
        } else if (this == grid[yMax][xMax]) {
            return mutableListOf(grid[y][x-1], grid[y-1][x], grid[y-1][x-1])
        } else {
            return emptyList()
        }
    }

    private fun borderNeighbors(grid: Array<Array<Octopus>>, yMax: Int, xMax: Int): List<Octopus> {
        if (y == 0) {
            return mutableListOf(grid[y+1][x], grid[y][x+1], grid[y][x-1], grid[y+1][x-1], grid[y+1][x+1])
        } else if (y == yMax) {
            return mutableListOf(grid[y-1][x], grid[y][x+1], grid[y][x-1], grid[y-1][x-1], grid[y-1][x+1])
        } else if (x == 0) {
            return mutableListOf(grid[y+1][x], grid[y-1][x], grid[y][x+1], grid[y-1][x+1], grid[y+1][x+1])
        } else if (x == xMax) {
            return mutableListOf(grid[y+1][x], grid[y-1][x], grid[y][x-1], grid[y+1][x-1], grid[y-1][x-1])
        } else {
            return emptyList()
        }
    }

    private fun normalNeighbors(grid: Array<Array<Octopus>>): List<Octopus> {
        return mutableListOf(grid[y][x-1],
            grid[y][x+1],
            grid[y+1][x],
            grid[y-1][x],
            grid[y-1][x-1],
            grid[y+1][x-1],
            grid[y-1][x+1],
            grid[y+1][x+1])
    }
}