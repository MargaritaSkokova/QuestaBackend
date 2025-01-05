package com.maran.data.models

import com.maran.data.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

@Serializable
data class Result(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val test: Test,
    val resultMessage: String
)