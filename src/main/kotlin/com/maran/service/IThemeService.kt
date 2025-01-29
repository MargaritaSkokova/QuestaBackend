package com.maran.service

import com.maran.controller.Dto
import com.maran.data.models.Model.Theme
import com.maran.service.results.OperationResult

interface IThemeService : IService<Theme>{
    suspend fun getByName(name: String): OperationResult
    fun mapDtoToModel(dto: Dto.Theme): Theme
    fun mapModelToDto(model: Theme): Dto.Theme
}