package com.soramitsukh.pos.service

import org.mindrot.jbcrypt.BCrypt

object PasswordService {
    fun hash(password: String) = BCrypt.hashpw(password, BCrypt.gensalt())
    fun verify(password: String, hash: String) = BCrypt.checkpw(password, hash)
}
