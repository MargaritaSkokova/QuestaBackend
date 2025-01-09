package com.maran.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable

object QuestionEntity: UUIDTable("question") {
    val text = varchar("text", 70)
    val test = reference("test_id", TestEntity)
    val order = integer("order")
}