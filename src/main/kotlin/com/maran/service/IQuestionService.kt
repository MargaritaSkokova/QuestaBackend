package com.maran.service

import com.maran.data.models.Model.Question
import com.maran.data.models.Model.Test
import com.maran.service.results.OperationResult

interface IQuestionService : IService<Question> {
    suspend fun getByTest(test: Test): OperationResult
}