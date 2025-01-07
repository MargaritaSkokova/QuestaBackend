package com.maran.service

import com.maran.data.models.Model.Result
import com.maran.data.models.Model.Test
import com.maran.data.repository.IResultRepository
import com.maran.data.repository.ITestRepository
import com.maran.service.results.OperationResult
import java.util.*
import javax.inject.Inject

class ResultService @Inject constructor(
    private val testRepository: ITestRepository,
    private val resultRepository: IResultRepository
) : IResultService {
    override suspend fun getAll(): OperationResult {
        return try {
            OperationResult.SuccessResult(resultRepository.getAll())
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getById(id: UUID): OperationResult {
        try {
            val result = resultRepository.getById(id) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(result))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun delete(id: UUID): OperationResult {
        try {
            resultRepository.delete(id)
            return OperationResult.SuccessResult(listOf())
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun update(value: Result): OperationResult {
        try {
            if (resultRepository.getById(value.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }

            if (testRepository.getById(value.test.id) == null) {
                return OperationResult.FailureResult("Not Found")
            }

            val result = resultRepository.update(value) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(result))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun insert(value: Result): OperationResult {
        return try {
            val result = resultRepository.insert(value) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(result))
        } catch (e: Exception) {
            OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }

    override suspend fun getByTest(test: Test): OperationResult {
        try {
            val result = resultRepository.getByTest(test) ?: return OperationResult.FailureResult("Not Found")
            return OperationResult.SuccessResult(listOf(result))
        } catch (e: Exception) {
            return OperationResult.FailureResult(e.message ?: "Unknown error")
        }
    }
}