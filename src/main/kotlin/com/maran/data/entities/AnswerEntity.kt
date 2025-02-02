package com.maran.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable

object AnswerEntity: UUIDTable("answer") {
    val question = reference("question_id", QuestionEntity)
    val text = varchar("text", 500)
    val isCorrect= bool("is_correct").nullable()
    val personality = varchar("personality", 70).nullable()
}