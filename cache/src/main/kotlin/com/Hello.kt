package com

import kotlinx.collections.immutable.*
//import net.sf.ehcache.CacheManager
//import net.sf.ehcache.Element
import org.mapdb.DBMaker
import org.mapdb.HTreeMap
import org.mapdb.Serializer
import java.util.concurrent.Executors

import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Store based on byte[]. In this mode data are serialized and stored into 1MB large byte[].
 * Technically this is still on-heap, but is not affected by GC overhead, since data are not visible to GC.
 * This mode is recommended by default, since it does not require any additional JVM settings.
 */
//val dbMemory = DBMaker.memoryDB().make() //it stores on heap?
/**
 * Store based on DirectByteBuffer. In this case data are stored completely off-heap.
 * in 1MB DirectByteBuffers created with ByteBuffer.allocateDirect(size).
 * You should increase maximal direct memory with JVM parameter.
 */
val dbMemory = DBMaker.memoryDirectDB().make()
var executor = Executors.newScheduledThreadPool(2)
const val cacheName = "name_of_map"

//with 1G index 2 960 000
fun main() {
    mapDbFewCache()
//    mapDbSingleCache()
//    mapDb()

//    smth()
//    ehcache()
}

fun mapDbFewCache() {
    println("first")
    mapDbCheck("first")
    println("first key" + hTreeMap("first")["key1"])
    println("first key999999 " + hTreeMap("first")["key999999"])
    Thread.sleep(60*1000)
    println("first key999999 " + hTreeMap("first")["key999999"])
    println("second..")
    mapDbCheck("second")
    println("first key999999 " + hTreeMap("first")["key999999"])
    println("second key" + hTreeMap("second")["key1"])
    println("third..")
    mapDbCheck("third")
    println("first key" + hTreeMap("third")["key1"])
    println("4..")
    mapDbCheck("4")
    println("first key" + hTreeMap("4")["key1"])
    println("5..")
    mapDbCheck("5")
    println("first key" + hTreeMap("5")["key1"])
}

private fun mapDbSingleCache() {
    println("cache mut")
//    mapDbCreate()
//    setIntoCache()
    mapDbCheck()
//    usualHashMap()
    println("finish at all")
    Thread.sleep(1 * 1 * 1000)
    println("take cache")
    val hTreeMap = dbMemory.get<Any?>(cacheName) as HTreeMap<*, *>
    Thread.sleep(10 * 1000)
    val any = hTreeMap["key"]
    println(any)
    Thread.sleep(1 * 10 * 1000)
}


private fun mapDbCreate() {
    val map: HTreeMap<String, String> = dbMemory.hashMap(cacheName)//ConcurrentMap
        .keySerializer(Serializer.STRING)
        .valueSerializer(Serializer.STRING)
        //        .expireStoreSize(10 * 1024 * 1024 * 1024)//with 10GB space limit
//        .expireStoreSize(5 * 1024 * 1024)//with 5mb space limit
        .expireMaxSize(128)
        .expireAfterCreate()
//        .expireAfterCreate(1, TimeUnit.MILLISECONDS)
        .expireExecutor(executor)
        .expireExecutorPeriod(1000)//5 sec
        .create()
    println("finish generate $map")
//    Thread.sleep(1 * 60 * 1000)
}

fun setIntoCache() {
    repeat(5_000_000) { i ->//10_000_000
        if (i % 10_000 == 0) {
            println("index $i")
//            Thread.sleep(100)
        }
        setToCache(i)
    }
}


private fun setToCache(i: Int) {
    val hTreeMap = hTreeMap()
    if (i % 10_000 == 0) {
//        println("index i $i in cache=${hTreeMap["key1"]}")
        println("index i ${i - 200} in cache=${hTreeMap["key${i - 200}"]?.length ?: ""}")
    }
//    hTreeMap.clearWithExpire()
    hTreeMap["key$i"] =
        "$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()"
}
@Suppress("UNCHECKED_CAST")
private fun hTreeMap(cacheId: String = cacheName): HTreeMap<String, String> {
    val hTreeMap = dbMemory.get<Any?>(cacheId) as HTreeMap<String, String>
    return hTreeMap
}

private fun usualHashMap() {
    val map: MutableMap<String, String> = HashMap()
    generate(map)
}


private fun mapDbCheck(cacheId: String = cacheName) {
    val map: HTreeMap<String, String> = dbMemory.hashMap(cacheId)//ConcurrentMap
        .keySerializer(Serializer.STRING)
        .valueSerializer(Serializer.STRING)
        .expireStoreSize(50 * 1024 * 1024)//with 5mb space limit
        .expireAfterGet(1, TimeUnit.MINUTES)
        .expireAfterCreate()
        .create()
    generate(map)
    println("finish generate")
//    Thread.sleep(1 * 60 * 1000)
}

private fun generate(map: MutableMap<String, String>) {
    map["key"] = "132L"
    repeat(1_000_000) { i ->//10_000_000
        if (i % 10_000 == 0) println("index $i")
        map["key$i"] =
            "$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()"
    }
    map["key"]
    println("completed")
    println(map["key"])
}

private fun mapDb() {
    Thread.sleep(10 * 1000)
    val dbMemory = DBMaker.memoryDB().make()
    val map: HTreeMap<String, Long> = dbMemory.hashMap("name_of_map")//ConcurrentMap
        .keySerializer(Serializer.STRING)
        .valueSerializer(Serializer.LONG)
        .create()
    map["key"] = 132L
    repeat(10_000_000) { i ->
        map["key$i"] = i.toLong()
    }
    map["key"]
    println(map["key"])
    Thread.sleep(3 * 60 * 1000)
}

private fun smth() {
    val persistentList: PersistentList<Long> = persistentListOf(0L, 93L)
    persistentList.let {

    }
}

//private fun ehcache() {
//    val manager = CacheManager.create()
//    val cache = manager.getCache("squareCache")
//    println(cache)
//    cache.put(Element("key1", "value1"))
//    val element = cache["key1"]
//    println(element.objectValue)
//    println()
//}
