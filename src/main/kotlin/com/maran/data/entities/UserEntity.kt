package com.maran.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable

object UserEntity : UUIDTable("user") {
    val username = varchar("username", 50)
    val role = reference("role", RoleEntity)
    val password = varchar("password_hash", 50)
}