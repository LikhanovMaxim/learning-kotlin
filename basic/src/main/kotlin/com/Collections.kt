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
    val products: PersistentList<Product> = persistentListOf(
        Product("dsa", 123),
        Product("first", 1234)
    )
)

