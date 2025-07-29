package com.plugins

import com.controller.AuthController
import com.routes.authRoutes
import com.routes.health
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val authController by inject<AuthController>()

    routing {
        health()
        authRoutes(authController)
        authenticate("auth-jwt") {

        }
    }
}
