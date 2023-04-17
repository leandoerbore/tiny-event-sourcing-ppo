package ru.quipy.delivery.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.quipy.cart.api.CartAggregate
import ru.quipy.cart.logic.CartAggregateState
import ru.quipy.core.EventSourcingService
import ru.quipy.delivery.api.DeliveryAggregate
import ru.quipy.delivery.api.createDeliveryDTO
import ru.quipy.delivery.logic.DeliveryAggregateState
import ru.quipy.slots.api.SlotsAggregate
import ru.quipy.slots.logic.SlotsAggregateState
import ru.quipy.slots.service.SlotsRepository
import ru.quipy.user.api.UserAggregate
import ru.quipy.user.logic.UserAggregateState
import ru.quipy.user.security.UserSecurity
import java.time.Instant
import java.util.UUID

@RestController()
@RequestMapping("/delivery")
class DeliveryController (
        val deliveryESService: EventSourcingService<UUID, DeliveryAggregate, DeliveryAggregateState>,
        val userESService: EventSourcingService<UUID, UserAggregate, UserAggregateState>,
//        val cartESService: EventSourcingService<UUID, CartAggregate, CartAggregateState>,
//        val slotsESService: EventSourcingService<UUID, SlotsAggregate, SlotsAggregateState>,
        val slotsRepository: SlotsRepository
){
    @PostMapping()
    fun createDelivery(@RequestBody request: createDeliveryDTO): ResponseEntity<Any> {
        val userLogged = (SecurityContextHolder.getContext().authentication.principal as UserSecurity)
        val cartId = userESService.getState(userLogged.id)!!.getCart()
                ?: return ResponseEntity.badRequest().body("No cart id")
        if (request.time > Instant.now()){
            return ResponseEntity.badRequest().body("Time is gone")
        }
        val slot = slotsRepository.findOneByTime(request.time)
                ?: return ResponseEntity.badRequest().body("There is not this time")
        val deliveryId = deliveryESService.create(){it.createDelivery(
                userId = userLogged.id,
                cartId = cartId,
                time = request.time
        )}.id
        return ResponseEntity.ok("deliveryId: $deliveryId")
    }

    @GetMapping("/{id}")
    fun trackDelivery(@PathVariable id: UUID): ResponseEntity<Any>{
        val delivery = deliveryESService.getState(id)!!.getInfo()

        return ResponseEntity.ok(delivery)
    }
}