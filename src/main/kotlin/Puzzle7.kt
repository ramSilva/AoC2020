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
            if(content.contains(color)){
                seenBags.add(rule.container)
                findContainers(rules, rule.container, seenBags)
            }
        }
    }
}


fun factorial(n: Int): Int {
    if (n == 1) {
        return 1
    } else {
        return n * factorial(n - 1)
    }
}

