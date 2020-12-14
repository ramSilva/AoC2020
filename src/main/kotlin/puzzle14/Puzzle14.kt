package puzzle14

import puzzle10.sumByLong
import java.io.File
import java.util.*

const val f = "input/puzzle14/input.txt"


fun dec2bin(value: Long, binaryString: String = "0"): String {
    if (value > 0) {
        val temp = "${binaryString}${value % 2}"
        return dec2bin(value / 2, temp)
    }
    return binaryString.reversed()
}

fun solvePuzzle14(): Long {
    val input = File(f).readLines()

    var orMask = 0L
    var andMask = 0L

    val mem = TreeMap<Int, Long>()
    input.forEach {
        if (it[1] == 'a') {
            val regex = Regex(""".*= (\w+)""")
            val (mask) = regex.find(it)!!.destructured

            orMask = mask.fold(0L) { a, m -> if (m == '1') a + a + 1 else a + a }
            andMask = mask.fold(1L) { a, m -> if (m == '0') a + a else a + a + 1 }
        } else {
            val regex = Regex(""".*\[(\d+)\] = (\d+)""")
            val (memPosition, valueAsString) = regex.find(it)!!.destructured

            var value = valueAsString.toLong() or orMask
            value = value and andMask

            mem[memPosition.toInt()] = value
        }
    }

    return mem.values.sumByLong { it }
}

fun solvePuzzle14dot1(): Long {
    val input = File(f).readLines()

    val mem = TreeMap<Long, Long>()
    var andMasks = listOf<Long>()
    var orMasks = listOf<Long>()
    input.forEach {
        if (it[1] == 'a') {
            val regex = Regex(""".*= (\w+)""")
            val (mask) = regex.find(it)!!.destructured

            andMasks = listOf(1L)
            orMasks = listOf(0L)

            mask.forEach { char ->
                andMasks = andMasks.map {
                    it * 2 + 1
                }
                orMasks = orMasks.map {
                    it * 2 + if (char == '1') 1 else 0
                }

                if (char == 'X') {
                    andMasks = andMasks.flatMap {
                        listOf(it, it - 1)
                    }
                    orMasks = orMasks.flatMap {
                        listOf(it, it + 1)
                    }
                }

            }
        } else {
            val regex = Regex(""".*\[(\d+)\] = (\d+)""")
            val (memPosition, valueAsString) = regex.find(it)!!.destructured

            andMasks.forEach { andMask ->
                orMasks.forEach { orMask ->
                    mem[memPosition.toLong() or orMask and andMask] = valueAsString.toLong()
                }
            }
        }
    }

    return mem.values.sumByLong { it }
}