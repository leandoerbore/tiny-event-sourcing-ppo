package ru.quipy.delivery.api

import ru.quipy.delivery.logic.DeliveryAggregateState
import java.beans.ConstructorProperties

data class updateDeliveryStatusDTO
@ConstructorProperties("status")
constructor(val status: DeliveryAggregateState.Status)