package puzzle7

import java.io.File

typealias Bag = String

const val f = "input/puzzle7/input.txt"

data class Rule(
    val container: Bag,
    val contents: List<Bag>
)

fun solvePuzzle7(): Int {
    val rules = arrayListOf<Rule>()
    val regex = Regex("""((?:\w+ ){2})(.*)""")
    val ourBag = "shiny gold"

    File(f).readLines().forEach {
        val find = regex.find(it)
        val (container, garbage) = find!!.destructured
        val rest = garbage.split(Regex("""\d+"""))
        val contents = arrayListOf<String>()
        rest.drop(1).forEach {
            val contentsRegex = Regex("""((?:\w+ ){2})""")
            val (contentsFind) = contentsRegex.find(it)!!.destructured

            contents.add(contentsFind)
        }

        rules.add(Rule(container, contents))
    }

    val validBags = mutableSetOf<Bag>()

    findContainers(rules, ourBag, validBags)

    return validBags.size
}

fun findContainers(rules: List<Rule>, color: Bag, seenBags: MutableSet<Bag>) {
    rules.forEach { rule ->
        rule.contents.forEach { content ->
            if (content.contains(color)) {
                seenBags.add(rule.container)
                findContainers(rules, rule.container, seenBags)
            }
        }
    }
}

typealias Content = Pair<Int, Bag>

data class Part2Rule(
    val container: Bag,
    val contents: List<Content>
)

fun solvePuzzle7dot1(): Int {
    val rules = arrayListOf<Part2Rule>()
    val regex = Regex("""((?:\w+ ){2})(.*)""")
    val ourBag = "shiny gold"

    File(f).readLines().forEach {
        val find = regex.find(it)
        val (container, garbage) = find!!.destructured
        val rest = garbage.split(Regex(""" (?=\d)"""))
        val contents = arrayListOf<Content>()
        rest.drop(1).forEach {
            val contentsRegex = Regex("""(\d+) ((?:\w+ ){2})""")
            val (amount, contentsFind) = contentsRegex.find(it)!!.destructured

            contents.add(Content(amount.toInt(), contentsFind))
        }

        rules.add(Part2Rule(container, contents))
    }

    return findContents(rules, ourBag)
}

fun findContents(rules: List<Part2Rule>, color: Bag): Int {
    var bagAmount = 0

    rules.forEach { rule ->
        if (rule.container.contains(color)) {
            if(rule.contents.isEmpty()){
                return 0
            }

            rule.contents.forEach { content ->
                bagAmount += content.first + content.first * findContents(rules, content.second)
            }
        }
    }

    return bagAmount
}