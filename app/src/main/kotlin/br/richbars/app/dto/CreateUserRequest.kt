package br.richbars.app.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class CreateUserRequest(
    @field:NotBlank(message = "O username é obrigatório")
    val username: String,

    @field:NotBlank(message = "A senha é obrigatória")
    @field:Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    val password: String,

    @field:Size(min = 1, message = "Deve haver pelo menos uma role")
    val roles: List<String>
)
