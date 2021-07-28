package com

import com.squareup.javapoet.JavaFile
import java.io.File


fun main() {
    println("generate sources")

    val javaGen = JavaGen()
    val javaFile: JavaFile = javaGen.getJavaFile()
    javaFile.writeTo(File("src/main/java"))
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

