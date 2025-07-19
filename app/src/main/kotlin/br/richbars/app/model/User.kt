package br.richbars.app.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "users")
data class User(
    @Id
    val id: String = UUID.randomUUID().toString(),  // gera UUIDv4 automaticamente
    val username: String,
    val password: String,
    val roles: List<String> = emptyList()
)
