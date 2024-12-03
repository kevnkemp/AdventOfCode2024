package org.kevnkemp.day2

import org.kevnkemp.helper.getFile
import java.io.File
import kotlin.math.abs
import kotlin.math.sign

fun day2() {
    val file = getFile("day2/day2_input")
    part1(file)
    part2(file)
}

private fun part1(file: File) {
    val count = file.useLines { lines ->
        lines.count { line ->
            isLineValid(line.split(" ").map { it.toInt() })
        }
    }

    println("Number of valid lines: $count")
}

private fun isLineValid(list: List<Int>): Boolean {
    var l = 0
    var r = 1

    val isDecreasing = list[l] > list[r]
    var isValid = true
    while (isValid && r < list.size) {
        val left = list[l]
        val right = list[r]
        isValid = if (isDecreasing) {
            left > right && (left - right in 1..3)
        } else {
            right > left && (right - left in 1..3)
        }

        l += 1
        r += 1
    }
    return isValid
}

private fun part2(file: File) {
    val count = file.useLines { lines ->
        lines.count { line ->
            val list = line.split(" ").map { it.toInt() }
            val linesToTest = List(list.size) { i->
                list.take(i) + list.drop(i + 1)
            }
            isLineValid(list) || linesToTest.any { isLineValid(it) }
        }
    }
    println("Number of valid lines with 1 or less errors: $count")
}

private fun checkLineRecursive(
    levels: List<Int>,
    left: Int = 0,
    right: Int = 1,
    numErrors: Int = 0,
    sign: Int? = null
): Boolean {

    if (numErrors >= 2) return false
    if (right == levels.size) return true

    val currentLeft = levels[left]
    val currentRight = levels[right]
    val step = currentLeft - currentRight
    val isInvalidStep = abs(step) !in 1..3

    val currentSign = step.sign.takeIf { it != 0 }
    val isLeftRightPairInvalid = isInvalidStep || (currentSign != sign && left > 0)

    println("levels: $levels,\n currentLeft: $currentLeft,\n currentRight: $currentRight,\n isLeftRightPairInvalid: $isLeftRightPairInvalid,\n isInvalidStep: $isInvalidStep,\n currentSign: $currentSign\n\n")
    val currentNumOfErrors = if (isLeftRightPairInvalid) numErrors + 1 else numErrors

    return if (currentNumOfErrors == 0) {
        checkLineRecursive(levels, right, right + 1, currentNumOfErrors, currentSign ?: 0)
    } else {
        checkLineRecursive(levels, left + 1, right + 1, currentNumOfErrors, currentSign ?: 0)
    }
}
