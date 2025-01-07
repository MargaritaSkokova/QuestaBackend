package com.maran

import com.maran.data.repository.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val component = DaggerQuestaComponent.builder().questaModule(QuestaModule()).build()

    val answerRepository = AnswerRepository()
    val questionRepository = QuestionRepository()
    val resultRepository = ResultRepository()
    val roleRepository = RoleRepository()
    val testRepository = TestRepository()
    val themeRepository = ThemeRepository()
    val userRepository = UserRepository()

//    configureSecurity()
    configureSerialization()
    configureDatabases()
    configureRouting(component.themeService())
}
