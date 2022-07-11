import space.kscience.plotly.Plotly
import space.kscience.plotly.makeFile
import space.kscience.plotly.models.Scatter
import space.kscience.plotly.models.ScatterMode
import space.kscience.plotly.models.Symbol
import space.kscience.plotly.trace
import java.io.File

fun main() {
    val lines = File("input.txt").readLines()
    val points = lines.filter { it.isNotBlank() && it[0] in '0'..'9' }
        .map {
            val x = it.substring(0, it.indexOf(',')).toInt()
            val y = it.substring(it.indexOf(',') + 1, it.lastIndex + 1).toInt()
            Point(x, y)
        }
        .toMutableSet()
    val foldInstruction = lines.filter { it.isNotBlank() && it[0] == 'f' }
        .map {
            val axisIndex = it.indexOf('x', 11)
            val value = it.substring(13)
            if (axisIndex == -1) {
                FoldingInstruction("up", value.toInt())
            } else {
                FoldingInstruction("left", value.toInt())
            }
        }
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(points, foldInstruction)
        else -> solutionPart1(points, foldInstruction)
    }
    println(answer)
}

private fun solutionPart1(points: MutableSet<Point>, foldingInstrs: List<FoldingInstruction>): Int {
    val foldInstruction = foldingInstrs[0]
    fold2dplane(foldInstruction, points)
    return points.size
}

private fun fold2dplane(foldInstruction: FoldingInstruction, points: MutableSet<Point>) {
    val foldedPoints = mutableSetOf<Point>()
    if (foldInstruction.axis == "up") {
        points
            .filter { it.y > foldInstruction.value }
            .groupBy { it.y }
            .map { entry ->
                val row = entry.key
                val pointsOnRow = entry.value
                val diff = row - foldInstruction.value

                points.removeAll(pointsOnRow)
                foldedPoints.addAll(pointsOnRow
                    .map { Point(it.x, foldInstruction.value - diff) }
                    .toMutableSet())
            }
    } else {
        points
            .filter { it.x > foldInstruction.value }
            .groupBy { it.x }
            .map { entry ->
                val col = entry.key
                val pointsOnCol = entry.value
                val diff = col - foldInstruction.value

                points.removeAll(pointsOnCol)
                foldedPoints.addAll(pointsOnCol
                    .map { Point(foldInstruction.value - diff, it.y) }
                    .toMutableSet())
            }
    }
    points.addAll(foldedPoints)
}

private fun solutionPart2(points: MutableSet<Point>, foldingInstrs: List<FoldingInstruction>) {
    for (foldInstruction in foldingInstrs) {
        fold2dplane(foldInstruction, points)
    }
    plot(points)
}

fun plot(points: MutableSet<Point>) {
    //plot is updside down, you can still see the letters though
    val xValues = points.map { it.x }
    val yValues = points.map { it.y }

    val trace1 = Scatter {
        x.numbers = xValues
        y.numbers = yValues
        mode = ScatterMode.markers
        marker {
            color("rgba(204, 204, 204, 0.95)")
            symbol = Symbol.circle
            size = 16
        }

    }

    val plot = Plotly.plot {
        traces(trace1)
    }
    plot.makeFile()
}

data class Point(var x: Int, var y: Int)

data class FoldingInstruction(val axis: String, val value: Int)