package org.polorized.orderservice.domain;

import lombok.AllArgsConstructor;
import org.polorized.orderservice.book.Book;
import org.polorized.orderservice.book.BookClient;
import org.polorized.orderservice.event.OrderAcceptedMessage;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookClient bookClient;
    private final StreamBridge streamBridge;

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> submitOrder(OrderRequest request) {
        return bookClient.getBookByIsbn(request.getIsbn())
                .map(book -> buildAcceptedOrder(book, request.getQuantity()))
                .defaultIfEmpty(Order.builder().bookIsbn(request.getIsbn()).quantity(request.getQuantity()).build())
                .flatMap(orderRepository::save);
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.findById(Math.toIntExact(orderId))
                .map(
                        existingOrder -> Order.builder()
                                .id(existingOrder.id())
                                .bookIsbn(existingOrder.bookIsbn())
                                .bookName(existingOrder.bookName())
                                .bookPrice(existingOrder.bookPrice())
                                .quantity(existingOrder.quantity())
                                .orderStatus(status)
                                .createdDate(existingOrder.createdDate())
                                .modifiedDate(existingOrder.modifiedDate())
                                .version(existingOrder.version())
                                .build()
                ).flatMap(orderRepository::save).subscribe();
    }

    private Order buildAcceptedOrder(Book book, int quantity) {
        return Order.builder()
                .bookIsbn(book.isbn())
                .quantity(quantity)
                .bookName(book.title())
                .orderStatus(OrderStatus.ACCEPTED)
                .bookPrice(book.price())
                .build();
    }

    public void publishOrderAcceptedEvent(Order order) {
        if (order.orderStatus().equals(OrderStatus.ACCEPTED)) {

            OrderAcceptedMessage orderAcceptedMessage = new OrderAcceptedMessage((long) order.id());

            streamBridge.send("order-accepted", orderAcceptedMessage);
        }

    }

}


