class Hi {

}

fun main() {
    println("hi from jvm")
    val a = MyExpect()
    a.transform("hi from JVM")

    val ret = NativeHost().callInt(42)
    println("ret from the native: $ret")

}
actual class MyExpect {
    actual external fun transform(smth: String)
}

//class MyExpect {
//    external fun transform(smth: String)
//}

class NativeHost {
    external fun callInt(x: Int) : Int
}
