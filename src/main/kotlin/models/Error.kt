package com.models
import kotlinx.serialization.Serializable


@Serializable
data class ErrorResponse(
    val success: Boolean = false,
    val message: String
)