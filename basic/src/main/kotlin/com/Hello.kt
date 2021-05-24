package com

import jdk.nashorn.internal.runtime.regexp.joni.Regex
import kotlin.time.*

fun main() {
    val a = TimeSource.Monotonic.markNow();
    println("hello")
    println(a.elapsedNow())

    Thread.sleep(10_000)
}

//lazy:
val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

fun test1(test: String?) = test?.let { println(it) }
fun test2(test: String?) {
    test?.let { println(it) }
}

fun takeIfCheckSize(input: String): String {
    return input.takeIf { it.length < 10 } ?: "too long string"
}

fun takeUnlessCheckSize(input: String): String {
    return input.takeUnless { it.length < 10 } ?: "too short string"
//    return input.takeUnless { it.length >= 10 } ?: "too long string"
}

fun syntaxRun() {
    val b: String? = null
    val a: String? = "asdansf"

    b?.run {
        println(b)
    }
    a?.run {
        println(a)
    }
    var p: Product? = com.Product("asd", 23)
    p = p?.run {
        copy(price = 777)
    }
    println(p)
}

fun smthBoolean(string: String?): Boolean {
    val b = string == null
    return b.also {

    }
}

fun syntaxTernary() {
    println("hi")
    val a = smth(4)
    a ?: println("lol")

    val b = a ?: "ker"
    println(b)


    var c = smth()
    c?.let { println("not null $c") } ?: println("null c=$c")
    c = smth(3)
    c?.let { println("not null $c") } ?: println("null c=$c")
}

fun smth(b: Int = 2): String? {
    if (b == 2)
        return "astfsdfa"
    if (b == 3)
        return null
    return ""
}

fun changeParamToValue(destination: String): String {
    val data = mapOf("scopeId" to "123", "testId" to "77")
    val res2 = destination + data.keys.first()
//        destination
//        .replace(Regex("\\{[A-Za-z]*}"))
//        {
//            val value = it.value.removePrefix("{").removeSuffix("}")
//            println(value)
//            data[value] ?: ""
//        }
    return res2
}
