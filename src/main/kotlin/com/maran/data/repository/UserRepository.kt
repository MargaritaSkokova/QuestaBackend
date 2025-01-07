package com.maran.data.repository

import com.maran.data.daos.RoleDao
import com.maran.data.daos.UserDao
import com.maran.data.entities.UserEntity
import com.maran.data.models.Model.User
import com.maran.data.suspendTransaction
import com.maran.data.userDaoToModel
import java.util.*

class UserRepository : IUserRepository {
    override suspend fun insert(value: User): User = suspendTransaction {
        userDaoToModel(UserDao.new { username = value.username; password = value.password;role = RoleDao[value.role.id] })
    }

    override suspend fun update(value: User): User? = suspendTransaction {
        val dao = UserDao.findByIdAndUpdate(value.id) { value.username; value.password; RoleDao[value.role.id] }
        if (dao == null) {
            null
        } else {
            userDaoToModel(dao)
        }
    }

    override suspend fun delete(id: UUID) = suspendTransaction {
        UserDao[id].delete()
    }

    override suspend fun getById(id: UUID): User? = suspendTransaction {
        UserDao.find { (UserEntity.id eq id) }.map(::userDaoToModel).singleOrNull()
    }

    override suspend fun getByName(name: String): User? = suspendTransaction {
        UserDao.find { (UserEntity.username eq name) }.map(::userDaoToModel).singleOrNull()
    }

    override suspend fun getAll(): List<User> = suspendTransaction {
        UserDao.all().map(::userDaoToModel)
    }
}