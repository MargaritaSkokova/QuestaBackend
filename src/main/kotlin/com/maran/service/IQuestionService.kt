package com.maran.service

import com.maran.controller.Dto
import com.maran.data.models.Model.Question
import com.maran.data.models.Model.Result
import com.maran.data.models.Model.Test
import com.maran.service.results.OperationResult
import java.util.*

interface IQuestionService : IService<Question> {
    suspend fun getByTest(id: UUID): OperationResult
    suspend fun mapDtoToModel(dto: Dto.Question): Question?
    fun mapModelToDto(model: Question): Dto.Question
}