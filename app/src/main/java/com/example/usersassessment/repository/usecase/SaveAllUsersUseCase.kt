package com.example.usersassessment.repository.usecase

import com.example.usersassessment.data.db.entity.User
import com.example.usersassessment.repository.repository.UserRepository

class SaveAllUsersUseCase(private val userRepositoryImpl: UserRepository) {

    suspend operator fun invoke(users: List<User>) {
        userRepositoryImpl.saveAllUsers(users)
    }
}