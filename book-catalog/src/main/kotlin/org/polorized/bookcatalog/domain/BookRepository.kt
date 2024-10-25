package org.polorized.bookcatalog.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface BookRepository : JpaRepository<Book, Long> {

    @Query("SELECT s FROM Book s WHERE s.isbn = :isbn")
    fun findByIsbn(@Param("isbn") isbn: String): Book?

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Book b WHERE b.isbn = :isbn")
    fun existsByIsbn(@Param("isbn") isbn: String): Boolean

    fun deleteByIsbn(@Param("isbn") isbn: String)

}