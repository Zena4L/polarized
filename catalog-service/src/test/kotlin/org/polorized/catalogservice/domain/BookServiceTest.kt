package org.polorized.catalogservice.domain

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.math.BigDecimal

class BookServiceTest {
    private lateinit var bookRepository: BookRepository
    private lateinit var bookService: BookService

    @BeforeEach
    fun setUp() {
        bookRepository = mock(BookRepository::class.java)
        bookService = BookService(bookRepository)
    }
    @Test
    fun returns_list_of_books_when_available() {
        val bookRepository = mock(BookRepository::class.java)
        val bookService = BookService(bookRepository)
        val books = listOf(
            Book("1234567890", "Author A", "Title A", BigDecimal(10)),
            Book("0987654321", "Author B", "Title B", BigDecimal(15))
        )
        `when`(bookRepository.findAll()).thenReturn(books)

        val result = bookService.viewBookList()

        assertNotNull(result)
        assertEquals(2, result?.count())
    }

    @Test
    fun returns_book_object_for_valid_isbn() {
        val bookRepository = mock(BookRepository::class.java)
        val bookService = BookService(bookRepository)
        val isbn = "1234567890"
        val expectedBook = Book(isbn, "Author Name", "Book Title", BigDecimal("29.99"))

        `when`(bookRepository.findByIsbn(isbn)).thenReturn(expectedBook)

        val result = bookService.viewBookDetails(isbn)

        assertNotNull(result)
        assertEquals(expectedBook, result)
    }

    @Test
    fun test_add_book_to_catalog_success() {
        val bookRepository = mock(BookRepository::class.java)
        val bookService = BookService(bookRepository)
        val book = Book("1234567890", "Author Name", "Book Title", BigDecimal(29.99))

        `when`(bookRepository.existsByIsbn(book.isbn)).thenReturn(false)
        `when`(bookRepository.save(book)).thenReturn(book)

        val result = bookService.addBookToCatalog(book)

        assertEquals(book, result)
        verify(bookRepository).save(book)
    }




}