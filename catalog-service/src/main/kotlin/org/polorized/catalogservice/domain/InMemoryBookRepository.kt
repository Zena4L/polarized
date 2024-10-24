package org.polorized.catalogservice.domain

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap



@Repository
class InMemoryBookRepository : BookRepository{
   companion object {
    private val books: MutableMap<String,Book> = ConcurrentHashMap()
   }

    override fun findAll(): Iterable<Book?>? {
        return books.values
    }

    override fun findByIsbn(isbn: String?): Book? {
      return books[isbn]
    }

    override fun existsByIsbn(isbn: String?): Boolean {
        return books[isbn] != null
    }

    override fun save(book: Book): Book {
        books[book.isbn] = book
        return book
    }

    override fun deleteByIsbn(isbn: String?) {
        books.remove(isbn)
    }

}