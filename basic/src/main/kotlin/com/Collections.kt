package com

import kotlinx.collections.immutable.persistentListOf

fun main() {

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
    }.mapValues { (_, values)->
        values.map { it.summary.duration }.sum()
    }
    print("sum $sum $sum2 $sum3 $groupBy")




}

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
