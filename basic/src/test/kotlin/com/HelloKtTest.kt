package com

import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class HelloKtTest {
    //todo add asserts

    @Test
    fun `inherited classes`() {
        val smth: WorkProbes = SimpleProbesImpl(10)
        smth.set(1, true)

//        val a = BitSet() //as WorkProbes
//        val bitSet = WorkProbesImpl(a)
//        bitSet.length()
    }


    @Test
    fun `delegation examples`() {
        val b = BaseImpl(10)
        Derived(b).print()
        val derived22 = Derived22(b)
        derived22.lol()
        derived22.print()
    }


    private fun BitSet.toBooleanArray(): BooleanArray {
        return BooleanArray(length()) {
            get(it)
        }
    }

    @Test
    fun `check bitSet`() {
        val bitSet = BitSet(5)
        bitSet.set(0, true)
        bitSet.set(2)
        bitSet.set(4)
        println(bitSet)
        val booleans = bitSet.toBooleanArray()
        println(booleans)
//        assertEquals(3, bitSet.size())
        assertEquals(5, booleans.size)
    }

    @Test
    fun `check bitSet length`() {
        val bitSet = BitSet(4)
        bitSet.set(0, true)
        bitSet.set(1)
        bitSet.set(3)
        assertEquals(4, bitSet.length())
    }


    @Test
    fun `check bitSet size`() {
        val bitSet = BitSet(4)
        val bitSet2 = BitSet(1_000_000)
        assertNotEquals(bitSet.size(), bitSet2.size())
    }

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
    @Test
    fun `second test`() {
        assertEquals(3, 2)
    }
    @Test
    fun `third test`() {
        assertTrue(3 == 2)
    }

}
