package org.kevnkemp.day5

import org.kevnkemp.helper.getFile

fun day5() {
    val file = getFile("day5/input")
    val lines = file.useLines { lines ->
        lines.toList()
    }
    val rules = lines.filter {
        it.contains("|")
    }
    val updates = lines.filter {
        it.contains(",")
    }
    val ruleMap = buildRuleMap(rules)
    part1(ruleMap, updates)
    part2(ruleMap, updates)
}

private fun part1(ruleMap: HashMap<String, List<String>>, updates: List<String>) {
    // build map where key = number and value = list of numbers that must appear after the key
    // for each line to check
        // check if current list contains any of the values in the map entry

    var count = 0
    for (update in updates) {
        var isValid = true
        val numbers = update.split(",")
        val checked = mutableListOf<String>()
        for (n in numbers) {
            checked.add(n)
            isValid = !(checked.any { it in (ruleMap[n] ?: emptyList()) })
            if (!isValid) break
        }
        if (isValid) {
            count += numbers[numbers.size / 2].toInt()
        }
    }
    println("count: $count")
}

private fun part2(ruleMap: HashMap<String, List<String>>, updates: List<String>) {
    var count = 0
    for (update in updates) {
        val numbers = update.split(",")
        val checked = mutableListOf<String>()
        for (n in numbers) {
            checked.add(n)
            if (checked.any { it in (ruleMap[n] ?: emptyList()) }) {
                count += reorder(ruleMap, numbers)
                break
            }

        }
    }
    println("part2 count: $count")
}

private fun reorder(ruleMap: HashMap<String, List<String>>, numbers: List<String>): Int {
    val ordered = mutableListOf<String>()
    for (n in numbers) {
        val rules = ruleMap[n] ?: emptyList()
        if (ordered.any { it in rules }) {
            var index = ordered.size
            for (o in ordered.size - 1 downTo 0) {
                if (ordered[o] in rules) {
                    index = o
                }
            }
            ordered.add(index, n)
        } else {
            ordered.add(n)
        }
    }
    return ordered[ordered.size / 2].toInt()
}

private fun buildRuleMap(rules: List<String>): HashMap<String, List<String>> {
    val ruleMap = hashMapOf<String, List<String>>()
    rules.forEach {
        val (k, v) = it.split("|")
        if (ruleMap[k] == null) {
            ruleMap[k] = listOf(v)
        } else {
            ruleMap[k] = ruleMap[k]!! + listOf(v)
        }
    }
    return ruleMap
}