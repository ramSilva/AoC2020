package puzzle16

import java.io.File

const val f = "input/puzzle16/input.txt"

data class Rule(val from: Int, val to: Int)

fun solvePuzzle16(): Int {
    val (rulesText, _, nearbyTickets) = File(f).readText().split("\n\n")

    val rules = arrayListOf<Rule>()

    rulesText.split('\n').forEach {
        val ruleRegex = Regex("""(\d+)-(\d+) or (\d+)-(\d+)""")
        val (r1, r2, r3, r4) = ruleRegex.find(it)!!.destructured

        rules.add(Rule(r1.toInt(), r2.toInt()))
        rules.add(Rule(r3.toInt(), r4.toInt()))
    }

    val invalidValues = arrayListOf<Int>()

    nearbyTickets.split('\n').drop(1).forEach {
        it.split(',').map { it.toInt() }.forEach values@{
            rules.forEach rules@{ rule ->
                if (it >= rule.from && it <= rule.to) {
                    return@values
                }
            }
            invalidValues.add(it)
        }
    }

    return invalidValues.sum()
}

data class ExtendedRule(val name: String, val option1: Pair<Int, Int>, val option2: Pair<Int, Int>) {
    fun isValueValid(value: Int) =
        value >= option1.first && value <= option1.second || value >= option2.first && value <= option2.second
}

fun solvePuzzle16dot1(): Long {
    val (rulesText, myTicketString, nearbyTickets) = File(f).readText().split("\n\n")

    val myTicket = myTicketString.split('\n')[1].split(',').map {
        it.toInt()
    }

    val rules = arrayListOf<ExtendedRule>()

    rulesText.split('\n').forEach {
        val ruleRegex = Regex("""([\w+\s]+): (\d+)-(\d+) or (\d+)-(\d+)""")
        val (name, r1, r2, r3, r4) = ruleRegex.find(it)!!.destructured

        rules.add(ExtendedRule(name, Pair(r1.toInt(), r2.toInt()), Pair(r3.toInt(), r4.toInt())))
    }

    val rulesIndexes = mutableMapOf<Int, MutableList<Int>>() //rulesIndexes[rule] = valueIndex

    val validTickets = nearbyTickets.split('\n').drop(1).filter { ticket ->
        val values = ticket.split(',').map { it.toInt() }

        values.count { value ->
            rules.count { rule ->
                rule.isValueValid(value)
            } > 0
        } == rules.size
    }

    rules.forEachIndexed { ruleIndex, rule ->
        rulesIndexes[ruleIndex] = mutableListOf()
        rulesIndexes[ruleIndex]!!.addAll(validTickets[0].split(',').indices)

        validTickets.forEach { ticket ->
            val values = ticket.split(',').map { it.toInt() }
            val possibleIndices = arrayListOf<Int>()
            values.forEachIndexed { valueIndex, value ->
                if (rule.isValueValid(value)) {
                    possibleIndices.add(valueIndex)
                }
            }

            rulesIndexes[ruleIndex] = possibleIndices.filter { rulesIndexes[ruleIndex]!!.contains(it) }.toMutableList()
        }
    }

    while (true) {
        var changedValues = false
        rulesIndexes.forEach { (k, v) ->
            if (v.size == 1) {
                rulesIndexes.forEach { (k2, v2) ->
                    if (k != k2 && v2.containsAll(v)) {
                        changedValues = v2.removeAll(v)
                    }
                }
            }
        }

        if (!changedValues) break
    }


    var returnValue = 1L

    rules.filter { it.name.contains("departure") }
        .forEachIndexed { i, _ -> returnValue *= myTicket[rulesIndexes[i]!!.first()] }

    return returnValue
}