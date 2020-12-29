package com

import kotlinx.collections.immutable.*

data class Product(
    val name: String,
    val price: Int
)

data class OrderMutable(
    val products: MutableCollection<Product> = mutableListOf()
)


data class OrderImmutable(
    var products: ImmutableList<Product> = persistentListOf()
)

data class OrderPersistent(
    val products: Collection<Product> = persistentListOf(Product("dsa", 123), Product("first", 1234))
)


val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

fun test1(test: String?) = test?.let { println(it) }
fun test2(test: String?) {
    test?.let { println(it) }
}

fun main() {
    println(test1("ads"))
    println(test2("ads"))

    val a1: Any = 123L
    val a2: Any = 55L
    val c = a1 as Long - a2 as Long
    println("c=$c")

    val test: String? = "tesfddg"
    val takeIf: String? = test?.takeIf { it !in listOf(test) }
    val a = ads(takeIf)
    println(a)
    println("lazy:")
    println(lazyValue)
    println(lazyValue)
    println(smthBoolean("asd"))
    println(smthBoolean(null))

    val orderPersistent = OrderPersistent()
//    orderPersistent.products = orderPersistent.products.plus(Product("dsa", 123))
//    val products : ImmutableList<Product> = persistentListOf(Product("dsa", 123), Product("first", 1234))
//    orderPersistent.products = products
    println(orderPersistent.products)

//    orderMutable()

//    syntaxTernary()

    syntaxRun()
}

private fun ads(takeIf: String?) = if (takeIf == null) {
    println("asd")
} else println("ads")
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

private fun smthBoolean(string: String?): Boolean {
    val b = string == null
    return b.also {

    }
}

private fun orderMutable() {
    val orderMutable = OrderMutable()
    orderMutable.products.add(Product("ads", 23))
    orderMutable.products.add(Product("second", 23))

    println(orderMutable.products)
}

private fun syntaxTernary() {
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

private fun smth(b: Int = 2): String? {
    if (b == 2)
        return "astfsdfa"
    if (b == 3)
        return null
    return ""
}

