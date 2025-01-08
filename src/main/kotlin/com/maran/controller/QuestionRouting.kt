package com.maran.controller

import com.maran.data.models.Model.*
import com.maran.service.IQuestionService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureQuestionRouting(questionService: IQuestionService) {
    routing {
        post("/question") {
            val question = call.receive<Question>()
            val result = questionService.insert(question)
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.Created)
                return@post
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        get("/question/all") {
            val result = questionService.getAll()
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.OK, result.value)
                return@get
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        get("/question/id/{id}") {
            val result = questionService.getById(UUID.fromString(call.parameters["id"]))
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.OK, result.value)
                return@get
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        get("/question/test") {
            val test = call.receive<Test>()
            val result = questionService.getByTest(test)
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.OK, result.value)
                return@get
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        delete("/question/{id}") {
            val result = questionService.delete(UUID.fromString(call.parameters["id"]))
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.OK)
                return@delete
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        put("/question") {
            val question = call.receive<Question>()
            val result = questionService.update(question)
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.Created)
                return@put
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }
    }
}