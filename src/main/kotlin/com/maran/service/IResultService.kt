package com.maran.service

import com.maran.data.models.Model.Result
import com.maran.data.models.Model.Test
import com.maran.service.results.OperationResult

interface IResultService : IService<Result> {
    suspend fun getByTest(test: Test): OperationResult
}