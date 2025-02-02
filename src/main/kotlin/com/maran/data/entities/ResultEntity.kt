package com.maran.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable

object ResultEntity : UUIDTable("result") {
    val test = reference("test_id", TestEntity)
    val resultMessage = varchar("result_message", 500)
    val maxPoints = integer("max_points").nullable()
    val personality = varchar("personality", 500).nullable()
}