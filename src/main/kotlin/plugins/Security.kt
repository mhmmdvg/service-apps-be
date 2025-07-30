package com.plugins

import com.models.ErrorResponse
import com.services.JWTService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond

fun Application.configureSecurity() {
    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JWTService.verifier)
            validate { credential ->
                val userId = credential.payload.getClaim("userId").asString()

                if (userId != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { defaultScheme, realm ->
                val errorResponse = ErrorResponse(
                    success = false,
                    message = "Authentication failed: Invalid or missing JWT token"
                )
                call.respond(HttpStatusCode.Unauthorized, errorResponse)
            }
        }
    }
}