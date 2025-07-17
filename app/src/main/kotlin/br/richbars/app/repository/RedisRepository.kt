package br.richbars.app.repository

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun saveEventGame(keyPrefix: String, id: String, value: String, ttlHours: Duration) {
        val key = "$keyPrefix:$id"
        try {
            redisTemplate.opsForValue().set(key, value, ttlHours.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS)
            logger.info("Evento salvo com sucesso: $key com TTL de $ttlHours")
        } catch (ex: Exception) {
            logger.error("Erro ao salvar evento '$id' com TTL: ${ex.message}", ex)
        }
    }
}
