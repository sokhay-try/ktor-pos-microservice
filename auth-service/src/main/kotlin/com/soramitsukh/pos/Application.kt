package com.soramitsukh.pos

import com.soramitsukh.pos.config.configureKtorPlugins
import com.soramitsukh.pos.config.configureRouting
import com.soramitsukh.pos.db.DatabaseFactory
import com.soramitsukh.pos.service.AuthService
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureKtorPlugins()
    DatabaseFactory.init(environment)
    val authService = AuthService()
    configureRouting(authService)
}
