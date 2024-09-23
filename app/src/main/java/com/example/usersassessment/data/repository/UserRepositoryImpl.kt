package com.example.usersassessment.data.repository

import com.example.usersassessment.data.db.UserDao
import com.example.usersassessment.data.db.entity.User
import com.example.usersassessment.data.mappers.toUsers
import com.example.usersassessment.data.network.api.UsersApi
import com.example.usersassessment.repository.repository.UserRepository

class UserRepositoryImpl(private val api: UsersApi, private val dao: UserDao) : UserRepository {

    override suspend fun fetchUsers(): Result<List<User>> =
        try {
            Result.success(api.getUsers().toUsers())
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun saveAllUsers(users: List<User>) {
        dao.insertUsers(users)
    }

    override suspend fun getAllUsers(): List<User> =
        dao.getAllUsers()

    override suspend fun getUsersByNickName(nickName: String): List<User> =
        dao.getUsersByNickName(nickName)

}