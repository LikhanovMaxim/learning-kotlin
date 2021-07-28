package com

import org.mapdb.DBMaker
import org.mapdb.HTreeMap
import org.mapdb.Serializer


fun main() {
    println("hello from database examples")
    dbXodusDNQ(arrayOf("some text"))
}

class Plugin {

    val dbMap = DBMaker.fileDB("filePlugin.db")
        .closeOnJvmShutdown()
        .make()

    private var probesDb: HTreeMap.KeySet<Any> = dbMap.hashSet("probes")
        .serializer(Serializer.JAVA)
        .createOrOpen()

    fun addAndGetElement() {
        probesDb.add("asdfasf")
        println(probesDb.first())
    }

    fun finally() {
        println("close db")
        dbMap.close()
    }

}
