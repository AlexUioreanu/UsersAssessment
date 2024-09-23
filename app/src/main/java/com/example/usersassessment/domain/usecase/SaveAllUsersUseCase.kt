package com.example.usersassessment.domain.usecase

import com.example.usersassessment.data.repository.UserRepository
import com.example.usersassessment.domain.model.User

class SaveAllUsersUseCase(private val userRepositoryImpl: UserRepository) {

    suspend operator fun invoke(users: List<User>) {
        userRepositoryImpl.saveAllUsers(users)
    }
}