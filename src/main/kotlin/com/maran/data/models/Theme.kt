package com.maran.data.models

import com.maran.data.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Theme(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String
)