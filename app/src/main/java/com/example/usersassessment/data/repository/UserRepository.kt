package com.example.usersassessment.data.repository

import com.example.usersassessment.data.network.api.UsersApi
import com.example.usersassessment.data.network.model.UserResponseItem

class UserRepository(private val api: UsersApi) {

    suspend fun fetchUsers(): Result<List<UserResponseItem>> =
        try {
            val response = api.getUsers()
            Result.success(response)
        } catch (e: Throwable) {
            Result.failure(e)
        }
}