import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        jackson()
    }

    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.localizedMessage)
        }
    }

    routing {
        get("/weather") {
            val weatherData = fetchWeatherData()
            call.respond(weatherData)
        }
    }
}

suspend fun fetchWeatherData(): Map<String, Any> {
    val client = HttpClient(CIO)
    val response: HttpResponse = client.get("https://api.open-meteo.com/v1/forecast?latitude=35.6895&longitude=139.6917&current_weather=true")
    val json = response.readText()
    return jacksonObjectMapper().readValue(json)
}
