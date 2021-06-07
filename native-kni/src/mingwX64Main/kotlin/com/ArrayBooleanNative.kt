package com

import kotlinx.cinterop.toByte

@ThreadLocal
actual object ArrayBooleanNative {
    lateinit var booleans: BooleanArray// = nativeHeap.allocArray<BooleanVar>(0)

    actual inline fun createNative(size: Int) {
        booleans = BooleanArray(size)
    }

    actual inline fun sett(index: Int) {
        booleans[index] = true
        //todo native set?
    }

    actual fun gett(index: Int): Byte {
        return booleans[index].toByte()
    }
}
