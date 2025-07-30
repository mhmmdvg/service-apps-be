package com.controller

import com.models.HTTPResponse
import com.models.TransactionDetailResponse
import com.models.TransactionRequest
import com.services.TransactionService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond

class TransactionController(
    private val transactionService: TransactionService
) {

    suspend fun createTransaction(call: ApplicationCall) {
        try {
            val req = call.receive<TransactionRequest>()
            val res = transactionService.createTransaction(req)

            if (res.success) {
                call.respond(HttpStatusCode.Created, res)
            } else {
                when (res.message) {
                    "Invalid Credentials" -> call.respond(HttpStatusCode.Unauthorized, res)
                    else -> call.respond(HttpStatusCode.InternalServerError, res)
                }
            }
        } catch (error: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                error.message ?: "An error occurred during transaction creation: ${error.message}"
            )
        }
    }

    suspend fun getTransactionById(call: ApplicationCall) {
        try {
            val id = call.parameters["id"]

            if (id.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    HTTPResponse<TransactionDetailResponse>(
                        success = false,
                        message = "Transaction ID is required"
                    )
                )
                return
            }

            val response = transactionService.getTransactionById(id)

            if (response.success) {
                call.respond(HttpStatusCode.OK, response)
            } else {
                when (response.message) {
                    "Transaction not found" -> call.respond(HttpStatusCode.NotFound, response)
                    else -> call.respond(HttpStatusCode.InternalServerError, response)
                }
            }
        } catch (error: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                error.message ?: "An error occurred during transaction retrieval: ${error.message}"
            )
        }
    }

    suspend fun getAllTransactions(call: ApplicationCall) {
        try {
            val response = transactionService.getAllTransactions()

            if (response.success) {
                call.respond(HttpStatusCode.OK, response)
            } else {
                call.respond(HttpStatusCode.InternalServerError, response)
            }
        } catch (error: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                error.message ?: "An error occurred during transaction retrieval: ${error.message}"
            )
        }
    }
}