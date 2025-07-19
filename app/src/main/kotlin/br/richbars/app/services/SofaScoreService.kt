package br.richbars.app.services

import br.richbars.app.configs.StructuredLogger
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class SofaScoreService(private val sofaClient: WebClient) {

    private val logger = StructuredLogger.of<SofaScoreService>()

    suspend fun getEventsLive(): String {
        logger.info("Chamando API da SofaScore /api/v1/sport/football/events/live", "statusCode" to 200)

        return sofaClient.get()
            .uri("/api/v1/sport/football/events/live")
            .retrieve()
            .bodyToMono(String::class.java)
            .awaitSingle()
    }
}
