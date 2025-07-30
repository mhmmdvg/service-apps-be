package com.models

import com.serializers.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TransactionRequest(
    val title: String,
    val description: String,
    val deviceBrand: String,
    val deviceModel: String,
    val deviceSecurity: String,
    val customerName: String,
    val customerNumber: String,
    val customerEmail: String,
    val amount: Long,
    val cashierId: String,
)

@Serializable
data class TransactionResponse(
    val id: String,
    val title: String,
    val description: String,
)

@Serializable
data class TransactionDetailResponse(
    val id: String,
    val title: String,
    val description: String,
    val deviceBrand: String,
    val deviceModel: String,
    val deviceSecurity: String,
    val customerName: String,
    val customerNumber: String,
    val customerEmail: String,
    val amount: Long,
    val cashierId: String,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
)