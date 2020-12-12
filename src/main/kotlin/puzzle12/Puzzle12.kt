package puzzle12

import java.io.File
import kotlin.math.*

const val f = "input/puzzle12/input.txt"

typealias Coordinate = Pair<Int, Int>

fun solvePuzzle12(): Int {
    val orientations = mapOf(
        Pair(0, Pair(1, 0)),
        Pair(90, Pair(0, 1)),
        Pair(180, Pair(-1, 0)),
        Pair(270, Pair(0, -1))
    )
    var orientation = 90
    var coordinate = Coordinate(0, 0)

    File(f).readLines().forEach {
        val regex = Regex("""(\w)(\d+)""")
        val (operation, amount) = regex.find(it)!!.destructured

        when (operation) {
            "N" -> coordinate = coordinate.copy(first = coordinate.first + amount.toInt())
            "S" -> coordinate = coordinate.copy(first = coordinate.first - amount.toInt())
            "E" -> coordinate = coordinate.copy(second = coordinate.second + amount.toInt())
            "W" -> coordinate = coordinate.copy(second = coordinate.second - amount.toInt())
            "L" -> orientation -= amount.toInt()
            "R" -> orientation += amount.toInt()
            "F" -> coordinate =
                coordinate.copy(
                    first = coordinate.first + amount.toInt() * orientations[orientation]!!.first,
                    second = coordinate.second + amount.toInt() * orientations[orientation]!!.second
                )
        }

        if (orientation >= 360) {
            orientation -= 360
        }
        if (orientation < 0) {
            orientation += 360
        }
    }

    return coordinate.first.absoluteValue + coordinate.second.absoluteValue
}

fun solvePuzzle12dot1(): Int {
    fun rotateWaypoint(waypoint: Pair<Double, Double>, angle: Int): Pair<Double, Double> {
        val waypointDistance = sqrt(waypoint.first * waypoint.first + waypoint.second * waypoint.second)
        val waypointAngle = atan2(waypoint.first, waypoint.second) + (angle * PI / 180.0)

        return Pair(
            round(sin(waypointAngle) * waypointDistance),
            round(cos(waypointAngle) * waypointDistance)
        )
    }

    val orientations = mapOf(
        Pair("N", Pair(1, 0)),
        Pair("E", Pair(0, 1)),
        Pair("S", Pair(-1, 0)),
        Pair("W", Pair(0, -1))
    )

    var coordinate = Coordinate(0, 0)
    var waypoint = Pair(1.0, 10.0)

    File(f).readLines().forEach {
        val regex = Regex("""(\w)(\d+)""")
        val (operation, amount) = regex.find(it)!!.destructured

        when (operation) {
            "N", "E", "S", "W" -> {
                waypoint = waypoint.copy(
                    first = waypoint.first + amount.toInt() * orientations[operation]!!.first,
                    second = waypoint.second + amount.toInt() * orientations[operation]!!.second
                )
            }
            "R" -> {
                waypoint = rotateWaypoint(waypoint, -amount.toInt())
            }
            "L" -> {
                waypoint = rotateWaypoint(waypoint, amount.toInt())
            }
            "F" -> {
                coordinate = coordinate.copy(
                    first = coordinate.first + waypoint.first.toInt() * amount.toInt(),
                    second = coordinate.second + waypoint.second.toInt() * amount.toInt(),
                )
            }
        }
    }

    return coordinate.first.absoluteValue + coordinate.second.absoluteValue
}