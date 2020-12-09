package puzzle9

import java.io.File

const val f = "input/puzzle9/input.txt"

fun solvePuzzle9(): Int {
    val nrLinesToParse = 25

    val file = File(f).readLines()
    file.subList(nrLinesToParse, file.size).forEachIndexed { index, line ->
        val linesToCheck = file.subList(index, index + nrLinesToParse)

        var hasFoundSum = false
        linesToCheck.forEachIndexed { op1Index, op1 ->
            linesToCheck.forEachIndexed { op2Index, op2 ->
                if (op1Index != op2Index && op1.toInt() + op2.toInt() == line.toInt()) {
                    hasFoundSum = true
                }
            }
        }

        if (!hasFoundSum) {
            return line.toInt()
        }
    }

    return 0
}

fun solvePuzzle9dot1(): Int {
    val target = 1124361034

    val file = File(f).readLines()

    var startingPosition = 0
    var interval = 1
    var sum = file[startingPosition].toInt()

    var highestSummedDigit = file[startingPosition].toInt()
    var lowestSummedDigit = file[startingPosition].toInt()

    while (true) {
        val currentDigit = file[startingPosition + interval].toInt()
        sum += currentDigit
        if (sum > target) {
            startingPosition++
            interval = 1
            sum = file[startingPosition].toInt()
            highestSummedDigit = file[startingPosition].toInt()
            lowestSummedDigit = file[startingPosition].toInt()

            continue
        }

        if (currentDigit > highestSummedDigit) {
            highestSummedDigit = currentDigit
        }
        if (currentDigit < lowestSummedDigit) {
            lowestSummedDigit = currentDigit
        }

        if (sum == target) {
            return highestSummedDigit + lowestSummedDigit
        }

        interval++
    }
}