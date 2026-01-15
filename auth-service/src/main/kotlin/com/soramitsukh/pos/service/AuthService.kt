package com.soramitsukh.pos.service

import com.soramitsukh.pos.db.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class AuthService {

    fun register(username: String, password: String, role: String) {
        val hash = PasswordService.hash(password)

        transaction {
            Users.insert {
                it[id] = UUID.randomUUID()
                it[Users.username] = username
                it[passwordHash] = hash
                it[Users.role] = role
            }
        }
    }

    fun validate(username: String, password: String): Pair<String, String> {
        val user = transaction {
            Users.select { Users.username eq username }.singleOrNull()
        } ?: throw RuntimeException("Invalid credentials")

        if (!PasswordService.verify(password, user[Users.passwordHash])) {
            throw RuntimeException("Invalid credentials")
        }

        return user[Users.id].toString() to user[Users.role]
    }
}
