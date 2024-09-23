package com.example.usersassessment.repository.usecase

import com.example.usersassessment.repository.repository.UserRepository

class GetAllUsersUseCase(private val userRepositoryImpl: UserRepository) {

    suspend operator fun invoke() =
        userRepositoryImpl.getAllUsers()
}