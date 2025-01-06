package com.maran.service

import com.maran.data.models.Question
import com.maran.data.models.Test
import java.util.*

interface QuestionRepository {
    suspend fun insert(question: Question): Question
    suspend fun update(question: Question): Question?
    suspend fun delete(id: UUID)
    suspend fun getById(id: UUID): Question?
    suspend fun getByTest(test: Test): Question?
    suspend fun getAll(): List<Question>
}