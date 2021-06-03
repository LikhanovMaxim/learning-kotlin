package com

import java.util.*
import kotlin.random.Random

class BitOperations {



}
fun setBooleanNative(probeCount: Int = 1000 * 1024): BooleanArray {
    val bool = BooleanArray(probeCount)
    (0 until probeCount / 2).map {
        bool[Random.nextInt(probeCount)] = true
    }
    return bool
}

fun setBitSetNative(probeCount: Int = 1000 * 1024): BitSet {
    val bool = BitSet(probeCount)
    (0 until probeCount / 2).map {
        bool.set(Random.nextInt(probeCount))
    }
    return bool
}
