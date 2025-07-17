package br.richbars.app.services

import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class SofaScoreService(private val sofaClient: WebClient) {

    suspend fun getEventsLive(): String {
        val logger = LoggerFactory.getLogger(this.javaClass)
        logger.info("Chamando API da SofaScore /api/v1/sport/football/events/live")

        return sofaClient.get()
            .uri("/api/v1/sport/football/events/live")
            .retrieve()
            .bodyToMono(String::class.java)
            .awaitSingle()
    }
}