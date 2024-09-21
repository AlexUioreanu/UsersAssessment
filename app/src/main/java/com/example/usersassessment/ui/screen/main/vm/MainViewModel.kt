package com.example.usersassessment.ui.screen.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersassessment.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val usersRepository: UserRepository) : ViewModel() {
    init {
        viewModelScope.launch {
            val s = usersRepository.fetchUsers()
            println(s)
        }
    }
}