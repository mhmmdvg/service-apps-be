package com.routes

import com.controller.TransactionController
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.transactionRoutes(transactionController: TransactionController) {
    route("/transaction") {
        post {
            transactionController.createTransaction(call)
        }
        get("/{id}") {
            transactionController.getTransactionById(call)
        }
    }

    route("/transactions") {
        get {
            transactionController.getAllTransactions(call)
        }
    }
}