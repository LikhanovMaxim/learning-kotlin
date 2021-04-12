package com

import kotlin.test.Test
import kotlin.test.assertEquals

class HelloKtTest {
    //todo add asserts

    @Test
    fun `code  from another module`() {
        val a = SuperUtil()
        assertEquals(100, a.sqrt2(10))
    }

    @Test
    fun `check null`() {
        val finalizedTests: String? = "344012jk"
        val typedTest = "dddd"
        finalizedTests?.contains(typedTest).let { println("lol") }
        println("kek")
    }

    @Test
    fun `transform functions `() {
        val colors = listOf("red", "brown", "grey")
        val animals = listOf("fox", "bear", "wolf")

        val transform: (a: String, b: String) -> String = { color, animal -> "The ${animal.capitalize()} is $color" }
        val message = colors.zip(animals, transform)
        val message2 = colors.zip(animals) { color, animal -> "The ${animal.capitalize()} is $color" }
        println(message)
        println(message2)
    }

    @Test
    fun `replace string`() {
        val destination = "/scope/{scopeId}/test"
        val res2 = changeParamToValue(destination)
        assertEquals("/scope/123/test", res2)
    }

    @Test
    fun `replace string - two value`() {
        val destination = "/scope/{scopeId}/test/{testId}"
        val res2 = changeParamToValue(destination)
        assertEquals("/scope/123/test/77", res2)
    }

    @Test
    fun `lazy cases`() {
        println(test1("ads"))
        println(test2("ads"))

        println("lazy:")
        println(lazyValue)
        println(lazyValue)
    }

    @Test
    fun `takeIf block`() {
        val test = "short"
        val testLong = "short5555555555555555555555555"
        assertEquals(test, takeIfCheckSize(test))
        assertEquals("too long string", takeIfCheckSize(testLong))
    }

    @Test
    fun `takeUnless block`() {
        val test = "short"
        val testLong = "short5555555555555555555555555"
//        assertEquals(test, takeUnlessCheckSize(test))
//        assertEquals("too long string", takeUnlessCheckSize(testLong))
        assertEquals("too short string", takeUnlessCheckSize(test))
        assertEquals(testLong, takeUnlessCheckSize(testLong))
    }

    @Test
    fun `syntax Run`() {
        syntaxRun()
    }

    @Test
    fun `boolean cases`() {
        println(smthBoolean("asd"))
        println(smthBoolean(null))
    }

    @Test
    fun `syntax ternary`() {
        syntaxTernary()
    }

    @Test
    fun `asd `() {
        val str = """
        str=b,str2=c
    """.trimIndent()

        println("starting...")
        val asAgentParams = str.asAgentParams()
        println(asAgentParams)
    }

    @Test
    fun `sda asd`() {
        val str = """
            drillInstallationDir=myVersion/
            adminAddress=localhost:8090
            agentId=Petclinic
            buildVersion=0.1.0
            logLevel=DEBUG
        """.trimIndent()

        val asAgentParams = str.asAgentParams("\n", "#")
        println(asAgentParams)

    }


    fun String?.asAgentParams(): Map<String, String> {
        if (this.isNullOrEmpty()) return mutableMapOf()
        return try {
            this.split(",")
                .associate {
                    val (key, value) = it.split("=")
                    key to value
                }
        } catch (parseException: Exception) {
            throw IllegalArgumentException("wrong agent parameters: $this")
        }
    }

    fun String?.asAgentParams(
        lineDelimiter: String = ",",
        removeStartWith: String = "",
        mapDelimiter: String = "=",
    ): Map<String, String> {
        if (this.isNullOrEmpty()) return mutableMapOf()
        return try {
            val split = this.split(lineDelimiter)
            println("split=$split")
            val mapNotNull = split
                .mapNotNull {
                    if (removeStartWith.isNotBlank() && it.startsWith(removeStartWith)) {
                        null
                    } else it
                }
            println("mapNotNull=$mapNotNull")
            mapNotNull.associate {
                val (key, value) = it.split(mapDelimiter)
                val pair = key to value
                println("pair $it =>$pair")
                pair
            }
        } catch (parseException: Exception) {
            throw IllegalArgumentException("wrong agent parameters: $this")
        }
    }

}
