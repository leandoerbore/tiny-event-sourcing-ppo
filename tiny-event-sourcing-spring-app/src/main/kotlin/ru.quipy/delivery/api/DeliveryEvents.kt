package ru.quipy.delivery.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.delivery.logic.DeliveryAggregateState.Status
import ru.quipy.domain.Event
import java.time.Instant
import java.util.*

const val DELIVERY_CREATED = "DELIVERY_CREATED"
//const val DELIVERY_TIME_UPDATED = "DELIVERY_TIME_UPDATED"
const val DELIVERY_STATUS_UPDATED = "DELIVERY_STATUS_UPDATED"
//const val DELIVERY_CANCELED = "DELIVERY_CANCELED"

@DomainEvent(name = DELIVERY_STATUS_UPDATED)
data class DeliveryStatusUpdated(
        val deliveryId: UUID,
        val status: Status
): Event<DeliveryAggregate>(
        name = DELIVERY_STATUS_UPDATED
)
@DomainEvent(name = DELIVERY_CREATED)
data class DeliveryCreatedEvent(
        val deliveryId: UUID,
        val userId: UUID,
        val cartId: UUID,
        val time: Instant
): Event<DeliveryAggregate>(
        name = DELIVERY_CREATED
)

//@DomainEvent(name = DELIVERY_TIME_UPDATED)
//data class DeliveryUpdatedEvent(
//        val deliveryId: UUID,
//        val time: Instant,
//): Event<DeliveryAggregate>(
//        name = DELIVERY_TIME_UPDATED
//)
//
//@DomainEvent(name = DELIVERY_CANCELED)
//data class DeliveryCanceledEvent(
//        val deliveryId: UUID
//): Event<DeliveryAggregate>(
//        name = DELIVERY_CANCELED
//)

