package com

import java.util.*

class DelegationExample {
}

interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() {
        println(x)
    }
}

class Derived(b: Base) : Base by b

class Derived22(b: Base) : Base by b {

    fun lol() {
        println("lol")
    }
}

interface WorkProbes {
    fun length(): Int
    fun get(ind: Int): Boolean
    fun set(ind: Int, value: Boolean)
}

class SimpleProbesImpl(private val size: Int) : WorkProbes {
    override fun length(): Int {
        return 0
    }

    override fun get(ind: Int): Boolean {
        return false
    }

    override fun set(ind: Int, value: Boolean) {
        println("set empty $size...")
    }

}

class WorkProbesImpl(set: WorkProbes) : WorkProbes by set {

}
