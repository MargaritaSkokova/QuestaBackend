package com.maran.service

import com.maran.data.daos.RoleDao
import com.maran.data.daos.UserDao
import com.maran.data.entities.UserEntity
import com.maran.data.models.User
import com.maran.data.suspendTransaction
import com.maran.data.userDaoToModel
import java.util.*

class UserRepositoryImpl : UserRepository {
    override suspend fun insert(user: User): User = suspendTransaction {
        userDaoToModel(UserDao.new { username = user.username; password = user.password;role = RoleDao[user.role.id] })
    }

    override suspend fun update(user: User): User? = suspendTransaction {
        val dao = UserDao.findByIdAndUpdate(user.id) { user.username; user.password; RoleDao[user.role.id] }
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