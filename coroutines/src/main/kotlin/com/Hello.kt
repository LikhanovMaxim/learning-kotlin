package com

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * GlobalScope
 * async like Callable in Java
 * launch like Runnable in Java
 * runBlocking
 */
fun main() {
    println("starting...")
    val scope = MainScope()
    scope.launch {
        loadConfigurationAndData()
    }
//    thirdExampleRunBlocking()

    println("hi from main")
//    firstExample()

//    secondExample()
}

private fun thirdExampleRunBlocking() {
    runBlocking<Unit> {
        val time = measureTimeMillis {
            val one: Deferred<Unit> = async { doSomethingUsefulUnit() }
            val two: Deferred<Int> = async { doSomethingUsefulTwo() }
//            val one =  doSomethingUsefulUnit()
//            val two =  doSomethingUsefulTwo()
//            println("The answer is ${one} ${two}")
            println("b = $b")
            println("The answer is ${one.await()} ${two.await()}")
            println("after await b = $b")
        }
        println("Completed in $time ms")
    }
}

// concurrently load configuration and data
suspend fun loadConfigurationAndData() {
    coroutineScope {
        launch { loadConfiguration() }
        launch { doSomethingUsefulUnit() }
    }
}

suspend fun loadConfiguration() {
    println("doSomethingUsefulTwo $b")
    delay(1000L) // pretend we are doing something useful here, too
//    val config = fetchConfigFromServer() // network request
//    updateConfiguration(config)
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
