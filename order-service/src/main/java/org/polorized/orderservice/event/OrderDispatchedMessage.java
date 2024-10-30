package org.polorized.orderservice.event;

public record OrderDispatchedMessage(
        Long orderId
) {
}
