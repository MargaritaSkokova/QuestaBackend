package com.maran.service.results

import com.maran.data.models.Model

sealed class OperationResult {
    class SuccessResult(val value: List<Model>) : OperationResult()
    class FailureResult(val errorMessage: String): OperationResult()
}