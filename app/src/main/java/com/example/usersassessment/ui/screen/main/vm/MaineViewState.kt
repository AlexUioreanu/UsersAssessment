package com.example.usersassessment.ui.screen.main.vm

import com.example.usersassessment.data.db.entity.User

data class MainViewState(
    val users: List<User>,
    val searchQuery: String,
    val onSearchByNickName: (String) -> Unit
)