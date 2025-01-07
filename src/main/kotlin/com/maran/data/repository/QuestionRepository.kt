package com.maran.data.repository

import com.maran.data.daos.QuestionDao
import com.maran.data.daos.TestDao
import com.maran.data.entities.QuestionEntity
import com.maran.data.models.Model.Question
import com.maran.data.models.Model.Test
import com.maran.data.questionDaoToModel
import com.maran.data.suspendTransaction
import java.util.*

class QuestionRepository :IQuestionRepository {
    override suspend fun insert(value: Question): Question = suspendTransaction {
        questionDaoToModel(QuestionDao.new {
            text = value.text; test = TestDao[value.test.id]; order = value.order
        })
    }

    override suspend fun update(value: Question): Question? = suspendTransaction {
        val dao =
            QuestionDao.findByIdAndUpdate(value.id) { value.text; TestDao[value.test.id]; value.order }
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