package com.maran.service

import com.maran.data.models.Theme
import java.util.*

interface ThemeRepository {
    suspend fun insert(theme: Theme): Theme
    suspend fun update(theme: Theme): Theme?
    suspend fun delete(id: UUID)
    suspend fun get(id: UUID): Theme?
    suspend fun getAll(): List<Theme>
}