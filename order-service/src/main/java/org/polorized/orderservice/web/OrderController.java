package org.polorized.orderservice.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.polorized.orderservice.domain.Order;
import org.polorized.orderservice.domain.OrderRequest;
import org.polorized.orderservice.domain.OrderService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    private Flux<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Mono<Order> submitOrder(@RequestBody @Valid OrderRequest request) {
        return orderService.submitOrder(request);
    }
}
