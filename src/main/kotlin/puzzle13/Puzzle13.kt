package puzzle13

import java.io.File

const val f = "input/puzzle13/input.txt"

fun solvePuzzle13(): Int {
    val timeToLeave = File(f).readLines()[0].toInt()

    val busTimes = File(f).readLines()[1].split(',').filter { it != "x" }.map { it.toInt() }


    var timeToWait = Int.MAX_VALUE
    var ourBus = 0

    busTimes.forEach {
        var i = 1
        while (true) {
            val leavingTime = it * i

            if (leavingTime >= timeToLeave) {
                if (leavingTime % timeToLeave < timeToWait) {
                    timeToWait = leavingTime % timeToLeave
                    ourBus = it
                }
                break
            }
            i++
        }
    }

    return timeToWait * ourBus


}


fun solvePuzzle13dot1(): Long {
    val busTimes = File(f).readLines()[1].split(',').map { if (it == "x") "1" else it }.map { it.toInt() }

    var ts = 0L
    var product = 1L
    busTimes.forEachIndexed { i, bus ->
        while ((ts + i) % bus != 0L) {
            ts += product
        }
        product *= bus
    }
    return ts
}