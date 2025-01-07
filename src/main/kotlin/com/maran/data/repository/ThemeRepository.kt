package com.maran.data.repository

import com.maran.data.daos.ThemeDao
import com.maran.data.entities.ThemeEntity
import com.maran.data.models.Model.Theme
import com.maran.data.suspendTransaction
import com.maran.data.themeDaoToModel
import java.util.*

class ThemeRepository : IThemeRepository {
    override suspend fun insert(value: Theme): Theme = suspendTransaction {
        themeDaoToModel(ThemeDao.new { name = value.name })
    }

    override suspend fun update(value: Theme): Theme? = suspendTransaction {
        val dao = ThemeDao.findByIdAndUpdate(value.id) { value.name }
        if (dao == null) {
            null
        } else {
            themeDaoToModel(dao)
        }
    }

    override suspend fun delete(id: UUID) = suspendTransaction {
        ThemeDao[id].delete()
    }

    override suspend fun getById(id: UUID): Theme? = suspendTransaction {
        ThemeDao.find { ThemeEntity.id eq id }.map(::themeDaoToModel).singleOrNull()
    }

    override suspend fun getAll(): List<Theme> = suspendTransaction {
        ThemeDao.all().map(::themeDaoToModel)
    }

    override suspend fun getByName(name: String): Theme? = suspendTransaction {
        ThemeDao.find { ThemeEntity.name eq name }.map(::themeDaoToModel).singleOrNull()
    }
}