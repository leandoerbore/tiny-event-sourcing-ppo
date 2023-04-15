package ru.quipy.slots.dto

import ru.quipy.slots.logic.SlotsAggregateState
import java.beans.ConstructorProperties
import java.time.Instant

data class CreateSlotDTO
@ConstructorProperties("time", "status")
constructor(val time: Instant, val status: SlotsAggregateState.Status)