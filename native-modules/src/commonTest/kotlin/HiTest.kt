import kotlin.test.*

class HiTest {

    @Test
    fun `check default`() {
        val hi = Hi()
        hi.printSmth()
        assertEquals(true, true)
    }
}
