package com.maran.service

import com.maran.controller.Dto
import com.maran.data.models.Model.Answer
import com.maran.data.models.Model.Question
import com.maran.service.results.OperationResult
import java.util.*

interface IAnswerService : IService<Answer> {
    suspend fun getByQuestion(id: UUID): OperationResult
    suspend fun mapDtoToModel(dto: Dto.Answer): Answer?
    fun mapModelToDto(model: Answer): Dto.Answer
}