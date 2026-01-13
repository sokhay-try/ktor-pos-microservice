package com.soramitsukh.pos.order

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderItemRequest(
    val productId: String,
    val quantity: Int
)

@Serializable
data class CreateOrderRequest(
    val items: List<CreateOrderItemRequest>
)

@Serializable
data class OrderItemResponse(
    val productId: String,
    val quantity: Int,
    val price: Double
)

@Serializable
data class OrderResponse(
    val id: String,
    val totalAmount: Double,
    val items: List<OrderItemResponse>
)
