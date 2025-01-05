package com.maran.data.models

import com.maran.data.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val username: String,
    val role: Role,
    val password: String
)