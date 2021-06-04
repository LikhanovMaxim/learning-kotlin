package com

import com.epam.drill.kni.Kni

@Kni
actual object WebContainerSource {
    fun fillWebAppSource(warPath: String?, warResource: String?) {
        println("fillWebAppSource")
        if (warPath == null || warResource == null) {
            return
        }
        webAppStarted()
    }

    actual external fun webAppStarted()
    actual external fun printSmth(index: Int)
    actual external fun printSmthString(s: String)
//    actual external fun printBoolean(arrBool: BooleanArray)
//    actual external fun printByteArr(arrBool: ByteArray): ByteArray
    actual external fun returnByteArr(): ByteArray
    actual external fun returnInt(): Int
}
