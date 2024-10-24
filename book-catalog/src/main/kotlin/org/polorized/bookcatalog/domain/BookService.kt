package org.polorized.bookcatalog.domain

import org.springframework.stereotype.Service

@Service
class BookService(val bookRepository: BookRepository) {

    fun viewBookList(): Iterable<Book?>? {
        return bookRepository.findAll()
    }

    fun viewBookDetails(isbn: String): Book? {
        return bookRepository.findByIsbn(isbn) ?: throw Exception("No such book")
    }

    fun addBookToCatalog(book: Book): Book {
        if (bookRepository.existsByIsbn(book.isbn)) throw RuntimeException("Book Already exits")

        return bookRepository.save(book)
    }

    fun removerBookFromCatalog(isbn: String) {
        if (bookRepository.existsByIsbn(isbn)) throw RuntimeException("No such book")

        bookRepository.deleteByIsbn(isbn)
    }

    fun editBookDetails(isbn: String, book: Book): Book {
        val existingBook = bookRepository.findByIsbn(isbn)
        if (existingBook != null) {
            addBookToCatalog(book)
        }
        val updateBok = existingBook?.let {
            Book(
                existingBook.id,
                book.isbn,
                book.author,
                book.title,
                book.price,
                book.createdDate,
                book.lastModifiedDate,
                existingBook.version
            )
        }

        return bookRepository.save(updateBok!!)
    }


}