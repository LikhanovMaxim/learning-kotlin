package com

//todo how to use only linux version?
actual object WebContainerSource {

    actual fun webAppStarted() {
        println("App is initialized. Native")
        println { "App  is initialized" }
    }

    actual fun printSmth(index: Int) {
        println("App is initialized. Native $index")
//        throw NullPointerException("eprt")
    }

    actual fun printSmthString(s: String) {
        println("App is initialized. Nativ")
    }

//    actual fun printBoolean(arrBool: BooleanArray) {
//        println("App is initialized. Nativ")
//    }

//    actual fun printByteArr(arrBool: ByteArray) : ByteArray {
//        println("App is initialized. Nativ")
//        return ByteArray(1)
//    }

    actual fun returnByteArr(): ByteArray = retrieveAdminUrl().encodeToByteArray()
//    {
//        return encodeToByteArray
//        println("returnByteArr $encodeToByteArray...")
//        val encodeToByteArray = s.encodeToByteArray()
//        println("returnByteArr $s...")
//        val s: String = "ByteArray(1)"
//        println("returnByteArr...")
//    }




    actual fun returnInt(): Int = 42

}

fun retrieveAdminUrl(): String {
    return "retrieveAdminUrl"
}
