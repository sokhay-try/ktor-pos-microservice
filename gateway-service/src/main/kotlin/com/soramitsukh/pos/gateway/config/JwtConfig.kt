package com.soramitsukh.pos.gateway.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

object JwtConfig {
    private const val secret = "pos-secret1"
    private const val issuer = "pos-auth"
    private const val audience = "pos-users"

    fun verifier() =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .withAudience(audience)
            .build()
}
