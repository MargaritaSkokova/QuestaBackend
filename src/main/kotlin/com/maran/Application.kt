package com.maran

import com.maran.controller.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val component = DaggerQuestaComponent.builder().questaModule(QuestaModule()).build()

    configureSecurity(component.userService())
    configureSerialization()
    configureDatabases()
    configureTestRouting(component.testService())
    configureAnswerRouting(component.answerService())
    configureQuestionRouting(component.questionService())
    configureResultRouting(component.resultService())
    configureRoleRouting(component.roleService())
    configureThemeRouting(component.themeService())
    configureUserRouting(component.userService())
}
