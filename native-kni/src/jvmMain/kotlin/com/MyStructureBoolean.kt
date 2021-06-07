package com

import com.epam.drill.kni.Kni

@Kni
actual object MyStructureBoolean {
    actual external fun create(size: Int)

    actual external fun setStub(index: Int)

    actual external fun get(index: Int): Int
    actual external fun close()

//    actual external fun setArray(array: List<Boolean>, index: Int)
    actual external fun setting(sizeBool: Int, probe: Int)
    actual external fun setting22(sizeBool: Int, probe: Int)
}
