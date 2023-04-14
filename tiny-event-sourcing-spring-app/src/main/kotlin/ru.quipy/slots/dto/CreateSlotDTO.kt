package ru.quipy.slots.dto

import java.beans.ConstructorProperties

data class CreateSlotDTO
@ConstructorProperties("time", "status")
constructor(val time: String, val status: String)