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

abstract class PassportValidator {
    abstract fun isValid(passport: String): Boolean
}

class EnglishPassportValidator : PassportValidator() {
    override fun isValid(passport: String) =
        expectedKeys.size == expectedKeys.count { key ->
            val regex = Regex("""$key$keyValueDelimiter""")
            regex.containsMatchIn(passport)
        }
}

class GermanPassportValidator : PassportValidator() {
    override fun isValid(passport: String): Boolean {
        return true
    }
}

enum class PassportEntry {
    byr {
        override fun isValid(value: String): Boolean {
            val validationRegex = Regex("""\d{4}""")
            return (validationRegex.matches(value) && value.toInt() >= 1920 && value.toInt() <= 2002)
        }
    },
    iyr {
        override fun isValid(value: String): Boolean {
            val validationRegex = Regex("""\d{4}""")
            return (validationRegex.matches(value) && value.toInt() >= 2010 && value.toInt() <= 2020)
        }
    },
    eyr {
        override fun isValid(value: String): Boolean {
            val validationRegex = Regex("""\d{4}""")
            return (validationRegex.matches(value) && value.toInt() >= 2020 && value.toInt() <= 2030)
        }
    },
    hgt {
        override fun isValid(value: String): Boolean {
            val validationRegex = Regex("""(\d+)(in|cm)""")
            if (validationRegex.matches(value)) {
                val (height, units) = validationRegex.find(value)!!.destructured

                return (units == "cm" && height.toInt() >= 150 && height.toInt() <= 193) || (units == "in" && height.toInt() >= 59 && height.toInt() <= 76)
            }

            return false
        }
    },
    hcl {
        override fun isValid(value: String): Boolean {
            val validationRegex = Regex("""#[0-9|a-f]{6}""")
            return validationRegex.matches(value)
        }
    },
    ecl {
        override fun isValid(value: String): Boolean {
            return (value == "amb" || value == "blu" || value == "brn" || value == "gry" || value == "grn" || value == "hzl" || value == "oth")
        }
    },
    pid {
        override fun isValid(value: String): Boolean {
            val validationRegex = Regex("""\d{9}""")
            return validationRegex.matches(value)
        }
    };

    abstract fun isValid(value: String): Boolean
}

val inputText = File("input/puzzle4/$filename").readText()
val passports = inputText.split(passportsDelimiter)

fun solvePuzzle4() = passports.count { passport ->
    EnglishPassportValidator().isValid(passport)
}

fun solvePuzzle4dot1(): Int {
    return passports.count { passport ->
        expectedKeys.size == expectedKeys.count { key ->
            val regex = Regex("""($key)$keyValueDelimiter(\S+)""")
            var valid = false

            if (regex.containsMatchIn(passport)) {
                val (_, value) = regex.find(passport)!!.destructured
                val passportEntry = PassportEntry.valueOf(key)

                valid = passportEntry.isValid(value)
            }
            valid
        }
    }
}