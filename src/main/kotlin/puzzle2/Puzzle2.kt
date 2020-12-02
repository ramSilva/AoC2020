package puzzle2

import java.io.File

data class PasswordRule(
    val min: Int,
    val max: Int,
    val char: Char,
    val password: String
)

fun parseInput(filename: String): List<PasswordRule> {
    val regex = Regex("""(\d+)-(\d+) (\S): (\S+)""")
    val rules = arrayListOf<PasswordRule>()

    File("input/puzzle2/$filename").forEachLine {
        val (min, max, char, password) = regex.find(it)!!.destructured

        rules.add(PasswordRule(min.toInt(), max.toInt(), char[0], password))
    }

    return rules
}

fun solvePuzzle2() = parseInput("input.txt").count { rule ->
    val numberOfOccurrences = rule.password.count { it == rule.char }

    numberOfOccurrences >= rule.min && numberOfOccurrences <= rule.max
}

fun solvePuzzle2dot1() = parseInput("input.txt").count { rule ->
    (rule.password[rule.min - 1] == rule.char).xor(rule.password[rule.max - 1] == rule.char)
}