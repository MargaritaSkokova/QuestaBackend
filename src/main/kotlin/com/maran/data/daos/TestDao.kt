package com.maran.data.daos

import com.maran.data.entities.TestEntity
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class TestDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TestDao>(TestEntity)

    var type by TestEntity.type
    var name by TestEntity.name
    var author by UserDao referencedOn TestEntity.author
    var theme by ThemeDao referencedOn TestEntity.theme
}