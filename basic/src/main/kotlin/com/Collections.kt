package com

import kotlinx.collections.immutable.*


data class DurationBuild(
    val id: String = "",
    val data: String
)

data class TestTypeSummary(
    val type: String,
    val summary: TestSummary
)

data class TestSummary(
    val duration: Long = 0L
)

//another cases:
fun orderMutable() {
    val orderMutable = OrderMutable()
    orderMutable.products.add(Product("ads", 23))
    orderMutable.products.add(Product("second", 23))

    println(orderMutable.products)
}
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
