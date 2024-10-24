package org.polorized.bookcatalog.persistence

import jakarta.persistence.EntityListeners
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.domain.support.AuditingEntityListener


@Configuration
@EntityListeners(AuditingEntityListener::class)
class DataConfig {
}