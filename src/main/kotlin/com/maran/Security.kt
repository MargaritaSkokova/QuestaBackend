package com.maran

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.maran.data.models.Model
import com.maran.data.models.Model.User
import com.maran.service.IUserService
import com.maran.service.results.OperationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun Application.configureSecurity(userService: IUserService) {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val myRealm = environment.config.property("jwt.realm").getString()
    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT
                .require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .build())
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
    routing {
        post("/login") {
            val userToCheck = call.receive<Model.Authentication>()
            val userReal = userService.getByName(userToCheck.username)
            if (userReal is OperationResult.SuccessResult) {
                if (!BCrypt.checkpw(userToCheck.password, (userReal.value[0] as User).password)) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
            } else if (userReal is OperationResult.FailureResult) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val token = JWT.create()
                .withIssuer(issuer)
                .withClaim("username", userToCheck.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 604800000))
                .sign(Algorithm.HMAC256(secret))
            call.respond(token)
        }

        authenticate("auth-jwt") {
            get("/hello") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
            }
        }
    }
}
