package puzzle4

import java.io.File

const val filename = "input.txt"

const val keyValueDelimiter = ":"
const val passportsDelimiter = "\n\n"
val expectedKeys = arrayListOf(
    "byr",
    "iyr",
    "eyr",
    "hgt",
    "hcl",
    "ecl",
    "pid"
)

val inputText = File("input/puzzle4/$filename").readText()
val passports = inputText.split(passportsDelimiter)

fun solvePuzzle4() = passports.count { passport ->
    expectedKeys.size == expectedKeys.count { key ->
        val regex = Regex("""$key$keyValueDelimiter""")
        regex.containsMatchIn(passport)
    }
}

fun solvePuzzle4dot1(): Int {
    return passports.count { passport ->
        expectedKeys.size == expectedKeys.count { key ->
            val regex = Regex("""($key)$keyValueDelimiter(\S+)""")
            var valid = false

            if (regex.containsMatchIn(passport)) {
                val (_, value) = regex.find(passport)!!.destructured

                when (key) {
                    "byr" -> {
                        val validationRegex = Regex("""\d{4}""")
                        if (validationRegex.matches(value) && value.toInt() >= 1920 && value.toInt() <= 2002) {
                            valid = true
                        }
                    }
                    "iyr" -> {
                        val validationRegex = Regex("""\d{4}""")
                        if (validationRegex.matches(value) && value.toInt() >= 2010 && value.toInt() <= 2020) {
                            valid = true
                        }
                    }
                    "eyr" -> {
                        val validationRegex = Regex("""\d{4}""")
                        if (validationRegex.matches(value) && value.toInt() >= 2020 && value.toInt() <= 2030) {
                            valid = true
                        }
                    }
                    "hgt" -> {
                        val validationRegex = Regex("""(\d+)(in|cm)""")
                        if (validationRegex.matches(value)) {
                            val (height, units) = validationRegex.find(value)!!.destructured

                            if (units == "cm" && height.toInt() >= 150 && height.toInt() <= 193) {
                                valid = true
                            }
                            if (units == "in" && height.toInt() >= 59 && height.toInt() <= 76) {
                                valid = true
                            }
                        }
                    }
                    "hcl" -> {
                        val validationRegex = Regex("""#[0-9|a-f]{6}""")
                        if (validationRegex.matches(value)) {
                            valid = true
                        }
                    }
                    "ecl" -> if (value == "amb" || value == "blu" || value == "brn" || value == "gry" || value == "grn" || value == "hzl" || value == "oth") {
                        valid = true
                    }
                    "pid" -> {
                        val validationRegex = Regex("""\d{9}""")
                        if (validationRegex.matches(value)) {
                            valid = true
                        }
                    }
                }
            }
            valid
        }
    }
}