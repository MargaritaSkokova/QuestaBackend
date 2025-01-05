package com.maran.data.models

import com.maran.data.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Question(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val text: String,
    val test: Test,
    val order: Int,
)