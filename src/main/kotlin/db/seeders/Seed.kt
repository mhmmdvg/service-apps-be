package com.db.seeders

import com.db.tables.Users
import com.services.PasswordService
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant


object Seed {
    fun userSeed() {
        transaction {
            val existingUsers = Users.select { Users.username eq "admin" }.singleOrNull()

            if (existingUsers != null) {
                println("Admin user already exists")
                return@transaction
            }

            Users.insertIgnore {
                it[name] = "Admin"
                it[email] = "admin@mail.com"
                it[username] = "admin"
                it[password] = PasswordService.hashPassword("password")
                it[isActive] = true
                it[createdAt] = Instant.now()
                it[updatedAt] = Instant.now()
                it[deletedAt] = null
            }
            println("Admin user seeded successfully")
        }
    }
}