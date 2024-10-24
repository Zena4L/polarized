package org.polorized.bookcatalog.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface BookRepository : JpaRepository<Book,Long> {

    @Query("select s from Book s where s.isbn =: isbn")
    fun findByIsbn(isbn: String): Book?

    fun existsByIsbn(isbn: String): Boolean

    fun deleteByIsbn(isbn: String)

}