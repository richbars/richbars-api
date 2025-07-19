package br.richbars.app.repository

import br.richbars.app.model.User
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class UserRepository(private val reactiveMongoTemplate: ReactiveMongoTemplate) {

    fun findAll(): Flux<User> = reactiveMongoTemplate.findAll(User::class.java)

    fun findById(id: String): Mono<User> {
        val query = Query(Criteria.where("id").`is`(id))
        return reactiveMongoTemplate.findOne(query, User::class.java)
    }

    fun save(user: User): Mono<User> {
        return reactiveMongoTemplate.save(user)
    }

    fun deleteById(id: String): Mono<Void> {
        val query = Query(Criteria.where("id").`is`(id))
        return reactiveMongoTemplate.remove(query, User::class.java).then()
    }
}
