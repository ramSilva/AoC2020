package puzzle11

import java.io.File

const val f = "input/puzzle11/input.txt"

fun solvePuzzle11(): Int {
    var seats = File(f).readLines()
    val airportWidth = seats[0].length

    while (true) {
        val seatsCopy = seats.toMutableList()

        var changedSeats = false
        for (row in seats.indices) {
            for (col in 0 until airportWidth) {
                var occupiedSeatsAround = 0
                for (rowOffset in -1..1) {
                    for (colOffset in -1..1) {
                        if ((colOffset == 0 && rowOffset == 0)
                            || col + colOffset < 0
                            || col + colOffset >= airportWidth
                            || row + rowOffset < 0
                            || row + rowOffset >= seats.size
                        ) {
                            continue
                        }
                        if (seats[row + rowOffset][col + colOffset] == '#') {
                            occupiedSeatsAround++
                        }
                    }
                }
                if (seats[row][col] == 'L' && occupiedSeatsAround == 0) {
                    seatsCopy[row] = seatsCopy[row].replaceRange(col, col + 1, "#")
                    changedSeats = true
                }
                if (seats[row][col] == '#' && occupiedSeatsAround >= 4) {
                    seatsCopy[row] = seatsCopy[row].replaceRange(col, col + 1, "L")
                    changedSeats = true
                }
            }
        }
        //println(seatsCopy)
        if (!changedSeats) {
            return seats.sumBy { it.count { it == '#' } }
        }
        seats = seatsCopy
    }
}

fun solvePuzzle11dot1(): Int {
    var seats = File(f).readLines()
    val airportWidth = seats[0].length

    while (true) {
        val seatsCopy = seats.toMutableList()

        var changedSeats = false
        for (row in seats.indices) {
            for (col in 0 until airportWidth) {
                var seenOccupiedSeats = 0
                for (rowDirection in -1..1) {
                    for (colDirection in -1..1) {
                        var rowOffset = 0
                        var colOffset = 0
                        while (true) {
                            rowOffset += rowDirection
                            colOffset += colDirection
                            if (
                                (rowOffset == 0 && colOffset == 0)
                                || col + colOffset < 0
                                || col + colOffset >= airportWidth
                                || row + rowOffset < 0
                                || row + rowOffset >= seats.size
                            ) {
                                break
                            }
                            if (seats[row + rowOffset][col + colOffset] == '.') {
                                continue
                            }

                            if (seats[row + rowOffset][col + colOffset] == '#') {
                                seenOccupiedSeats++
                                break
                            }
                            if (seats[row + rowOffset][col + colOffset] == 'L') {
                                break
                            }
                        }
                    }
                }

                if (seats[row][col] == 'L' && seenOccupiedSeats == 0) {
                    seatsCopy[row] = seatsCopy[row].replaceRange(col, col + 1, "#")
                    changedSeats = true
                }
                if (seats[row][col] == '#' && seenOccupiedSeats >= 5) {
                    seatsCopy[row] = seatsCopy[row].replaceRange(col, col + 1, "L")
                    changedSeats = true
                }
            }
        }
        println(seatsCopy)
        if (!changedSeats) {
            return seats.sumBy { it.count { it == '#' } }
        }
        seats = seatsCopy
    }
}