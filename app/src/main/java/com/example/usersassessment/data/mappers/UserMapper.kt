package com.example.usersassessment.data.mappers

import com.example.usersassessment.data.db.entity.User
import com.example.usersassessment.data.network.model.UserResponseItem

fun List<UserResponseItem>.toUsers(): List<User> =
    map { userResponseItem ->
        User(
            id = userResponseItem.id,
            nickName = userResponseItem.login,
            imageUrl = userResponseItem.avatarUrl
        )
    }
