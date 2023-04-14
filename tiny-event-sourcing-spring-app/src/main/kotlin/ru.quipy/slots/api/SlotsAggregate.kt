package ru.quipy.slots.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType(aggregateEventsTableName = "slots")
class SlotsAggregate: Aggregate