import io.ktor.websocket.*
import kotlin.test.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.mapNotNull
import kotlinx.serialization.*

//todo fix test
class MyAppTest {
    @Test
    fun testConversation() {
//        withTestApplication {
//            application.install(WebSockets)
//
//            val received = arrayListOf<String>()
//            application.routing {
//                webSocket("/echo") {
//                    try {
//                        while (true) {
//                            val text = (incoming.receive() as Frame.Text).readText()
//                            received += text
//                            outgoing.send(Frame.Text(text))
//                        }
//                    } catch (e: ClosedReceiveChannelException) {
//                        // Do nothing!
//                    } catch (e: Throwable) {
//                        e.printStackTrace()
//                    }
//                }
//            }
//
//            handleWebSocketConversation("/echo") { incoming, outgoing ->
//                val textMessages = listOf("HELLO", "WORLD")
//                for (msg in textMessages) {
//                    outgoing.send(Frame.Text(msg))
//                    assertEquals(msg, (incoming.receive() as Frame.Text).readText())
//                }
//                assertEquals(textMessages, received)
//            }
//        }
    }
}
