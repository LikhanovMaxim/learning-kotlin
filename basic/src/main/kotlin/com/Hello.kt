package com

fun main() {
    println("hello")
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

fun blockTakeIf(takeIf: String?) = if (takeIf == null) {
    println("asd")
} else println("adsdsa")
//    takeIf.let {
//        println("not in let $it")
//    } ?: {
//        println("else in $")
//    }

fun syntaxRun() {
    val b: String? = null
    val a: String? = "asdansf"

    b?.run {
        println(b)
    }
    a?.run {
        println(a)
    }
    var p: Product? = Product("asd", 23)
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

