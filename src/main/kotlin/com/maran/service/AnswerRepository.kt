package com.maran.service

import com.maran.data.models.Answer
import com.maran.data.models.Question
import java.util.*

interface AnswerRepository {
    suspend fun getAll(): List<Answer>
    suspend fun getById(id: UUID): Answer?
    suspend fun getByQuestion(question: Question): Answer?
    suspend fun insert(answer: Answer): Answer?
    suspend fun update(answer: Answer): Answer?
    suspend fun delete(id: UUID)
}