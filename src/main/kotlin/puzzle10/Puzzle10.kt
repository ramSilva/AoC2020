package puzzle10

import java.io.File

const val f = "input/puzzle10/input.txt"

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

class Node {
    private val parents = mutableListOf<Node>()

    var alternatives: Long = 1

    fun addParent(node: Node) {
        parents.add(node)

        alternatives = parents.sumByLong { it.alternatives }
    }
}

fun solvePuzzle10dot1(): Long {
    val lines = File(f).readLines().map { it.toInt() }.sorted()

    val graph = sortedMapOf<Int, Node>()
    graph[0] = Node()

    lines.forEach { line ->
        val node = Node()
        graph[line] = node

        for (i in (line - 3) until line) {
            if (graph[i] == null) {
                continue
            }
            node.addParent(graph[i]!!)
        }
    }

    return graph[graph.lastKey()!!]!!.alternatives
}