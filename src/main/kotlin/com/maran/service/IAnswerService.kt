package com.maran.service

import com.maran.data.models.Model.Answer
import com.maran.data.models.Model.Question
import com.maran.service.results.OperationResult

interface IAnswerService : IService<Answer> {
    suspend fun getByQuestion(question: Question): OperationResult
}