package com.maran.data.repository

import com.maran.data.models.Model.*

interface ITestRepository : IRepository<Test>{
    suspend fun getByTheme(theme: Theme): List<Test>
    suspend fun getByType(type: String): List<Test>
    suspend fun getByAuthor(author: User): List<Test>
}