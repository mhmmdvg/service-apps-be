package com.routes

import com.services.HealthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.health() {
    get("/health") {
        val healthService = HealthService()
        val healthStatus = healthService.checkHealth()

        val statusCode = if (healthStatus.data?.database?.status == "healthy") {
            HttpStatusCode.OK
        } else {
            HttpStatusCode.ServiceUnavailable
        }

        call.respond(statusCode, healthStatus)
    }
}