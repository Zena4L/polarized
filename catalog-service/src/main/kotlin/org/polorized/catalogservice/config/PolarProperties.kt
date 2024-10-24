package org.polorized.catalogservice.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "polar")
data class PolarProperties(val greeting:String)
