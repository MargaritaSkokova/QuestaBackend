package com.maran.service

import com.maran.controller.Dto
import com.maran.data.models.Model.Test
import com.maran.data.models.Model.Theme
import com.maran.data.models.Model.User
import com.maran.data.repository.ITestRepository
import com.maran.data.repository.IThemeRepository
import com.maran.data.repository.IUserRepository
import com.maran.service.results.OperationResult
import java.util.*
import javax.inject.Inject

class TestService @Inject constructor(
    private val testRepository: ITestRepository,

    private val themeRepository: IThemeRepository,

    private val userRepository: IUserRepository
) : ITestService {
    override suspend fun getAll(): OperationResult {
        return try {
            OperationResult.SuccessResult(testRepository.getAll())
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getById(id: UUID): OperationResult {
        try {
            val test = testRepository.getById(id) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(test))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun delete(id: UUID): OperationResult {
        try {
            testRepository.delete(id)
            return OperationResult.SuccessResult(listOf())
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun update(value: Test): OperationResult {
        try {
            if (testRepository.getById(value.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }

            if (userRepository.getById(value.author.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }
            var newValue = value
            if (themeRepository.getById(value.theme.id) == null) {
                var newTheme = themeRepository.getByName(value.theme.name)
                if (newTheme != null) {
                    newValue = Test(id = value.id, testType = value.testType, name = value.name, author = value.author, theme = newTheme, description = value.description)
                } else {
                    newTheme = themeRepository.insert(Theme(UUID.randomUUID(), value.theme.name))
                    newValue = Test(id = value.id, testType = value.testType, name = value.name, author = value.author, theme = newTheme!!, description = value.description)
                }
            }

            val test = testRepository.update(newValue) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(test))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun insert(value: Test): OperationResult {
        return try {
            val test = testRepository.insert(value) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(test))
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getByTheme(name: String): OperationResult {
        try {
            val theme = themeRepository.getByName(name) ?: return OperationResult.FailureResult("Not Found")

            val tests = testRepository.getByTheme(theme)
            return OperationResult.SuccessResult(tests)
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getByType(type: String): OperationResult {
        try {
            val tests = testRepository.getByType(type)
            return OperationResult.SuccessResult(tests)
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getByAuthor(name: String): OperationResult {
        try {
            val author = userRepository.getByName(name)
            if (author == null) {
                return OperationResult.FailureResult("Not Found")
            }

            val tests = testRepository.getByAuthor(author)
            return OperationResult.SuccessResult(tests)
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun mapDtoToModel(dto: Dto.Test): Test? {
        val user = userRepository.getByName(dto.author) ?: return null
        val theme = themeRepository.getByName(dto.theme) ?: return null
        return Test(dto.id, dto.testType, dto.name, user, theme, dto.description)
    }

    override fun mapModelToDto(model: Test): Dto.Test {
        return Dto.Test(model.id, model.testType, model.name, model.author.username, model.theme.name, model.description)
    }
}