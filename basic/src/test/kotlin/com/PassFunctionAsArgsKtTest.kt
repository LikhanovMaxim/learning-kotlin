package com

import kotlin.test.*


class PassFunctionAsArgsKtTest {
    @Test
    fun `check `() {
        val build = Build("1", "0", "dataasdasd")

        val build2 = updateBuild(build) {
            copy(version = "4444")
        }
        println(build2)
    }
}
