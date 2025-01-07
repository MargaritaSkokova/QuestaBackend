package com.maran.data.repository

import com.maran.data.models.Model.User

interface IUserRepository : IRepository<User> {
    suspend fun getByName(name: String): User?
}