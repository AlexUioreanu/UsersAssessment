package com.example.usersassessment.repository.usecase

import com.example.usersassessment.data.db.entity.User
import com.example.usersassessment.repository.repository.UserRepository

class GetUsersByNickName(private val userRepositoryImpl: UserRepository) {

    suspend operator fun invoke(nickName: String): List<User> =
        userRepositoryImpl.getUsersByNickName(nickName)
}