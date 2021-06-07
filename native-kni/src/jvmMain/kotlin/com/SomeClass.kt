package com

import com.epam.drill.kni.Kni

//TODO
//@Kni
actual class SomeClass {
    actual external fun ddd()
}
/**
 * What went wrong:
Execution failed for task ':native-kni:generateNativeClasses'.
> Can't escape identifier `<init>` because it contains illegal characters: <>
 */
