package com.soramitsukh.pos.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {
    private const val secret = "pos-secret1"
    private const val issuer = "pos-auth"
    private const val audience = "pos-users"

    fun generate(userId: String, role: String): String =
        JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("userId", userId)
            .withClaim("role", role)
            .withExpiresAt(Date(System.currentTimeMillis() + 86400000))
            .sign(Algorithm.HMAC256(secret))
}
