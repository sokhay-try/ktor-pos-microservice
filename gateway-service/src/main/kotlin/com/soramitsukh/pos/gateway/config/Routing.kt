package com.soramitsukh.pos.gateway.config

import com.soramitsukh.pos.gateway.proxy.proxyRequest
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.request.uri
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {

        route("/auth/{path...}") {
            handle {
                proxyRequest(call, ServiceConfig.AUTH_SERVICE)
            }
        }

        authenticate("auth-jwt") {
            route("/products/{path...}") {
                handle {
                    proxyRequest(call, ServiceConfig.PRODUCT_SERVICE)
                }
            }

            route("/orders/{path...}") {
                handle {
                    proxyRequest(call, ServiceConfig.ORDER_SERVICE)
                }
            }
        }
    }
}
