package puzzle6

import java.io.File

private const val f = "input/puzzle6/testInput.txt"

fun solvePuzzle6() =
    File(f).readText().split("\n\n").sumBy{it.filter{it!='\n'}.toCharArray().distinct().size}