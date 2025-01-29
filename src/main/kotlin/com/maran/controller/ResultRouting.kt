package com.maran.controller

import com.maran.data.models.Model.*
import com.maran.service.IResultService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureResultRouting(resultService: IResultService) {
    routing {
        authenticate("auth-jwt") {
            post("/result") {
                val value = call.receive<Dto.Result>()
                val model = resultService.mapDtoToModel(value)
                if (model == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val result = resultService.insert(model)
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.Created)
                    return@post
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/result/all") {
                val result = resultService.getAll()
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> resultService.mapModelToDto(el as Result) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/result/id/{id}") {
                val result = resultService.getById(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> resultService.mapModelToDto(el as Result) }.single())
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/result/test/{id}") {
                val result = resultService.getByTest(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> resultService.mapModelToDto(el as Result) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            delete("/result/{id}") {
                val result = resultService.delete(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK)
                    return@delete
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            put("/result") {
                val value = call.receive<Dto.Result>()
                val model = resultService.mapDtoToModel(value)
                if (model == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }
                val result = resultService.update(model)
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.Created)
                    return@put
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }
    }
}