package com.routes

import com.models.AuthenticationRequest
import com.models.ErrorResponse
import com.services.AuthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route


fun Route.authRoutes() {
    val authService = AuthService()

    route("/auth") {
        post("/login") {
            try {
                val loginRequest = call.receive<AuthenticationRequest>()

                if (loginRequest.email.isBlank() || loginRequest.password.isBlank()) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(message = "Email and password are required")
                    )
                    return@post
                }

                val loginResponse = authService.authenticate(loginRequest)

                val statusCode = if (loginResponse.success) {
                    HttpStatusCode.OK
                } else {
                    HttpStatusCode.Unauthorized
                }

                call.respond(statusCode, loginResponse)
            } catch (error: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse(message = "An error occurred during login: ${error.message}")
                )
            }
        }
    }
}