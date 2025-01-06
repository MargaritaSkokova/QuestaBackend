package com.maran

import com.maran.service.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val answerRepository = AnswerRepositoryImpl()
    val questionRepository = QuestionRepositoryImpl()
    val resultRepository = ResultRepositoryImpl()
    val roleRepository = RoleRepositoryImpl()
    val testRepository = TestRepositoryImpl()
    val themeRepository = ThemeRepositoryImpl()
    val userRepository = UserRepositoryImpl()

    configureSecurity()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
