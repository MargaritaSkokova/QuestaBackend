package com.maran.service

import com.maran.data.models.Role
import java.util.*

interface RoleRepository {
    suspend fun insert(role: Role): Role
    suspend fun update(role: Role): Role?
    suspend fun delete(id: UUID)
    suspend fun get(id: UUID): Role?
    suspend fun getAll(): List<Role>
}