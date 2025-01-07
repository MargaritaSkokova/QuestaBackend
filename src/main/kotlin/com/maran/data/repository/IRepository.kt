package com.maran.data.repository

import java.util.*

interface IRepository<T> {
    suspend fun getAll(): List<T>
    suspend fun getById(id: UUID): T?
    suspend fun insert(value: T): T?
    suspend fun update(value: T): T?
    suspend fun delete(id: UUID)
}