package com.services

import com.password4j.Hash
import com.password4j.Password

object PasswordService {
    fun hashPassword(plain: String): String {
        val hash: Hash = Password.hash(plain)
            .addRandomSalt(16)
            .withArgon2()
        return hash.result
    }

    fun verifyPassword(plain: String, hashed: String): Boolean {
        return Password.check(plain, hashed).withArgon2()
    }
}