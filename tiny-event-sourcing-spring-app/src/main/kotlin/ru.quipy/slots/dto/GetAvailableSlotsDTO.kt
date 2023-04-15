package ru.quipy.slots.dto

import ru.quipy.slots.logic.SlotsAggregateState
import java.beans.ConstructorProperties
import java.time.Instant
import java.util.UUID

data class GetAvailableSlotsDTO
@ConstructorProperties("time", "status", "id")
constructor(val time: Instant, val status: SlotsAggregateState.Status, val id: UUID)