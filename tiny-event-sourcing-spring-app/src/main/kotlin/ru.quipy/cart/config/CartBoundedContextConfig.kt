package ru.quipy.cart.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.cart.api.CartAggregate
import ru.quipy.cart.logic.CartAggregateState
import ru.quipy.catalog.api.CatalogAggregate
import ru.quipy.catalog.logic.CatalogAggregateState
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import java.util.UUID

@Configuration
class CartBoundedContextConfig {
    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun cartItemESService(): EventSourcingService<UUID, CartAggregate, CartAggregateState> = eventSourcingServiceFactory.create()
}