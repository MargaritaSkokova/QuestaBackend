package com.maran.data.repository

import com.maran.data.models.Model.Answer
import com.maran.data.models.Model.Question

interface IAnswerRepository: IRepository<Answer> {
    suspend fun getByQuestion(question: Question): List<Answer>
}