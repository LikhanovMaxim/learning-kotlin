package com.test2code

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update

// TODO after solving the bug: https://github.com/Kotlin/kotlinx.atomicfu/issues/118
//val myInt = atomic(0)
//
//private val top = atomic<Int?>(null)
//
//fun main() {
//    top.update { 0 }
//    println()
//}
//
//private fun workWithInt() {
//    println(myInt.value)
//    println(myInt.incrementAndGet())
//}
