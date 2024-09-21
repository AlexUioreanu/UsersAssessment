package com.example.usersassessment.data.network.api

import com.example.usersassessment.data.network.model.UserResponseItem
import retrofit2.http.GET

interface UsersApi {

    @GET("users")
    suspend fun getUsers(): List<UserResponseItem>

}