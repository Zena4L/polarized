package org.polorized.bookcatalog.demo


import org.polorized.bookcatalog.domain.Book
import org.polorized.bookcatalog.domain.BookRepository
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
        bookRepository.deleteAll()

        val book1 = Book(
            isbn = "1234567891",
            author = "Clement",
            title = "Spring Test",
            price = BigDecimal.valueOf(10.00)
        )

        val book2 = Book(
            isbn = "1234567892",
            author = "Zena",
            title = "Cloud Native",
            price = BigDecimal.valueOf(12.00)
        )

        bookRepository.saveAll(mutableListOf(book1, book2))
    }
}
