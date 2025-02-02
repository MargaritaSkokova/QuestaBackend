package com.maran.controller

import com.maran.data.models.Model.*
import com.maran.service.ITestService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureTestRouting(testService: ITestService) {
    routing {
        authenticate("auth-jwt") {
            post("/test") {
                val test = call.receive<Dto.Test>()
                val model = testService.mapDtoToModel(test)
                if (model == null) {
                    call.respond(HttpStatusCode.BadRequest, "Theme doesn't exist")
                    return@post
                }
                val result = testService.insert(model)
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.Created, "OK")
                    return@post
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/test/all") {
                val result = testService.getAll()
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> testService.mapModelToDto(el as Test) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/test/id/{id}") {
                val result = testService.getById(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> testService.mapModelToDto(el as Test) }.single())
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/test/author/{name}") {
                if (call.parameters["name"] == null) {
                    call.respond(HttpStatusCode.BadRequest)
                }
                val result = testService.getByAuthor(call.parameters["name"]!!)
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> testService.mapModelToDto(el as Test) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/test/theme/{name}") {
                if (call.parameters["name"] == null) {
                    call.respond(HttpStatusCode.BadRequest)
                }
                val result = testService.getByTheme(call.parameters["name"]!!)
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> testService.mapModelToDto(el as Test) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/test/type/{type}") {
                val type = call.parameters["type"]
                if (type == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val result = testService.getByType(type)
                    if (result is OperationResult.SuccessResult) {
                        call.respond(HttpStatusCode.OK, result.value.map { el -> testService.mapModelToDto(el as Test) })
                        return@get
                    } else if (result is OperationResult.FailureResult) {
                        call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                    }
                }
            }
        }

        authenticate("auth-jwt") {
            delete("/test/{id}") {
                val result = testService.delete(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK)
                    return@delete
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            put("/test") {
                val test = call.receive<Dto.Test>()
                val model = testService.mapDtoToModel(test)
                if (model == null) {
                    call.respond(HttpStatusCode.BadRequest, "Theme doesn't exist")
                    return@put
                }
                val result = testService.update(model)
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.Created, "OK")
                    return@put
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }
    }
}