package ru.quipy.slots.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*
import java.time.Instant
import ru.quipy.slots.logic.SlotsAggregateState.Status

const val SLOT_CREATED = "SLOT_CREATED"
const val SLOT_REMOVED = "SLOT_REMOVED"
const val SLOT_UPDATED_STATUS = "SLOT_UPDATED_STATUS"

@DomainEvent(name = SLOT_CREATED)
data class SlotCreatedEvent(
        val slotId: UUID,
        val time: Instant,
        val status: Status
) : Event<SlotsAggregate>(
        name = SLOT_CREATED
)

@DomainEvent(name = SLOT_REMOVED)
data class SlotRemovedEvent(
        val slotId: UUID,
) : Event<SlotsAggregate>(
        name = SLOT_REMOVED
)

@DomainEvent(name = SLOT_UPDATED_STATUS)
data class SlotUpdatedStatusEvent(
        val slotId: UUID,
        val status: Status,
) : Event<SlotsAggregate> (
        name = SLOT_UPDATED_STATUS
)

