package com

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HelloKtTest {
    @Test
    fun `first test`() {
        assertEquals(2, 2)
    }

    @Test
    fun `asd `(){
        val str = """
        str=b,str2=c
    """.trimIndent()

        println("starting...")
        val asAgentParams = str.asAgentParams()
        println(asAgentParams)
    }

    @Test
    fun `sda asd`(){
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
        mapDelimiter: String = "="
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
