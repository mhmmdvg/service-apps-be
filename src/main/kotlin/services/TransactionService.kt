package com.services

import com.models.HTTPResponse
import com.models.TransactionDetailResponse
import com.models.TransactionRequest
import com.models.TransactionResponse
import com.repository.TransactionRepository

class TransactionService(
    private val transactionRepository: TransactionRepository
) {
    fun createTransaction(req: TransactionRequest): HTTPResponse<TransactionResponse> {
        return try {
            val transaction = transactionRepository.createTransaction(req)
                ?: return HTTPResponse(
                    success = false,
                    message = "Failed to create transaction"
                )

            HTTPResponse(
                success = true,
                message = "Transaction created successfully",
                data = transaction
            )
        } catch (error: Exception) {
            HTTPResponse(
                success = false,
                message = "An error occurred during transaction creation: ${error.message}"
            )
        }
    }

    fun getTransactionById(id: String): HTTPResponse<TransactionDetailResponse> {
        return try {
            val transaction = transactionRepository.getTransactionById(id)
                ?: return HTTPResponse(
                    success = false,
                    message = "Transaction not found"
                )

            HTTPResponse(
                success = true,
                message = "Transaction found successfully",
                data = transaction
            )
        } catch (error: Exception) {
            HTTPResponse(
                success = false,
                message = "An error occurred during transaction retrieval: ${error.message}"
            )
        }
    }

    fun getAllTransactions(): HTTPResponse<List<TransactionDetailResponse>> {
        return try {
            val transaction = transactionRepository.getAllTransactions()

            HTTPResponse(
                success = true,
                message = "Transactions retrieved successfully",
                data = transaction
            )
        } catch (error: Exception) {
            HTTPResponse(
                success = false,
                message = "An error occurred during transaction retrieval: ${error.message}"
            )
        }
    }
}