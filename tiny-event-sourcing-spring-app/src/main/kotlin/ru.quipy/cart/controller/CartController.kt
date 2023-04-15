package ru.quipy.cart.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.quipy.cart.api.CartAggregate
import ru.quipy.cart.logic.CartAggregateState
import ru.quipy.catalog.api.CatalogAggregate
import ru.quipy.catalog.logic.CatalogAggregateState
import ru.quipy.catalog.service.CatalogItemRepository
import ru.quipy.core.EventSourcingService
import ru.quipy.user.api.UserAggregate
import ru.quipy.user.logic.UserAggregateState
import ru.quipy.user.service.UserRepository
import java.util.*

@RestController
@RequestMapping("/cart")
class CartController(
        val catalogItemESService: EventSourcingService<UUID, CatalogAggregate, CatalogAggregateState>,
        val catalogItemRepository: CatalogItemRepository,
        val cartESservice: EventSourcingService<UUID, CartAggregate, CartAggregateState>,
        val userESService: EventSourcingService<UUID, UserAggregate, UserAggregateState>,
        val userRepository: UserRepository,
) {
//    @PostMapping("/{userId}")
//    fun createCart(@PathVariable userId: UUID): Any{
//
//    }
}