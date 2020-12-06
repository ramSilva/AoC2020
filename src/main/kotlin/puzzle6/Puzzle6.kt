package puzzle6

import java.io.File

private const val f = "input/puzzle6/testInput.txt"

fun solvePuzzle6() =
    File(f).readText().split("\n\n").sumBy { it.filter { it != '\n' }.toSet().size }

fun solvePuzzle6dot1() =
    File(f).readText().split("\n\n").sumBy { it.lines().reduce { a, s -> a.filter { it in s } }.length }

fun solvePuzzle6LikeABoss(): Int {
    val groups = File(f).readText().split("\n\n")
    var final = 0
    for (group in groups) {
        var uniquech = ""
        var groupfilter = ""
        for (answer in group) {
            if (answer != '\n') groupfilter += answer
        }

        for (answer in groupfilter) {
            if (!uniquech.contains(answer)) uniquech += answer
        }
        final += uniquech.length

    }
    return final

}

fun solvePuzzle6dot1LikeABoss(): Int {
    val groups = File(f).readText().split("\n\n")
    var final = 0
    for (group in groups) {
        var allpx = ""
        var persons = arrayListOf<String>()
        var person = ""
        for (answer in group) {
            if (answer != '\n') person += answer
            else {
                persons.add(person)
                person = ""
            }
        }
        persons.add(person)

        allpx = persons[0]
        for (person in persons) {
            for (answer in allpx) {
                if (!person.contains(answer)) {
                    allpx = allpx.replace(answer.toString(), "")
                }
            }
        }
        final += allpx.length

    }
    return final
}








