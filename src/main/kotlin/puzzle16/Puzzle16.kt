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

data class ExtendedRule(val name: String, val option1: Pair<Int, Int>, val option2: Pair<Int, Int>)

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

    val rulesIndexes = mutableMapOf<Int, MutableList<Int>>()

    nearbyTickets.split('\n').drop(1).filter { ticket ->
        ticket.split(',').map { it.toInt() }.count { value ->
            rules.count { rule ->
                value >= rule.option1.first && value <= rule.option1.second || value >= rule.option2.first && value <= rule.option2.second
            } > 0
        } == ticket.split(',').size
    }.forEach {
        it.split(',').map { it.toInt() }.map { value ->
            val possibleRulesIndex = arrayListOf<Int>()
            rules.forEachIndexed { ruleIndex, rule ->
                if (value >= rule.option1.first && value <= rule.option1.second || value >= rule.option2.first && value <= rule.option2.second) {
                    possibleRulesIndex.add(ruleIndex)
                }
            }
            possibleRulesIndex
        }.forEachIndexed { i, possibleRule ->
            rulesIndexes[i] =
                if (rulesIndexes[i] != null) possibleRule.filter { rulesIndexes[i]!!.contains(it) }.toMutableList()
                else possibleRule

            while (true) {
                var changedKeys = false
                rulesIndexes.forEach { (k, v) ->
                    if (v.size == 1) {
                        rulesIndexes.forEach { (k2, v2) ->
                            if (k != k2) {
                                changedKeys = v2.removeAll(v)
                            }
                        }
                    }
                }
                if (!changedKeys) break
            }
        }
    }

    println(rulesIndexes)
    var returnValue = 1L

    rules.forEachIndexed { i, v ->
        returnValue *= if (v.name.contains("departure")) {
            myTicket[rulesIndexes.filterKeys { rulesIndexes[it]!!.first() == i }.keys.first()]
        } else 1
    }

    return returnValue
}