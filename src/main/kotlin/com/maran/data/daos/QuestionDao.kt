package com.maran.data.daos

import com.maran.data.entities.QuestionEntity
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class QuestionDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<QuestionDao>(QuestionEntity)

    var text by QuestionEntity.text
    var test by TestDao referencedOn QuestionEntity.test
    var order by QuestionEntity.order
}