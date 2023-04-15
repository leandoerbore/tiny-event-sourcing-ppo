package ru.quipy.slots.service

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId
import ru.quipy.slots.logic.SlotsAggregateState.Status
import java.time.Instant
import java.util.*

@Document
data class SlotsMongo(
        @MongoId(value = FieldType.STRING)
        val aggregateId: UUID,
        val time: Instant,
        val status: Status,
)