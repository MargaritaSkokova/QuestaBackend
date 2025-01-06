package com.maran.service

import com.maran.data.models.Result
import com.maran.data.models.Test
import java.util.*

interface ResultRepository {
    suspend fun insert(result: Result): Result
    suspend fun update(result: Result): Result?
    suspend fun delete(id: UUID)
    suspend fun getById(id: UUID): Result?
    suspend fun getByTest(test: Test): Result?
    suspend fun getAll(): List<Result>
}