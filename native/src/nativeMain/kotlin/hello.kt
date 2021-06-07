//import platform.windows.boolean
import com.asAgentParams
import com.asAgentParamsProperties
import com.readFile
import kotlinx.cinterop.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.properties.Properties
import net.mamoe.yamlkt.Yaml
import platform.posix.getenv
import platform.windows.booleanVar
import kotlin.native.concurrent.freeze
import kotlin.random.Random


const val DIR = "E:/projects/github/learning-kotlin_update/native/src/nativeMain/resources/"

@ExperimentalSerializationApi
@InternalSerializationApi
fun main() {
    printSystemVariable()
    printSystemVariable("DRILL_AGENT_CONFIG_PATH")
    printSystemVariable("DRILL_PLUGINS_REMOTE_ENABLED")
    printSystemVariable("TEMP")
    /**
    val nameVariable = "JAVA_HOME"
    val cfsv: CPointer<ByteVar>? = getenv(nameVariable)
    //ByteVarOf<Byte>

    //    NativePtr
    val rawValue = cfsv?.rawValue
    println("variable = ${cfsv?.toKString()} ${cfsv?.rawValue.toString()}")

    println("reading txt...")
    readTxt()

    println("reading json...")
    serializeJsonFile()

    println("reading YAML...")
    serializeYamlFile()
     **/
    println("reading properties...")
    val properties = readFile("${DIR}configFile.properties")

    val mapNotNull = listOf("drillInstallationDir=myVersion")
    val associate = mapNotNull.associate {
        val (key, value) = it.split("=")
        val pair = key to value
        println("pair $it =>$pair")
        pair
    }
    println(associate)

    val asAgentParams = properties.asAgentParams("\n", "#")
    println(asAgentParams)
//    serializePropertiesFile()
}

fun setBooleanNative(probeCount: Int = 1000 * 1024): BooleanArray {
    val bool = BooleanArray(probeCount)
    (0 until probeCount / 2).map {
        bool[Random.nextInt(probeCount)] = true
    }
    return bool
}

class MyStructureBooleanArr(size: Int) {
    private val bytePtr = BooleanArray(size)
    fun set(index: Int) {
        bytePtr[index] = true
    }

    fun get(index: Int) = bytePtr[index]
}

class MyStructureInt(size: Int) {
    private val bytePtr = nativeHeap.allocArray<IntVar>(size)
    fun setStub(index: Int, value: Int) {
        bytePtr[index] = value
    }

    fun get(index: Int) = bytePtr[index]

    fun close() {
        nativeHeap.free(bytePtr)
    }
}

class MyStructureBoolean1(size: Int) {
    //        private val bytePtr: CPointer<BooleanVarOf<Boolean>> = nativeHeap.allocArray<BooleanVar>(size)
    private lateinit var bytePtr: CPointer<BooleanVarOf<Boolean>>// = nativeHeap.allocArray<BooleanVar>(0)
    fun init(size: Int) {
        bytePtr = nativeHeap.allocArray(size)
    }

    fun setStub(index: Int) {
        bytePtr[index].value = true
    }

    fun get(index: Int): BooleanVar = bytePtr[index]
    fun close() {
        nativeHeap.free(bytePtr)
    }
}


fun setBooleanNative2(probeCount: Int = 1000 * 1024): BooleanArray {
//    val bool: boolean
//    bool = true.toByte().toboolean()
    val bool2: booleanVar
//    bool2.rawPtr
//    bool2.
//    val bool222: ArrayList<boolean>
//    val bool244: Array<Boolean> = Array<Bo>(probeCount)
//

//    (0 until probeCount / 2).map {
//        bool222[Random.nextInt(probeCount)] = true
//    }
//    cValuesOf(true, false, true)
    val cValuesOf = cValuesOf(1, 2, 3)
    return BooleanArray(1)
}

fun setBitSetNative(probeCount: Int = 1000 * 1024): BitSet {
    val bool = BitSet(probeCount)
    (0 until probeCount / 2).map {
        bool.set(Random.nextInt(probeCount))
    }
    return bool
}

private fun printSystemVariable(varName: String = "JAVA_HOME") {
    val agentParameters = mapOf<String, String>()
    val confPath: CPointer<ByteVar>? = getenv(varName)
    val configFile = agentParameters["configPath"] ?: confPath?.toKString()
    println("varName $varName values: ${confPath?.toKString()}  file = $configFile")
}

@Serializable
data class ConfigFile(
    val id: String
)

@Serializable
data class ComplexProperties(
    val id: String,
    val another: Int,
    val isBool: Boolean
)

@ExperimentalSerializationApi
private fun serializePropertiesFile() {
    val properties = readFile("${DIR}object.properties")

    val asAgentParams = properties.asAgentParamsProperties()
    println("map = $asAgentParams")

    val resultProperties = Properties.decodeFromMap(ComplexProperties.serializer(), asAgentParams)
    println(resultProperties)
}

private fun serializeYamlFile() {
    val yaml = readFile("${DIR}object.yaml")
    val result = Yaml.default.decodeAnyFromString(yaml)
    println(result)
    val configFileYaml = Yaml.default.decodeFromString(ConfigFile.serializer(), yaml)
    println(configFileYaml)
    // another yaml https://github.com/charleskorn/kaml support JVM only
//    val result = Yaml.default.decodeFromString(Object.serializer(), yaml)
//    println(result)
}

private fun serializeJsonFile() {
    val json = readFile("${DIR}object.json")
    println(json)
    val configFileJson = Json.decodeFromString<ConfigFile>(json)
    println(configFileJson)
}

private fun readTxt() {
    //    File.readText
//    File("name")
//    val dir = "E:/projects/github/kotlin_learning/native/src/nativeMain/resources/"
    val txt = readFile("${DIR}Example.txt")
    println(txt)
}

private const val JAVA_APP_JAR_ENV_VAR = "JAVA_APP_JAR"
fun calv(): String? {
    val kString = getenv(JAVA_APP_JAR_ENV_VAR)?.toKString()
    return calcBv(kString)
}

fun calcBv(versionParam: String?): String? = runCatching {
    versionParam?.let {
        "(.*)/(.*).jar".toRegex().matchEntire(it)?.let { matchResult: MatchResult ->
            val groupValues: List<String> = matchResult.groupValues
            println("$groupValues ${matchResult.groupValues.size}")
            if (matchResult.groupValues.size == 3) {
                val s = matchResult.groupValues[2]
                println("res=$s")
                s
            } else null
        }
    }
}.getOrNull()

actual class MyExpect {
    actual fun transform(smth: String) {
        println(smth)
    }
}


//class MyExpect {
//    @CName("Java_MyExpect_transform")
//    fun transform(smth: String) {
//        println(smth)
//    }
//}


//@Suppress("UNUSED_PARAMETER")
//@CName("Java_org_jonnyzzz_jni_java_NativeHost_callInt")
//fun callInt(env: CPointer<JNIEnvVar>, clazz: jclass, it: jint): jint {
//    initRuntimeIfNeeded()
//    Platform.isMemoryLeakCheckerActive = false
//
//    println("Native function is executed with: $it")
//    return it + 1
//}
//
//@CName("Java_com_example_hellojni_HelloJni_stringFromJNI")
//fun stringFromJNI(env: CPointer<JNIEnvVar>, thiz: jobject): jstring {
//    memScoped {
//        return env.pointed.pointed!!.NewStringUTF!!.invoke(env, "This is from Kotlin Native!!".cstr.ptr)!!
//    }
//}

object MyStructureBoolean {
    //        private val bytePtr: CPointer<BooleanVarOf<Boolean>> = nativeHeap.allocArray<BooleanVar>(size)
    //todo threadLocal
    private lateinit var bytePtr: CPointer<BooleanVarOf<Boolean>>// = nativeHeap.allocArray<BooleanVar>(0)

    //val a = ThreadLocal
    public fun create(size: Int) {
        println("creating...")
        bytePtr.freeze()
        println("freeze...")
        bytePtr = nativeHeap.allocArray(size)
    }

    public fun setStub(index: Int) {
        println("setting...")
        bytePtr[index].value.freeze()//.also {
//            it = true
//        }
        println("freeze")
        bytePtr[index].value = true
    }

    public fun get(index: Int): Int {
        val booleanVarOf = bytePtr[index]
        println("booleanVarOf $index")
        return booleanVarOf.value.toByte().toInt()
    }

    public fun close() {
        nativeHeap.free(bytePtr)
    }

//    actual fun setArray(array: List<Boolean>, index: Int) {
//        println("array $index $array")
//    }

    public fun setting() {
        println()
        val length = 1_000_000
        val probeCount = 250_000
        val booleans: CPointer<BooleanVarOf<Boolean>> = nativeHeap.allocArray(length)
        for (i in 0..probeCount) {
            booleans[i].value = true
        }
        nativeHeap.free(booleans)
//        todo
//        nativeHeap.free()
    }


}


@ThreadLocal
object Smth {
    var count: Int = 0
    fun add() {
        count++
    }
}


@ThreadLocal
object ArrayBoolean {
    lateinit var booleans: CPointer<BooleanVarOf<Boolean>>// = nativeHeap.allocArray<BooleanVar>(0)

    public fun create(size: Int) {
        booleans = nativeHeap.allocArray(size)
    }

    fun set(index: Int) {
        booleans[index].value = true
    }

    fun get(index: Int) = booleans[index].value
}
