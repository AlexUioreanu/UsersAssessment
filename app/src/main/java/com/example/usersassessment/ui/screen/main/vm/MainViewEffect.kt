package com.example.usersassessment.ui.screen.main.vm

sealed interface MainViewEffect {
    sealed interface Dialog : MainViewEffect {
        data class Error(val title: String) : Dialog
    }
}