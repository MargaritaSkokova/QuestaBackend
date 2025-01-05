package com.maran.data.models

import com.maran.data.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Test(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val type: String,
    val name: String,
    val author: User,
    val theme: Theme
)