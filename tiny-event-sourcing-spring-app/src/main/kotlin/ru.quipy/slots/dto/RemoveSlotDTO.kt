package ru.quipy.slots.dto

import java.beans.ConstructorProperties
import java.util.*

data class RemoveSlotDTO
@ConstructorProperties("id")
constructor(val id: UUID)