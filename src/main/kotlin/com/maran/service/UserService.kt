package com.maran.service

import com.maran.data.models.Model.User
import com.maran.data.repository.IUserRepository
import com.maran.service.results.OperationResult
import org.mindrot.jbcrypt.BCrypt
import java.util.*
import javax.inject.Inject

class UserService @Inject constructor(private val userRepository: IUserRepository) : IUserService {
    override suspend fun getAll(): OperationResult {
        return try {
            OperationResult.SuccessResult(userRepository.getAll())
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getById(id: UUID): OperationResult {
        try {
            val user = userRepository.getById(id) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(user))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun delete(id: UUID): OperationResult {
        try {
            userRepository.delete(id)
            return OperationResult.SuccessResult(listOf())
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun update(value: User): OperationResult {
        try {
            if (userRepository.getById(value.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }

            val user = userRepository.update(value) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(user))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun insert(value: User): OperationResult {
        return try {
            if (userRepository.getByName(value.username) == null) {
                val securedUser = User(id = value.id, username = value.username, password = BCrypt.hashpw(value.password, BCrypt.gensalt()).toString(), role = value.role)
                val user = userRepository.insert(securedUser) ?: return OperationResult.FailureResult("Not Found")
                OperationResult.SuccessResult(listOf(user))
            }
            else {
                OperationResult.FailureResult("Username already exists")
            }
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getByName(name: String): OperationResult {
        try {
            val user = userRepository.getByName(name) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(user))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }
}