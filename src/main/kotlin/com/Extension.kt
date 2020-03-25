package com

import kotlinx.atomicfu.atomic
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf


val storage = persistentHashMapOf<String, String>()

val globalStorage = _globalStorage.value



//seocnd option:
typealias GroupAgentId = Pair<String, String>

internal val _mainGlobalStorage = atomic(persistentHashMapOf<String, SummaryDtoEasy>())

data class SummaryDtoEasy(val lol: String)

public var foo: PersistentMap<String, SummaryDtoEasy>           // public val/var
    get() = _mainGlobalStorage.value
    set(value) {
        _mainGlobalStorage.value = value
    }


var persistentMap = persistentHashMapOf<String, String>()
