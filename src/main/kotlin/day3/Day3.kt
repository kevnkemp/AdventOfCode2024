package org.kevnkemp.day3

import org.kevnkemp.helper.getFile
import org.kevnkemp.helper.measureExecutionTime

const val MUL_REGEX = "(mul\\((\\d{1,3}),(\\d{1,3})\\))"
const val DO_REGEX = "(do\\(\\))"
const val DONT_REGEX = "(don't\\(\\))"

fun day3() {
    val input = getFile("day3/day3_input").readText()
    val time1 = measureExecutionTime {
        part1(input)
    }
    val time2 = measureExecutionTime {
        part2(input)
    }

    println("part 1 time: $time1, part 2 time: $time2")
}

private fun part1(input: String) {
    val regex = MUL_REGEX.toRegex()
    val sum = regex.findAll(input).map {
        (it.groups[2]?.value?.toInt() ?: 0) * (it.groups[3]?.value?.toInt() ?: 0)
    }.sum()

    println("sum: $sum")
}

private fun part2(input: String) {
    val splitInput = input.split(DO_REGEX.toRegex())
    val mulRegex = MUL_REGEX.toRegex()
    val dontRegex = DONT_REGEX.toRegex()
    val totalSum = splitInput.sumOf { fragment ->
        val dosOnly = fragment.split(dontRegex)
        mulRegex.findAll(dosOnly.first()).map {
            (it.groups[2]?.value?.toInt() ?: 0) * (it.groups[3]?.value?.toInt() ?: 0)
        }.sum()
    }
    println("totalSum: $totalSum")
}