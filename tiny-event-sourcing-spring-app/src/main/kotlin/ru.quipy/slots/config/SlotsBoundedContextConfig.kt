package ru.quipy.slots.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.slots.api.SlotsAggregate
import ru.quipy.slots.logic.SlotsAggregateState
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import java.util.UUID

@Configuration
class SlotsBoundedContextConfig {
    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun slotsItemESService(): EventSourcingService<UUID, SlotsAggregate, SlotsAggregateState> = eventSourcingServiceFactory.create()
}