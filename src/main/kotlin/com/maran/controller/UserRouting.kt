package com.maran.controller

import com.maran.data.models.Model.User
import com.maran.service.IUserService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureUserRouting(userService: IUserService) {
    routing {
        post("/user") {
            val user = call.receive<User>()
            val result = userService.insert(user)
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.Created)
                return@post
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        get("/user/all") {
            val result = userService.getAll()
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.OK, result.value)
                return@get
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        get("/user/id/{id}") {
            val result = userService.getById(UUID.fromString(call.parameters["id"]))
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.OK, result.value)
                return@get
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        delete("/user/{id}") {
            val result = userService.delete(UUID.fromString(call.parameters["id"]))
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.OK)
                return@delete
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        get("/user/name") {
            val name = call.parameters["name"]
            if (name == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            } else {
                val result = userService.getByName(name)
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value)
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        put("/user") {
            val user = call.receive<User>()
            val result = userService.update(user)
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.Created)
                return@put
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }
    }
}