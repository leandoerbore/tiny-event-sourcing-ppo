package ru.quipy.slots.dto

import java.beans.ConstructorProperties
import java.util.UUID

data class GetAvailableSlotsDTO
@ConstructorProperties("time", "status", "id")
constructor(val time: String, val status: String, val id: UUID)