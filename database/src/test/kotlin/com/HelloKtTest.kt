package com

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.AfterEach
import org.mapdb.DBMaker
import org.mapdb.HTreeMap
import org.mapdb.Serializer
import kotlin.test.Test
import kotlin.test.assertEquals


class HelloKtTest {

    @Test
    fun `mapdb example`() {
        val db = DBMaker.memoryDB().closeOnJvmShutdown().make()
        val map = db
            .hashMap("myTreeMap")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.STRING)
            .create()
        map["key1"] = "value1"
        map["key2"] = "value2"

        assertEquals(2, map.size)
        assertEquals("value2", map["key2"])
    }

    @Test
    fun `mapdb file`() {
        val db = DBMaker
            .fileDB("file.db")
            .closeOnJvmShutdown()
            .make()
        val map = db
            .hashMap("myTreeMap")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.STRING)
            .createOrOpen()
        map["key1"] = "value1"
        map["key2"] = "value2"

        assertEquals(2, map.size)
        assertEquals("value2", map["key2"])
//        map.close()
        db.close()
    }

    @Serializable
    data class Epta(val sss: String) : java.io.Serializable

    @Serializable
    data class CoverDataPart(val sessionId: String, val data: List<Epta>) : java.io.Serializable

    //    @OptIn(InternalSerializationApi::class)
    val dbMap = DBMaker
        .fileDB("fileMap.db")
        .closeOnJvmShutdown()
        .make()

    @Test
    fun `mapdb file data object`(): Unit = runBlocking {
        coroutineScope {
            launch {
                val map = dbMap
                    .hashMap("myTreeMap")
                    .keySerializer(Serializer.STRING)
                    .valueSerializer(Serializer.JAVA)
                    .createOrOpen()
                val coverDataPart = CoverDataPart("sadsd", listOf(Epta("ads")))
                val coverDataPart2 = CoverDataPart("2222", listOf())
//        val aaa: KSerializer<CoverDataPart> = CoverDataPart.serializer()
//        val serializer: KSerializer<CoverDataPart> = CoverDataPart::class.serializer()
//        val serializer2 = CoverDataPart::javaClass
                map["key1"] = coverDataPart
                map["key2"] = coverDataPart2

//        assertEquals(2, map.size)


                launch {
                    setttt(map)
                }

                delay(100L)

                println(map["key2"])

                launch {
                    for (i in 1..3) {
                        map.forEach {
                            println(it.value)
                            map.remove(it.key)
                        }
                        assertEquals(0, map.size)
                        println("finish $i")
                        delay(10L)
                    }
                    println("finish all")
                }
            }

        }

    }

    private suspend fun setttt(map: HTreeMap<String, Any>) {
        for (i in 3..500) {
            map["key$i"] = CoverDataPart("session$i", listOf(Epta("ads")))
            println("set $i successfully")
            delay(1L)
        }
    }

    @AfterEach
    fun close() {
        dbMap.close()
    }

    @Test
    fun `mapdb file set object`() {
        val coverDataPart = CoverDataPart("sadsd", listOf(Epta("ads")))
        val coverDataPart2 = CoverDataPart("2222", listOf())
        val db = DBMaker.fileDB("fileSet.db")
            .closeOnJvmShutdown()
            .make()
        val treeSet = db.treeSet("example")
            .serializer(Serializer.JAVA)
            .createOrOpen()

        treeSet.add(coverDataPart)
        treeSet.add(coverDataPart2)

        treeSet.stream().forEach {
            println(it)
        }

    }


    @Test
    fun `mapdb file data object with set`(): Unit = runBlocking {
        coroutineScope {
            launch {
                val set = dbMap
                    .hashSet("set")//todo concurrent?
                    .serializer(Serializer.JAVA)
                    .createOrOpen()
                val coverDataPart = CoverDataPart("sadsd", listOf(Epta("ads")))
                val coverDataPart2 = CoverDataPart("2222", listOf())
//        val aaa: KSerializer<CoverDataPart> = CoverDataPart.serializer()
//        val serializer: KSerializer<CoverDataPart> = CoverDataPart::class.serializer()
//        val serializer2 = CoverDataPart::javaClass
                set.add(coverDataPart)
                set.add(coverDataPart2)

//        assertEquals(2, map.size)


                launch {
                    setttt(set)
                }

                delay(100L)

                println(set.first())

                launch {
                    for (i in 1..3) {
                        set.forEach {
                            println(it)
                            set.remove(it)
                        }
                        //todo chunked?
                        assertEquals(0, set.size)
                        println("finish $i")
                        delay(10L)
                    }
                    println("finish all")
                }
            }

        }

    }

    @Test
    fun `close db right`() {
        val plugin = Plugin()
        plugin.addAndGetElement()
    }

    private suspend fun setttt(map: HTreeMap.KeySet<Any>) {
        for (i in 3..500) {
            map.add(CoverDataPart("session$i", listOf(Epta("ads"))))
            println("set $i successfully")
            delay(1L)
        }
    }


    @Serializable
    data class SmthData(val sessionId: String) : java.io.Serializable


}
//@Serializer(forClass = HelloKtTest.SmthData::class)
//object SmthDataSerializer: KSerializer<HelloKtTest.SmthData> {
//    override fun deserialize(decoder: Decoder): HelloKtTest.SmthData {
//        TODO("Not yet implemented")
//    }
//
//    override val descriptor: SerialDescriptor
//        get() = TODO("Not yet implemented")
//
//    override fun serialize(encoder: Encoder, value: HelloKtTest.SmthData) {
//        TODO("Not yet implemented")
//    }
//
//}
