package com.maran.data.models

import com.maran.data.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Answer(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val question: Question,
    val text: String,
    val isCorrect: Boolean
)