package com.maran.service

import com.maran.data.models.Model.Question
import com.maran.data.models.Model.Test
import com.maran.data.repository.IQuestionRepository
import com.maran.data.repository.ITestRepository
import com.maran.service.results.OperationResult
import java.util.*
import javax.inject.Inject

class QuestionService @Inject constructor(
    private val testRepository: ITestRepository,
    private val questionRepository: IQuestionRepository
) : IQuestionService {
    override suspend fun getAll(): OperationResult {
        return try {
            OperationResult.SuccessResult(questionRepository.getAll())
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getById(id: UUID): OperationResult {
        try {
            val question = questionRepository.getById(id) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(question))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun delete(id: UUID): OperationResult {
        try {
            questionRepository.delete(id)
            return OperationResult.SuccessResult(listOf())
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun update(value: Question): OperationResult {
        try {
            if (questionRepository.getById(value.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }

            if (testRepository.getById(value.test.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }

            val question = questionRepository.update(value) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(question))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun insert(value: Question): OperationResult {
        return try {
            val question = questionRepository.insert(value) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(question))
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getByTest(test: Test): OperationResult {
        try {
            val question = questionRepository.getByTest(test) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(question))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }
}