package com.test2code

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.updateAndGet
import kotlinx.collections.immutable.persistentHashMapOf

typealias AgentSummary = Map<String, SummaryDto>

fun main() {
//    globalState.aggregateMessage(GroupAgentId("serviceGroupId", "agentId"), SummaryDto("first"))
    globalState.aggregateMessage("serviceGroupId2", mapOf("agentId2" to SummaryDto("second")))
    globalState.aggregateMessage("serviceGroupId", mapOf("agentId" to SummaryDto("kek")))
    val res = globalState.aggregateMessage("serviceGroupId2", mapOf("agentId" to SummaryDto("first")))
    println(res)
    println(globalState._summaryStorage)
}

val globalState = GlobalState()

//TODO remove GlobalState after solving the bug: https://github.com/Kotlin/kotlinx.atomicfu/issues/118
class GlobalState {
    val _summaryStorage = atomic(persistentHashMapOf<String, AgentSummary>())

    fun aggregateMessage(
        serviceGroup: String,
        updatedAgentSummary: AgentSummary
    ): Collection<SummaryDto>? {
        val storage = _summaryStorage.updateAndGet {
            val agentSummary: AgentSummary = it[serviceGroup] ?: emptyMap()
            val newAgentSummary: AgentSummary = agentSummary.plus(updatedAgentSummary)
            it.put(serviceGroup, newAgentSummary)
        }
        return storage[serviceGroup]?.values
//            .filter { it.key.first == key.first }
//            .values
    }
}

data class SummaryDto(
    val string: String
)
