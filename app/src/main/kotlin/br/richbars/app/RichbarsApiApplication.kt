package br.richbars.app

import br.richbars.app.configs.RedisConfig
import br.richbars.app.repository.RedisRepository
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@SpringBootApplication
class RichbarsApiApplication

fun main(args: Array<String>) {
	runApplication<RichbarsApiApplication>(*args)
}

// Componente para usar o RedisTemplate via injeção de dependência
@Component
class RedisExample(
	private val redisRepository: RedisRepository
) {

	@PostConstruct
	fun runExample() {
		redisRepository.saveEventGame("Save", "123", "valor", Duration.ofHours(1))
	}
}
