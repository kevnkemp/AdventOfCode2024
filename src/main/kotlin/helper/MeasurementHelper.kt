package org.kevnkemp.helper

fun measureExecutionTime(block: () -> Unit): String {
    val start = System.nanoTime()
    block()
    val end = System.nanoTime()
    return "${(end - start) / 1_000_000.0} ms"
}