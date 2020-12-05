package puzzle5

import java.io.File

const val f = "input/puzzle5/testInput.txt"

fun solvePuzzle5mesmoàMacho() =
    File(f).readLines().maxOf { it.fold("") { a, c -> a + if (c == 'B' || c == 'R') "1" else "0" }.toInt(2) }
