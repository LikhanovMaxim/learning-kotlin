package com

interface Agent {
    val list: List<String>

}

class ClassesAndFields {
    init {
        val s = "ddd"
        println(s)
    }

    companion object {
        data class Lol(val sss: String = "")
    }
}
