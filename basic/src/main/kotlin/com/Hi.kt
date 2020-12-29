//package com
//
//import kotlin.reflect.*
//
//class LazyDelegateEtc {
//
//    //    Delegated Properties
//    var p: String by Delegate()
//
//}
//
//class Delegate {
//    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
//        return "$thisRef, thank you for delegating '${property.name}' to me!"
//    }
//
//    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
//        println("$value has been assigned to '${property.name}' in $thisRef.")
//    }
//}
//
//val lazyValue: String by lazy {
//    println("computed!")
//    "Hello"
//}
//
//
//fun main() {
//    println(lazyValue)
//    println(lazyValue)
//
////    val e = LazyDelegateEtc()
////    println(e.p)
//
//
//}
