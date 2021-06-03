package com


import java.util.*
import kotlin.test.*


class BitOperationsTest {
    lateinit var words: LongArray

    /**
     *  private long[] words;
     *  set:
     *         val bitIndex = 2
     *         var wordIndex = 1
     *         words.get(wordIndex) = words.get(wordIndex) or (1L shl bitIndex) // Restores invariants
     */
    @Test
    fun `check hi`() {
        val bitSet = BitSet(20)
        bitSet.set(10)

        println("kek")
    }

    @Test
    fun `native boolean`() {
        val booleanNative = setBooleanNative()
        println(booleanNative.size)
    }

    @Test
    fun `native bitSet`() {
        val booleanNative: BitSet = setBitSetNative()
        println(booleanNative.size())
    }

}
