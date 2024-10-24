package org.polorized.bookcatalog.domain

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.time.Instant
import kotlin.test.assertEquals


class BookServiceTest {
    private lateinit var bookRepository: BookRepository
    private lateinit var bookService: BookService
    private lateinit var b1 : Book
    private lateinit var b2: Book

    @BeforeEach
    fun setup() {
        bookRepository = mock(BookRepository::class.java)
        bookService = BookService(bookRepository)

        b1 = Book(
            isbn = "1234567891",
            author = "Test author",
            title = "Test native",
            price = BigDecimal.valueOf(100.0)
        )

         b2 = Book(
            isbn = "1234567892",
            author = "Another author",
            title = "Cloud native",
            price = BigDecimal.valueOf(10.0)
        )

    }

    @Test
    fun `should return all books`() {
        val books = listOf(b1,b2)

        `when`(bookRepository.findAll()).thenReturn(books)

        val res = bookService.viewBookList()

        assertEquals(books,res)
    }

    @Test
    fun `should return book by isbn`() {

        `when`(bookRepository.findByIsbn(b1.isbn)).thenReturn(b1)

        val res = bookService.viewBookDetails(b1.isbn)

        assertEquals(b1, res)
    }

    @Test
    fun `should throw Exception if book not found`() {

        val isbn = "0987654321"

        `when`(bookRepository.findByIsbn(isbn)).thenReturn(null)

        assertThrows<Exception> {bookService.viewBookDetails(isbn)}
    }

    @Test
    fun `add book to catalog is not exist`() {

        `when`(bookRepository.existsByIsbn(b1.isbn)).thenReturn(false)
        `when`(bookRepository.save(b1)).thenReturn(b1)

        val res = bookService.addBookToCatalog(b1)

        assertEquals(b1, res)
    }

    @Test
    fun `if book already exist then throw exception`() {

        `when`(bookRepository.existsByIsbn(b1.isbn)).thenReturn(true)

        assertThrows<Exception>{bookService.addBookToCatalog(b1)}
    }


    @Test
    fun `should edit book`() {

        val existingBook = Book(
            id = 1L,
            isbn = "1234567891",
            author = "Clement",
            title = "Spring Test",
            price = BigDecimal.valueOf(10.00),
            createdDate = Instant.now(),
            lastModifiedDate = Instant.now(),
            version = 1
        )

        val updatedBook = Book(
            id = null,
            isbn = "1234567891",
            author = "New Author",
            title = "New Title",
            price = BigDecimal.valueOf(15.00),
            createdDate = existingBook.createdDate,
            lastModifiedDate = existingBook.lastModifiedDate,
            version = null
        )

        `when`(bookRepository.findByIsbn(existingBook.isbn)).thenReturn(existingBook)
        `when`(bookRepository.save(any(Book::class.java))).thenAnswer { it.getArgument(0) }

        val result = bookService.editBookDetails(existingBook.isbn, updatedBook)

        assertNotNull(result)
        assertEquals(existingBook.id, result.id)

    }


}