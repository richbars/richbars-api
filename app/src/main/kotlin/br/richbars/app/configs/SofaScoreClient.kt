package br.richbars.app.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class SofaScoreClient {

    @Bean
    fun sofaClient(): WebClient {
        val httpClient = HttpClient.create()

        return WebClient.builder()
            .baseUrl("https://www.sofascore.com")
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .codecs { config ->
                config.defaultCodecs().maxInMemorySize(10 * 1024 * 1024) // 10 MB
            }
            .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0")
            .defaultHeader(HttpHeaders.ACCEPT, "application/json")
            .build()
    }

}