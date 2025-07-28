package com.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRequest(
    val email: String,
    val password: String,
)

@Serializable
data class AuthenticationResponse(
    val id: String,
    val name: String,
    val email: String,
    val username: String,
    val token: String,
    val isActive: Boolean,
)


