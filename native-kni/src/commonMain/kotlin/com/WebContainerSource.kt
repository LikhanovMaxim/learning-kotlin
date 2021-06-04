package com

expect object WebContainerSource {
    actual fun webAppStarted()
    actual fun printSmth(index: Int)
    fun printSmthString(s: String)
//    actual fun printBoolean(arrBool: BooleanArray)
//    fun printByteArr(arrBool: ByteArray): ByteArray
    fun returnByteArr(): ByteArray
    fun returnInt(): Int
}
