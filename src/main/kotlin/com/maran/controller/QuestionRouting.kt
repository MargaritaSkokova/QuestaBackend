package com.maran.controller

import com.maran.data.models.Model.*
import com.maran.service.IQuestionService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureQuestionRouting(questionService: IQuestionService) {
    routing {
        authenticate("auth-jwt") {
            post("/question") {
                val question = call.receive<Dto.Question>()
                val model = questionService.mapDtoToModel(question)
                if (model == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val result = questionService.insert(model)
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.Created)
                    return@post
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/question/all") {
                val result = questionService.getAll()
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> questionService.mapModelToDto(el as Question) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/question/id/{id}") {
                val result = questionService.getById(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> questionService.mapModelToDto(el as Question) }.single())
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/question/test/{id}") {
                val result = questionService.getByTest(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> questionService.mapModelToDto(el as Question) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            delete("/question/{id}") {
                val result = questionService.delete(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK)
                    return@delete
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            put("/question") {
                val question = call.receive<Dto.Question>()
                val model = questionService.mapDtoToModel(question)
                if (model == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }
                val result = questionService.update(model)
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