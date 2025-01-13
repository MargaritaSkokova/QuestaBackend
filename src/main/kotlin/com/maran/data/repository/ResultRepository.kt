package com.maran.data.repository

import com.maran.data.daos.ResultDao
import com.maran.data.daos.TestDao
import com.maran.data.entities.ResultEntity
import com.maran.data.models.Model.Result
import com.maran.data.models.Model.Test
import com.maran.data.resultDaoToModel
import com.maran.data.suspendTransaction
import java.util.*

class ResultRepository : IResultRepository {
    override suspend fun insert(value: Result): Result = suspendTransaction {
        resultDaoToModel(ResultDao.new { test = TestDao[value.test.id]; resultMessage = value.resultMessage; maxPoints = value.maxPoints; personality = value.personality; } )
    }

    override suspend fun update(value: Result): Result? = suspendTransaction {
        val dao = ResultDao.findByIdAndUpdate(value.id) {
            TestDao[value.test.id]; value.resultMessage; value.maxPoints; value.personality
        }
        if (dao == null) {
            null
        } else {
            resultDaoToModel(dao)
        }
    }

    override suspend fun delete(id: UUID) = suspendTransaction {
        ResultDao[id].delete()
    }

    override suspend fun getById(id: UUID): Result? = suspendTransaction {
        ResultDao.find { ResultEntity.id eq id }.map(::resultDaoToModel).singleOrNull()
    }

    override suspend fun getByTest(test: Test): Result? = suspendTransaction {
        ResultDao.find { ResultEntity.test eq test.id }.map(::resultDaoToModel).singleOrNull()
    }

    override suspend fun getAll(): List<Result> = suspendTransaction {
        ResultDao.all().map(::resultDaoToModel)
    }
}