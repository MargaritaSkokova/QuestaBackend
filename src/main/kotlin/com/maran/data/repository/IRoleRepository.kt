package com.maran.data.repository

import com.maran.data.models.Model.Role

interface IRoleRepository : IRepository<Role> {
    suspend fun getByName(name: String): Role?
}