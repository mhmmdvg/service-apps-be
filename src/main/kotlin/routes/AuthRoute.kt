package com.routes

import com.controller.AuthController
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.authRoutes(authController: AuthController) {
    route("/auth") {
        post("/login") {
            authController.login(call)
        }
    }
}