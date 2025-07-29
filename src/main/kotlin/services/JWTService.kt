package com.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date

object JWTService {
    private val SECRET = System.getenv("JWT_SECRET") ?: "my-jwt-secret-token"
    private val ISSUER = System.getenv("JWT_ISSUER") ?: "service-apps"
    private val AUDIENCE = System.getenv("JWT_AUDIENCE") ?: "service-apps-users"
    private val VALIDITY_MS = System.getenv("JWT_VALIDITY_HOURS")?.toLong()?.times(3600000)
        ?: (24 * 3600000)

    private val algorithm = Algorithm.HMAC256(SECRET)

    val verifier: JWTVerifier = JWT.require(algorithm)
        .withAudience(AUDIENCE)
        .withIssuer(ISSUER)
        .build()

    fun generateToken(userId: String): String {
        return JWT.create()
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .withClaim("userId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY_MS))
            .sign(algorithm)
    }

    fun verifyToken(token: String): DecodedJWT? {
        return try {
            JWT.require(algorithm)
                .withAudience(AUDIENCE)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
        } catch (error: Exception) {
            null
        }
    }
}