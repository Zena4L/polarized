package org.polorized.bookcatalog.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.polorized.bookcatalog.config.PolarProperties
import org.polorized.bookcatalog.domain.BookNotFoundException
import org.polorized.bookcatalog.domain.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CatalogController::class)
class CatalogControllerTest{

    @Autowired
    private lateinit var mockMvc : MockMvc

    @MockBean
    private lateinit var bookService: BookService

    @MockBean
    private lateinit var polarProperties: PolarProperties

    @Test
    fun `when book not exit then return 404`(){
        val isbn = "000000000000"

        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException::class.java)

        mockMvc.perform(get("/api/v1/catalog/$isbn"))
            .andExpect(status().isNotFound)
    }
}