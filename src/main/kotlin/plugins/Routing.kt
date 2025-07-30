package com.plugins

import com.controller.AuthController
import com.controller.TransactionController
import com.routes.authRoutes
import com.routes.health
import com.routes.transactionRoutes
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val authController by inject<AuthController>()
    val transactionController by inject<TransactionController>()

    routing {
        health()
        authRoutes(authController)
        authenticate("auth-jwt") {
            transactionRoutes(transactionController)
        }
    }
}
