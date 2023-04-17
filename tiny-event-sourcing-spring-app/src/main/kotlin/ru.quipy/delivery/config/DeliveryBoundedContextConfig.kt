package ru.quipy.delivery.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.cart.api.CartAggregate
import ru.quipy.cart.logic.CartAggregateState
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.delivery.api.DeliveryAggregate
import ru.quipy.delivery.logic.DeliveryAggregateState
import java.util.UUID

@Configuration
class DeliveryBoundedContextConfig {
    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory
    @Bean
    fun deliveryESService(): EventSourcingService<UUID, DeliveryAggregate, DeliveryAggregateState> = eventSourcingServiceFactory.create()
}