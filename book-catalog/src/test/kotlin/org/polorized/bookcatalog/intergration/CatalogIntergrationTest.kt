package org.polorized.bookcatalog.intergration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.polorized.bookcatalog.domain.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `when post request then return book`() {

        val postBody = Book(
            isbn = "1234567896",
            author = "Clement",
            title = "Integration test",
            price = BigDecimal.valueOf(10.0)
        )

        webTestClient.post().uri("/api/v1/catalog")
            .bodyValue(postBody)
            .exchange()
            .expectStatus().isCreated
            .expectBody(Book::class.java)
            .value { actualBook ->
                assertNotNull(actualBook)
                assertEquals(postBody.isbn, actualBook?.isbn)
            }
    }
}