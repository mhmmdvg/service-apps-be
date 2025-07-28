package com.models

import kotlinx.serialization.Serializable

@Serializable
data class HealthResponse(
    val database: DatabaseHealth,
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class DatabaseHealth(
    val status: String,
    val responseTime: Long? = null,
    val error: String? = null
)