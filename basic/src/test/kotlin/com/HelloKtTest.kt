package com

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HelloKtTest {
    @Test
    fun `first test`() {
        assertEquals(2, 2)
    }
    @Test
    fun `second test`() {
        assertEquals(3, 2)
    }
    @Test
    fun `third test`() {
        assertTrue(3 == 2)
    }

}
