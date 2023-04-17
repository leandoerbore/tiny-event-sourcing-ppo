package ru.quipy.cart.api

import java.beans.ConstructorProperties
import java.util.UUID

data class AddItemToCartDTO
@ConstructorProperties("cartId", "itemId", "amount")
constructor(val cartId: UUID, val itemId: UUID, val amount: UInt)