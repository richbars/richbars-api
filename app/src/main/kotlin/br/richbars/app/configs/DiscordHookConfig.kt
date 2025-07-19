import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

data class DiscordWebhookPayload(val content: String)

class DiscordHookConfig {

    private val client = WebClient.create()

    suspend fun sendDiscordWebhook(webhookUrl: String, message: String) {
        val payload = DiscordWebhookPayload(content = message)

        val response = client.post()
            .uri(webhookUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .retrieve()
            .bodyToMono(String::class.java)
            .awaitSingleOrNull()

        println("✅ Mensagem enviada com sucesso: $response")
    }
}
