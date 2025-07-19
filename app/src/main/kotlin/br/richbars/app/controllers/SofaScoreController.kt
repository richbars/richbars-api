package br.richbars.app.controllers

import DiscordHookConfig
import DiscordWebhookPayload
import br.richbars.app.dto.SofaScoreResponse
import br.richbars.app.repository.RedisRepository
import br.richbars.app.services.SofaScoreService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.logstash.logback.argument.StructuredArguments.entries
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sofascore")
class SofaScoreController(
    private val sofaScoreService: SofaScoreService,
    private val redisRepository: RedisRepository,
) {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/all")
    suspend fun getEvents(): SofaScoreResponse {
        val jsonString = sofaScoreService.getEventsLive()

        val eventResponse: SofaScoreResponse = objectMapper.readValue(jsonString)

        if (logger.isInfoEnabled) {
            logger.info("SofaScoreService retornou {} eventos (originais) ativos", eventResponse.events.size)
        }

        return SofaScoreResponse(eventResponse.events.size, eventResponse.events)
    }

    @PostMapping("/discord")
    suspend fun postDiscord(@RequestBody request: DiscordWebhookPayload): ResponseEntity<String> {
        return try {
            DiscordHookConfig().sendDiscordWebhook(
                "https://discord.com/api/webhooks/1391541172173930618/DNYzqXJqkda8zSg4I3xcV9Yv9Hmqq18tgIKtD-XVjzwl9u2wJ6juhqWFjndwM-NQghkX",
                request.content
            )
            val params = mapOf(
                "tipo" to "mensagem",
                "dia" to "sábado",
                "status" to "enviada"
            )
            logger.info("Ação realizada", entries(params))
            ResponseEntity.ok("Mensagem enviada com sucesso")
        } catch (e: Exception) {
            logger.error("Erro ao enviar para o Discord", e)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao enviar mensagem: ${e.message}")
        }
    }


}