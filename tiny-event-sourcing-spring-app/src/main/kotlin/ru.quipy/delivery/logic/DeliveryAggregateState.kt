package ru.quipy.delivery.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.delivery.api.*
import ru.quipy.domain.AggregateState
import java.time.Instant
import java.util.UUID

class DeliveryAggregateState: AggregateState<UUID, DeliveryAggregate> {
    enum class Status(val status: String){
        CREATED("CREATED"),
        IN_PROGRESS("IN_PROGRESS"),
        DELIVERED("DELIVERED"),
        CANCELED("CANCELED")
    }
    private lateinit var userId: UUID
    private lateinit var cartId: UUID
    private lateinit var deliveryId: UUID
    private lateinit var time: Instant
    private lateinit var status: Status
    override fun getId(): UUID? = deliveryId

    fun getInfo(): InfoDeliveryDto {
        return InfoDeliveryDto(
                deliveryId = deliveryId,
                userId = userId,
                cartId = cartId,
                time = time,
                status = status
        )
    }

    fun createDelivery(
            deliveryId: UUID = UUID.randomUUID(),
            userId: UUID,
            cartId: UUID,
            time: Instant
    ): DeliveryCreatedEvent = DeliveryCreatedEvent(
            deliveryId,
            userId,
            cartId,
            time
    )

    fun updateStatusDelivery(
            deliveryId: UUID,
            status: Status
    ): DeliveryStatusUpdated =
            DeliveryStatusUpdated(
                    deliveryId,
                    status
            )
    @StateTransitionFunc
    fun updateStatusDelivery(event: DeliveryStatusUpdated){
        deliveryId = event.deliveryId
        status = event.status
    }

    @StateTransitionFunc
    fun createDelivery(event: DeliveryCreatedEvent){
        deliveryId = event.deliveryId
        userId = event.userId
        cartId = event.cartId
        time = event.time
        status = Status.CREATED
    }
}