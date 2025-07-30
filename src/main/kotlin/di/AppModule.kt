package com.di

import com.controller.AuthController
import com.controller.TransactionController
import com.repository.AuthRepository
import com.repository.TransactionRepository
import com.services.AuthService
import com.services.TransactionService
import org.koin.dsl.module

val appModule = module {
//    Auth DI
    single { AuthRepository() }
    single { AuthService(authRepository = get()) }
    single { AuthController(authService = get()) }

// Transaction DI
    single { TransactionRepository() }
    single { TransactionService(transactionRepository = get()) }
    single { TransactionController(transactionService = get()) }
}