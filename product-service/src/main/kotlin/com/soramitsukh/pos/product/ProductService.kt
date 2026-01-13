package com.soramitsukh.pos.product

import com.soramitsukh.pos.common.BadRequestException
import com.soramitsukh.pos.common.NotFoundException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object ProductService {

    /**
     * CREATE PRODUCT
     */
    fun create(request: CreateProductRequest): ProductResponse {

        // ✅ VALIDATION RULES
        if (request.name.isBlank()) {
            throw BadRequestException("Product name is required")
        }

        if (request.price <= 0) {
            throw BadRequestException("Price must be greater than 0")
        }

        if (request.stock < 0) {
            throw BadRequestException("Stock cannot be negative")
        }

        val id = UUID.randomUUID()

        transaction {
            Products.insert {
                it[Products.id] = id
                it[name] = request.name.trim()
                it[price] = request.price.toBigDecimal()
                it[stock] = request.stock
            }
        }

        return ProductResponse(
            id = id.toString(),
            name = request.name.trim(),
            price = request.price,
            stock = request.stock
        )
    }

    /**
     * GET ALL PRODUCTS
     */
    fun getAll(): List<ProductResponse> =
        transaction {
            Products.selectAll().map {
                ProductResponse(
                    id = it[Products.id].toString(),
                    name = it[Products.name],
                    price = it[Products.price].toDouble(),
                    stock = it[Products.stock]
                )
            }
        }

    /**
     * GET PRODUCT BY ID
     */
    fun getById(id: String): ProductResponse {
        val uuid = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            throw BadRequestException("Invalid product ID format")
        }

        return transaction {
            Products
                .select { Products.id eq uuid }
                .map {
                    ProductResponse(
                        id = it[Products.id].toString(),
                        name = it[Products.name],
                        price = it[Products.price].toDouble(),
                        stock = it[Products.stock]
                    )
                }
                .singleOrNull()
                ?: throw NotFoundException("Product not found")
        }
    }

    fun update(id: String, request: UpdateProductRequest): ProductResponse {

        if (request.name.isBlank()) {
            throw BadRequestException("Product name is required")
        }

        if (request.price <= 0) {
            throw BadRequestException("Price must be greater than 0")
        }

        if (request.stock < 0) {
            throw BadRequestException("Stock cannot be negative")
        }

        val uuid = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            throw BadRequestException("Invalid product ID format")
        }

        val updatedRows = transaction {
            Products.update({ Products.id eq uuid }) {
                it[name] = request.name.trim()
                it[price] = request.price.toBigDecimal()
                it[stock] = request.stock
            }
        }

        if (updatedRows == 0) {
            throw NotFoundException("Product not found")
        }

        return getById(id)
    }

    fun delete(id: String) {

        val uuid = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            throw BadRequestException("Invalid product ID format")
        }

        val deletedRows = transaction {
            Products.deleteWhere { Products.id eq uuid }
        }

        if (deletedRows == 0) {
            throw NotFoundException("Product not found")
        }
    }

    fun deductStock(id: String, quantity: Int) {

        if (quantity <= 0) {
            throw BadRequestException("Quantity must be greater than 0")
        }

        val uuid = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            throw BadRequestException("Invalid product ID format")
        }

        val updated = transaction {

            val product = Products
                .select { Products.id eq uuid }
                .singleOrNull()
                ?: throw NotFoundException("Product not found")

            if (product[Products.stock] < quantity) {
                throw BadRequestException("Insufficient stock")
            }

            Products.update({ Products.id eq uuid }) {
                it[stock] = product[Products.stock] - quantity
            }
        }

        if (updated == 0) {
            throw NotFoundException("Product not found")
        }
    }



}
