package com.maran.service

import com.maran.data.daos.QuestionDao
import com.maran.data.daos.TestDao
import com.maran.data.entities.QuestionEntity
import com.maran.data.models.Question
import com.maran.data.models.Test
import com.maran.data.questionDaoToModel
import com.maran.data.suspendTransaction
import java.util.*

class QuestionRepositoryImpl : QuestionRepository {
    override suspend fun insert(question: Question): Question = suspendTransaction {
        questionDaoToModel(QuestionDao.new {
            text = question.text; test = TestDao[question.test.id]; order = question.order
        })
    }

    override suspend fun update(question: Question): Question? = suspendTransaction {
        val dao =
            QuestionDao.findByIdAndUpdate(question.id) { question.text; TestDao[question.test.id]; question.order }
        if (dao == null) {
            null
        } else {
            questionDaoToModel(dao)
        }
    }

    override suspend fun delete(id: UUID) = suspendTransaction {
        QuestionDao[id].delete()
    }

    override suspend fun getById(id: UUID): Question? = suspendTransaction {
        QuestionDao.find { (QuestionEntity.id eq id) }.map(::questionDaoToModel).singleOrNull()
    }

    override suspend fun getByTest(test: Test): Question? = suspendTransaction {
        QuestionDao.find { (QuestionEntity.test eq test.id) }.map(::questionDaoToModel).singleOrNull()
    }

    override suspend fun getAll(): List<Question> = suspendTransaction {
        QuestionDao.all().map(::questionDaoToModel)
    }
}