package puzzle5

import java.io.File

const val f = "input/puzzle5/testInput.txt"

fun solvePuzzle5mesmoàMacho() = Integer.valueOf(
    File(f).readText().split('\n')
        .maxOf { it.fold(0) { a, c -> a * 10 + if (c == 'B' || c == 'R') 1 else 0 } }.toString(), 2
)