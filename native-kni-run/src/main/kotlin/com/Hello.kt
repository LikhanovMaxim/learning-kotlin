package com

import kotlin.time.*

fun main() {
    val a = TimeSource.Monotonic.markNow();
    println("hello from native-kni-run")
    println(a.elapsedNow())
    WebContainerSource.webAppStarted("sad")
//    Thread.sleep(10_000)
}
