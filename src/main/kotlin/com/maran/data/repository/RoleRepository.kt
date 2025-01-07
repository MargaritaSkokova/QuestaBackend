package com.maran.data.repository

import com.maran.data.daos.RoleDao
import com.maran.data.entities.RoleEntity
import com.maran.data.models.Model.Role
import com.maran.data.roleDaoToModel
import com.maran.data.suspendTransaction
import java.util.*

class RoleRepository : IRoleRepository {
    override suspend fun insert(value: Role): Role = suspendTransaction {
        roleDaoToModel(RoleDao.new { name = value.name })
    }

    override suspend fun update(value: Role): Role? = suspendTransaction {
        val dao = RoleDao.findByIdAndUpdate(value.id) { value.name }
        if (dao == null) {
            null
        } else {
            roleDaoToModel(dao)
        }
    }

    override suspend fun delete(id: UUID) = suspendTransaction {
        RoleDao[id].delete()
    }

    override suspend fun getById(id: UUID): Role? = suspendTransaction {
        RoleDao.find { RoleEntity.id eq id }.map(::roleDaoToModel).singleOrNull()
    }

    override suspend fun getAll(): List<Role> = suspendTransaction {
        RoleDao.all().map(::roleDaoToModel)
    }

    override suspend fun getByName(name: String): Role? = suspendTransaction {
        RoleDao.find { RoleEntity.name eq name }.map(::roleDaoToModel).singleOrNull()
    }
}