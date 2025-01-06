package com.maran.service

import com.maran.data.models.User
import java.util.*

interface UserRepository {
    suspend fun insert(user: User): User
    suspend fun update(user: User): User?
    suspend fun delete(id: UUID)
    suspend fun getById(id: UUID): User?
    suspend fun getByName(name: String): User?
    suspend fun getAll(): List<User>
}