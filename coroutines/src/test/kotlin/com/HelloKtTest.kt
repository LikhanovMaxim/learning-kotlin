package com

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.measureTimedValue

class HelloKtTest {
    @Test
    fun asd() {
        assertTrue { true }
//        val list = listOf("asd", "sgsdf", "1111")
        val list = (0 until 10).map { "$it ${Random.nextInt()}" }

        PluginWorker.launch {
//            list.forEach {
//                println(it)
//                prinMy(it)
//            }
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

internal object PluginWorker : CoroutineScope {
    override val coroutineContext: CoroutineContext = run {
        Executors.newFixedThreadPool(4).asCoroutineDispatcher() + SupervisorJob()
    }
}


inline fun <T> trackTime(tag: String = "", block: () -> T) {
    measureTimedValue { block() }.apply {
        println("[$tag] took $duration; ${duration.inSeconds}")
    }
}
