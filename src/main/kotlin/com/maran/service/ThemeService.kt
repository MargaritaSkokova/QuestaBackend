package com.maran.service

import com.maran.controller.Dto
import com.maran.data.models.Model.Theme
import com.maran.data.repository.IThemeRepository
import com.maran.service.results.OperationResult
import java.util.*
import javax.inject.Inject

class ThemeService @Inject constructor(private val themeRepository: IThemeRepository) : IThemeService {
    override suspend fun getAll(): OperationResult {
        return try {
            OperationResult.SuccessResult(themeRepository.getAll())
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getById(id: UUID): OperationResult {
        try {
            val theme = themeRepository.getById(id) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(theme))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun delete(id: UUID): OperationResult {
        try {
            themeRepository.delete(id)
            return OperationResult.SuccessResult(listOf())
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun update(value: Theme): OperationResult {
        try {
            if (themeRepository.getById(value.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }

            val theme = themeRepository.update(value) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(theme))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun insert(value: Theme): OperationResult {
        return try {
            if (themeRepository.getByName(value.name) == null) {
                val theme = themeRepository.insert(value) ?: return OperationResult.FailureResult("Not Found")
                return OperationResult.SuccessResult(listOf(theme))
            } else {
                OperationResult.FailureResult("Name already exists")
            }
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getByName(name: String): OperationResult {
        try {
            val theme = themeRepository.getByName(name) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(theme))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override fun mapDtoToModel(dto: Dto.Theme): Theme {
        return Theme(dto.id, dto.name)
    }

    override fun mapModelToDto(model: Theme): Dto.Theme {
        return Dto.Theme(model.id, model.name)
    }
}