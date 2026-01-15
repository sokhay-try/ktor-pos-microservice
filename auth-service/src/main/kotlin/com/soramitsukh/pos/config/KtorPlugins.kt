package com.soramitsukh.pos.config

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureKtorPlugins() {
    install(ContentNegotiation) {
        json()
    }
}
