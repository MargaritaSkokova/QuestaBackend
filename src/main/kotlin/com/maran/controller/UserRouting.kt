package com.maran.controller

import com.maran.data.models.Model.User
import com.maran.controller.Dto
import com.maran.service.IUserService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureUserRouting(userService: IUserService) {
    routing {
        post("/user") {
            val user = call.receive<Dto.SignUp>()
            val model = userService.mapSignUpDtoToModel(user)
            if (model == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val result = userService.insert(model)
            if (result is OperationResult.SuccessResult) {
                call.respond(HttpStatusCode.Created)
                return@post
            } else if (result is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest, result.errorMessage)
            }
        }

        authenticate("auth-jwt") {
            get("/user/all") {
                val result = userService.getAll()
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> userService.mapModelToDto(el as User) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/user/id/{id}") {
                val result = userService.getById(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, userService.mapModelToDto(result.value.single() as User))
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            delete("/user/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val user = (userService.getByName(username) as OperationResult.SuccessResult).value[0] as User

                if (user.id.toString() != call.parameters["id"] && user.role.name != "admin") {
                    call.respond(HttpStatusCode.Forbidden)
                    return@delete
                }

                val result = userService.delete(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK)
                    return@delete
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/user/name/{name}") {
                val name = call.parameters["name"]
                if (name == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                } else {
                    val result = userService.getByName(name)
                    if (result is OperationResult.SuccessResult) {
                        call.respond(
                            HttpStatusCode.OK,
                            result.value.map { el -> userService.mapModelToDto(el as User) })
                        return@get
                    } else if (result is OperationResult.FailureResult) {
                        call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                    }
                }
            }
        }

        authenticate("auth-jwt") {
            put("/user") {
                val user = call.receive<Dto.SignUp>()
                val model = userService.mapSignUpDtoToModel(user)
                if (model == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }
                val result = userService.update(model)
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