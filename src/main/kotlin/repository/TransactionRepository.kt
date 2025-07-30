package com.repository

import com.db.tables.Transactions
import com.db.tables.Users
import com.models.TransactionDetailResponse
import com.models.TransactionRequest
import com.models.TransactionResponse
import com.models.UserData
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.UUID

class TransactionRepository {
    fun createTransaction(req: TransactionRequest): TransactionResponse? {
        return transaction {
            val transactionId = Transactions.insertAndGetId {
                it[title] = req.title
                it[description] = req.description
                it[deviceBrand] = req.deviceBrand
                it[deviceModel] = req.deviceModel
                it[deviceSecurity] = req.deviceSecurity
                it[customerName] = req.customerName
                it[customerNumber] = req.customerNumber
                it[customerEmail] = req.customerEmail
                it[amount] = req.amount
                it[cashierId] = UUID.fromString(req.cashierId)
                it[createdAt] = Instant.now()
            }

            TransactionResponse(
                id = transactionId.toString(),
                title = req.title,
                description = req.description
            )
        }
    }

    fun getTransactionById(id: String): TransactionDetailResponse? {
        return transaction {
            Transactions.select { Transactions.id eq UUID.fromString(id) }
                .singleOrNull()
                ?.let { row ->
                    TransactionDetailResponse(
                        id = row[Transactions.id].toString(),
                        title = row[Transactions.title],
                        description = row[Transactions.description],
                        deviceBrand = row[Transactions.deviceBrand],
                        deviceModel = row[Transactions.deviceModel],
                        deviceSecurity = row[Transactions.deviceSecurity],
                        customerName = row[Transactions.customerName],
                        customerNumber = row[Transactions.customerNumber],
                        customerEmail = row[Transactions.customerEmail],
                        amount = row[Transactions.amount],
                        cashierId = row[Transactions.cashierId].toString(),
                        createdAt = row[Transactions.createdAt]
                    )
                }
        }
    }

    fun getAllTransactions(): List<TransactionDetailResponse> {
        return transaction {
            Transactions.selectAll()
                .map { row ->
                    TransactionDetailResponse(
                        id = row[Transactions.id].toString(),
                        title = row[Transactions.title],
                        description = row[Transactions.description],
                        deviceBrand = row[Transactions.deviceBrand],
                        deviceModel = row[Transactions.deviceModel],
                        deviceSecurity = row[Transactions.deviceSecurity],
                        customerName = row[Transactions.customerName],
                        customerNumber = row[Transactions.customerNumber],
                        customerEmail = row[Transactions.customerEmail],
                        amount = row[Transactions.amount],
                        cashierId = row[Transactions.cashierId].toString(),
                        createdAt = row[Transactions.createdAt]
                    )
                }
        }
    }
}