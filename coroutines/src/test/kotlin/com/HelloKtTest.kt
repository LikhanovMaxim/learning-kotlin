package com

import kotlinx.coroutines.*
import org.junit.jupiter.api.AfterEach
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.measureTimedValue

internal val availableProcessors: Int = Runtime.getRuntime().availableProcessors()

internal val allAvailableProcessDispatcher: ExecutorCoroutineDispatcher =
    Executors.newFixedThreadPool(availableProcessors).asCoroutineDispatcher()

class HelloKtTest {

    val map = mutableMapOf<String, String>()

    @AfterEach
    fun removeAll() {
        println("removing...")
        map.clear()
    }

    @Test
//    @RepeatedTest(2)
    fun `should work with map in suspend when afterTest remove data from map`() = runBlocking {
        println("starting")
        map["12"] = "dasda"
        map["13"] = "dasda"
        delay(100)
        assertEquals(2, map.size)
        delay(100)
        setSmth()
        delay(100)
        assertEquals(102, map.size)
        println("finished test")
    }

    suspend fun setSmth() {
        repeat(100) {
//            delay(100)
            map["2$it"] = "dasgfa"
        }
    }

    @Test
    fun asd() {
        assertTrue { true }
//        val list = listOf("asd", "sgsdf", "1111")
        val list = (0 until 1_000).map { "$it ${Random.nextInt()}" }

        trackTime("list to smth") {
            list.toSmth()
        }

        trackTime("list to smth2") {
            PluginWorker.launch {
                list.toSmth2()
            }
        }


        PluginWorker.launch {
            list.forEach {
                println(it)
                prinMy(it)
            }
            trackTime("run all") {
                list.forEach {
//                    println(it)
                    launch {
                        prinMy(it)
                    }
                }
            }
//            list.parallelStream().forEach {
//                println(it)
//                launch {
//                    prinMy(it)
//                }
//            }
        }
        Thread.sleep(10_000)
//        delay(10000)
//        withObjects()

    }

    private fun withObjects() {
        val list2 = listOf(Something("asd", 2), Something("awdw", 2), Something("a22sd", 2))

        list2.parallelStream().forEach {
            println(it)
        }
    }

    suspend fun prinMy(string: String) {
        delay(2000)
        println(string)
    }

    data class Something(
        val string: String,
        val intds: Int,
    )
}

private fun List<String>.toSmth(): List<String> {
    return runBlocking(allAvailableProcessDispatcher) {
        val subCollectionSize = (size / availableProcessors).takeIf { it > 0 } ?: 1
        println("subCollectionSize= $subCollectionSize")
        chunked(subCollectionSize).map { subList: List<String> ->
            async {
                delay(500)//some calculation
                subList.reduce { a: String, b: String -> (a.length + b.length).toString() }
            }
        }.map { it.await() }.apply {
            println("res=$this")
        }
    }
}

private suspend fun List<String>.toSmth2(): List<String> {
    val a = System.currentTimeMillis()
    val subCollectionSize = (size / availableProcessors).takeIf { it > 0 } ?: 1
    println("subCollectionSize= $subCollectionSize")
    return chunked(subCollectionSize).map { subList: List<String> ->
        PluginWorker.async {
            delay(500)//some calculation
            subList.reduce { a: String, b: String -> (a.length + b.length).toString() }
        }
    }.map { it.await() }.apply {
        println("${System.currentTimeMillis() - a}res=$this")
    }
}


internal object PluginWorker : CoroutineScope {
    init {
        println("PluginWorker with size pool = $availableProcessors ")
    }

    override val coroutineContext: CoroutineContext = run {
        Executors.newFixedThreadPool(availableProcessors).asCoroutineDispatcher() + SupervisorJob()
    }
}


inline fun <T> trackTime(tag: String = "", block: () -> T) {
    measureTimedValue { block() }.apply {
        println("[$tag] took $duration; ${duration.inSeconds}")
    }
}
