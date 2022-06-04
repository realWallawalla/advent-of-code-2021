import java.io.File

fun main() {
    val input = File("input.txt").readLines().map { it.map { it.toString().toInt() }.toIntArray() }
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(listOf(1))
        else -> solutionPart1(input)
    }
    println(answer)
}

private fun solutionPart1(input: List<IntArray>): Int {
    val columnSize = input[0].size
    val rowSize = input.size
    val lows = mutableListOf<Int>()

    val height2dMap = fill2dMap(input, rowSize, columnSize)

    for (row in 0 until rowSize) {
        for (col in 0 until columnSize) {
            if (isAdjecentCoordinatesHigher(height2dMap, row, col)) {
                lows.add(height2dMap[row][col])
            }
        }
    }
    return lows.map { it.inc() }.sum()
}

private fun fill2dMap(
    input: List<IntArray>,
    rowSize: Int,
    columnSize: Int
): Array<Array<Int>> {
    val grid = Array(rowSize) {Array(columnSize) {0} }

    for (row in 0 until rowSize) {
        for ((col, value) in input[row].withIndex()) {
            grid[row][col] = value
        }
    }
    return grid
}

private fun isAdjecentCoordinatesHigher(height2dMap: Array<Array<Int>>, row: Int, col: Int): Boolean {
    try {
        return normalPoint(height2dMap, row, col)
    } catch (e: ArrayIndexOutOfBoundsException) {
        try {
            return borderPoint(height2dMap, row, col)
        } catch (e: ArrayIndexOutOfBoundsException) {
            return cornerPoint(height2dMap, row, col)
        }
    }
}

private fun normalPoint(
    height2dMap: Array<Array<Int>>,
    row: Int,
    col: Int
) = (height2dMap[row][col] < height2dMap[row + 1][col]
        && height2dMap[row][col] < height2dMap[row - 1][col]
        && height2dMap[row][col] < height2dMap[row][col + 1]
        && height2dMap[row][col] < height2dMap[row][col - 1])

private fun borderPoint(
    height2dMap: Array<Array<Int>>,
    row: Int,
    col: Int): Boolean
{
    return when (row) {
        0 -> height2dMap[row][col] < height2dMap[row + 1][col]
                && height2dMap[row][col] < height2dMap[row][col + 1]
                && height2dMap[row][col] < height2dMap[row][col - 1]
        height2dMap.size-1 -> height2dMap[row][col] < height2dMap[row - 1][col]
                && height2dMap[row][col] < height2dMap[row][col + 1]
                && height2dMap[row][col] < height2dMap[row][col - 1]
        else ->
            when(col) {
                0 -> height2dMap[row][col] < height2dMap[row + 1][col]
                        && height2dMap[row][col] < height2dMap[row - 1][col]
                        && height2dMap[row][col] < height2dMap[row][col + 1]
                height2dMap[0].size-1 -> height2dMap[row][col] < height2dMap[row + 1][col]
                        && height2dMap[row][col] < height2dMap[row - 1][col]
                        && height2dMap[row][col] < height2dMap[row][col - 1]
                else -> false
            }
    }
}

private fun cornerPoint(
    height2dMap: Array<Array<Int>>,
    row: Int,
    col: Int): Boolean {
    var isLowestPoint = false
    if (row == 0 && col == 0) {
        isLowestPoint = height2dMap[row][col] < height2dMap[row][col+1] && height2dMap[row][col] < height2dMap[row+1][col]
    } else if (row == height2dMap.size-1 && col == 0) {
        isLowestPoint = height2dMap[row][col] < height2dMap[row][col+1] && height2dMap[row][col] < height2dMap[row-1][col]
    } else if (row == 0 && col == height2dMap[0].size-1) {
        isLowestPoint = height2dMap[row][col] < height2dMap[row][col-1] && height2dMap[row][col] < height2dMap[row+1][col]
    } else {
        isLowestPoint = height2dMap[row][col] < height2dMap[row][col-1] && height2dMap[row][col] < height2dMap[row-1][col]
    }
    return isLowestPoint
}

private fun solutionPart2(input: List<Int>) = input.reduce(Int::times)