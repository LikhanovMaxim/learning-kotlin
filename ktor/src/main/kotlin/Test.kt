import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.pipeline.*
import io.ktor.utils.io.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlin.reflect.jvm.jvmErasure

@Serializable
sealed class Message {
    abstract val content: String
}

@Serializable
data class BroadcastMessage(override val content: String) : Message()

@Serializable
data class DirectMessage(override val content: String, val recipient: String) : Message()

//@Serializable
//sealed class WithStatusCode {
//    abstract val code: Int
//}
//
//@Serializable
//@SerialName("StatusMessageResponse")
//data class StatusMessageResponse(
//    override val code: Int,
//    val message: String
//) : WithStatusCode()
//
//@Serializable
//@SerialName("StatusResponse")
//data class StatusResponse(
//    override val code: Int,
//    val data: JsonElement
//) : WithStatusCode()

interface WithStatusCode {
    val code: Int
}

@Serializable
data class StatusMessageResponse(
    override val code: Int,
    val message: String
) : WithStatusCode

@Serializable
data class StatusResponse(
    override val code: Int,
    val data: JsonElement
) : WithStatusCode


val data: List<Message> = listOf(
    DirectMessage("Hey, Joe!", "Joe"),
    BroadcastMessage("Hey, all!")
)

val data2 = listOf(
    StatusMessageResponse(
        code = 404,
        message = "Not found"
    ),
    StatusResponse(
        code = 200,
        data = Json.encodeToJsonElement(BroadcastMessage.serializer(), BroadcastMessage("Hey, all!"))
    )
)


@Serializable
sealed class Response

@Serializable
@SerialName("empty")
object EmptyResponse : Response()

@Serializable
@SerialName("Response")
class TextResponse(val text: String) : Response()


interface Project {
    val name: String
}

@Serializable
@SerialName("owned")
class OwnedProject(override val name: String, val owner: String) : Project

@Serializable
@SerialName("new")
class NewProject(override val name: String, val isNew: Boolean) : Project

@OptIn(KtorExperimentalLocationsAPI::class)
fun main() {
    embeddedServer(Netty, port = 8072, host = "localhost") {
        install(ContentNegotiation) {
            json()
            register(ContentType.Any, EmptyContentConverter)
        }
        install(Locations)
        routing {
            get("/") {
                kotlin.runCatching { call.respond(HttpStatusCode.OK, data2) }.onFailure {
                    it.printStackTrace()
                }
            }
            get("/exceptionGeneric") {
                kotlin.runCatching { call.respond(HttpStatusCode.OK, data2 as List<Any>) }.onFailure {
                    it.printStackTrace()
                }//exception
            }
            get("/sealed") {
                kotlin.runCatching { call.respond(HttpStatusCode.OK, listOf(EmptyResponse, TextResponse("OK"))) }
                    .onFailure {
                        it.printStackTrace()
                    }
            }
            get("/mapOf") {
                kotlin.runCatching { call.respond(HttpStatusCode.OK, mapOf("smth" to "asd", "dddd" to "dddd")) }
                    .onFailure {
                        it.printStackTrace()
                    }
            }
            post("/postObject") {
                val customer = call.receive<Customer>()
                println(customer)
                call.respond(HttpStatusCode.OK)
            }
            post("/postMap") {
                val params = call.receive<Map<String, String>>()
                println(params)
                call.respond(HttpStatusCode.OK)
            }
            get<ApiRoot.Version> {
                println("version")
                call.respond(HttpStatusCode.OK)
            }
            get<ApiRoot.AgentParameters> { params ->
                val agentId = params.agentId
                println("agentId $agentId")
                call.respond(HttpStatusCode.OK)
            }
            patch<ApiRoot.AgentParameters> { params ->
                val agentId = params.agentId
                println("agentId $agentId")
                call.respond(HttpStatusCode.OK)
            }
            get("/interface") {
                kotlin.runCatching {
                    call.respond(
                        HttpStatusCode.OK, listOf(
                            OwnedProject("kotlinx.coroutines", "kotlin"),
                            NewProject("kotlinx.coroutines.new", false)
                        )
                    )
                }.onFailure {
                    it.printStackTrace()
                }
            }
            get("/jsonElement") {
                kotlin.runCatching {
//                    call.respond(
//                        HttpStatusCode.OK, listOf(
//                            WithStatusCode.serializer() toJson StatusMessageResponse(
//                                code = 404,
//                                message = "Not found"
//                            ),
//                            WithStatusCode.serializer() toJson StatusResponse(
//                                code = 200,
//                                data = Json.encodeToJsonElement(BroadcastMessage.serializer(), BroadcastMessage("Hey, all!"))
//                            )
//                        )
//                    )
                }.onFailure {
                    it.printStackTrace()
                }
            }
            get("/jsonElement2") {
                kotlin.runCatching {
//                    call.respond(
//                        HttpStatusCode.OK, listOf(
//                            WithStatusCode.serializer() toJson StatusMessageResponse(
//                                code = 404,
//                                message = "Not found"
//                            ),
//                            WithStatusCode.serializer() toJson StatusResponse(
//                                code = 200,
//                                data = Json.encodeToJsonElement(BroadcastMessage.serializer(), BroadcastMessage("Hey, all!"))
//                            )
//                        )
//                    )
                }.onFailure {
                    it.printStackTrace()
                }
            }
        }
    }.start(wait = true)
}

@Serializable
data class Customer(
    val name: String,
    val parameters: Map<String, String>
)

val json = Json { encodeDefaults = true }
infix fun <T> KSerializer<T>.toJson(rawData: T): JsonElement = json.encodeToJsonElement(this, rawData)

private object EmptyContentConverter : ContentConverter {
    override suspend fun convertForReceive(
        context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>
    ): Any? {
        val request = context.subject
        val readChannel = request.value as? ByteReadChannel
        val discarded = readChannel?.discard()
        return Unit.takeIf { discarded == 0L && request.typeInfo.jvmErasure == it::class }
    }

    override suspend fun convertForSend(
        context: PipelineContext<Any, ApplicationCall>,
        contentType: ContentType,
        value: Any
    ): Any? = value
}
