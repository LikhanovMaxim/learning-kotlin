import kotlinx.cinterop.toBoolean
import kotlinx.cinterop.toByte
import kotlinx.cinterop.value
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlin.test.Test

@InternalSerializationApi
@ExperimentalSerializationApi
class BoolTest {

    @Test
    fun `native boolean`() {
        val booleanNative = setBooleanNative()
        println(booleanNative.size)
    }

    @Test
    fun `native bitSet`() {
        val booleanNative = setBitSetNative()
        println(booleanNative.size)
    }

    @Test
    fun `structure ads`(){
        val structure = MyStructureInt(30)
        structure.setStub(1, 20)
        println("structure ${structure.get(1)}")
    }
    @Test
    fun `structure boolean vs byte`(){
        val ss: Byte = false.toByte()
        val ss2: Byte = true.toByte()
        ss.toBoolean()
        println("$ss ss2=$ss2 ${ss2.toBoolean()}")
    }

    @Test
    fun `structure boolean`(){
        val structure = MyStructureBoolean(30)
        structure.setStub(2)
        val get = structure.get(1)
        println("structure ${get.rawPtr}, ${get.value} ${structure.get(2).value}")
    }
}
