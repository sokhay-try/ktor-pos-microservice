package com.soramitsukh.pos.http

import com.soramitsukh.pos.common.ApiResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object ProductClient {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private const val BASE_URL = "http://product-service:8081"

    suspend fun getProduct(productId: String): ProductDto {
        return client
            .get("$BASE_URL/products/$productId")
            .body<ApiResponse<ProductDto>>()
            .data
            ?: throw RuntimeException("Product not found")
    }

    suspend fun deductStock(productId: String, quantity: Int) {
        client.post("$BASE_URL/products/$productId/deduct") {
            contentType(ContentType.Application.Json)
            setBody(DeductStockRequest(quantity))
        }

    }
}

@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val price: Double,
    val stock: Int
)

@Serializable
data class DeductStockRequest(
    val quantity: Int
)

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)
