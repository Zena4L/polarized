package org.polorized.catalogservice.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandle :ResponseEntityExceptionHandler(){

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors = ex.bindingResult.fieldErrors.joinToString(","){ "${it.field}:${it.defaultMessage}"}
        return exceptionBuilder(errors,HttpStatus.BAD_REQUEST)
    }

    private fun exceptionBuilder(errors: String, status: HttpStatus): ResponseEntity<Any>? {
       val problemDetails : ProblemDetail = ProblemDetail.forStatus(status).apply {
           this.detail = errors
           this.title = status.reasonPhrase
       }

        return ResponseEntity(problemDetails,status)
    }

}