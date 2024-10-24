package org.polorized.catalogservice.domain

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import java.math.BigDecimal

data class Book(
    @field:Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must follow the standards ISBN-10 or ISBN-13")
    @field:NotNull(message = "The book must have an isbn number")
    val isbn:String,
    @field:NotNull(message = "The book must have an author")
    val author: String,
    @field:NotNull(message = "Book title must be defined")
    val title : String,
    @field:NotNull(message = "Book price must be defined")
    val price : BigDecimal
) {
}