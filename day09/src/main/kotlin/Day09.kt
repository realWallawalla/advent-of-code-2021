import java.io.File

fun main() {
    val input = File("input.txt").readLines().
    mapIndexed { y, row ->
        row.mapIndexed  { x, depth -> Point(x, y, depth.digitToInt()) }
            .toTypedArray() }
        .toTypedArray()

    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

private fun solutionPart1(grid: Array<Array<Point>>): Int {
    val lows = mutableListOf<Int>()
    for (row in 0 until grid.size) {
        for (col in 0 until grid[0].size) {
            if (isLowPoint(grid, grid[row][col])) {
                lows.add(grid[row][col].depthValue)
            }
        }
    }
    return lows.map { it.inc() }.sum()
}

private fun isLowPoint(height2dMap: Array<Array<Point>>, point: Point): Boolean {
    return point.getNeighbors(height2dMap).all { neighbor -> point.depthValue < neighbor.depthValue }
}

private fun solutionPart2(input: Array<Array<Point>>): Int {
    val yMax = input.size
    val xMax = input[0].size

    val basins = findDepthOfBasins(yMax, xMax, input)
    return basins.sorted().takeLast(3).reduce {acc, next -> acc * next}
}

private fun findDepthOfBasins(
    yMax: Int,
    xMax: Int,
    height2dMap: Array<Array<Point>>,
): MutableList<Int> {
    val basinSizes = mutableListOf<Int>()

    for (y in 0 until yMax) {
        for (x in 0 until xMax) {
            if (isLowPoint(height2dMap, height2dMap[y][x])) {
                basinSizes.add(findBasinSize(height2dMap, height2dMap[y][x]))
            }
        }
    }
    return basinSizes
}
fun findBasinSize(height2dMap: Array<Array<Point>>, point: Point): Int {
    if (point.depthValue == 9) {
        return 0 // we want to stop at 9. lowpoints is origin regardless of type of point
    }
    point.depthValue = 9 // reassign to not count twice
    return 1 + point.getNeighbors(height2dMap).map { findBasinSize(height2dMap, it) }.sum()
}
data class Point(val x: Int, val y: Int, var depthValue: Int) {
    fun isNormalPoint(yMax: Int, xMax: Int): Boolean =
        y != 0 && y != yMax && x != 0 && x != xMax

    fun isCornerPoint(yMax: Int, xMax: Int): Boolean =
        (y == 0 && x == 0) || (y == 0 && x == xMax) || (y == yMax && x == 0) || (y == yMax && x == xMax)

    fun isBorderPoint(yMax: Int, xMax: Int): Boolean {
        return !isNormalPoint(yMax, xMax) && !isCornerPoint(yMax, xMax)
    }

    fun getNeighbors(height2dMap: Array<Array<Point>>): List<Point> {
        val yMax = height2dMap.size - 1
        val xMax = height2dMap[0].size - 1

        var neighbors = cornersNeighbors(height2dMap, yMax, xMax)
        if (neighbors.isEmpty()) {
            neighbors = borderNeighbors(height2dMap, yMax, xMax)
        }
        if (neighbors.isEmpty()) {
            neighbors = normalNeighbors(height2dMap)
        }

        return neighbors
    }

    private fun cornersNeighbors(height2dMap: Array<Array<Point>>, yMax: Int, xMax: Int): List<Point> {
        if (this == height2dMap[0][0]) {
            return mutableListOf(height2dMap[y][x+1], height2dMap[y+1][x])
        } else if (this == height2dMap[0][xMax]) {
            return mutableListOf(height2dMap[y][x-1], height2dMap[y+1][x])
        } else if (this == height2dMap[yMax][0]) {
            return mutableListOf(height2dMap[y][x+1], height2dMap[y-1][x])
        } else if (this == height2dMap[yMax][xMax]) {
            return mutableListOf(height2dMap[y][x-1], height2dMap[y-1][x])
        } else {
            return emptyList()
        }
    }

    private fun borderNeighbors(height2dMap: Array<Array<Point>>, yMax: Int, xMax: Int): List<Point> {
        if (y == 0) {
            return mutableListOf(height2dMap[y+1][x], height2dMap[y][x+1], height2dMap[y][x-1])
        } else if (y == yMax) {
            return mutableListOf(height2dMap[y-1][x], height2dMap[y][x+1], height2dMap[y][x-1])
        } else if (x == 0) {
            return mutableListOf(height2dMap[y+1][x], height2dMap[y-1][x], height2dMap[y][x+1])
        } else if (x == xMax) {
            return mutableListOf(height2dMap[y+1][x], height2dMap[y-1][x], height2dMap[y][x-1])
        } else {
            return emptyList()
        }
    }

    private fun normalNeighbors(height2dMap: Array<Array<Point>>): List<Point> {
        return mutableListOf(height2dMap[y][x-1], height2dMap[y][x+1], height2dMap[y+1][x], height2dMap[y-1][x])
    }
}