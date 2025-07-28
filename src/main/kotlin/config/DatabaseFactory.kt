package com.config

import io.ktor.util.logging.Logger
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        Database.connect(
            url = System.getenv("DATABASE_URL") ?: "jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;",
            driver = System.getenv("DATABASE_DRIVER") ?: "org.postgresql.Driver",
            user = System.getenv("DATABASE_USER") ?: "postgres",
            password = System.getenv("DATABASE_PASSWORD") ?: "password"
        )
    }
}