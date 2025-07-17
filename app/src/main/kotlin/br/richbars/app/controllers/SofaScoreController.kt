package br.richbars.app.controllers

import br.richbars.app.dto.SofaScoreResponse
import br.richbars.app.repository.RedisRepository
import br.richbars.app.services.SofaScoreService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}