package org.polorized.orderservice.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@AllArgsConstructor
public class BookClient {
    private static final String BOOKS_ROOT_API = "/api/v1/catalog";
    private final WebClient webClient;

    public Mono<Book> getBookByIsbn(String isbn) {
        return webClient
                .get()
                .uri(BOOKS_ROOT_API + isbn)
                .retrieve()
                .bodyToMono(Book.class)
                .timeout(Duration.ofSeconds(2), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty())
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)));
    }
}
