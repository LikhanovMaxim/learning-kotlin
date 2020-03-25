package com

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.updateAndGet
import kotlinx.collections.immutable.persistentHashMapOf


val summaryStorage = atomic(persistentHashMapOf<GroupAgentId, SummaryDtoEasy>())
fun main() {
    aggregateMessage(
        GroupAgentId("key", "sda"),
        SummaryDtoEasy(
            "countMethod = Count(0, 0),"
        )
    )
}

fun aggregateMessage(
    key: GroupAgentId,
    summaryDtoEasy: SummaryDtoEasy
) {
    println(summaryDtoEasy)
    val storage = summaryStorage.updateAndGet {
        it.put(
//        GroupAgentId("key", "sda"),
            key,
            summaryDtoEasy
        )
    }
    storage
        .filter { it.key.first == key.first }
        .values
        .forEach { println(it) }
//        .reduce { acc, element -> acc + element }
}
