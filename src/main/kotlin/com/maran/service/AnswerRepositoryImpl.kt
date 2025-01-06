package com.maran.service

import com.maran.data.answerDaoToModel
import com.maran.data.daos.AnswerDao
import com.maran.data.daos.QuestionDao
import com.maran.data.entities.AnswerEntity
import com.maran.data.models.Answer
import com.maran.data.models.Question
import com.maran.data.suspendTransaction
import java.util.*

class AnswerRepositoryImpl : AnswerRepository {
    override suspend fun getAll(): List<Answer> = suspendTransaction {
        AnswerDao.all().map(::answerDaoToModel)
    }

    override suspend fun getById(id: UUID): Answer? = suspendTransaction {
        AnswerDao.find { (AnswerEntity.id eq id) }.map(::answerDaoToModel).singleOrNull()
    }

    override suspend fun getByQuestion(question: Question): Answer? = suspendTransaction {
        AnswerDao.find { (AnswerEntity.question eq question.id) }.map(::answerDaoToModel).singleOrNull()
    }

    override suspend fun insert(answer: Answer): Answer = suspendTransaction {
        answerDaoToModel(AnswerDao.new { question = QuestionDao[answer.question.id]; text = answer.text; isCorrect = answer.isCorrect })
    }

    override suspend fun update(answer: Answer): Answer? = suspendTransaction {
        val dao = AnswerDao.findByIdAndUpdate(answer.id) { QuestionDao[answer.question.id]; answer.text; answer.isCorrect }
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