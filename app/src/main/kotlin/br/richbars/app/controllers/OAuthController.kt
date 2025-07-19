package br.richbars.app.controllers

import br.richbars.app.dto.CreateUserRequest
import br.richbars.app.model.User
import br.richbars.app.repository.UserRepository
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/oauth")
class OAuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/users")
    fun createUser(@Valid @RequestBody request: CreateUserRequest): Mono<ResponseEntity<Void>> {

        val hashedPassword = passwordEncoder.encode(request.password)

        val user = User(
            username = request.username,
            password = hashedPassword,
            roles = request.roles
        )
        return userRepository.save(user)
            .map { ResponseEntity.status(HttpStatus.CREATED).build() }
    }

}