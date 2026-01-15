package com.soramitsukh.pos.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(environment: ApplicationEnvironment) {
        val config = environment.config

        val hikariConfig = HikariConfig().apply {
            jdbcUrl = config.property("database.url").getString()
            driverClassName = config.property("database.driver").getString()
            username = config.property("database.user").getString()
            password = config.property("database.password").getString()
            maximumPoolSize = 5
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

        // ✅ 1. CONNECT FIRST
        val dataSource = HikariDataSource(hikariConfig)
        Database.connect(dataSource)

        // ✅ 2. ONLY AFTER CONNECT → run Exposed code
        transaction {
            SchemaUtils.create(
                Users
            )
        }
    }
}
