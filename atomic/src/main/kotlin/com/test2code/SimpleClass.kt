package com.test2code

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.updateAndGet
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf

fun main() {
//    val simpleClass = SimpleClass()
    simpleClass.update("1", "xxx")
    simpleClass.update("2", "xxx")
    val res = simpleClass.update("3", "xxx")
    println(res)

    val someInstance = SomeInstance()
    val someInstance2 = SomeInstance()
    someInstance.updateAndDot("123")
    someInstance2.updateAndDot("444")
    println(simpleClass.storage.value)
}
val simpleClass = SimpleClass()

class SimpleClass {
    val storage = atomic(persistentHashMapOf<String, String>())

    fun update(key: String, value: String): PersistentMap<String, String> = storage.updateAndGet { it.put(key, value) }
}

class SomeInstance{
    fun updateAndDot(s: String) {
        simpleClass.update(s, "xxx")
    }
}
