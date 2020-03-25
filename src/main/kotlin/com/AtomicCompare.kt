package com

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.updateAndGet
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf

val storage2 = atomic(persistentHashMapOf<String, String>())

fun main(){
    println(storage2.value)
    updateAtomic(1, 2)
    updateAtomic(2, 3)
}
private fun updateAtomic(key: Int, a: Int): PersistentMap<String, String> {
    val value = "valuesss$a"
    return storage2.updateAndGet {
        it.put("hey$key", value)
    }
}
