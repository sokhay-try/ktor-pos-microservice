package com.soramitsukh.pos.order

import com.soramitsukh.pos.common.ApiResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.orderRoutes() {

    routing {
        post("/orders") {
            println("Handled by " + System.getenv("HOSTNAME"))
            val req = call.receive<CreateOrderRequest>()
            call.respond(ApiResponse(true, OrderService.create(req)))
        }

        get("/orders/{id}") {
            val id = call.parameters["id"]!!
            call.respond(ApiResponse(true, OrderService.getById(id)))
        }
    }
}
