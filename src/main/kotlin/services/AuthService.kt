package com.services

import com.db.tables.Users
import com.models.AuthenticationRequest
import com.models.AuthenticationResponse
import com.models.HTTPResponse
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class AuthService {
    fun authenticate(authRequest: AuthenticationRequest): HTTPResponse<AuthenticationResponse> {
        return transaction {
            try {
                val user = Users.select {
                    Users.email eq authRequest.email
                }.singleOrNull()

                if (user == null) {
                    return@transaction HTTPResponse(
                        success = false,
                        message = "Invalid Credentials"
                    )
                }

                val isPasswordValid = PasswordService.verifyPassword(
                    plain = authRequest.password,
                    hashed = user[Users.password]
                )

                if (!isPasswordValid) {
                    return@transaction HTTPResponse(
                        success = false,
                        message = "Invalid Credentials"
                    )
                }

                if (!user[Users.isActive]) {
                    return@transaction HTTPResponse(
                        success = false,
                        message = "Account is not active"
                    )
                }

                val token = JWTService.generateToken(user[Users.id].toString())
                val userResponse = AuthenticationResponse(
                    id = user[Users.id].toString(),
                    name = user[Users.name],
                    email = user[Users.email],
                    username = user[Users.username],
                    token = token,
                    isActive = user[Users.isActive]
                )

                HTTPResponse(
                    success = true,
                    message = "Login Successful",
                    data = userResponse
                )
            } catch (e: Exception) {
                println("Authentication error: ${e.message}")
                e.printStackTrace()

                HTTPResponse<AuthenticationResponse>(
                    success = false,
                    message = "Authentication failed: ${e.message}"
                )
            }
        }
    }
}