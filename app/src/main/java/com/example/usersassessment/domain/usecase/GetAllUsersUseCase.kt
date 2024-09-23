package com.example.usersassessment.domain.usecase

import com.example.usersassessment.data.repository.UserRepository

class GetAllUsersUseCase(private val userRepositoryImpl: UserRepository) {

    suspend operator fun invoke() =
        userRepositoryImpl.getAllUsers()
}