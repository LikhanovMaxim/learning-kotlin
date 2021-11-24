package com

import kotlinx.serialization.Serializable
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import java.sql.DriverManager
import java.util.*
import javax.persistence.*

fun main() {
    println("hello posgresql")

    jdbcConnect()

//    hibernate()
//    doInHib
}

private fun hibernate() {
    HibernateUtil.sessionFactory?.openSession()?.use {
        val list = it.createQuery("from Person", Person::class.java).list()
        println(list.first().name)
        println("size = ${list.size}")
    }
}

private fun jdbcConnect() {
    //    Database.connect(
//        "jdbc:postgresql://localhost:5432/postgres", driver = "org.postgresql.Driver",
//        user = "postgres", password = "admin123"
//    )
    val url = "jdbc:postgresql://localhost:5432/postgres"
    val props = Properties()
    props.setProperty("user", "postgres")
    props.setProperty("password", "mysecretpassword")
//    props.setProperty("ssl", "true")
    val con = DriverManager.getConnection(url, props)
    val st = con.createStatement()
    val rs = st.executeQuery("select data from table_json")

    val json = rs.next()
    println(json)
    val message = rs.getString(1)
    println(message)
//    Json.
    rs.close()
    st.close()
}

@Serializable
data class ExampleJson(
    val name: String,
    val count: Int
)

@Entity
class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String
)


object HibernateUtil {
    private var registry: StandardServiceRegistry? = null
    // Create registry

    // Create MetadataSources

    // Create Metadata

    // Create SessionFactory
    var sessionFactory: SessionFactory? = null
        get() {
            if (field == null) {
                try {
                    // Create registry
                    val standardServiceRegistryBuilder1 = StandardServiceRegistryBuilder()
//                    val configFile = "hibernate.properties"
//                    val standardServiceRegistryBuilder: StandardServiceRegistryBuilder = standardServiceRegistryBuilder1.loadProperties(configFile)
                    //use hibernate.cfg.xml
                    val standardServiceRegistryBuilder: StandardServiceRegistryBuilder =
                        standardServiceRegistryBuilder1.configure()

//                    StandardServiceRegistryBuilder().configure().addService(Person::class.java)
//                    standardServiceRegistryBuilder.addService(Person::class.java)
//                    standardServiceRegistryBuilder.addAn
                    registry = standardServiceRegistryBuilder.build()

                    // Create MetadataSources
                    val sources = MetadataSources(registry)

                    // Create Metadata
                    val metadata = sources.metadataBuilder.build()

                    // Create SessionFactory
                    field = metadata.sessionFactoryBuilder.build()
                } catch (e: Exception) {
                    e.printStackTrace()
                    if (registry != null) {
                        StandardServiceRegistryBuilder.destroy(registry)
                    }
                }
            }
            return field
        }
        private set

    fun shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry)
        }
    }
}
