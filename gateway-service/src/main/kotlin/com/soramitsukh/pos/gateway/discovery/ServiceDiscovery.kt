package com.soramitsukh.pos.gateway.discovery

import java.net.InetAddress

object ServiceDiscovery {

    fun getInstances(serviceName: String): List<String> {
        return InetAddress.getAllByName(serviceName).map {
            it.hostAddress
        }
    }
}
