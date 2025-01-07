package com.maran.service

import com.maran.data.models.Model.User
import com.maran.service.results.OperationResult

interface IUserService : IService<User> {
    suspend fun getByName(name: String): OperationResult
}