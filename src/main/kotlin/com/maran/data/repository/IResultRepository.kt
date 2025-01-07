package com.maran.data.repository

import com.maran.data.models.Model.Result
import com.maran.data.models.Model.Test

interface IResultRepository : IRepository<Result>{
    suspend fun getByTest(test: Test): Result?
}