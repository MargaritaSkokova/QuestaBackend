package com.maran

import com.maran.data.models.Model.Theme
import com.maran.service.IThemeService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRouting(themeRepository: IThemeService) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/know") {
            call.respondText("I know, you know, we know, Lee know")
        }

        post("/new") {
            themeRepository.insert(Theme(id = UUID.randomUUID(), name = "Dark"))
            call.respond(HttpStatusCode.Created)
        }

        get("/theme") {
            try {
                val themes = themeRepository.getAll()
                println(themes)
//                call.respond(HttpStatusCode.OK)
                call.respond((themes as OperationResult.SuccessResult).value)
            } catch (e: Exception) {
                println(e.localizedMessage)
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        delete("/{id}") {
            themeRepository.delete(UUID.fromString(call.parameters["id"]))
            call.respond(HttpStatusCode.OK)
        }
    }
}
