package org.polorized.dispatchservice

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.function.context.FunctionCatalog
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.function.Function
import java.util.function.Predicate


@FunctionalSpringBootTest
internal class DispatchingFunctionsIntegrationTests {

    @Autowired
    private lateinit var catalog: FunctionCatalog

    @Test
    fun `pack and label order`() {
        val packAndLabel = catalog.lookup<Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>>>("pack|label")
        val orderId = 121L

        StepVerifier.create(packAndLabel.apply(OrderAcceptedMessage(orderId)))
            .expectNextMatches { dispatchedOrder ->
                dispatchedOrder == OrderDispatchedMessage(orderId)
            }
            .verifyComplete()
    }

}