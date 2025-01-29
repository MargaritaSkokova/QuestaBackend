package com.maran.service

import com.maran.controller.Dto
import com.maran.data.models.Model.*
import com.maran.service.results.OperationResult
import java.util.*

interface ITestService : IService<Test> {
    suspend fun getByTheme(name: String): OperationResult
    suspend fun getByType(type: String): OperationResult
    suspend fun getByAuthor(name: String): OperationResult
    suspend fun mapDtoToModel(dto: Dto.Test): Test?
    fun mapModelToDto(model: Test): Dto.Test
}