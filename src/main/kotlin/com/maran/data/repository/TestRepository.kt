package com.maran.data.repository

import com.maran.data.daos.TestDao
import com.maran.data.daos.ThemeDao
import com.maran.data.daos.UserDao
import com.maran.data.entities.TestEntity
import com.maran.data.models.Model.*
import com.maran.data.suspendTransaction
import com.maran.data.testDaoToModel
import java.util.*

class TestRepository : ITestRepository {
    override suspend fun getAll(): List<Test> = suspendTransaction {
        TestDao.all().map(::testDaoToModel)
    }

    override suspend fun getById(id: UUID): Test? = suspendTransaction {
        TestDao.find { TestEntity.id eq id }.map(::testDaoToModel).singleOrNull()
    }

    override suspend fun getByTheme(theme: Theme): List<Test> = suspendTransaction {
        TestDao.find { TestEntity.theme eq theme.id }.map(::testDaoToModel)
    }

    override suspend fun getByType(type: String): List<Test> = suspendTransaction {
        TestDao.find { TestEntity.type eq type }.map(::testDaoToModel)
    }

    override suspend fun getByAuthor(author: User): List<Test> = suspendTransaction {
        TestDao.find { TestEntity.author eq author.id }.map(::testDaoToModel)
    }

    override suspend fun insert(value: Test): Test = suspendTransaction {
        testDaoToModel(TestDao.new {
            type = value.testType; name = value.name; author = UserDao[value.author.id]; theme = ThemeDao[value.theme.id]; description = value.description
        })
    }

    override suspend fun update(value: Test): Test? = suspendTransaction {
        val dao =
            TestDao.findByIdAndUpdate(value.id) { value.testType; value.name; UserDao[value.author.id]; ThemeDao[value.theme.id]; value.description }
        if (dao == null) {
            null
        } else {
            testDaoToModel(dao)
        }
    }

    override suspend fun delete(id: UUID) = suspendTransaction {
        TestDao[id].delete()
    }
}