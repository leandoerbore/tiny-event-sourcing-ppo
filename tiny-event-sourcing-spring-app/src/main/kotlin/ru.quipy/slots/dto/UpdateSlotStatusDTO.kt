package ru.quipy.slots.dto

import java.util.*
import java.beans.ConstructorProperties

data class UpdateSlotStatusDTO
@ConstructorProperties("id", "status")
constructor(val id: UUID, val status: String)