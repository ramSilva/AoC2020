package puzzle1

import java.io.File

fun solvePuzzle1() = File("input/puzzle1/input.txt").readLines().zipWithNext().count { it.first.toInt() < it.second.toInt() }

fun solvePuzzle1dot1(): Int {
    return 0
}
