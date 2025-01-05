package com.maran.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable

object RoleEntity : UUIDTable("role") {
    val name = varchar("name", 50)
}