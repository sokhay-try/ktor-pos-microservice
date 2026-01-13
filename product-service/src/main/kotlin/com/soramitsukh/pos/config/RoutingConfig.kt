package com.soramitsukh.pos.config

import com.soramitsukh.pos.product.productRoutes
import io.ktor.server.application.*

fun Application.configureRouting() {
    productRoutes()
}
