package com

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update
import kotlinx.atomicfu.updateAndGet
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

val _globalStorage = atomic(persistentHashMapOf<String, String>())
fun main(): Unit = runBlocking {
    val suspendsSmth = SuspendsSmth()
    val b = async { suspendsSmth.suspendAndAtomic(1) }
    val b2 = async { suspendsSmth.suspendAndAtomic(2) }
    val b3 = async { suspendsSmth.suspendAndAtomic(3) }
    b3.await()
    b2.await()
    b.await()
    println(_globalStorage.value)
}

class SuspendsSmth{

    suspend fun suspendAndAtomic(key: Int) {
        println("hi")
        val a = sleep()
        val cur = updateAtomic(key, a)
        println(cur.size)
        println(cur)
        sleep(cur.size + 100L)
    }

    private suspend fun sleep(long: Long = 1000L): Int {
        println("sleep $long")
        delay(long) // pretend we are doing something useful here
        return 13
    }

}

private fun updateAtomic(key: Int, a: Int): PersistentMap<String, String> {
    val value = "valuesss$a"
    return _globalStorage.updateAndGet {
        it.put("hey$key", value)
    }
}

private fun smthElse() {
    persistentMap = persistentMap.put("asd", "DA")
    println(persistentMap)

    println(foo)
    foo = persistentHashMapOf()
    foo.put("lol", SummaryDtoEasy("das"))
    println(_mainGlobalStorage)
}

private fun simpleAtomic() {
    println("hi")
    storage.put("lol", "value")
    println(storage["lol"])

    println("hi")
    _globalStorage.update {
        it.put("hey", "valuesss")
    }
    _globalStorage.update { it.put("key", "valuesss") }
    println(_globalStorage.value)
    println(globalStorage)

    val hello = Hello()
    hello.kek()
    println("In the end:")
    println(_globalStorage.value)
}

class Hello {
    val string: String = ""
    internal fun kek() {
        _globalStorage.update { it.put("hey2", "valuesss") }
        println(_globalStorage.value.filter { it.key == "hey" })
    }


}
