package com.maran.controller

import com.maran.data.models.Model.*
import com.maran.service.IAnswerService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureAnswerRouting(answerService: IAnswerService) {
    routing {
        authenticate("auth-jwt") {
            post("/answer") {
                val answer = call.receive<Dto.Answer>()
                val model = answerService.mapDtoToModel(answer)
                if (model == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val result = answerService.insert(model)
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.Created)
                    return@post
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/answer/all") {
                val result = answerService.getAll()
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> answerService.mapModelToDto(el as Answer) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/answer/id/{id}") {
                val result = answerService.getById(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> answerService.mapModelToDto(el as Answer) }.single())
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/answer/question/{id}") {
                val result = answerService.getByQuestion(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> answerService.mapModelToDto(el as Answer) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            delete("/answer/{id}") {
                val result = answerService.delete(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK)
                    return@delete
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            put("/answer") {
                val answer = call.receive<Dto.Answer>()
                val model = answerService.mapDtoToModel(answer)
                if (model == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }
                val result = answerService.update(model)
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