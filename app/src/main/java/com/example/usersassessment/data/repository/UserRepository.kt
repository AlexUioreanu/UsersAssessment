package com.example.usersassessment.data.repository

import com.example.usersassessment.data.db.UserDao
import com.example.usersassessment.data.mappers.toUsers
import com.example.usersassessment.data.network.api.UsersApi
import com.example.usersassessment.domain.model.User

class UserRepository(private val api: UsersApi, private val dao: UserDao) {

    suspend fun fetchUsers(): Result<List<User>> =
        try {
            val response = api.getUsers().toUsers()
            Result.success(response)
        } catch (e: Throwable) {
            Result.failure(e)
        }

    suspend fun saveAllUsers(users: List<User>) {
        dao.insertUsers(users)
    }

    suspend fun getAllUsers(): List<User> =
        dao.getAllUsers()

    suspend fun getUsersByNickName(nickName: String): List<User> =
        dao.getUsersByNickName(nickName)

}