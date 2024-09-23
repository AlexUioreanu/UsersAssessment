package com.example.usersassessment.repository.repository

import com.example.usersassessment.data.db.entity.User

interface UserRepository {
    suspend fun fetchUsers(): Result<List<User>>

    suspend fun saveAllUsers(users: List<User>)

    suspend fun getAllUsers(): List<User>

    suspend fun getUsersByNickName(nickName: String): List<User>
}