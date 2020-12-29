import kotlinx.cinterop.*
import kotlinx.serialization.*
import platform.posix.*


const val DIR = "E:/projects/github/learning-kotlin_update/native/src/nativeMain/resources/"

@ExperimentalSerializationApi
@InternalSerializationApi
fun main() {
    printSystemVariable()
    printSystemVariable("DRILL_AGENT_CONFIG_PATH")
    printSystemVariable("DRILL_PLUGINS_REMOTE_ENABLED")
    printSystemVariable("TEMP")
}

private fun printSystemVariable(varName: String = "JAVA_HOME") {
    val agentParameters = mapOf<String, String>()
    val confPath: CPointer<ByteVar>? = getenv(varName)
    val configFile = agentParameters["configPath"] ?: confPath?.toKString()
    println("varName $varName values: ${confPath?.toKString()}  file = $configFile")
}
