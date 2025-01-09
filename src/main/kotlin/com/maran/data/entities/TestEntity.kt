package com.maran.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable

object TestEntity : UUIDTable("Test") {
    val type = varchar("type", 70)
    val name= varchar("name", 70)
    val author = reference("author", UserEntity)
    val theme= reference("theme", ThemeEntity)
}