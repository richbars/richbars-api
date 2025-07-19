package br.richbars.app.configs

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory

@Configuration
class MongoReactiveConfig {

    @Value("\${spring.data.mongodb.host}")
    lateinit var host: String

    @Value("\${spring.data.mongodb.port}")
    var port: Int = 27017

    @Value("\${spring.data.mongodb.username}")
    lateinit var username: String

    @Value("\${spring.data.mongodb.password}")
    lateinit var password: String

    @Value("\${spring.data.mongodb.database}")
    lateinit var database: String

    @Value("\${spring.data.mongodb.authentication-database}")
    lateinit var authDb: String

    @Bean
    fun reactiveMongoClient(): MongoClient {
        val connectionString = "mongodb://${username}:${password}@${host}:${port}/${database}?authSource=${authDb}"
        return MongoClients.create(connectionString)
    }

    @Bean
    fun reactiveMongoDatabaseFactory(): ReactiveMongoDatabaseFactory {
        return SimpleReactiveMongoDatabaseFactory(reactiveMongoClient(), database)
    }

    @Bean
    fun reactiveMongoTemplate(): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(reactiveMongoDatabaseFactory())
    }
}
