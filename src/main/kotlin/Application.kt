import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(CORS) {
        allowHost("localhost:8000")
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Get)
    }

    routing {
        get("/") {
            call.respondRedirect("/weather")
        }

        get("/weather") {
            val client = HttpClient(CIO)
            try {
                val city = "Berkeley Heights"
                val encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString())
                val apiUrl = "https://wttr.in/Berkeley+Heights"

                val response: String = runBlocking {
                    client.get("https://wttr.in/Berkeley+Heights")
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