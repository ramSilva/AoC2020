package puzzle10

import java.io.File
import java.util.*

const val f = "input/puzzle10/testInput.txt"

fun solvePuzzle10(): Int {
    val lines = File(f).readLines().map { it.toInt() }.sorted()
    var diff1 = 0
    var diff3 = 0
    val firstDiff = lines[0]
    if (firstDiff == 1) {
        diff1++
    }
    if (firstDiff == 3) {
        diff3++
    }
    lines.forEachIndexed { index, line ->
        if (index == lines.size - 1) {
            diff3++
        } else {
            val diff = lines[index + 1] - line
            if (diff == 1) {
                diff1++
            }
            if (diff == 3) {
                diff3++
            }
        }
    }

    return diff1 * diff3
}

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun solvePuzzle10dot1(): Long {
    val lines = File(f).readLines().map { it.toInt() }.sorted()

    val graph = TreeMap<Int, Long>()
    graph[0] = 1

    lines.forEach { line ->
        graph[line] = 0

        for (i in (line - 3) until line) {
            graph[line] = graph[line]!! + (graph[i] ?: 0)
        }
    }

    return graph.lastEntry().value
}

fun solvePuzzle10dot1EmChines() =
    File(f).readLines().map{it.toInt()}.sorted().fold(TreeMap(mapOf(Pair(0,1L)))){a,v->a[v]=0;for(i in v-3 until v)a[v]=(a[i]?:0)+a[v]!!;a}.lastEntry().value