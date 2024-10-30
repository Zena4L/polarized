package org.polorized.orderservice.event;

public record OrderAcceptedMessage(
        Long orderId
) {
}
