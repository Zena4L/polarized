package org.polorized.bookcatalog.controller

import jakarta.validation.Valid
import org.polorized.bookcatalog.config.PolarProperties
import org.polorized.bookcatalog.domain.Book
import org.polorized.bookcatalog.domain.BookService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/catalog")
class CatalogController (private val bookService: BookService, private val property : PolarProperties){

    @GetMapping
    fun get() : MutableList<Book> {
        return bookService.viewBookList()
    }

    @GetMapping("{isbn}")
    fun getByIsbn(@PathVariable isbn : String) : Book? {
        return bookService.viewBookDetails(isbn)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@Valid @RequestBody book:Book) : Book {
        return bookService.addBookToCatalog(book)
    }

    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable isbn: String){
        return bookService.removerBookFromCatalog(isbn)
    }

    @PutMapping("{isbn}")
    fun put(@PathVariable isbn: String, @Valid @RequestBody book : Book) : Book {
        return bookService.editBookDetails(isbn,book)
    }

    @GetMapping("/hello")
    fun hello() = property.greeting


}