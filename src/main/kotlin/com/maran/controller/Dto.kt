package com.maran.controller
import com.maran.data.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
sealed class Dto {
    @Serializable
    data class Answer(
        @Serializable(with = UUIDSerializer::class)
        val id: UUID,
        @Serializable(with = UUIDSerializer::class)
        val questionId: UUID,
        val text: String,
        val isCorrect: Boolean?,
        val personality: String?
    ) : Dto()

    @Serializable
    data class Question(
        @Serializable(with = UUIDSerializer::class)
        val id: UUID,
        val text: String,
        @Serializable(with = UUIDSerializer::class)
        val testId: UUID,
        val order: Int,
    ) : Dto()

    @Serializable
    data class Result(
        @Serializable(with = UUIDSerializer::class)
        val id: UUID,
        @Serializable(with = UUIDSerializer::class)
        val testId: UUID,
        val resultMessage: String,
        val maxPoints: Int?,
        val personality: String?
    ) : Dto()

    @Serializable
    data class Role(
        @Serializable(with = UUIDSerializer::class)
        val id: UUID,
        val name: String
    ) : Dto()

    @Serializable
    data class Test(
        @Serializable(with = UUIDSerializer::class)
        val id: UUID,
        val testType: String,
        val name: String,
        val author: String,
        val theme: String,
        val description: String,
    ) : Dto()

    @Serializable
    data class Theme(
        @Serializable(with = UUIDSerializer::class)
        val id: UUID,
        val name: String
    ) : Dto()

    @Serializable
    data class User(
        @Serializable(with = UUIDSerializer::class)
        val id: UUID,
        val username: String,
        val role: String
    ) : Dto()

    @Serializable
    data class Authentication(
        @Serializable(with = UUIDSerializer::class)
        val username: String,
        val password: String
    ) : Dto()

    @Serializable
    data class SignUp(
        @Serializable(with = UUIDSerializer::class)
        val id: UUID,
        val username: String,
        val role: String,
        val password: String
    ) : Dto()
}