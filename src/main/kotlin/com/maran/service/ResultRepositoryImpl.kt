package com.maran.service

import com.maran.data.daos.ResultDao
import com.maran.data.daos.TestDao
import com.maran.data.entities.ResultEntity
import com.maran.data.models.Result
import com.maran.data.models.Test
import com.maran.data.resultDaoToModel
import com.maran.data.suspendTransaction
import java.util.*

class ResultRepositoryImpl : ResultRepository {
    override suspend fun insert(result: Result): Result = suspendTransaction {
        resultDaoToModel(ResultDao.new { test = TestDao[result.test.id]; resultMessage = result.resultMessage })
    }

    override suspend fun update(result: Result): Result? = suspendTransaction {
        val dao = ResultDao.findByIdAndUpdate(result.id) {
            TestDao[result.test.id]; result.resultMessage
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