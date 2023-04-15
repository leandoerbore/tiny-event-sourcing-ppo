package ru.quipy.slots.service

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.slots.logic.SlotsAggregateState
import java.util.*

interface SlotsRepository: MongoRepository<SlotsMongo, String> {

    @org.springframework.lang.Nullable
    fun findOneByTime(time: Date): SlotsMongo?

    @org.springframework.lang.Nullable
    fun findOneByAggregateId(id: UUID): SlotsMongo?

    @org.springframework.lang.Nullable
    fun findAllByStatus(status: SlotsAggregateState.Status): List<SlotsMongo>
}