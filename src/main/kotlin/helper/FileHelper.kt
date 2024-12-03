package org.kevnkemp.helper

import java.io.File

fun getFile(fileName: String): File {
    return File("src/main/kotlin/$fileName")
}