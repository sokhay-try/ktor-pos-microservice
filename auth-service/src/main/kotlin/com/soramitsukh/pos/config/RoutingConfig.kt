package com.soramitsukh.pos.config

import com.soramitsukh.pos.service.AuthService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable data class RegisterReq(val username: String, val password: String, val role: String)
@Serializable data class LoginReq(val username: String, val password: String)

fun Application.configureRouting(auth: AuthService) {

    routing {

        route("/auth") {
            post("/register") {
                val req = call.receive<RegisterReq>()
                auth.register(req.username, req.password, req.role)
                call.respond(mapOf("success" to true))
            }

            post("/login") {
                val req = call.receive<LoginReq>()
                val (userId, role) = auth.validate(req.username, req.password)
                val token = JwtConfig.generate(userId, role)
                call.respond(mapOf("token" to token))
            }
        }
    }
}
