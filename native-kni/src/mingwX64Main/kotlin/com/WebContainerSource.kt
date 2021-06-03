package com

//todo how to use only linux version?
actual object WebContainerSource {

    actual fun webAppStarted() {
        println("App is initialized. Native")
        println { "App  is initialized" }
    }

    actual fun printSmth(index: Int) {
        println("App is initialized. Native $index")
    }

}
