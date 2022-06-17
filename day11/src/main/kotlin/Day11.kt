import java.io.File

fun main() {
    val input = File("input.txt").readLines()
        .flatMap { it.split(",") }
        .map { it.toInt() }
        .toList()

    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

private fun solutionPart1(grid: List<Int>): Int {
    return 1
}

private fun solutionPart2(input: List<Int>): Int {
    return 2
}