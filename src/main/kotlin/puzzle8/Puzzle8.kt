package puzzle8

import java.io.File

const val f = "input/puzzle8/input.txt"

fun solvePuzzle8(): Int {
    val lines = File(f).readText().split('\n')
    var currentLine = 0
    var accumulator = 0
    val visitedLines = arrayListOf<Int>()

    while (true) {
        if (visitedLines.contains(currentLine)) {
            return accumulator
        }
        visitedLines.add(currentLine)

        val line = lines[currentLine]
        val (instruction, argument) = line.split(' ')

        when (instruction) {
            "acc" -> {
                accumulator += argument.toInt()
                currentLine++
            }
            "jmp" -> {
                currentLine += argument.toInt()
            }
            else -> {
                currentLine++
            }
        }
    }
}


fun solvePuzzle8dot1(): Int {
    val lines = File(f).readText().split('\n')

    var currentlyChangedLine = 0

    while (true) {
        val alteredLines = lines.toMutableList()
        val line = alteredLines[currentlyChangedLine]

        val (instruction) = line.split(' ')

        if (instruction == "nop") {
            alteredLines[currentlyChangedLine] = alteredLines[currentlyChangedLine].replace("nop", "jmp")
            currentlyChangedLine++
        } else if (instruction == "jmp") {
            alteredLines[currentlyChangedLine] = alteredLines[currentlyChangedLine].replace("jmp", "nop")
            currentlyChangedLine++
        } else {
            currentlyChangedLine++
            continue
        }

        var currentLine = 0
        var accumulator = 0
        val visitedLines = arrayListOf<Int>()
        while (true) {
            if (currentLine == alteredLines.size) {
                return accumulator
            }

            if (visitedLines.contains(currentLine)) {
                break
            }

            visitedLines.add(currentLine)

            val line = alteredLines[currentLine]
            val (instruction, argument) = line.split(' ')

            when (instruction) {
                "acc" -> {
                    accumulator += argument.toInt()
                    currentLine++
                }
                "jmp" -> {
                    currentLine += argument.toInt()
                }
                else -> {
                    currentLine++
                }
            }
        }
    }
}