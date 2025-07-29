package com.di

import com.controller.AuthController
import com.repository.AuthRepository
import com.services.AuthService
import org.koin.dsl.module

val appModule = module {
//    Auth DI
    single { AuthRepository() }
    single { AuthService(authRepository = get()) }
    single { AuthController(authService = get()) }

//
}