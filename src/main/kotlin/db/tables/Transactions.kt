package com.db.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object Transactions : UUIDTable("transactions") {
    val title = varchar("title", 255)
    val description = varchar("description", 255)
    val deviceBrand = varchar("device_brand", 255)
    val deviceModel = varchar("device_model", 255)
    val deviceSecurity = varchar("device_security", 255)
    val customerName = varchar("customer_name", 255)
    val customerNumber = varchar("customer_number", 255)
    val customerEmail = varchar("customer_email", 255)
    val createdAt = timestamp("created_at").default(Instant.now())
    val updatedAt = timestamp("updated_at").nullable()
    val deletedAt = timestamp("deleted_at").nullable()
}