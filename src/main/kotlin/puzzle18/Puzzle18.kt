package puzzle18

import puzzle10.sumByLong
import java.io.File

const val f = "input/puzzle18/input.txt"

fun solvePuzzle18() = File(f).readLines().sumByLong { l ->
    var reducedLine = "($l)"
    val parenthesisRegex = Regex("""\([^()]*\)""")

    while (true) {
        var didStuff = false

        parenthesisRegex.findAll(reducedLine).firstOrNull()?.also {
            var match = it.value

            val operationRegex = Regex("""(\d+) ([+|*]) (\d+)""")
            operationRegex.findAll(match).firstOrNull()?.also {
                didStuff = true
                val (op1, op, op2) = it.destructured

                val result = when (op) {
                    "+" -> op1.toLong() + op2.toLong()
                    "*" -> op1.toLong() * op2.toLong()
                    else -> 0
                }

                match = match.replaceRange(it.range, result.toString())
            }

            reducedLine = reducedLine.replaceRange(it.range, match)

            val singleDigitRegex = Regex("""\((\d+)\)""")
            singleDigitRegex.findAll(match).forEach {
                didStuff = true
                reducedLine = reducedLine.replace(it.value, it.destructured.component1())
            }
        }

        if (!didStuff) break
    }

    reducedLine.toLong()
}


fun solvePuzzle18dot1() = File(f).readLines().sumByLong { l ->
    var reducedLine = "($l)"
    val parenthesisRegex = Regex("""\([^()]*\)""")

    while (true) {
        var didStuff = false

        parenthesisRegex.findAll(reducedLine).firstOrNull()?.also {
            var match = it.value

            val additionRegex = Regex("""(\d+) \+ (\d+)""")
            if (additionRegex.containsMatchIn(match)) {
                additionRegex.findAll(match).firstOrNull()?.also {
                    didStuff = true
                    val (op1, op2) = it.destructured
                    val result = op1.toLong() + op2.toLong()
                    match = match.replaceRange(it.range, result.toString())
                }
            } else {
                val multRegex = Regex("""(\d+) \* (\d+)""")
                if (multRegex.containsMatchIn(match)) {
                    didStuff = true
                    multRegex.findAll(match).firstOrNull()?.also {
                        val (op1, op2) = it.destructured
                        val result = op1.toLong() * op2.toLong()
                        match = match.replaceRange(it.range, result.toString())
                    }
                }
            }

            reducedLine = reducedLine.replaceRange(it.range, match)

            val singleDigitRegex = Regex("""\((\d+)\)""")
            singleDigitRegex.findAll(match).forEach {
                didStuff = true
                reducedLine = reducedLine.replace(it.value, it.destructured.component1())
            }
        }

        if (!didStuff) break
    }

    reducedLine.toLong()
}