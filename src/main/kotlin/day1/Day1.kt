package org.kevnkemp.day1

import org.kevnkemp.helper.getFile
import org.kevnkemp.helper.measureExecutionTime
import java.io.File
import kotlin.math.abs

fun day1() {
    val file = getFile("day1/day1_input")
    val time1 = measureExecutionTime {
        part1(file)
    }
    val time2 = measureExecutionTime {
        part2(file)
    }

    println("part 1 time: $time1, part 2 time: $time2")
}

private fun part1(file: File) {
    val lists = buildLists(file)

    val difference = lists.first.sorted().zip(lists.second.sorted()).sumOf {
        abs(it.first - it.second)
    }

    println("part 1 solution: $difference")
}

private fun part2(file: File) {
    val lists = buildLists(file)
    val rightListFrequency = hashMapOf<Int, Int>()
    lists.second.forEach { rightInt ->
        rightListFrequency[rightInt] = (rightListFrequency[rightInt] ?: 0) + 1
    }
    val sum = lists.first.sumOf { leftInt ->
        leftInt * (rightListFrequency[leftInt] ?: 0)
    }

    println("part 2 solution: $sum")
}

private fun buildLists(file: File): Pair<List<Int>, List<Int>> {
    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()
    file.readLines().forEachIndexed { index, line ->
        val inputs = line.split("   ")
        if (inputs[0].isNotEmpty() && inputs[1].isNotEmpty()) {
            leftList.add(inputs[0].toInt())
            rightList.add(inputs[1].toInt())
        }
    }
    return leftList to rightList
}