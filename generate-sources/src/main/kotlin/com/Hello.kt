package com

import java.io.File
import com.squareup.javapoet.JavaFile




fun main() {
    println("generate sources")

    val javaGen = JavaGen()
    javaGen.getJavaFile().writeTo(File("src/main/java"))
//    val kGen = KotlinGen()
//    kGen.getGeneratedTest().writeTo(File("src/main/kotlin"))
}

private fun getJavaFile(javaGen: JavaGen): JavaFile? {
    val javaFile = JavaFile
        .builder("com.generated", javaGen.getGeneratedTest())
        .indent("    ")
        .build()
    return javaFile
}

