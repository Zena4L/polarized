package org.polorized.bookcatalog.domain

import org.springframework.stereotype.Service

@Service
class BookService(val bookRepository: BookRepository) {

    fun viewBookList(): MutableList<Book> = bookRepository.findAll()


    fun viewBookDetails(isbn: String): Book? {
        return bookRepository.findByIsbn(isbn) ?: throw BookNotFoundException("No such book")
    }

    fun addBookToCatalog(book: Book): Book {
        if (bookRepository.existsByIsbn(book.isbn)) throw RuntimeException("Book Already exits")

        return bookRepository.save(book)
    }

    fun removerBookFromCatalog(isbn: String) {
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