package com

import kotlin.test.*
import kotlinx.serialization.*
import kotlinx.serialization.protobuf.*

//todo move to kts InternalSerializationApi ExperimentalSerializationApi
@InternalSerializationApi
@ExperimentalSerializationApi
class HelloKtTest {

    @Serializable
    data class Build(
        val parentVersion: String = "",
        val total: Int = 0,
    )

    @Test
    fun `check protobuf `() {
        val data = Build("asd", 2)
        val serializer = Build.serializer()
        val dump = ProtoBuf.dump(serializer, data)
        val load = ProtoBuf.load(Build.serializer(), dump)
        println(load)

        val store = StoreInfo<Build>(data)
//        val dump2 = store.set(data::class, data)
        val dump2 = store.set(data)
        val get = store.get(dump2)
        println(get)
        val get2 = store.get2<Build>(dump2)
        println(get2)
    }

    @Ignore
    @Test
    fun `check protobuf collections`() {
        val data = Build("asd", 2)
        val list = listOf(data)

//        val serializer = List.serializer()
//        val dump = ProtoBuf.dump(serializer, list)
//        val load = ProtoBuf.load(BuildKotlin.serializer(), dump)
//        println(load)

        val storeList = StoreInfo<List<Build>>(list)
        val dump3 = storeList.set(list)
        val get3 = storeList.get(dump3)
        println(get3)
    }
}

@InternalSerializationApi
@ExperimentalSerializationApi
@Suppress("UNCHECKED_CAST")
class StoreInfo<T : Any>(
    val smth: T,
) {
    //clazz: Class<T>, kSerializer: KSerializer<T>? = null,
    fun set(smth: T): ByteArray {
//        val ser: KSerializer<T> = clazz.actionSerializerOrNull() as KSerializer<T>

        val ser: SerializationStrategy<T> = smth::class.serializer() as SerializationStrategy<T>
//        val ser2 = (smth as List<Any>).getOrNull(0)!!::class.serializer()
        return ProtoBuf.dump(ser, smth)
    }

    fun get(dump2: ByteArray): T {
//        val des: DeserializationStrategy<T> = clazz.actionSerializerOrNull() as KSerializer<T>
        return ProtoBuf.load(smth::class.serializer(), dump2)
    }
//    fun < T : Any>get3(dump2: ByteArray): T {
//        return ProtoBuf.load(T::class.serializer(), dump2)
//    }

    inline fun <reified T : Any> get2(dump2: ByteArray): T {
        val objectInstance = T::class.objectInstance
        println(objectInstance)
        return ProtoBuf.load(T::class.serializer(), dump2)
    }
}
