import com.*
import kotlinx.serialization.*
import kotlinx.serialization.properties.*
import net.mamoe.yamlkt.*
import kotlin.test.*

@InternalSerializationApi
@ExperimentalSerializationApi
class HelloKtTest {
    @Test
    fun `should parse arg from agent options`() {
        val str = "configPath=myVersion/configFile.properties"
        val asAgentParams = str.asAgentParams()
        assertEquals(mapOf("configPath" to "myVersion/configFile.properties"), asAgentParams)
    }

    @Test
    fun `should be empty map if string is empty`() {
        val str: String? = null
        assertTrue { str.asAgentParams("\n", "#").isEmpty() }
    }

    @Test
    fun `should be parse string to map`() {
        val str = "id=string"
        val actual = str.asAgentParams("\n", "#")
        val expected = mapOf("id" to "string")
        assertEquals(expected, actual)
    }

    @Test
    fun `should be parse two lines to map`() {
        val str = """
            id=string
            int=2
        """.trimIndent()
        val actual = str.asAgentParams("\n", "#")
        val expected = mapOf("id" to "string", "int" to "2")
        assertEquals(expected, actual)
    }

    @Test
    fun `should skip comments when parse file`() {
        val str = """
            id=string
            #int=2
        """.trimIndent()
        val actual = str.asAgentParams("\n", "#")
        val expected = mapOf("id" to "string")
        assertEquals(expected, actual)
    }

    @Test
    fun `should skip empty element when parse file`() {
        val str = """
            drillInstallationDir=myVersion/
            
        """.trimIndent()

        val asAgentParams = str.asAgentParams("\n", "#")
        println(asAgentParams)
        val expected = mapOf("drillInstallationDir" to "myVersion/")
        assertEquals(1, asAgentParams.size)
        assertEquals(expected, asAgentParams)
    }

    @Test
    fun `should deserialize complex object from map`() {
        val input = mapOf("id" to "string", "anotherObject.kek" to "adsd")
        val objectFromMap = Properties.decodeFromMap(Conf.serializer(), input)
        assertEquals(Conf("string", AnotherObject("adsd")), objectFromMap)
    }

    @Test
    fun `should parse list to map`() {
        val str = "list=host1,host2,host3"
        val actual = str.asAgentParamsProperties()
        val expected = mapOf("list" to "host1,host2,host3")
        assertEquals(expected, actual)
    }

    @Test
    fun `should deserialize `(){

    }

    //Properties decodeFromMap
    @Test
    fun `should deserialize list from map`() {
        val input = mapOf("list" to "host1:host2:host3")
        val objectFromMap = Properties.decodeFromMap(ListConf.serializer(), input)
        val expected = listOf("host1", "host2", "host3")
        assertEquals(ListConf(list = "host1:host2:host3"), objectFromMap)
        println(objectFromMap.hostList)
        assertEquals(expected, objectFromMap.hostList)
    }

    @Test
    fun `smth with map`() {
        val map1 = mapOf("id" to "dasasd:asd22:asd3")
        val map2 = mapOf("second" to "dasasd")
        println(map1.plus(map2))
        println(map1.mapValues { it.value.replace(":", ",") })
    }



    @Test
    fun `should deserialize list with another delimiter from map`() {
        val input = mapOf("list" to "host1,host2,host3")
        val objectFromMap = Properties.decodeFromMap(ListConf.serializer(), input)
        val expected = listOf("host1", "host2", "host3")
        assertEquals(ListConf(list = "host1,host2,host3"), objectFromMap)
        println(objectFromMap.hostList)
        assertEquals(expected, objectFromMap.hostList)
    }

    //end properties, start another examples:
    @Test
    fun `should deserialize complex object YAML`() {
        val str = """
            id: "from YAML"
            #asfasc     
            anotherObject:
              kek: "asfasf"
        """.trimIndent()
//        val actual = str.asAgentParams()
//        val expected = mapOf("id" to "string", "anotherObject.kek" to "adsd")
//        println(Properties.decodeFromMap<String>(expected))
        val expected = Conf("from YAML", AnotherObject("asfasf"))
        val actual = Yaml.default.decodeFromString(Conf.serializer(), str)
        println(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun `should deserialize diff types from map`() {
        val str = """
            id=string from properties
            another=2
            isBool=true
            #db.password=password
            #afssaf
        """.trimIndent()
        val actual = str.asAgentParamsProperties()

        val expected: Map<String, Any> = mapOf("id" to "string", "another" to 2, "isBool" to true)
//        TODO can't cast
//        val expected = mapOf("id" to "string", "another" to "2", "isBool" to "true")
        println(Properties.decodeFromMap(ComplexProperties.serializer(), expected))
    }

    @Test
    fun `should cast to needed types`() {
        //todo how to do it??
        //
    }

    @Test
    fun `should create map diff types`() {
        val str = """
            id=string from properties
            another=2
            isBool=true
            #db.password=password
            #afssaf
        """.trimIndent()
        val actual = str.asAgentParamsProperties()

        val expected = mapOf("id" to "string", "another" to 2, "isBool" to true)
        println(Properties.decodeFromMap(ComplexProperties.serializer(), expected))
    }

    @Ignore
    @Test
    fun `should parse easy map`() {
        val input = mapOf("id" to "string", "anotherObject.kek" to "adsd")

        val parseAs = input.parseAs<Conf>()

        println(parseAs)
        val expected = Conf("from YAML", AnotherObject("asfasf"))
    }

    @Test
    fun `should `() {
        val splitAndFilter = "asd=asd,#ads=sadf".splitAndFilter()
        println(splitAndFilter)
    }



    //


    @Test
    fun `should parse version`(){
        val buildVersion = "gs-spring-boot-docker-0.1.0"
        val calcBv = calcBv("/deployments/tmp/src/$buildVersion.jar")
        println(calcBv)
        assertEquals(buildVersion, calcBv)
    }

    @Test
    fun `should parse version by env`(){
        val calcBv = calv()
        println("calcBv=$calcBv")
    }


}

@Serializable
data class Conf(
    val id: String,
    val anotherObject: AnotherObject?
)

@Serializable
data class AnotherObject(
    val kek: String
)

@Serializable
data class ListConf(
    val listDelimiter: String = ":",
    val list: String = "",
) {
    val hostList: List<String>
        get() = list.split(":", ",")

}


private fun String.splitAndFilter(
    lineDelimiter: String = ",",
    removeStartWith: String = "",
    mapDelimiter: String = "="
): Map<String, String> {
    return this.split(lineDelimiter)
        .mapNotNull {
            if (removeStartWith.isNotBlank() && it.startsWith(removeStartWith)) {
                null
            } else it
        }.associate {
            val (key, value) = it.split(mapDelimiter)
            val pair = key to value
            pair
        }
}
