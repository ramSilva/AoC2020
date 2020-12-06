package puzzle5

import java.io.File

const val f = "input/puzzle5/input.txt"

fun solvePuzzle5mesmoàMacho() =
    File(f).readLines()
        .map { it.fold("") { a, c -> a + if (c in "BR") "1" else "0" }.toInt(2) }
        .also { println("Puzzle 5: ${it.maxOrNull()}") }
        .sorted().zipWithNext().first { it.first != it.second - 1 }
        .also { println("Puzzle 5.1: ${it.first + 1}") }


fun solvePuzzle5paraMeninas() {
    val seats = File(f).readLines()

    val seatCodes = arrayListOf<Int>()
    for (seat in seats) {
        var maxR = 127
        var minR = 0

        var maxC = 7
        var minC = 0

        for (i in 0..6) {
            val character = seat[i]

            if (character == 'F') {
                maxR = (maxR + minR - 1) / 2
            } else {
                minR = (maxR + minR + 1) / 2
            }
        }

        for (i in 7..9) {
            val character = seat[i]

            if (character == 'L') {
                maxC = (maxC + minC - 1) / 2
            } else {
                minC = (maxC + minC + 1) / 2
            }
        }

        seatCodes.add(maxR * 8 + maxC)
    }

    println("Puzzle 5: ${seatCodes.maxOrNull()}")

    var ourSeat = 0

    seatCodes.sort()
    seatCodes.forEachIndexed { i, _ ->
        if (i < seatCodes.size - 1) {
            if (seatCodes[i] != seatCodes[i + 1] - 1) {
                ourSeat = seatCodes[i] + 1
            }
        }
    }

    println("Puzzle 5.1: $ourSeat")
}