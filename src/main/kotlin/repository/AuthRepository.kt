package com.repository

import com.db.tables.Users
import com.models.UserData
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class AuthRepository {
    fun findByEmail(email: String): UserData? {
        return transaction {
            Users.select { Users.email eq email }
                .singleOrNull()
                ?.let { row ->
                    UserData(
                        id = row[Users.id].toString(),
                        name = row[Users.name],
                        email = row[Users.email],
                        username = row[Users.username],
                        password = row[Users.password],
                        isActive = row[Users.isActive]
                    )
                }
        }
    }
}