package com.maran.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable

object ThemeEntity : UUIDTable("theme") {
    val name = varchar("name", 70)
}