package com.soramitsukh.pos.gateway.loadbalancer

import kotlin.random.Random

object LoadBalancer {

    fun choose(instances: List<String>): String {
        if (instances.isEmpty()) throw RuntimeException("No instances available")
        return instances[Random.nextInt(instances.size)]
    }
}
