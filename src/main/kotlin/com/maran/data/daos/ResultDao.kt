package com.maran.data.daos

import com.maran.data.entities.ResultEntity
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ResultDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ResultDao>(ResultEntity)

    var test by TestDao referencedOn ResultEntity.test
    var resultMessage by ResultEntity.resultMessage
    var maxPoints by ResultEntity.maxPoints
    var personality by ResultEntity.personality
}