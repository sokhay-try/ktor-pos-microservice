package com.soramitsukh.pos.common

open class ApiException(
    val statusCode: Int,
    override val message: String
) : RuntimeException(message)

class BadRequestException(message: String) : ApiException(400, message)
class NotFoundException(message: String) : ApiException(404, message)
