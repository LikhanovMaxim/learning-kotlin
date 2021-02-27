package com

import kotlinx.collections.immutable.*
import kotlin.collections.plus
import kotlin.test.*

class CollectionsKtTest {

    @Test
    fun `another cases`() {
        val orderPersistent = OrderPersistent()
//    orderPersistent.products = orderPersistent.products.plus(Product("dsa", 123))
//    val products : ImmutableList<Product> = persistentListOf(Product("dsa", 123), Product("first", 1234))
//    orderPersistent.products = products
        println(orderPersistent.products)
//    orderMutable()
    }

    //todo create tests
    @Test
    fun `all things`() {
        val list = mutableListOf<Boolean>(true, false, true)

        val all = list.all { it }
        println("all $all")

//list
        val listInDB = persistentListOf<DurationBuild>(DurationBuild("1", "asd"), DurationBuild("2", "gsfdas"))

        val newObj = DurationBuild("3", "new")

        val newList = listInDB.plus(newObj)
        println(newList)

/// to map
        val listTestSummary: List<TestTypeSummary> = persistentListOf(
            TestTypeSummary("AUTO", TestSummary(20)),
            TestTypeSummary("AUTO", TestSummary(25)),
            TestTypeSummary("MANUAL", TestSummary(10))
        )
        val sum = listTestSummary.map { it.summary.duration }.sum()
        val sum2 = listTestSummary.map { it.type to it.summary.duration }.toMap()
        val sum3 = listTestSummary.map { it.type to it.summary.duration }.toMap()

        val groupBy = listTestSummary.groupBy {
            it.type
        }.mapValues { (_, values) ->
            values.map { it.summary.duration }.sum()
        }
        print("sum $sum $sum2 $sum3 $groupBy")


    }
}
