import kotlinx.cinterop.toBoolean
import kotlinx.cinterop.toByte
import kotlinx.cinterop.value
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlin.test.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue


const val sizeBoolean = 1_000_000
const val probeCount = 250_000

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
    fun `structure ads`() {
        val structure = MyStructureInt(30)
        structure.setStub(1, 20)
        println("structure ${structure.get(1)}")
    }

    @Test
    fun `structure boolean vs byte`() {
        val ss: Byte = false.toByte()
        val ss2: Byte = true.toByte()
        ss.toBoolean()
        println("$ss ss2=$ss2 ${ss2.toBoolean()}")
    }

    @Test
    fun `structure boolean`() {
        val structure = MyStructureBoolean1(30)
        structure.setStub(2)
        val get = structure.get(1)
        println("structure ${get.rawPtr}, ${get.value} ${structure.get(2).value}")
    }

    @Test
    fun `structure boolean 2`() {
//        val structure = MyStructureBoolean
        println("create")
        MyStructureBoolean.create(2)
        println("set")
        MyStructureBoolean.setStub(2)
//        val get = MyStructureBoolean.get(1)
        println("structure ${MyStructureBoolean.get(1)}, ${MyStructureBoolean.get(2)}")
    }

    @Test
    fun `structure boolean array`() {
        trackTime("setMy") {
            val str = MyStructureBooleanArr(sizeBoolean)
            (0 until probeCount).map {
                str.set(it)
            }
        }

//        println("structure ${str.get(1)}, ${str.get(2)}")
    }

    @Test
    fun `thread local`() {
        val a = Smth
        a.add()
        a.add()
        println(a.count)

        val dd = ArrayBoolean
        dd.create(100)
        dd.set(1)
        dd.set(2)

        println(dd.get(1))
        println(dd.get(3))

    }
}


@OptIn(ExperimentalTime::class)
inline fun <T> trackTime(tag: String = "", debug: Boolean = true, block: () -> T) =
    measureTimedValue { block() }.apply {
        when {
            duration.inSeconds > 1 -> {
                println("[$tag] took: $duration")
            }
            duration.inSeconds > 30 -> {
                println("[$tag] took: $duration")
            }
            else -> if (debug) println("[$tag] took: $duration") else println("[$tag] took: $duration")
        }
    }.value
