package com.soramitsukh.pos.gateway.http

import io.ktor.client.*
import io.ktor.client.engine.cio.*

object GatewayHttpClient {
    val client = HttpClient(CIO)
}
