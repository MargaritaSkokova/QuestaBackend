package com.maran.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable

object QuestionEntity: UUIDTable("question") {
    val text = varchar("text", 500)
    val test = reference("test_id", TestEntity)
    val order = integer("order")
}