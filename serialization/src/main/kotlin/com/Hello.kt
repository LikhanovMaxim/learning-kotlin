package com

import kotlinx.serialization.*
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

@Serializable
data class Project(val name: String, val language: String)

@ExperimentalSerializationApi
@InternalSerializationApi
fun main() {
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
