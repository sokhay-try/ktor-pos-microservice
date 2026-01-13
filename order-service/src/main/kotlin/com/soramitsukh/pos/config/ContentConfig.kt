package com.soramitsukh.pos.config

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureContentNegotiation() {
    install(ContentNegotiation) {
        json()
    }
}
