package com

import com.epam.drill.kni.Kni

@Kni
actual object ArrayBooleanNative {
    actual external fun createNative(size: Int)
    actual external fun sett(index: Int)
    actual external fun gett(index: Int) : Byte
}
