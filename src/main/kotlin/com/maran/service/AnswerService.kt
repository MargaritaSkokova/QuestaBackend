package com.maran.service

import com.maran.controller.Dto
import com.maran.data.models.Model.Answer
import com.maran.data.models.Model.Question
import com.maran.data.repository.IAnswerRepository
import com.maran.data.repository.IQuestionRepository
import com.maran.service.results.OperationResult
import java.util.*
import javax.inject.Inject

class AnswerService @Inject constructor(
    private val answerRepository: IAnswerRepository,
    private val questionRepository: IQuestionRepository
) : IAnswerService {
    override suspend fun getByQuestion(id: UUID): OperationResult {
        try {
            val question = questionRepository.getById(id) ?: return OperationResult.FailureResult("Not Found")
            val answer = answerRepository.getByQuestion(question) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(answer)
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun mapDtoToModel(dto: Dto.Answer): Answer? {
        val question = questionRepository.getById(dto.questionId) ?: return null
        return Answer(dto.id, question, dto.text, dto.isCorrect, dto.personality)
    }

    override fun mapModelToDto(model: Answer): Dto.Answer {
        return Dto.Answer(model.id, model.question.id, model.text, model.isCorrect, model.personality)
    }

    override suspend fun getAll(): OperationResult {
        return try {
            OperationResult.SuccessResult(answerRepository.getAll())
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getById(id: UUID): OperationResult {
        try {
            val answer = answerRepository.getById(id) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(answer))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun delete(id: UUID): OperationResult {
        try {
            answerRepository.delete(id)
            return OperationResult.SuccessResult(listOf())
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun update(value: Answer): OperationResult {
        try {
            if (answerRepository.getById(value.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }

            if (questionRepository.getById(value.question.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }

            val answer = answerRepository.update(value) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(answer))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun insert(value: Answer): OperationResult {
        return try {
            val answer = answerRepository.insert(value) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(answer))
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }
}