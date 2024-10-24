package org.polorized.bookcatalog.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.Instant


@Entity
@EntityListeners(AuditingEntityListener::class)
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @field:Pattern(
        regexp = "^([0-9]{10}|[0-9]{13})$",
        message = "The ISBN format must follow the standards ISBN-10 or ISBN-13"
    )
    @field:NotNull(message = "The book must have an isbn number")
    val isbn: String,

    @field:NotNull(message = "The book must have an author")
    val author: String,

    @field:NotNull(message = "Book title must be defined")
    val title: String,

    @field:NotNull(message = "Book price must be defined")
    val price: BigDecimal,

    @CreatedDate
    @Column(updatable = false)
    var createdDate: Instant? = null,

    @LastModifiedDate
    val lastModifiedDate: Instant? = null,

    @Version
    var version: Int? = null
) {
}