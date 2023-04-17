package ru.quipy.delivery.api

import ru.quipy.delivery.logic.DeliveryAggregateState
import java.beans.ConstructorProperties
import java.time.Instant
import java.util.UUID

data class InfoDeliveryDto
@ConstructorProperties("deliveryId", "userId", "cartId", "time", "status")
constructor(val deliveryId: UUID, val userId: UUID, val cartId: UUID, val time: Instant, val status: DeliveryAggregateState.Status)