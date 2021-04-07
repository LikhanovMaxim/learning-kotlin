package com

import kotlinx.coroutines.*
import kotlin.system.*

/**
 * GlobalScope
 * async
 * launch
 * runBlocking
 */
fun main() {
    runBlocking<Unit> {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulUnit() }
            val two = async { doSomethingUsefulTwo() }
            println("b = $b")
            println("The answer is ${one.await()} ${two.await()}")
            println("after await b = $b")
        }
        println("Completed in $time ms")
    }
//    firstExample()

//    secondExample()

}

suspend fun doSomethingUsefulTwo(): Int {
    println("doSomethingUsefulTwo $b")
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    b = 1
    return 13
}

suspend fun doSomethingUsefulUnit(): Unit {
    println("start doSomethingUsefulUnit")
    delay(2000L) // pretend we are doing something useful here
//    Thread.sleep(1000L)
    println("ending doSomethingUsefulUnit")
    b = 2
}

private fun secondExample() {
    val deferred = (1..1_000_000).map { n ->
        GlobalScope.async {
            n
        }
    }
    runBlocking {
        val sum = deferred.sumOf { it: Deferred<Int> -> it.await().toLong() }
        println("Sum: $sum")
    }
}

private fun firstExample() {
    println("hello from coroutines")
    GlobalScope.launch {
        println("${System.currentTimeMillis()} Hello $b ")
        doSomethingUsefulUnit()
        doSomethingUsefulTwo()
        println("${System.currentTimeMillis()} Hello after $b")
    }
    Thread.sleep(4000)
    println("stop")
}

var b = 0
