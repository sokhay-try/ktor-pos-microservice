package com.soramitsukh.pos.order

import com.soramitsukh.pos.common.BadRequestException
import com.soramitsukh.pos.common.NotFoundException
import com.soramitsukh.pos.http.ProductClient
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object OrderService {

    fun create(request: CreateOrderRequest): OrderResponse = runBlocking {

        if (request.items.isEmpty()) {
            throw BadRequestException("Order items required")
        }

        val orderIdVal = UUID.randomUUID()
        var total = 0.0

        // 1️⃣ Fetch products & validate stock
        val products = request.items.map { item ->
            val product = ProductClient.getProduct(item.productId)

            if (item.quantity <= 0) {
                throw BadRequestException("Quantity must be > 0")
            }

            if (product.stock < item.quantity) {
                throw BadRequestException("Insufficient stock for ${product.name}")
            }

            total += product.price * item.quantity
            product
        }

        // 2️⃣ Deduct stock (REMOTE CALL)
        request.items.forEach {
            ProductClient.deductStock(it.productId, it.quantity)
        }

        // 3️⃣ Save order locally
        transaction {
            Orders.insert {
                it[id] = orderIdVal
                it[totalAmount] = total.toBigDecimal()
            }

            request.items.forEachIndexed { index, item ->
                OrderItems.insert {
                    it[id] = UUID.randomUUID()
                    it[orderId] = orderIdVal
                    it[productId] = UUID.fromString(item.productId)
                    it[quantity] = item.quantity
                    it[price] = products[index].price.toBigDecimal()
                }
            }
        }

        getById(orderIdVal.toString())
    }

    fun getById(id: String): OrderResponse {
        val uuid = runCatching { UUID.fromString(id) }
            .getOrElse { throw BadRequestException("Invalid order id") }

        return transaction {
            val order = Orders.selectAll().where { Orders.id eq uuid }.singleOrNull()
                ?: throw NotFoundException("Order not found")

            val items = OrderItems
                .selectAll().where { OrderItems.orderId eq uuid }
                .map {
                    OrderItemResponse(
                        productId = it[OrderItems.productId].toString(),
                        quantity = it[OrderItems.quantity],
                        price = it[OrderItems.price].toDouble()
                    )
                }

            OrderResponse(
                id = uuid.toString(),
                totalAmount = order[Orders.totalAmount].toDouble(),
                items = items
            )
        }
    }
}
