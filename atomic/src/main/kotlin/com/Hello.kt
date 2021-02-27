package com

import kotlinx.atomicfu.*
import kotlinx.collections.immutable.*

fun main() {
    println("hi")
    val smth = Smth()
    smth.addToSet("first")
    smth.addToSet("first")
    smth.addToSet("second")
    println(smth.getPluginRoutes)
}

class Smth {
    val getPluginRoutes: PersistentSet<String>
        get() = _getPluginRoutes.value
    private val _getPluginRoutes = atomic(persistentSetOf<String>())

    fun addToSet(string: String) {
        _getPluginRoutes.update { it.add(string) }
    }
}
