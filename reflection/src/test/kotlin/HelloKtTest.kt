import com.*
import io.ktor.locations.*
import kotlin.test.*

@KtorExperimentalLocationsAPI
class HelloKtTest {

    @Test
    fun `reflection RoutesOne`() {
        val kClassRoutes = Class.forName("com.RoutesOne").kotlin
        val map = findAllRoutesPath(kClassRoutes)
        println("res=$map")
        assertEquals(1, map.size)
    }

    @Test
    fun `reflection RoutesOne child`() {
        val kClassRoutes = Class.forName("com.RoutesChild").kotlin
        val map = findAllRoutesPath(kClassRoutes)
        println("res=$map")
        assertEquals(2, map.size)
    }

    @Test
    fun `reflection `() {
        val kClassRoutes = Class.forName("com.Routes").kotlin
        val map = findAllRoutesPath(kClassRoutes)
        println("res=$map")
        assertEquals(4, map.size)
    }

    @Test
    fun `set RoutesOne child`() {
        val set = setOf("/fir", "sec")
        println(set.plus("/third"))
        assertEquals(setOf("/fir", "sec", "/third"), set.plus("/third"))
    }
}
