package com.soramitsukh.pos.product

import org.jetbrains.exposed.sql.Table

object Products : Table("products") {
    val id = uuid("id")
    val name = varchar("name", 255)
    val price = decimal("price", 10, 2)
    val stock = integer("stock")

    override val primaryKey = PrimaryKey(id)
}
