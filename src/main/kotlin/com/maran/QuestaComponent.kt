package com.maran

import com.maran.service.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [QuestaModule::class])
interface QuestaComponent {
    fun userService(): UserService
    fun answerService(): AnswerService
    fun questionService(): QuestionService
    fun resultService(): ResultService
    fun roleService(): RoleService
    fun testService(): TestService
    fun themeService(): ThemeService
}