package com

import kotlin.random.Random
import kotlin.time.measureTimedValue


fun main() {
    println("loading the lib...")
    loadNativeLib()

//    hiFromNative()

    WebContainerSource.printSmth(1)

    println(WebContainerSource.returnInt())
    runCatching {
        WebContainerSource.printSmthString("eeee")
    }.onFailure {
        println(it)
    }

//    println(concat)

//    val byteArray = ByteArray(3)
//    WebContainerSource.printByteArr(byteArray)

    runCatching {
        val returnByteArr = WebContainerSource.returnByteArr()
        println("decode ${returnByteArr.decodeToString()}")
    }.onFailure {
        println(it)
    }

    println("starting time experiments...")
    val arr = BooleanArray(1_000_000)
    trackTime("setManyTimes") {
        arr.setManyTimes(250_000)
    }

}

private fun loadNativeLib() {
    //todo
    val dir =
        "C:\\Users\\Maksim_Likhanov\\IdeaProjects\\learning-kotlin\\native-kni\\build\\bin\\mingwX64\\my-nativeDebugShared"
    val libname = "my_native"
//    val libname = "native-kni"
//    System.loadLibrary(libname)//it works but need to put dll, def, h to a strange folder
    System.load("$dir\\$libname.dll")
}

private fun hiFromNative() {
    println("sda")
    println { WebContainerSource::fillWebAppSource.name }
    println("invoke from native...")

    WebContainerSource.fillWebAppSource("asd", "asd22")
}

fun BooleanArray.setManyTimes(probeCount: Int) {
    (0 until probeCount).map {
        set(Random.nextInt(size - 1), true)
    }
}

inline fun <T> trackTime(tag: String = "", debug: Boolean = true, block: () -> T) =
    measureTimedValue { block() }.apply {
        when {
            duration.inSeconds > 1 -> {
                println("[$tag] took: $duration")
            }
            duration.inSeconds > 30 -> {
                println("[$tag] took: $duration")
            }
            else -> if (debug) println("[$tag] took: $duration") else println("[$tag] took: $duration")
        }
    }.value
