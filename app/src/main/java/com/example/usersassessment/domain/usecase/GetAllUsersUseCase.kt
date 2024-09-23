package com.example.usersassessment.domain.usecase

import com.example.usersassessment.data.repository.UserRepository

class GetAllUsersUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke() =
        userRepository.getAllUsers()
}