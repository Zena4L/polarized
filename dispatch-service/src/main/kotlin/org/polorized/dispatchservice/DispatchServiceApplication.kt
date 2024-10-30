package org.polorized.dispatchservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DispatchServiceApplication

fun main(args: Array<String>) {
    runApplication<DispatchServiceApplication>(*args)
}
