package com.maran.data.repository

import com.maran.data.answerDaoToModel
import com.maran.data.daos.AnswerDao
import com.maran.data.daos.QuestionDao
import com.maran.data.entities.AnswerEntity
import com.maran.data.models.Model.Question
import com.maran.data.models.Model.Answer
import com.maran.data.suspendTransaction
import java.util.*

class AnswerRepository: IAnswerRepository {
    override suspend fun getAll(): List<Answer> = suspendTransaction {
        AnswerDao.all().map(::answerDaoToModel)
    }

    override suspend fun getById(id: UUID): Answer? = suspendTransaction {
        AnswerDao.find { (AnswerEntity.id eq id) }.map(::answerDaoToModel).singleOrNull()
    }

    override suspend fun getByQuestion(question: Question): Answer? = suspendTransaction {
        AnswerDao.find { (AnswerEntity.question eq question.id) }.map(::answerDaoToModel).singleOrNull()
    }

    override suspend fun insert(value: Answer): Answer = suspendTransaction {
        answerDaoToModel(AnswerDao.new { question = QuestionDao[value.question.id]; text = value.text; isCorrect = value.isCorrect })
    }

    override suspend fun update(value: Answer): Answer? = suspendTransaction {
        val dao = AnswerDao.findByIdAndUpdate(value.id) { QuestionDao[value.question.id]; value.text; value.isCorrect }
        if (dao == null) {
            null
        } else {
            answerDaoToModel(dao)
        }
    }

    override suspend fun delete(id: UUID) = suspendTransaction {
        AnswerDao[id].delete()
    }
}