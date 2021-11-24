package com

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionsKtTest {

    @Test
    fun `check null`() {
//        val newWeb = emptyList<String>()
//        check(newWeb)
//        val parameters = mapOf<String, String>("dd" to "dd")
//        check(parameters["webAppNames"]?.split(":", ","))
        check("".split(":", ","))
    }

    private fun check(newWeb: List<String>?) {
        if (newWeb?.isNotEmpty() == true) {
            println("yesss")
        } else print("noooo")
    }

    @Test
    fun `change orderMutable`() {
        //another cases:
        val orderMutable = OrderMutable()
        orderMutable.products.add(Product("ads", 23))
        orderMutable.products.add(Product("second", 23))

        println(orderMutable.products)
    }


    @Test
    fun `another cases`() {
        val orderPersistent = OrderPersistent()
        assertEquals(2, orderPersistent.products.size)
        val add = orderPersistent.products.add(Product("dsa", 123))
//    val products : ImmutableList<Product> = persistentListOf(Product("dsa", 123), Product("first", 1234))
//    orderPersistent.products = products
        println(orderPersistent.products)
        assertEquals(2, orderPersistent.products.size)
        assertEquals(3, add.size)
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
        println("sum $sum $sum2 $sum3 $groupBy")
    }

    @Test
    fun `set and operations`() {
        val set = persistentSetOf<String>()
        set.add("dd")
    }

    @Test
    fun `map vs flatMap`() {
        val list = listOf("abc", "24", "777z")
        val listLength: List<Int> = list.map { it.length }
        assertEquals(listOf(3, 2, 4), listLength)
        val flatMapCharList: List<Char> = list.flatMap { it.toList() }
        assertEquals(9, flatMapCharList.size)
        println(flatMapCharList)//[a, b, c, 2, 4, 7, 7, 7, z]

        val map = mapOf("122" to 2, "3455" to 3)
        println(map.flatMap { (key, value) -> key.take(value).toList() }) // [1, 2, 3, 4, 5]

        //use flatMap when want to flatten one-to-many relationships
        //example task: we’re going to find all the distinct item names
        val orders: List<Order> = listOf(
            Order(listOf(OrderLine("Garlic", 1), OrderLine("Chives", 2))),
            Order(listOf(OrderLine("Tomato", 1), OrderLine("Garlic", 2))),
            Order(listOf(OrderLine("Potato", 1), OrderLine("Chives", 2))),
        )
        val lines: List<OrderLine> = orders.flatMap { it.lines }
        println(lines)
        val names = lines.map { it.name }.distinct()
        println(names)
    }

    class Order(val lines: List<OrderLine>)
    class OrderLine(val name: String, private val price: Int) {
        override fun toString(): String {
            return "OrderLine(name='$name', price=$price)"
        }
    }
}
