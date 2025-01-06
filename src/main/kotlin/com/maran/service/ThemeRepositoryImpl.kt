package com.maran.service

import com.maran.data.daos.ThemeDao
import com.maran.data.entities.ThemeEntity
import com.maran.data.models.Theme
import com.maran.data.suspendTransaction
import com.maran.data.themeDaoToModel
import java.util.*

class ThemeRepositoryImpl : ThemeRepository {
    override suspend fun insert(theme: Theme): Theme = suspendTransaction {
        themeDaoToModel(ThemeDao.new { name = theme.name })
    }

    override suspend fun update(theme: Theme): Theme? = suspendTransaction {
        val dao = ThemeDao.findByIdAndUpdate(theme.id) { theme.name }
        if (dao == null) {
            null
        } else {
            themeDaoToModel(dao)
        }
    }

    override suspend fun delete(id: UUID) = suspendTransaction {
        ThemeDao[id].delete()
    }

    override suspend fun get(id: UUID): Theme? = suspendTransaction {
        ThemeDao.find { ThemeEntity.id eq id }.map(::themeDaoToModel).singleOrNull()
    }

    override suspend fun getAll(): List<Theme> = suspendTransaction {
        ThemeDao.all().map(::themeDaoToModel)
    }
}