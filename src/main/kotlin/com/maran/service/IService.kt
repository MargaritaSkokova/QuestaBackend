package com.maran.service

import com.maran.service.results.OperationResult
import java.util.*

interface IService<in T> {
    suspend fun getAll(): OperationResult
    suspend fun getById(id: UUID): OperationResult
    suspend fun insert(value: T): OperationResult
    suspend fun update(value: T): OperationResult
    suspend fun delete(id: UUID): OperationResult
}