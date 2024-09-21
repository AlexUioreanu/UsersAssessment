package com.example.usersassessment.domain.usecase

import com.example.usersassessment.data.repository.UserRepository
import com.example.usersassessment.domain.model.User

class GetUsersByNickName(private val userRepository: UserRepository) {

    suspend operator fun invoke(nickName: String): List<User> =
        userRepository.getUsersByNickName(nickName)
}