package com.test2code

import kotlinx.atomicfu.atomic


val myInt = atomic(0)


fun main() {
    println(myInt.value)
    println(myInt.incrementAndGet())
}
