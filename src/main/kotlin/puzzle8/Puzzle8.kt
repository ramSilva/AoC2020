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
                println(currentlyChangedLine)
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



typealias Line = Int

fun solvePuzzle8dot1Recursive(): Int {
    val lines = File(f).readText().split('\n')

    val accumulator = mutableListOf<Int>()
    getPath(lines, lines.size, accumulator)
    return accumulator.sum()
}

fun getPath(
    input: List<String>,
    target: Line,
    accumulator: MutableList<Int>,
    visitedLines: MutableList<Line> = arrayListOf(),
    hasFixed: Boolean = false
): Boolean {
    if (target == 0) {
        return true
    }
    input.forEachIndexed { i, l ->
        val (operation, argument) = l.split(' ')

        var success = false
        if (!visitedLines.contains(i)) {
            if (operation == "jmp" && target == i + argument.toInt()) {
                visitedLines.add(i)
                success = getPath(input, i, accumulator, visitedLines, hasFixed)
            }
            if (!success && operation == "nop" && target == i + 1) {
                visitedLines.add(i)
                success = getPath(input, i, accumulator, visitedLines, hasFixed)
            }
            if (!success && operation == "acc" && target == i + 1) {
                visitedLines.add(i)
                accumulator.add(argument.toInt())
                success = getPath(input, i, accumulator, visitedLines, hasFixed)
                if (!success) {
                    accumulator.remove(argument.toInt())
                }
            }
            if (!success && !hasFixed && operation == "nop" && target == i + argument.toInt()) {
                visitedLines.add(i)
                success = getPath(input, i, accumulator, visitedLines, true)
            }
            if (!success && !hasFixed && operation == "jmp" && target == i + 1) {
                visitedLines.add(i)
                success = getPath(input, i, accumulator, visitedLines, true)
            }
            if (success) {
                return true
            }
            visitedLines.remove(i)
        }
    }
    return false
}