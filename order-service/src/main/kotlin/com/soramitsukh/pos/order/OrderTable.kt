package com.soramitsukh.pos.order

import org.jetbrains.exposed.sql.Table

object Orders : Table("orders") {
    val id = uuid("id")
    val totalAmount = decimal("total_amount", 10, 2)
    override val primaryKey = PrimaryKey(id)
}

object OrderItems : Table("order_items") {
    val id = uuid("id")
    val orderId = uuid("order_id")
    val productId = uuid("product_id")
    val quantity = integer("quantity")
    val price = decimal("price", 10, 2)
    override val primaryKey = PrimaryKey(id)
}
