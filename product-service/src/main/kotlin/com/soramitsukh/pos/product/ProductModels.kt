package com.soramitsukh.pos.product

import kotlinx.serialization.Serializable

@Serializable
data class CreateProductRequest(
    val name: String,
    val price: Double,
    val stock: Int
)

@Serializable
data class ProductResponse(
    val id: String,
    val name: String,
    val price: Double,
    val stock: Int
)

@Serializable
data class UpdateProductRequest(
    val name: String,
    val price: Double,
    val stock: Int
)

@Serializable
data class DeductStockRequest(
    val quantity: Int
)
