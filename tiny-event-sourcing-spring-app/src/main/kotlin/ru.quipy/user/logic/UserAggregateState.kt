package ru.quipy.user.logic

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.user.api.*
import java.util.*

class UserAggregateState: AggregateState<UUID, UserAggregate> {
    private lateinit var userId: UUID
    private lateinit var cartId: UUID
    private var trackerIds = ArrayList<UUID>()
    override fun getId(): UUID? = userId

    fun getCart(): UUID? = cartId

    fun existCart(): Boolean = this::cartId.isInitialized

    fun getTracks(): ArrayList<UUID>? = trackerIds

    fun resetField(target: Any, fieldName: String) {
        val field = target.javaClass.getDeclaredField(fieldName)
        with (field) {
            isAccessible = true
            set(target, null)
        }
    }

    fun createUser(id: UUID = UUID.randomUUID()): UserCreatedEvent = UserCreatedEvent(id)
    fun createNewCart(id: UUID, cartId: UUID): UserCreatedCartEvent = UserCreatedCartEvent(id, cartId)
    fun resetCart(id: UUID, cartId: UUID): UserResetCartEvent = UserResetCartEvent(id, cartId)
    fun addTrack(id: UUID, trackId: UUID): UserAddTrackEvent = UserAddTrackEvent(id, trackId)


    @StateTransitionFunc
    fun createUser(event: UserCreatedEvent) {
        userId = event.userId
    }

    @StateTransitionFunc
    fun createNewCart(event: UserCreatedCartEvent) {
        userId = event.userId
        cartId = event.cartId
    }

    @StateTransitionFunc
    fun resetCart(event: UserResetCartEvent) {
        userId = event.userId
        cartId = event.cartId

//        resetField(this, "cartId")
    }

    @StateTransitionFunc
    fun addTrack(event: UserAddTrackEvent) {
        userId = event.userId
        trackerIds.add(event.trackId)
    }
}