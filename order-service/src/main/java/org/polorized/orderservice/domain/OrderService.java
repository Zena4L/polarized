package org.polorized.orderservice.domain;

import lombok.AllArgsConstructor;
import org.polorized.orderservice.book.Book;
import org.polorized.orderservice.book.BookClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookClient bookClient;

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> submitOrder(OrderRequest request) {
        return bookClient.getBookByIsbn(request.getIsbn())
                .map(book -> buildAcceptedOrder(book, request.getQuantity()))
                .defaultIfEmpty(Order.builder().bookIsbn(request.getIsbn()).quantity(request.getQuantity()).build())
                .flatMap(orderRepository::save);
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

}


