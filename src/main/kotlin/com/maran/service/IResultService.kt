package com.maran.service

import com.maran.controller.Dto
import com.maran.data.models.Model.*
import com.maran.service.results.OperationResult
import java.util.*

interface IResultService : IService<Result> {
    suspend fun getByTest(id: UUID): OperationResult
    suspend fun mapDtoToModel(dto: Dto.Result): Result?
    fun mapModelToDto(model: Result): Dto.Result
}