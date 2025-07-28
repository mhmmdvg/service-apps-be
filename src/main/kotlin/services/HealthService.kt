package com.services

import com.models.DatabaseHealth
import com.models.HTTPResponse
import com.models.HealthResponse
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class HealthService {

   suspend fun checkHealth(): HTTPResponse<HealthResponse> {
        return try {
            val dbHealth = checkDatabaseHealth()

            HTTPResponse<HealthResponse>(
                success = true,
                message = "Health check successful",
                data = HealthResponse(
                    database = dbHealth,
                    timestamp = System.currentTimeMillis()
                )
            )
        } catch (error: Exception) {
            HTTPResponse<HealthResponse>(
                success = false,
                message = "Health check failed: ${error.message}"
            )
        }
    }


    private suspend fun checkDatabaseHealth(): DatabaseHealth {
        return try {
            val startTime = System.currentTimeMillis()

            val result = withTimeoutOrNull(5000) {
                performDatabaseQuery()
            }

            val responseTime = System.currentTimeMillis() - startTime

            if (result != null) {
                DatabaseHealth(
                    status = "healthy",
                    responseTime = responseTime
                )
            } else {
                DatabaseHealth(
                    status = "timeout",
                    error = "Database query timed out after 5 seconds"
                )
            }
        } catch (error: Exception) {
            DatabaseHealth(
                status = "error",
                error = error.message ?: "Database connection failed"
            )
        }
    }

    private suspend fun performDatabaseQuery(): Int {
        return newSuspendedTransaction {
            exec("SELECT 1") { rs ->
                rs.next()
                rs.getInt(1)
            } ?: throw Exception("Query returned null")
        }
    }

    private fun determineOverallStatus(dbHealth: DatabaseHealth): String {
        return when (dbHealth.status) {
            "healthy" -> "healthy"
            "timeout", "error" -> "unhealthy"
            else -> "unknown"
        }
    }
}