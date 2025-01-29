package com.maran.service

import com.maran.controller.Dto
import com.maran.data.models.Model.User
import com.maran.service.results.OperationResult

interface IUserService : IService<User> {
    suspend fun mapSignUpDtoToModel(dto: Dto.SignUp): User?
    suspend fun getByName(name: String): OperationResult
    fun mapModelToDto(model: User): Dto.User
}