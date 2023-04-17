package ru.quipy.user.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import ru.quipy.cart.api.CartAggregate
import ru.quipy.cart.logic.CartAggregateState
import ru.quipy.core.EventSourcingService
import ru.quipy.user.api.UserAggregate
import ru.quipy.user.api.UserRegisterDTO
import ru.quipy.user.logic.UserAggregateState
import ru.quipy.user.service.UserMongo
import ru.quipy.user.service.UserRepository
import java.util.*


@RestController
@RequestMapping()
class UserAuthController (
        val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>,
        val usersRepository: UserRepository,
        val passwordEncoder: BCryptPasswordEncoder,
        val cartESService: EventSourcingService<UUID, CartAggregate, CartAggregateState>
) {
    @PostMapping("/signUp")
    fun registration(@RequestBody request: UserRegisterDTO): ResponseEntity<Any> {
        if (usersRepository.findOneByEmail(request.email) != null) {
            return ResponseEntity<Any>(null, HttpStatus.CONFLICT)
        }
        val cartId = cartESService.create { it.createNewCart() }.cartId
        val userId = userEsService.create { it.createUser() }.userId
        userEsService.update(userId){it.createNewCart(userId, cartId)}
        val user = usersRepository.save(UserMongo(
                email = request.email,
                password =  passwordEncoder.encode(request.password),
                aggregateId = userId,
                role = "user"
        ))
        return ResponseEntity.ok("UserId: $userId, CartId: $cartId")
    }
}