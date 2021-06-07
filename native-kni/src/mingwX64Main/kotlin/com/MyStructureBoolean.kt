package com

import kotlinx.cinterop.*

actual object MyStructureBoolean {
    //        private val bytePtr: CPointer<BooleanVarOf<Boolean>> = nativeHeap.allocArray<BooleanVar>(size)
    //todo threadLocal
//    @ThreadLocal
    private lateinit var bytePtr: CPointer<BooleanVarOf<Boolean>>// = nativeHeap.allocArray<BooleanVar>(0)

    //val a = ThreadLocal
    actual fun create(size: Int) {
        bytePtr = nativeHeap.allocArray(size)
    }

    actual fun setStub(index: Int) {
        bytePtr[index].value = true
    }

    actual fun get(index: Int): Int {
        val booleanVarOf = bytePtr[index]
        println("booleanVarOf $index")
        return booleanVarOf.value.toByte().toInt()
    }

    actual fun close() {
        nativeHeap.free(bytePtr)
    }

//    actual fun setArray(array: List<Boolean>, index: Int) {
//        println("array $index $array")
//    }

    actual fun setting(sizeBool: Int, probe: Int) {
        println("setting")
//        val length = 1_000_000
        val booleans: CPointer<BooleanVarOf<Boolean>> = nativeHeap.allocArray(sizeBool)
        setMany(probe, booleans)
        setMany(probe, booleans)
//        for (i in probeCount..probeCount * 2) {
//            booleans[i].value = true
//        }
//        nativeHeap.free(booleans)//todo
    }

    private fun setMany(
        probeCount: Int,
        booleans: CPointer<BooleanVarOf<Boolean>>
    ) {
        for (i in 0..probeCount) {
            booleans[i].value = true
        }
    }

    actual fun setting22(sizeBool: Int, probe: Int) {
        val boolean2 = BooleanArray(sizeBool)

        for (i in 0..probe) {
            boolean2[i] = true
        }
        for (i in 0..probe) {
            boolean2[i] = true
        }
    }

}
