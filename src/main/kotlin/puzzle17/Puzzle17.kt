package puzzle17

import java.io.File
import kotlin.math.absoluteValue

const val f = "input/puzzle17/input.txt"

data class Coordinate(val x: Int, val y: Int, val z: Int) {
    fun isAdjacent(coordinate: Coordinate): Boolean {
        var isAdjacent = true

        if ((coordinate.x == x && coordinate.y == y && coordinate.z == z)
            || ((coordinate.x - x).absoluteValue > 1 || (coordinate.y - y).absoluteValue > 1 || (coordinate.z - z).absoluteValue > 1)
        )
            isAdjacent = false

        return isAdjacent
    }
}

fun solvePuzzle17(): Int {
    var cubePositions = mutableListOf<Coordinate>()

    File(f).readLines().forEachIndexed { y, s ->
        s.forEachIndexed { x, v ->
            if (v == '#') cubePositions.add(Coordinate(x, y, 0))
        }
    }


    for (i in 0 until 6) {
        val minX = cubePositions.fold(0) { acc, coordinate -> if (coordinate.x < acc) coordinate.x else acc }
        val maxX = cubePositions.fold(0) { acc, coordinate -> if (coordinate.x > acc) coordinate.x else acc }
        val minY = cubePositions.fold(0) { acc, coordinate -> if (coordinate.y < acc) coordinate.y else acc }
        val maxY = cubePositions.fold(0) { acc, coordinate -> if (coordinate.y > acc) coordinate.y else acc }
        val minZ = cubePositions.fold(0) { acc, coordinate -> if (coordinate.z < acc) coordinate.z else acc }
        val maxZ = cubePositions.fold(0) { acc, coordinate -> if (coordinate.z > acc) coordinate.z else acc }

        val cubePositionsCopy = cubePositions.toMutableList()

        for (x in minX - 1..maxX + 1) {
            for (y in minY - 1..maxY + 1) {
                for (z in minZ - 1..maxZ + 1) {
                    val currentCoordinate = Coordinate(x, y, z)
                    var adjacentNr = 0
                    cubePositions.forEach { position ->
                        if (position.isAdjacent(currentCoordinate)) {
                            adjacentNr++
                        }
                    }

                    if (cubePositions.contains(currentCoordinate) && (adjacentNr < 2 || adjacentNr > 3)) {
                        cubePositionsCopy.remove(currentCoordinate)
                    }
                    if (!cubePositions.contains(currentCoordinate) && adjacentNr == 3) {
                        cubePositionsCopy.add(currentCoordinate)
                    }
                }
            }
        }

        cubePositions = cubePositionsCopy
    }

    return cubePositions.size
}

data class HyperCoordinate(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun isAdjacent(coordinate: HyperCoordinate): Boolean {
        var isAdjacent = true

        if ((coordinate.x == x && coordinate.y == y && coordinate.z == z && coordinate.w == w)
            || ((coordinate.x - x).absoluteValue > 1
                    || (coordinate.y - y).absoluteValue > 1
                    || (coordinate.z - z).absoluteValue > 1)
            || (coordinate.w - w).absoluteValue > 1
        )
            isAdjacent = false

        return isAdjacent
    }
}

fun solvePuzzle17dot1(): Int {
    var cubePositions = mutableListOf<HyperCoordinate>()

    File(f).readLines().forEachIndexed { y, s ->
        s.forEachIndexed { x, v ->
            if (v == '#') cubePositions.add(HyperCoordinate(x, y, 0, 0))
        }
    }


    for (i in 0 until 6) {
        val minX = cubePositions.fold(0) { acc, coordinate -> if (coordinate.x < acc) coordinate.x else acc }
        val maxX = cubePositions.fold(0) { acc, coordinate -> if (coordinate.x > acc) coordinate.x else acc }
        val minY = cubePositions.fold(0) { acc, coordinate -> if (coordinate.y < acc) coordinate.y else acc }
        val maxY = cubePositions.fold(0) { acc, coordinate -> if (coordinate.y > acc) coordinate.y else acc }
        val minZ = cubePositions.fold(0) { acc, coordinate -> if (coordinate.z < acc) coordinate.z else acc }
        val maxZ = cubePositions.fold(0) { acc, coordinate -> if (coordinate.z > acc) coordinate.z else acc }
        val minW = cubePositions.fold(0) { acc, coordinate -> if (coordinate.w < acc) coordinate.w else acc }
        val maxW = cubePositions.fold(0) { acc, coordinate -> if (coordinate.w > acc) coordinate.w else acc }

        val cubePositionsCopy = cubePositions.toMutableList()

        for (x in minX - 1..maxX + 1) {
            for (y in minY - 1..maxY + 1) {
                for (z in minZ - 1..maxZ + 1) {
                    for (w in minW - 1..maxW + 1) {
                        val currentCoordinate = HyperCoordinate(x, y, z, w)
                        var adjacentNr = 0
                        cubePositions.forEach { position ->
                            if (position.isAdjacent(currentCoordinate)) {
                                adjacentNr++
                            }
                        }

                        if (cubePositions.contains(currentCoordinate) && (adjacentNr < 2 || adjacentNr > 3)) {
                            cubePositionsCopy.remove(currentCoordinate)
                        }
                        if (!cubePositions.contains(currentCoordinate) && adjacentNr == 3) {
                            cubePositionsCopy.add(currentCoordinate)
                        }
                    }
                }
            }
        }

        cubePositions = cubePositionsCopy
    }
    return cubePositions.size
}