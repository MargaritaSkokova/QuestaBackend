package com.maran

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/know") {
            call.respondText("I know, you know, we know, Lee know")
        }
    }
}
