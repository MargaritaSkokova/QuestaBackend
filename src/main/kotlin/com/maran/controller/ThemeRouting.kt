package com.maran.controller

import com.maran.controller.Dto.*
import com.maran.data.models.Model.Theme
import com.maran.service.IThemeService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureThemeRouting(themeService: IThemeService) {
    routing {
        authenticate("auth-jwt") {
            post("/theme") {
                val theme = call.receive<Dto.Theme>()
                val result = themeService.insert(themeService.mapDtoToModel(theme))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.Created)
                    return@post
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/theme/all") {
                val result = themeService.getAll()
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> themeService.mapModelToDto(el as Theme) })
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/theme/id/{id}") {
                val result = themeService.getById(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK, result.value.map { el -> themeService.mapModelToDto(el as Theme) }.single())
                    return@get
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            delete("/theme/{id}") {
                val result = themeService.delete(UUID.fromString(call.parameters["id"]))
                if (result is OperationResult.SuccessResult) {
                    call.respond(HttpStatusCode.OK)
                    return@delete
                } else if (result is OperationResult.FailureResult) {
                    call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                }
            }
        }

        authenticate("auth-jwt") {
            get("/theme/name/{name}") {
                val name = call.parameters["name"]
                if (name == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                } else {
                    val result = themeService.getByName(name)
                    if (result is OperationResult.SuccessResult) {
                        call.respond(HttpStatusCode.OK, result.value.map { el -> themeService.mapModelToDto(el as Theme) })
                        return@get
                    } else if (result is OperationResult.FailureResult) {
                        call.respond(HttpStatusCode.BadRequest, result.errorMessage)
                    }
                }
            }
        }

        authenticate("auth-jwt") {
            put("/theme") {
                val theme = call.receive<Dto.Theme>()
                val result = themeService.update(themeService.mapDtoToModel(theme))
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