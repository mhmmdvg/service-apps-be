package com.models

import kotlinx.serialization.Serializable

@Serializable
data class HTTPResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)
