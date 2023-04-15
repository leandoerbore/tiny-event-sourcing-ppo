package ru.quipy.slots.logic

import ru.quipy.slots.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class SlotsAggregateState: AggregateState<UUID, SlotsAggregate> {
    enum class Status(val status: String){
        FREE("FREE"),
        OCCUPIED("OCCUPIED")
    }
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")

    private lateinit var slotId: UUID
    private lateinit var time: Date
    private lateinit var status: Status

    override fun getId(): UUID = slotId
    fun getTime(): String = dateFormatter.format(time)
    fun getStatus(): String = status.status

    fun resetField(target: Any, fieldName: String) {
        val field = target.javaClass.getDeclaredField(fieldName)

        with (field) {
            isAccessible = true
            set(target, null)
        }
    }

    fun createSlot(id: UUID = UUID.randomUUID(), time: String, status: String) : SlotCreatedEvent {
        return SlotCreatedEvent(id, dateFormatter.parse(time), Status.valueOf(status))
    }
    fun removeSlot(id: UUID): SlotRemovedEvent =
            SlotRemovedEvent(id)
    fun updateSlotStatus(id: UUID, status: String): SlotUpdatedStatusEvent {
        return SlotUpdatedStatusEvent(id, Status.valueOf(status))
    }

    @StateTransitionFunc
    fun createSlot(event: SlotCreatedEvent){
        slotId = event.slotId
        time = event.time
        status = event.status
    }

    @StateTransitionFunc
    fun removeItem(event: SlotRemovedEvent){
        resetField(this, "slotId")
    }

    @StateTransitionFunc
    fun updateSlotStatus(event: SlotUpdatedStatusEvent){
        slotId = event.slotId
        status = event.status
    }
}