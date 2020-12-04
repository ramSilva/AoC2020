package puzzle4

import java.io.File

const val filename = "testInput.txt"

fun solvePuzzle4(): Int {
    val keyValueDelimiter = ":"
    val keyMatches = arrayListOf(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid"
    )

    val inputText = File("input/puzzle4/$filename").readText()
    val passports = inputText.split("\n\n")

    return passports.count { passport ->
        keyMatches.size == keyMatches.count { key ->
            val regex = Regex("""$key$keyValueDelimiter""")
            passport.contains(regex)
        }
    }
}