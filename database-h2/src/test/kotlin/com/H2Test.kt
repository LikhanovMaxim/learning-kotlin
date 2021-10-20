package com

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase
import org.hibernate.testing.transaction.TransactionUtil.doInHibernate
import org.junit.Assert.assertTrue
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*
import java.util.function.Consumer
import kotlin.test.Test
import kotlin.test.assertTrue

class H2Test : BaseCoreFunctionalTestCase() {

    private val logger = LoggerFactory.getLogger(H2Test::class.java)

    private val properties: Properties
        @Throws(IOException::class)
        get() {
            val properties = Properties()
            properties.load(javaClass.classLoader.getResourceAsStream("hibernate.properties"))
            return properties
        }

    override fun getAnnotatedClasses(): Array<Class<*>> {
        return arrayOf(Person::class.java)
//        return arrayOf(Person::class.java, Address::class.java, PhoneNumber::class.java)
    }

    lateinit var buildSessionFactory: SessionFactory
    override fun configure(configuration: Configuration) {
        super.configure(configuration)
        buildSessionFactory = configuration.buildSessionFactory()
        configuration.properties = properties
    }

    @Test
    fun givenPerson_whenSaved_thenFound() {
        val configuration = Configuration()
        configuration.addAnnotatedClass(Person::class.java)
        configuration.properties = properties
//        doInHibernate()
        buildSessionFactory = configuration.buildSessionFactory()

//        val value: (Session) -> Unit = { session ->
//            val personToSave = Person(0, "John")
//            session.persist(personToSave)
//            val personFound = session.find(Person::class.java, personToSave.id)
//            session.refresh(personFound)
//
//            assertTrue(personToSave.name == personFound.name)
//        }
//        val smth = null
        logger.debug("asdasd")


//        val b: Consumer<Session> = Consumer { session ->
//            val personToSave = Person(0, "John")
//            session.persist(personToSave)
//            val personFound = session.find(Person::class.java, personToSave.id)
//            session.refresh(personFound)
//
//            assertTrue(personToSave.name == personFound.name)
//        }
//        doInHibernate({ this.sessionFactory() }, b)


//        val function: Consumer<Session> = s -> s.
//        val sessionFactory: SessionFactoryImplementor = this.sessionFactory()
//        val function: () -> SessionFactory = { sessionFactory.openSession() }
        doInHibernate({ buildSessionFactory }, Consumer { session ->
            val personToSave = Person(0, "John")
            session.persist(personToSave)
            val personFound = session.find(Person::class.java, personToSave.id)
            session.refresh(personFound)

            assertTrue(personToSave.name == personFound.name)
        })

    }

    @Test
    fun `init test`() {

        println("sad")
//        logger.debug("asdasd")
        assertTrue { true }

        val list = arrayListOf<String>("asd", "das")
        val action: (String) -> Unit = { it -> it + "d" }
        list.forEach(action)
        list.forEach(Consumer { it -> it + "d" })
    }
}
