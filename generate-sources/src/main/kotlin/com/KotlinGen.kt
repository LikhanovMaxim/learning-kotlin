package com

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec

class KotlinGen {

    fun getGeneratedTest(
        packageName: String = "com.generate",
        clazzName: String = "Big",
        id: Int = 1
    ) = FileSpec.builder(packageName, clazzName)
        .addType(generateClass(clazzName, id)).build()

    fun generateClass(clazzName: String, id: Int): TypeSpec {

        return TypeSpec.classBuilder(clazzName)
            .addFunction(someFunction(id))
            .build()
    }

    fun someFunction(id: Int): FunSpec {
        return FunSpec.builder("someFun$id")
            .addStatement("println(\"hi\")")
            .build()
    }
}
