package com.maran.service

import com.maran.data.daos.RoleDao
import com.maran.data.entities.RoleEntity
import com.maran.data.models.Role
import com.maran.data.roleDaoToModel
import com.maran.data.suspendTransaction
import java.util.*

class RoleRepositoryImpl : RoleRepository {
    override suspend fun insert(role: Role): Role = suspendTransaction {
        roleDaoToModel(RoleDao.new { name = role.name })
    }

    override suspend fun update(role: Role): Role? = suspendTransaction {
        val dao = RoleDao.findByIdAndUpdate(role.id) { role.name }
        if (dao == null) {
            null
        } else {
            roleDaoToModel(dao)
        }
    }

    override suspend fun delete(id: UUID) = suspendTransaction {
        RoleDao[id].delete()
    }

    override suspend fun get(id: UUID): Role? = suspendTransaction {
        RoleDao.find { RoleEntity.id eq id }.map(::roleDaoToModel).singleOrNull()
    }

    override suspend fun getAll(): List<Role> = suspendTransaction {
        RoleDao.all().map(::roleDaoToModel)
    }
}