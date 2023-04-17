package ru.quipy.delivery.api

import java.beans.ConstructorProperties
import java.time.Instant

data class CreateDeliveryDTO
@ConstructorProperties("time")
constructor(
        val time: Instant
)