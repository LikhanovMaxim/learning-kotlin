package com

import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.*
import kotlinx.dnq.query.firstOrNull
import kotlinx.dnq.query.iterator
import kotlinx.dnq.store.container.StaticStoreContainer
import kotlinx.dnq.util.initMetaData
import org.joda.time.DateTime
import org.mapdb.DBMaker
import org.mapdb.HTreeMap
import org.mapdb.Serializer
import java.io.File


fun main() {
    println("hello from database examples")
    dbXodusDNQ(arrayOf("some text"))
}


class Plugin() {

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

// Define persistent class. It should extend XdEntity
class XdPost(entity: Entity) : XdEntity(entity) {
    //  and have component object of type XdEntityType
    companion object : XdNaturalEntityType<XdPost>()

    // Define persistent property of type org.joda.time.DateTime?
    var publishedAt by xdDateTimeProp()

    // Define required persistent property of type String
    var text by xdRequiredStringProp()
}

class XdBlog(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdBlog>()

    // Define multi-value link to XdPost
    val posts by xdLink0_N(XdPost)
}

fun dbXodusDNQ(args: Array<String>) {
    // Register persistent classes
    XdModel.registerNodes(XdPost, XdBlog)

    // Initialize Xodus persistent storage
    val xodusStore = StaticStoreContainer.init(
        dbFolder = File(System.getProperty("user.home"), ".xodus-dnq-blog-db"),
        environmentName = "db"
    )

    // Initialize Xodus-DNQ metadata
    initMetaData(XdModel.hierarchy, xodusStore)

    // Do in transaction
    val blog = xodusStore.transactional {
        // Find an existing blog in database
        XdBlog.all().firstOrNull()
        // or create a new one if there are no blogs yet
            ?: XdBlog.new()
    }

    xodusStore.transactional {
        // Create new post
        val post = XdPost.new {
            this.publishedAt = DateTime.now()
            this.text = args.firstOrNull() ?: "Empty post"
        }

        // Add new post to blog
        blog.posts.add(post)
    }

    // Do in read-only transaction
    xodusStore.transactional(readonly = true) {
        // Print all blog posts
        for (post in blog.posts) {
            println("${post.publishedAt}: ${post.text}")
        }
    }
}
