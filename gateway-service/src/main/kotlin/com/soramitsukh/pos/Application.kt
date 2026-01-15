package com.soramitsukh.pos

import com.soramitsukh.pos.gateway.config.configureKtorPlugins
import com.soramitsukh.pos.gateway.config.configureRouting
import com.soramitsukh.pos.gateway.config.configureSecurity
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureKtorPlugins()
    configureSecurity()
    configureRouting()
}
