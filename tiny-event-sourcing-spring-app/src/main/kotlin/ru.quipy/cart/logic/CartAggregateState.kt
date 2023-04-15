package ru.quipy.cart.logic

import ru.quipy.cart.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.lang.IllegalStateException
import java.util.UUID

class CartAggregateState: AggregateState<UUID, CartAggregate> {
    private lateinit var cartId: UUID
    private var items = HashMap<UUID, Int>()
    override fun getId(): UUID? = cartId

    fun getItems(): HashMap<UUID, Int> = items

    fun createNewCart(id: UUID = UUID.randomUUID()): CartCreatedEvent = CartCreatedEvent(id)
//    fun removeCart(id: UUID): CartRemovedEvent = CartRemovedEvent(id)
    fun addItems(id: UUID, itemId: UUID, amount: Int): CartAddedItems {
        return CartAddedItems(
                cartId = id,
                itemId = itemId,
                amount = amount
        )
    }
    fun removeItems(id: UUID, itemId: UUID, amount: Int): CartRemovedItems {
        if (!items.containsKey(itemId)) throw IllegalStateException("Error: there is no item with this id")
        return CartRemovedItems(
                cartId = id,
                itemId = itemId,
                amount = amount
        )
    }

    @StateTransitionFunc
    fun createNewCart(event: CartCreatedEvent) {
        cartId = event.cartId
    }

    @StateTransitionFunc
    fun addItems(event: CartAddedItems){
        if (items.containsKey(event.itemId)){
            items[event.itemId] = event.amount + items[event.itemId]!!
        } else {
            items.put(event.itemId, event.amount)
        }
    }

    @StateTransitionFunc
    fun removeItems(event: CartRemovedItems){
        val left = items[event.itemId]!! - event.amount
        if (left < 0 ){
            items[event.itemId] = 0
        } else {
            items[event.itemId] = left
        }
    }
}