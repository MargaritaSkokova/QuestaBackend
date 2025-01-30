package com.maran.data.repository

import com.maran.data.models.Model.Question
import com.maran.data.models.Model.Test

interface IQuestionRepository: IRepository<Question> {
    suspend fun getByTest(test: Test): List<Question>
}