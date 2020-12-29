import com.*
import kotlinx.cinterop.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.properties.*
import net.mamoe.yamlkt.*
import platform.posix.*


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

