package com.maran.service

import com.maran.data.models.Model.Role
import com.maran.service.results.OperationResult

interface IRoleService : IService<Role> {
    suspend fun getByName(name: String): OperationResult
}