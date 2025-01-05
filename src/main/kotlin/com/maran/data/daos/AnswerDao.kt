package com.maran.data.daos

import com.maran.data.entities.AnswerEntity
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class AnswerDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AnswerDao>(AnswerEntity)

    var question by QuestionDao referencedOn AnswerEntity.question
    var text by AnswerEntity.text
    var isCorrect by AnswerEntity.isCorrect
}