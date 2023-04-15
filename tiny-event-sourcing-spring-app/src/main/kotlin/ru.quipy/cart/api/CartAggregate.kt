package ru.quipy.cart.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType(aggregateEventsTableName = "cart")
class CartAggregate: Aggregate