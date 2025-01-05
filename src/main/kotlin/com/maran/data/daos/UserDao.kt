package com.maran.data.daos

import com.maran.data.entities.UserEntity
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserDao (id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserDao>(UserEntity)

    var username by UserEntity.username
    var role by RoleDao referencedOn UserEntity.role
    var password by UserEntity.password
}