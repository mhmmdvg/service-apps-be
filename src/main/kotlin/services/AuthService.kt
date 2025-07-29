package com.services

import com.models.AuthenticationRequest
import com.models.AuthenticationResponse
import com.models.HTTPResponse
import com.repository.AuthRepository
class AuthService(
    private val authRepository: AuthRepository,
) {
    suspend fun authenticate(authRequest: AuthenticationRequest): HTTPResponse<AuthenticationResponse> {
        return try {
            val user = authRepository.findByEmail(authRequest.email)
                ?: return HTTPResponse(
                    success = false,
                    message = "Invalid Credentials"
                )

            val isPasswordValid = PasswordService.verifyPassword(
                plain = authRequest.password,
                hashed = user.password
            )

            if (!isPasswordValid) {
                return HTTPResponse(
                    success = false,
                    message = "Invalid Credentials"
                )
            }

            if (!user.isActive) {
                return HTTPResponse(
                    success = false,
                    message = "User is not active"
                )
            }

            val token = JWTService.generateToken(user.id)
            val userResponse = AuthenticationResponse(
                id = user.id,
                name = user.name,
                email = user.email,
                username = user.username,
                token = token,
                isActive = user.isActive
            )

            HTTPResponse(
                success = true,
                message = "Login Successful",
                data = userResponse
            )
        } catch (error: Exception) {
            println("Authentication error: ${error.message}")
            error.printStackTrace()

            HTTPResponse(
                success = false,
                message = "An error occurred during login: ${error.message}"
            )
        }
    }
}