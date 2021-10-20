package com

import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import javax.persistence.*


fun main() {
    println("hello")

    HibernateUtil.sessionFactory?.openSession()?.use {
        val transaction = it.beginTransaction()
        it.save(Person(12, "Tomas"))
        transaction.commit()
    }

    HibernateUtil.sessionFactory?.openSession()?.use {
        val list = it.createQuery("from Person", Person::class.java).list()
        println(list.first().name)
        println("size = ${list.size}")
    }

}

@Entity
@Table(name = "person")
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
//                    val configFile = "hibernate.properties"
                    val standardServiceRegistryBuilder1 = StandardServiceRegistryBuilder()
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
