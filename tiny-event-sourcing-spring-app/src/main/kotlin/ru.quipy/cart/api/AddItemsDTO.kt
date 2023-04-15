package ru.quipy.cart.api

import java.beans.ConstructorProperties
import java.util.UUID

data class AddItemsDTO
@ConstructorProperties("itemId", "amont")
constructor(val itemId: UUID, val amount: Int)