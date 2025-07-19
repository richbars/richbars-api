package br.richbars.app.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Value("\${redis.url}")
    private lateinit var redisUrl: String

    @Value("\${redis.port}")
    private var redisPort: Int = 0

    @Value("\${redis.username:}")
    private var redisUsername: String? = null

    @Value("\${redis.password}")
    private lateinit var redisPassword: String

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val config = RedisStandaloneConfiguration(redisUrl, redisPort)
        if (!redisUsername.isNullOrBlank()) {
            config.username = redisUsername
        }
        config.password = RedisPassword.of(redisPassword)
        return LettuceConnectionFactory(config)
    }

    @Bean
    fun redisTemplate(connectionFactory: LettuceConnectionFactory): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.setConnectionFactory(connectionFactory)
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()
        template.afterPropertiesSet() // importante para inicializar o template
        return template
    }

}
