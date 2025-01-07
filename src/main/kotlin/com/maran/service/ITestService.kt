package com.maran.service

import com.maran.data.models.Model.Test
import com.maran.data.models.Model.Theme
import com.maran.data.models.Model.User
import com.maran.service.results.OperationResult

interface ITestService : IService<Test> {
    suspend fun getByTheme(theme: Theme): OperationResult
    suspend fun getByType(type: String): OperationResult
    suspend fun getByAuthor(author: User): OperationResult
}