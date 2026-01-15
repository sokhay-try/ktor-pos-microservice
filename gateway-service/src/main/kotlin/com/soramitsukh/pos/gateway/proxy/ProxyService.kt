package com.soramitsukh.pos.gateway.proxy

import com.soramitsukh.pos.gateway.discovery.ServiceDiscovery
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import com.soramitsukh.pos.gateway.http.GatewayHttpClient
import com.soramitsukh.pos.gateway.loadbalancer.LoadBalancer
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.httpMethod
import io.ktor.server.request.receiveChannel
import io.ktor.server.request.uri
import io.ktor.server.response.respondBytes

suspend fun proxyRequest(call: ApplicationCall, targetBaseUrl: String) {

    val base = targetBaseUrl.removePrefix("http://")
    val host = base.substringBefore(":")
    val port = base.substringAfter(":")

    val instances = ServiceDiscovery.getInstances(host)
    val selected = LoadBalancer.choose(instances)
    val targetUrl = "http://$selected:$port${call.request.uri}"

    println("Proxy Forwarding to: $targetUrl")

    val principal = call.principal<JWTPrincipal>()
    val userId = principal?.payload?.getClaim("userId")?.asString()
    val role = principal?.payload?.getClaim("role")?.asString()

    val clientResponse: HttpResponse = GatewayHttpClient.client.request(targetUrl) {
        method = call.request.httpMethod

        // Forward headers
        call.request.headers.forEach { key, values ->
            if (!key.equals(HttpHeaders.Host, true)) {
                values.forEach { value -> header(key, value) }
            }
        }

        // Inject identity headers
        if (userId != null) header("X-User-Id", userId)
        if (role != null) header("X-Role", role)

        // Forward body
        setBody(call.receiveChannel())
    }

    call.respondBytes(
        bytes = clientResponse.body(),
        status = clientResponse.status,
        contentType = clientResponse.headers[HttpHeaders.ContentType]?.let { ContentType.parse(it) }
    )
}