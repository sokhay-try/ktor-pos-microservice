package com.soramitsukh.pos.db

import org.jetbrains.exposed.sql.Table
import java.util.UUID

object Users : Table("users") {
    val id = uuid("id")
    val username = varchar("username", 50).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val role = varchar("role", 20)

    override val primaryKey = PrimaryKey(id)
}
