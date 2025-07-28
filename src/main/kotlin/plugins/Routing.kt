package com.plugins

import com.routes.authRoutes
import com.routes.health
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        health()
        authRoutes()
        authenticate("auth-jwt") {

        }
    }
}
