package com.soramitsukh.pos.gateway.config

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun Application.configureKtorPlugins() {

    install(ContentNegotiation) {
        json()
    }
    install(Authentication)
}
