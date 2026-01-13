package com.soramitsukh.pos.config

import com.soramitsukh.pos.order.orderRoutes
import io.ktor.server.application.*

fun Application.configureRouting() {
    orderRoutes()
}
