package com.maran.service

import com.maran.controller.Dto
import com.maran.data.models.Model
import com.maran.data.models.Model.User
import com.maran.data.repository.IRoleRepository
import com.maran.data.repository.IUserRepository
import com.maran.data.repository.RoleRepository
import com.maran.service.results.OperationResult
import org.mindrot.jbcrypt.BCrypt
import java.util.*
import javax.inject.Inject

class UserService @Inject constructor(
    private val userRepository: IUserRepository,
    private val roleRepository: IRoleRepository
) : IUserService {
    override suspend fun mapSignUpDtoToModel(dto: Dto.SignUp): User? {
        val role = roleRepository.getByName(dto.role)
        return if (role != null) {
            User(dto.id, dto.username, role, dto.password)
        } else {
            null
        }
    }

    override fun mapModelToDto(model: User): Dto.User {
        return Dto.User(model.id, model.username, model.role.name)
    }

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
                val securedUser = User(
                    id = value.id,
                    username = value.username,
                    password = BCrypt.hashpw(value.password, BCrypt.gensalt()).toString(),
                    role = value.role
                )
                val user = userRepository.insert(securedUser) ?: return OperationResult.FailureResult("Not Found")
                OperationResult.SuccessResult(listOf(user))
            } else {
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