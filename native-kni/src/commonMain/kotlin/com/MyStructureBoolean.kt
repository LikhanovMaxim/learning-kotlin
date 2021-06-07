package com


expect object MyStructureBoolean {
    actual fun create(size: Int)

    actual fun setStub(index: Int)

    actual fun get(index: Int): Int
    actual fun close()

//    actual fun setArray(array: List<Boolean>, index: Int)
    actual fun setting(sizeBool: Int, probe: Int)
    actual fun setting22(sizeBool: Int, probe: Int)
}
