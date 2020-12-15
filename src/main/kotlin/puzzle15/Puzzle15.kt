package puzzle15

import java.io.File

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
    val spokenNumbers = mutableMapOf<Int, MutableList<Int>>()

    var numberToLookFor = 0

    File(f).readText().split(',').forEachIndexed { i, v ->
        spokenNumbers[v.toInt()] = mutableListOf(i)
        numberToLookFor = v.toInt()
    }

    for (i in spokenNumbers.size until 30000000) {
        numberToLookFor = i - 1 - (spokenNumbers[numberToLookFor]!!.lastOrNull {
            it != i - 1
        } ?: i - 1)

        if (spokenNumbers[numberToLookFor] != null) {
            if (spokenNumbers[numberToLookFor]!!.size > 1) {
                spokenNumbers[numberToLookFor]!!.removeFirst()
            }
            spokenNumbers[numberToLookFor]!!.add(i)
        } else {
            spokenNumbers[numberToLookFor] = arrayListOf(i)
        }
    }

    return numberToLookFor
}
