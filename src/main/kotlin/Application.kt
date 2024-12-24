import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Hello, World!")
        }

        get("/weather") {
            val client = HttpClient(CIO)
            try {
                val response: String = runBlocking {
                    client.get("https://api.open-meteo.com/v1/forecast?latitude=35.6895&longitude=139.6917&current_weather=true")
                        .bodyAsText()
                }
                call.respondText("Weather Data: $response")
            } catch (e: Exception) {
                call.respondText("Failed to fetch weather data: ${e.localizedMessage}")
            } finally {
                client.close()
            }
        }
    }
}