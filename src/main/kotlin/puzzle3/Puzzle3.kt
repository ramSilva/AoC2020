package puzzle3

import java.io.File

data class Position(
    var row: Int,
    var column: Int
)

typealias Toboggan = Position

data class Mountain(
    val width: Int,
    val trees: List<Position>
)

fun parseInput(filename: String): Mountain {
    val trees = arrayListOf<Position>()
    var row = 0
    var column = 0

    File("input/puzzle3/$filename").forEachLine { line ->
        column = 0
        line.forEach { char ->
            if (char == '#') trees.add(Position(row, column))

            column++
        }
        row++
    }

    return Mountain(column, trees)
}

fun solvePuzzle3(): Int {
    val mountain = parseInput("input.txt")

    val position = Position(0, 0)
    val lowestTree = mountain.trees.last().row

    var encounteredTrees = 0

    while (true) {
        position.row += 1
        position.column += 3

        if (position.row > lowestTree) return encounteredTrees

        if (position.column >= mountain.width) position.column -= mountain.width
        if (mountain.trees.contains(position)) encounteredTrees++
    }
}

fun solvePuzzle3dot1(): Int {
    val mountain = parseInput("input.txt")

    val toboggans = arrayListOf(
        Toboggan(1, 1),
        Toboggan(1, 3),
        Toboggan(1, 5),
        Toboggan(1, 7),
        Toboggan(2, 1)
    )

    val lowestTree = mountain.trees.last().row

    val encounteredTreesPerToboggan = arrayListOf<Int>()

    toboggans.forEach { toboggan ->
        val position = Position(0, 0)
        var encounteredTrees = 0
        while (true) {
            position.row += toboggan.row
            position.column += toboggan.column

            if (position.row > lowestTree) {
                encounteredTreesPerToboggan.add(encounteredTrees)
                break
            }

            if (position.column >= mountain.width) position.column -= mountain.width
            if (mountain.trees.contains(position)) encounteredTrees++
        }
    }

    return encounteredTreesPerToboggan.reduce { acc, trees -> acc * trees }
}

data class TobogganRecord(
    val toboggan: Toboggan,
    val position: Position = Position(0, 0),
    var encounteredTrees: Int = 0,
    var hasFinished: Boolean = false
)

fun solvePuzzle3dot1MasComplicado(): Int {
    val mountain = parseInput("input.txt")

    val tobogganRecords = arrayListOf(
        TobogganRecord(Toboggan(1, 1)),
        TobogganRecord(Toboggan(1, 3)),
        TobogganRecord(Toboggan(1, 5)),
        TobogganRecord(Toboggan(1, 7)),
        TobogganRecord(Toboggan(2, 1)),
    )

    val lowestTree = mountain.trees.last().row
    var allToboggansFinished = false

    while (true) {
        tobogganRecords
            .filter { record -> !record.hasFinished }
            .also { records ->
                if (records.isEmpty()) allToboggansFinished = true
            }
            .forEach { record ->
                record.position.row += record.toboggan.row
                record.position.column += record.toboggan.column

                if (record.position.row > lowestTree) {
                    record.hasFinished = true
                }

                if (record.position.column >= mountain.width) record.position.column -= mountain.width
                if (mountain.trees.contains(record.position)) record.encounteredTrees++
            }

        if (allToboggansFinished) break
    }

    return tobogganRecords
        .map { record -> record.encounteredTrees }
        .reduce { acc, trees -> acc * trees }
}