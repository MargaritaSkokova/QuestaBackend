package com.maran.data.repository

import com.maran.data.models.Model.Theme

interface IThemeRepository: IRepository<Theme>{
    suspend fun getByName(name: String): Theme?
}