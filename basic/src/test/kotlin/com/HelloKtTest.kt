package com

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HelloKtTest {
    @Test
    fun `first test`() {
        assertTrue(2 == 2)
    }

    @Test
    fun `second test`() {
        assertTrue(2 != 2)
    }
    @Test
    fun `third test`() {
        assertEquals(2, 3)
    }

}
