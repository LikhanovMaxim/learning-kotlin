package com


expect object ArrayBooleanNative {
    fun createNative(size: Int)
    fun sett(index: Int)
    fun gett(index: Int) : Byte
}
