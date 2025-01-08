package com.maran.controller

import com.maran.data.models.Model.Role
import com.maran.service.IRoleService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRoleRouting(roleService: IRoleService) {
    routing {
        post("/role") {
            val role = call.receive<Role>()
            val result = roleService.insert(role)
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.Created)
                return@post
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        get("/role/all") {
            val result = roleService.getAll()
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.OK, result.value)
                return@get
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        get("/role/id/{id}") {
            val result = roleService.getById(UUID.fromString(call.parameters["id"]))
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.OK, result.value)
                return@get
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        delete("/role/{id}") {
            val result = roleService.delete(UUID.fromString(call.parameters["id"]))
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.OK)
                return@delete
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        get("/role/name") {
            val name = call.parameters["name"]
            if (name == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            } else {
                val result = roleService.getByName(name)
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value)
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        put("/role") {
            val role = call.receive<Role>()
            val result = roleService.update(role)
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.Created)
                return@put
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }
    }
}