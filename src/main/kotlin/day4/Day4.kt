package org.kevnkemp.day4

import org.kevnkemp.helper.getFile

fun day4() {
    val file = getFile("day4/input")
    file.useLines { lines ->
        val list = lines.toList()
        part1(list)
        part2(list)
    }
}

val fakeInput = listOf(
    "MMMSXXMASM",
    "MSAMXMSMSA",
    "AMXSXMAAMM",
    "MSAMASMSMX",
    "XMASAMXAMM",
    "XXAMMXXAMA",
    "SMSMSASXSS",
    "SAXAMASAAA",
    "MAMMMXMMMM",
    "MXMXAXMASX",
)
private fun part1(lines: List<String>) {
    // find all occurrences of XMAS AND SAMX in horizontal lines
    // build vertical lines and find the same
    // build diagonal lines, both ways, and find the same
    val horizontal = getCountInLines(lines)
    val vertical = getCountInLines(buildVerticals(lines))
    val diagonal = countDiagonals(lines)
    val diagonalReversed = buildDiagonalsReversed(lines)
    println("horizontal: $horizontal")
    println("vertical: $vertical")
    println("diagonal: $diagonal")
    println("diagonal reversed: $diagonalReversed")
    println("total: ${horizontal + vertical + diagonal + diagonalReversed}")
}

private fun part2(lines: List<String>) {
    println("mas: ${findMas(lines)}")
}

private fun getCountInLines(lines: List<String>): Int {
    val xmasRegex = "XMAS".toRegex()
    val samxRegex = "SAMX".toRegex()
    return lines.sumOf { line ->
        xmasRegex.findAll(line).count() + samxRegex.findAll(line).count()
    }
}

private fun buildVerticals(lines: List<String>): List<String> {
    val verticalLineMap = hashMapOf<Int, String>()
    for (i in lines.indices) {
        for (j in 0..<lines[i].length) {
            if (verticalLineMap[j] == null) {
                verticalLineMap[j] = "${lines[i][j]}"
            } else {
                verticalLineMap[j] = verticalLineMap[j] + lines[i][j]
            }
        }
    }
    println(verticalLineMap)
    return verticalLineMap.values.toList()
}

private fun countDiagonals(lines: List<String>): Int {
    var count = 0
    for (i in 3..<lines.size) {
        for (j in 3..<lines[i].length) {
            val first = lines[i - 3][j - 3]
            val second = lines[i - 2][j - 2]
            val third = lines[i - 1][j - 1]
            val fourth = lines[i][j]
            val currentString = "$first$second$third$fourth"
            if (currentString == "XMAS" || currentString == "SAMX") {
                count++
            }
        }
    }
    return count
}

private fun buildDiagonalsReversed(lines: List<String>): Int {
    return countDiagonals(lines.map { it.reversed() })
}

private fun findMas(lines: List<String>): Int {
    var count = 0
    val masOrSam = listOf("MAS", "SAM")
    for (i in 1..lines.size-2) {
        for (j in 1..lines[i].length-2) {
            if (lines[i][j] == 'A') {
                val topLeft = lines[i-1][j-1]
                val topRight = lines[i-1][j+1]
                val bottomLeft = lines[i+1][j-1]
                val bottomRight = lines[i+1][j+1]
                val middle = lines[i][j]
                val diag = "$topLeft$middle$bottomRight"
                val reverseDiag = "$topRight$middle$bottomLeft"
                if (diag in masOrSam && reverseDiag in masOrSam){
                    count++
                }
            }
        }
    }
    return count
}