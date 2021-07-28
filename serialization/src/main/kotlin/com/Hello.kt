package com

import kotlinx.serialization.*
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

@Serializable
data class Project(val name: String, val language: String)

@Serializable
data class ProjectSmth(val params: Map<String, String> = mapOf("dsa" to "ads", "asd" to "adsddd"))


//drill
@Serializable
abstract class AgentConfig(
    val id: String,
    val parameters: Map<String, AgentParameter> = emptyMap(),
) : Update


interface Update {
    fun updateParameters()
}

@Serializable
data class AgentParameter(
    val type: String,
    var value: String,//todo var ?
)

abstract class UpdateConf {
    abstract fun updateParams(params: AgentConfig)
}

interface AgentConfigUpdater {
    fun updateParameters(params: AgentConfig)
}

class AgentConfigUpdaterImpl : AgentConfigUpdater {
    override fun updateParameters(params: AgentConfig) {
        println("AgentConfigUpdaterImpl")
    }
}

//java-agent
@Serializable //todo
class AgentConfigImpl : AgentConfig {
    constructor(id: String, parameters: Map<String, AgentParameter>)
            : super(id, parameters)

    override fun updateParameters() {
        println("dsa")
    }
}

class UpdateConfImpl : UpdateConf() {
    override fun updateParams(params: AgentConfig) {
        println("from java-agent")
    }

}

@Serializable
abstract class DDD(val id: String) : Update

@Serializable
class Ex : DDD {
    constructor(id: String) : super(id)

    override fun updateParameters() {
    }
}

val updateConf = UpdateConfImpl()
val agentConfigUpdater: AgentConfigUpdater = AgentConfigUpdaterImpl()

//agent

@ExperimentalSerializationApi
@InternalSerializationApi
fun main() {
    //java-agent
    val config = AgentConfigImpl("ds", mapOf("logLevel" to AgentParameter("sti", "info")))
    //agent
    config.updateParameters()
    updateConf.updateParams(config)
    agentConfigUpdater.updateParameters(config)
//    val string = Json.encodeToString(config)
//    println(string)
//    // Deserializing back into objects
//    val obj22 = Json.decodeFromString<AgentConfig>(string)
//    println(obj22)

    val map = ProjectSmth()
    val serializer = map::class.serializer()
    println(serializer)

//    val map2 = mapOf("dsa" to "ads", "asd" to "adsddd")
//    val map1 =  LinkedHashMap(map2)
//    println(map2::class.serializer())
    //todo doesn't work
    /**
     * Exception in thread "main" kotlinx.serialization.SerializationException: Serializer for class 'LinkedHashMap' is not found.
    Mark the class as @Serializable or provide the serializer explicitly.
    at kotlinx.serialization.internal.Platform_commonKt.serializerNotRegistered(Platform.common.kt:91)
    at kotlinx.serialization.SerializersKt__SerializersKt.serializer(Serializers.kt:130)
    at kotlinx.serialization.SerializersKt.serializer(Unknown Source)
     */

//    jsonExample()
    val data = Project("project name", "Kotlin")
    val bytes = Cbor.encodeToByteArray(data)
    println(bytes.size)
    // Deserializing back into objects
    val obj = Cbor.decodeFromByteArray<Project>(bytes)
    println(obj) // Project(name=project name, language=Kotlin)
}

@InternalSerializationApi
fun jsonExample() {
    // Serializing objects
    val data = Project("project name", "Kotlin")
    val string = Json.encodeToString(data)
    println(string) // {"name":"project name","language":"Kotlin"}
    // Deserializing back into objects
    val obj = Json.decodeFromString<Project>(string)
    println(obj) // Project(name=project name, language=Kotlin)

    store("asdasd")
//    Json.parseToJsonElement()
    val list = listOf<String>("asd", "312")
    list.forEach {
        val kClass: KClass<*> = it::class
        val serializer = kClass.serializer()
        val b = serializer as KSerializer<*>
        println("$it $serializer $b")

    }
}

val json = Json { encodeDefaults = true }


class XodusEncoder

@InternalSerializationApi
inline fun <reified T : Any> store(any: T) {
    val toString = any::class.simpleName.toString()
    val serializer = T::class.serializer()
    println("$toString $serializer")
//    XodusEncoder(this, obj).encode(T::class.serializer(), any)
}

@InternalSerializationApi
inline fun <reified T : Any> store2(any: T) {
    val toString = any::class.simpleName.toString()
    val serializer = T::class.serializer()
    println("$toString $serializer")
//    XodusEncoder(this, obj).encode(T::class.serializer(), any)
}
