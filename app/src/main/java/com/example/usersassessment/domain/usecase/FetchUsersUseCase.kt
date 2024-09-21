package com.example.usersassessment.domain.usecase

import com.example.usersassessment.data.repository.UserRepository
import com.example.usersassessment.domain.model.User

class FetchUsersUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Result<List<User>> =
        userRepository.fetchUsers()
}