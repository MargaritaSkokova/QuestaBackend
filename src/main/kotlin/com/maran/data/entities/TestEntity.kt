package com.maran.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable

object TestEntity : UUIDTable("Test") {
    val type = varchar("type", 500)
    val name= varchar("name", 500)
    val author = reference("author", UserEntity)
    val theme= reference("theme", ThemeEntity)
    val description = varchar("description", 500)
}