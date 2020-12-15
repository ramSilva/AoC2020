package puzzle15

import java.io.File
import java.util.*

const val f = "input/puzzle15/input.txt"

fun solvePuzzle15(): Int {
    val spokenNumbers = arrayListOf<Int>()

    File(f).readText().split(',').forEach {
        spokenNumbers.add(it.toInt())
    }

    for (i in spokenNumbers.size until 2020) {
        val allButLast = spokenNumbers.subList(0, spokenNumbers.size - 1)

        if (allButLast.contains(spokenNumbers.last())) {
            spokenNumbers.add(spokenNumbers.size - 1 - allButLast.indexOfLast { it == spokenNumbers.last() })
        } else {
            spokenNumbers.add(0)
        }
    }


    return spokenNumbers.last()
}

fun solvePuzzle15dot1(): Int {
    val spokenNumbers = mutableMapOf<Int, Int>()

    var numberToLookFor = File(f).readText().split(',')
        .map { it.toInt() }
        .also {
            it.dropLast(1).forEachIndexed { i, v ->
                spokenNumbers[v] = i
            }
        }.last()

    for (i in spokenNumbers.size until 30000000 - 1) {
        val nextNumberToLookFor = i - (spokenNumbers[numberToLookFor] ?: i)
        spokenNumbers[numberToLookFor] = i

        numberToLookFor = nextNumberToLookFor
    }

    return numberToLookFor
}
