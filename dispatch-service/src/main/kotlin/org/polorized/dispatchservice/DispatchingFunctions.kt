package org.polorized.dispatchservice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux

@Configuration
class DispatchingFunctions {

    @Bean
    fun messageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(DispatchingFunctions::class.java)
    }

    @Bean
    fun pack(): (OrderAcceptedMessage) -> Long = { orderAcceptedMessage ->
        log.info("The order with id {} is packed.", orderAcceptedMessage.orderId)
        orderAcceptedMessage.orderId
    }

    @Bean
    fun label(): (Flux<Long>) -> Flux<OrderDispatchedMessage> = { orderFlux ->
        orderFlux.map { orderId ->
            log.info("The order with id {} is labeled", orderId)
            OrderDispatchedMessage(orderId)
        }
    }
}
