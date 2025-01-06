package com.maran.service

import com.maran.data.models.Test
import com.maran.data.models.Theme
import com.maran.data.models.User
import java.util.*

interface TestRepository {
    suspend fun getAll(): List<Test>
    suspend fun getById(id: UUID): Test?
    suspend fun getByTheme(theme: Theme): List<Test>
    suspend fun getByType(type: String): List<Test>
    suspend fun getByAuthor(author: User): List<Test>
    suspend fun insert(test: Test): Test
    suspend fun update(test: Test): Test?
    suspend fun delete(id: UUID)
}