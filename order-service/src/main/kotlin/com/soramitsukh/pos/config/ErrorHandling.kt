package com.soramitsukh.pos.config

import com.soramitsukh.pos.common.ApiException
import com.soramitsukh.pos.common.ApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureErrorHandling() {
    install(StatusPages) {

        exception<ApiException> { call, cause ->
            call.respond(
                HttpStatusCode.fromValue(cause.statusCode),
                ApiResponse<Unit>(
                    success = false,
                    message = cause.message
                )
            )
        }

        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ApiResponse<Unit>(
                    success = false,
                    message = "Internal server error"
                )
            )
            cause.printStackTrace()
        }
    }
}
