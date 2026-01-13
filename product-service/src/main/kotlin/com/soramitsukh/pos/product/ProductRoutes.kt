package com.soramitsukh.pos.product

import com.soramitsukh.pos.common.ApiResponse
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.productRoutes() {

    routing {

        // POST /products
        post("/products") {
            val request = call.receive<CreateProductRequest>()
            val product = ProductService.create(request)
            call.respond(product)
        }

        // GET /products
        get("/products") {
            val products = ProductService.getAll()
            call.respond(products)
        }

        get("/products/{id}") {

            val id = call.parameters["id"]
                ?: throw BadRequestException("Product ID is required")

            val product = ProductService.getById(id)

            call.respond(ApiResponse(success = true, data = product))
        }

        put("/products/{id}") {
            val id = call.parameters["id"]
                ?: throw BadRequestException("Product ID is required")

            val request = call.receive<UpdateProductRequest>()
            val product = ProductService.update(id, request)

            call.respond(ApiResponse(true, product))
        }

        delete("/products/{id}") {
            val id = call.parameters["id"]
                ?: throw BadRequestException("Product ID is required")

            ProductService.delete(id)
            call.respond(
                ApiResponse(
                    true,
                    null,
                    message = "Product deleted successfully"
                )
            )
        }

        post("/products/{id}/deduct") {

            val id = call.parameters["id"]
                ?: throw BadRequestException("Product ID is required")

            val request = call.receive<DeductStockRequest>()

            ProductService.deductStock(id, request.quantity)

            call.respond(ApiResponse(true, data = null, message = "Stock deducted"))
        }



    }
}
