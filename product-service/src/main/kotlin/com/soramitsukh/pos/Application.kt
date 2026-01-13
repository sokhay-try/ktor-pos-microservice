package com.soramitsukh.pos

import com.soramitsukh.pos.config.configureContentNegotiation
import com.soramitsukh.pos.config.configureErrorHandling
import com.soramitsukh.pos.config.configureRouting
import com.soramitsukh.pos.db.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureContentNegotiation()
    configureErrorHandling()
    DatabaseFactory.init(environment)
    configureRouting()

}
