package com.maran.service

import com.maran.data.models.Model.Role
import com.maran.data.repository.IRoleRepository
import com.maran.service.results.OperationResult
import java.util.*
import javax.inject.Inject

class RoleService @Inject constructor(
    private val roleRepository: IRoleRepository
) : IRoleService {
    override suspend fun getAll(): OperationResult {
        return try {
            OperationResult.SuccessResult(roleRepository.getAll())
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getById(id: UUID): OperationResult {
        try {
            val role = roleRepository.getById(id) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(role))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun delete(id: UUID): OperationResult {
        try {
            roleRepository.delete(id)
            return OperationResult.SuccessResult(listOf())
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun update(value: Role): OperationResult {
        try {
            if (roleRepository.getById(value.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }

            val role = roleRepository.update(value) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(role))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun insert(value: Role): OperationResult {
        return try {
            if (roleRepository.getByName(value.name) == null) {
                val role = roleRepository.insert(value) ?: return OperationResult.FailureResult("Not Found")
                return OperationResult.SuccessResult(listOf(role))
            } else {
                OperationResult.FailureResult("Name already exists")
            }
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getByName(name: String): OperationResult {
        try {
            val role = roleRepository.getByName(name) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(role))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }
}