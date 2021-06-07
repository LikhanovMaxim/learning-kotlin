package com

import kotlin.time.measureTimedValue

const val sizeBoolean = 1_000_000
const val probeCount = 250_000
fun main() {
    println("loading the lib...")
    loadNativeLib()
//    val a = SomeClass()
//    a.ddd()
//    webContainerSource()
//    ArrayBooleanNative.sett(1)
//    ArrayBooleanNative.sett(2)
//    println(ArrayBooleanNative.gett(2))
//    println(ArrayBooleanNative.gett(3))
    trackTime("setManyTimes native ThreadLocal") {
        ArrayBooleanNative.createNative(sizeBoolean)
        (0 until probeCount).map {
            ArrayBooleanNative.sett(it)
        }
    }
    println("starting time experiments...")
    trackTime("setManyTimes java BooleanArray") {
        val arr = BooleanArray(sizeBoolean)
        arr.setManyTimes(probeCount)
        arr.setManyTimes(probeCount)
    }
    trackTime("setManyTimes pass BooleanArray") {
        val arr = BooleanArray(10)
        KotlinPassParams.smth(arr)
    }
    nativeBoolean()
}

private fun nativeBoolean() {
    //    val structure = MyStructureBoolean()
//    println("setting")
//    convertFromBoolArrToByteArr()

    trackTime("setManyTimes native BooleanVarOf") {
        MyStructureBoolean.setting(sizeBoolean, probeCount)
    }
    trackTime("setManyTimes native BooleanArray") {
        MyStructureBoolean.setting22(sizeBoolean, probeCount)
    }
//    println("creating...") //todo
//    MyStructureBoolean.create(10)
//    println("setStub...")
//    MyStructureBoolean.setStub(2)
//    val get = MyStructureBoolean.get(1)
//    println("structure  $get ${MyStructureBoolean.get(2)}")
}

//TODO
private fun convertFromBoolArrToByteArr() {
    val array = BooleanArray(10)
    val message: List<Boolean> = array.toList()
    val byteArray = message.toString().toByteArray()
    println("$message $byteArray")
    println("${byteArray.toUByteArray()}")
    println("$byteArray ")
}

private fun webContainerSource() {
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
        set(it, true)
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
