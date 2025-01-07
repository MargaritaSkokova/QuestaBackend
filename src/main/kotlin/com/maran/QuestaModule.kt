package com.maran

import com.maran.data.repository.*
import dagger.Provides
import dagger.Module
import javax.inject.Singleton

@Module
class QuestaModule {
    @Provides
    @Singleton
    fun answerRepository(): IAnswerRepository {
        return AnswerRepository()
    }

    @Provides
    @Singleton
    fun questionRepository(): IQuestionRepository {
        return QuestionRepository()
    }

    @Provides
    @Singleton
    fun resultRepository(): IResultRepository {
        return ResultRepository()
    }

    @Provides
    @Singleton
    fun roleRepository(): IRoleRepository {
        return RoleRepository()
    }

    @Provides
    @Singleton
    fun testRepository(): ITestRepository {
        return TestRepository()
    }

    @Provides
    @Singleton
    fun themeRepository(): IThemeRepository {
        return ThemeRepository()
    }

    @Provides
    @Singleton
    fun userRepository(): IUserRepository {
        return UserRepository()
    }
}