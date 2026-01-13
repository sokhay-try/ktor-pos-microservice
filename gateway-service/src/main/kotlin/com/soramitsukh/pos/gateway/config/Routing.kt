package com.soramitsukh.pos.gateway.config

import com.soramitsukh.pos.gateway.proxy.proxyRequest
import io.ktor.server.application.*
import io.ktor.server.request.uri
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {

        route("/products/{...}") {
            handle {
                proxyRequest(call, ServiceConfig.PRODUCT_SERVICE)
            }
        }

        route("/orders/{...}") {
            handle {
                proxyRequest(call, ServiceConfig.ORDER_SERVICE)
            }
        }
    }
}
