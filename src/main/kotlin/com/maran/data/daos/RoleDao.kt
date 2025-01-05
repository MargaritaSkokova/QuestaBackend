package com.maran.data.daos

import com.maran.data.entities.RoleEntity
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class RoleDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<RoleDao>(RoleEntity)

    var name by RoleEntity.name
}