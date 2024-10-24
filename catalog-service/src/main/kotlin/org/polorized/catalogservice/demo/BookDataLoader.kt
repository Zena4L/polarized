package org.polorized.catalogservice.demo

import org.polorized.catalogservice.domain.Book
import org.polorized.catalogservice.domain.BookRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Profile("test-data")
@Component
class BookDataLoader(private val bookRepository: BookRepository) {

    @EventListener(ApplicationReadyEvent::class)
    fun loadBookTestData() {
        val book1 = Book("1234567891", "Clement", "Spring Test", BigDecimal.valueOf(10.00))
        val book2 = Book("1234567892", "Zena", "Cloud Native", BigDecimal.valueOf(12.00))

        bookRepository.save(book1)
        bookRepository.save(book2)
    }
}