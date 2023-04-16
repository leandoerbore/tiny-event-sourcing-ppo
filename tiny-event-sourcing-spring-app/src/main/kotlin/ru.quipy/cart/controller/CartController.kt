package ru.quipy.cart.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.quipy.cart.api.AddItemsDTO
import ru.quipy.cart.api.CartAggregate
import ru.quipy.cart.logic.CartAggregateState
import ru.quipy.catalog.api.CatalogAggregate
import ru.quipy.catalog.logic.CatalogAggregateState
import ru.quipy.catalog.service.CatalogItemRepository
import ru.quipy.core.EventSourcingService
import ru.quipy.user.api.UserAggregate
import ru.quipy.user.logic.UserAggregateState
import ru.quipy.user.security.UserSecurity
import ru.quipy.user.service.UserRepository
import java.util.*
import java.util.concurrent.locks.ReentrantLock

@RestController
@RequestMapping("/cart")
class CartController(
        val catalogItemESService: EventSourcingService<UUID, CatalogAggregate, CatalogAggregateState>,
        val catalogItemRepository: CatalogItemRepository,
        val cartESservice: EventSourcingService<UUID, CartAggregate, CartAggregateState>,
        val userESService: EventSourcingService<UUID, UserAggregate, UserAggregateState>,
        val userRepository: UserRepository,
) {
    private val mutexAddItem = ReentrantLock()
   @PostMapping("/reset")
   fun createCart(): ResponseEntity<Any>{
       val userLogged = (SecurityContextHolder.getContext().authentication.principal as UserSecurity)
       val cartId = cartESservice.create { it.createNewCart() }.cartId
       userESService.update(userLogged.id){it.resetCart(userLogged.id, cartId)}

       return ResponseEntity.ok("CartId: $cartId, UserId: ${userLogged.id}")
   }

    @PostMapping("/addItem")
    fun addItem(@RequestBody request: AddItemsDTO): ResponseEntity<Any>{
        val userLogged = (SecurityContextHolder.getContext().authentication.principal as UserSecurity)
        val cartId = userESService.getState(userLogged.id)!!.getCart()!!
        mutexAddItem.lock()
        try {
            cartESservice.update(cartId){it.addItems(cartId, request.itemId, request.amount)}
        }finally {
            mutexAddItem.unlock()
        }
        return ResponseEntity.ok("")
    }

    @PostMapping("/removeItem")
    fun removeItem(@RequestBody request: AddItemsDTO): ResponseEntity<Any>{
        val userLogged = (SecurityContextHolder.getContext().authentication.principal as UserSecurity)
        val cartId = userESService.getState(userLogged.id)!!.getCart()!!
        mutexAddItem.lock()
        try {
            cartESservice.update(cartId){it.removeItems(cartId, request.itemId, request.amount)}
        }finally {
            mutexAddItem.unlock()
        }
        return ResponseEntity.ok("")
    }

    @GetMapping("/list")
    fun getList(): ResponseEntity<Any>{
        val userLogged = (SecurityContextHolder.getContext().authentication.principal as UserSecurity)
        val cartId = userESService.getState(userLogged.id)!!.getCart()!!
        return ResponseEntity.ok(cartESservice.getState(cartId)!!.getItems())
    }
}