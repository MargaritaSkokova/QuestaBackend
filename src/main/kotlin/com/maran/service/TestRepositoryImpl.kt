package com.maran.service

import com.maran.data.daos.TestDao
import com.maran.data.daos.ThemeDao
import com.maran.data.daos.UserDao
import com.maran.data.entities.TestEntity
import com.maran.data.models.Test
import com.maran.data.models.Theme
import com.maran.data.models.User
import com.maran.data.suspendTransaction
import com.maran.data.testDaoToModel
import java.util.*

class TestRepositoryImpl : TestRepository {
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

    override suspend fun insert(test: Test): Test = suspendTransaction {
        testDaoToModel(TestDao.new {
            type = test.type; name = test.name; author = UserDao[test.author.id]; theme = ThemeDao[test.theme.id]
        })
    }

    override suspend fun update(test: Test): Test? = suspendTransaction {
        val dao =
            TestDao.findByIdAndUpdate(test.id) { test.type; test.name; UserDao[test.author.id]; ThemeDao[test.theme.id] }
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