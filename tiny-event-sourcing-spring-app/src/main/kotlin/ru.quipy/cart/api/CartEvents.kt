package ru.quipy.cart.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val CART_CREATED = "CART_CREATED"
const val CART_REMOVED = "CART_REMOVED"
const val CART_ADDED_ITEMS = "CART_ADDED_ITEMS"
const val CART_REMOVED_ITEMS = "CART_REMOVED_ITEMS"

@DomainEvent(name = CART_CREATED)
data class CartCreatedEvent(
        val cartId: UUID,
): Event<CartAggregate>(
        name = CART_CREATED
)

@DomainEvent(name = CART_REMOVED)
data class CartRemovedEvent(
        val cartId: UUID,
): Event<CartAggregate>(
        name = CART_REMOVED
)

@DomainEvent(name = CART_ADDED_ITEMS)
data class CartAddedItems(
        val cartId: UUID,
        val itemId: UUID,
        val amount: Int,
): Event<CartAggregate>(
        name  = CART_ADDED_ITEMS
)

@DomainEvent(name = CART_REMOVED_ITEMS)
data class CartRemovedItems(
        val cartId: UUID,
        val itemId: UUID,
        val amount: Int,
): Event<CartAggregate>(
        name = CART_REMOVED_ITEMS
)