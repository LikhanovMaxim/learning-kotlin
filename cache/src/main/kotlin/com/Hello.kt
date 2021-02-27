package com

import kotlinx.collections.immutable.*

class Hello {
}
fun main(){
    println("cache")
    val persistentList: PersistentList<Long> = persistentListOf(0L, 93L)
    persistentList.let {

    }
    println()
}
