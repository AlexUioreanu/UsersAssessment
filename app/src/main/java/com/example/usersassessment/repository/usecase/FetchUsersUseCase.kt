package com.example.usersassessment.repository.usecase

import com.example.usersassessment.data.db.entity.User
import com.example.usersassessment.repository.repository.UserRepository

class FetchUsersUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Result<List<User>> =
        userRepository.fetchUsers()
}