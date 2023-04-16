package ru.quipy.slots.logic

import ru.quipy.slots.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.UUID

class SlotsAggregateState: AggregateState<UUID, SlotsAggregate> {
    enum class Status(val status: String){
        FREE("FREE"),
        OCCUPIED("OCCUPIED")
    }

    private lateinit var slotId: UUID
    private lateinit var time: Instant
    private lateinit var status: Status

    override fun getId(): UUID = slotId
    fun getTime(): Instant = time
    fun getStatus(): Status = status

    fun resetField(target: Any, fieldName: String) {
        val field = target.javaClass.getDeclaredField(fieldName)

        with (field) {
            isAccessible = true
            set(target, null)
        }
    }

    fun createSlot(id: UUID = UUID.randomUUID(), time: Instant, status: Status) : SlotCreatedEvent {
        return SlotCreatedEvent(id, time, status)
    }
    fun removeSlot(id: UUID): SlotRemovedEvent =
            SlotRemovedEvent(id)
    fun updateSlotStatus(id: UUID, status: Status): SlotUpdatedStatusEvent {
        return SlotUpdatedStatusEvent(id, status)
    }

    @StateTransitionFunc
    fun createSlot(event: SlotCreatedEvent){
        slotId = event.slotId
        time = event.time
        status = event.status
    }

    @StateTransitionFunc
    fun removeSlot(event: SlotRemovedEvent){
        resetField(this, "slotId")
    }

    @StateTransitionFunc
    fun updateSlotStatus(event: SlotUpdatedStatusEvent){
        slotId = event.slotId
        status = event.status
    }
}