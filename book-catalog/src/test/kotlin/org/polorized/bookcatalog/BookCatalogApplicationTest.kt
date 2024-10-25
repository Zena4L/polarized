package org.polorized.bookcatalog

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.polorized.bookcatalog.domain.Book
import org.polorized.bookcatalog.domain.BookRepository
import org.polorized.bookcatalog.persistence.DataConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.time.Instant

@Testcontainers
@DataJpaTest
@Import(DataConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookCatalogApplicationTest @Autowired constructor(
    private val bookRepository: BookRepository,
    private val entityManager : EntityManager
){

    companion object {
        @Container
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:latest")
    }

    @Test
    fun `when connected return true`(){
        assertThat(postgres.isCreated).isTrue()
    }

    @Test
    fun `find book by ISBN when existing`() {
        val bookIsbn = "1234561235"
        val book = Book(
            id = null,
            isbn = bookIsbn,
            title = "Title",
            author = "Author",
            price = BigDecimal.valueOf(10.00),
            createdDate = Instant.now(),
            lastModifiedDate = null,
            version = null
        )

        entityManager.persist(book)
        entityManager.flush()
        val actualBook = bookRepository.findByIsbn(bookIsbn)

        assertThat(actualBook).isNotNull
        assertEquals(book.isbn, actualBook?.isbn)
    }


}
