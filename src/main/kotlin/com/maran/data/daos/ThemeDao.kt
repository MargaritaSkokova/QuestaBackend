package com.maran.data.daos

import com.maran.data.entities.ThemeEntity
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ThemeDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ThemeDao>(ThemeEntity)

    var name by ThemeEntity.name
}