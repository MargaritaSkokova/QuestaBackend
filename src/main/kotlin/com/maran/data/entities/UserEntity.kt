package com.maran.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable

object UserEntity : UUIDTable("User") {
    val username = varchar("username", 70)
    val role = reference("role", RoleEntity)
    val password = varchar("password_hash", 70)
}