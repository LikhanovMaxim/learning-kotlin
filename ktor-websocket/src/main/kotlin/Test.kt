import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import mu.KotlinLogging
import secure.JwtConfig
import secure.User
import secure.jwtLifetime

@Serializable
data class ExampleJson(
    val str: String = "strwqe",
    val integer: Int = 42,
)

/**
 * https://ktor.io/docs/websocket.html#installing
 *
 */
val wsSessions = mutableListOf<DefaultWebSocketServerSession>()
private val logger = KotlinLogging.logger {}
val testUser = User(1, "guest", "", "admin")

fun main() {
    embeddedServer(Netty, port = 8073, host = "localhost") {
        install(ContentNegotiation) {
            json()
        }
        install(WebSockets)
        install(Authentication) {
            jwt {
                skipWhen { applicationCall ->
                    applicationCall.request.headers["Referer"]?.contains("openapi.json") ?: false
                }
                realm = "test app"
                verifier(JwtConfig.verifier)
                skipWhen { call -> call.request.headers["no-auth"]?.toBoolean() ?: false }
                validate {
                    testUser
                }
            }
        }
        routing {
            get("/") {
                call.respond(HttpStatusCode.OK, "hello from ktor")
            }
            get("/json") {
                call.respond(HttpStatusCode.OK, ExampleJson())
            }
            /**
             * Usage as an suspend actor
             */
            webSocket("/ws/suspend-actor") { // websocketSession
                suspendActorRepeate()
            }
            get("/send-event/{message}") {
                val message = call.parameters["message"]
                val messageToWs = "fasfas it asd $message"
                wsSessions.forEach {
                    logger.info { "session ${it.hashCode()} $message" }
                    it.send("some messages")
                    it.outgoing.send(Frame.Text(messageToWs))
                }
                call.respond(HttpStatusCode.OK, "even is sent. Message $messageToWs")
            }
            get("/flush-sessions") {
                wsSessions.clear()
                call.respond(HttpStatusCode.OK, "sessions size ${wsSessions.size}")
            }
            get("/blocking") {
                runBlocking {
                    wsSessions.clear()
                    logger.info { "delay..." }
                    delay(10_000)
                    logger.info { "finish delay." }
                    call.respond(HttpStatusCode.OK, "sessions size ${wsSessions.size}")
                }
            }
            get("/calc/{count}") {
                val count = call.parameters["count"]?.toInt() ?: 100_000
//                var sum = ""
//                for (i in 1..1000) {
//                    sum += "i" + i + 1
//                }
                val map = mutableMapOf<String, String>()
                repeat(count) { i ->//10_000_000
                    if (i % 10_000 == 0) println("index $i")
                    map["key$i"] =
                        "$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()$i.toLong()"
                }
                call.respond(HttpStatusCode.OK, "sum ${map.size}")
            }
            webSocket("/ws/my-websocket") {
//                val wsSession = WebSocketServerSession
                val session: DefaultWebSocketServerSession = this
                wsSessions.add(session)
                logger.info { "$ my-websocket acquired session ${session.hashCode()}" }
                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            outgoing.send(Frame.Text("YOU SAID: $text"))
                            if (text.equals("bye", ignoreCase = true)) {
                                close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                            }
                        }
                    }
                }

            }
            authWebSocket("/ws/auth") {
                suspendActorRepeate()
            }
            get("/getToken") {
                val token = JwtConfig.makeToken(jwtLifetime)
                call.response.header(HttpHeaders.Authorization, token)
                call.respond(HttpStatusCode.OK, JWT(token))
            }
//                incoming.consumeEach { frame ->
//                    val json = (frame as Frame.Text).readText()
//                    val event = WsReceiveMessage.serializer() parse json
////                    logger.debug { "Receiving event $event" }
//
//                    val destination = event.destination
//                    when (event) {
//                        is Subscribe -> {
//                            sessionStorage.subscribe(destination, session)
//                            val message = wsTopic.resolve(destination)
//                            val messageToSend = message.toWsMessageAsString(destination, WsMessageType.MESSAGE)
//                            session.send(messageToSend)
//                            logger.debug { "$destination is subscribed" }
//                        }
//
//                        is Unsubscribe -> {
//                            if (sessionStorage.unsubscribe(destination, session)) {
//                                logger.debug { "$destination is unsubscribed" }
//                            }
//                        }
//                    }
//                }

        }
    }.start(wait = true)
}

private suspend fun DefaultWebSocketServerSession.suspendActorRepeate() {
    for (frame in incoming) {
        when (frame) {
            is Frame.Text -> {
                val text = frame.readText()
                outgoing.send(Frame.Text("YOU SAID: $text"))
                if (text.equals("bye", ignoreCase = true)) {
                    close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                }
            }
        }
    }
}

@Serializable
private data class JWT(val token: String)
