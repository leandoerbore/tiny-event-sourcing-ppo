package ru.quipy.cart.logic

import ru.quipy.cart.api.CartAggregate
import ru.quipy.domain.AggregateState
import java.util.UUID

class CartAggregateState: AggregateState<UUID, CartAggregate> {
    private lateinit var cartId: UUID
    private var items: HashMap<UUID, Int>()
    override fun getId(): UUID? {
        TODO("Not yet implemented")
    }
}