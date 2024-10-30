package org.polorized.orderservice.event;

import org.polorized.orderservice.domain.OrderService;
import org.polorized.orderservice.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderFunction {
    private static final Logger log = LoggerFactory.getLogger(OrderFunction.class);

    @Bean
    public Consumer<OrderDispatchedMessage> dispatchedOrder(OrderService orderService) {
        return orderDispatchedMessage -> {
            log.info("The order with id {} has been dispatched", orderDispatchedMessage.orderId());
            orderService.updateOrderStatus(orderDispatchedMessage.orderId(), OrderStatus.DISPATCHED);
        };
    }
}
