package com.test2code

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.updateAndGet
import kotlinx.collections.immutable.persistentHashMapOf


fun main() {
    globalState.aggregateMessage(GroupAgentId("serviceGroupId", "agentId"), SummaryDto("first"))
    val res = globalState.aggregateMessage(GroupAgentId("serviceGroupId2", "agentId"), SummaryDto("first"))
    println(res)
}

typealias GroupAgentId = Pair<String, String>

val globalState = GlobalState()

//TODO remove GlobalState after solving the bug: https://github.com/Kotlin/kotlinx.atomicfu/issues/118
class GlobalState {
    private val summaryStorage = atomic(persistentHashMapOf<GroupAgentId, SummaryDto>())

    fun aggregateMessage(
        key: GroupAgentId,
        summaryDto: SummaryDto
    ): Collection<SummaryDto> {
        val storage = summaryStorage.updateAndGet { it.put(key, summaryDto) }
        return storage
            .filter { it.key.first == key.first }
            .values
    }
}

data class SummaryDto(
    val string: String
)
