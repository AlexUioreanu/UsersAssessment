package com.example.usersassessment.ui.screen.main.vm

import androidx.lifecycle.ViewModel
import com.example.usersassessment.domain.model.User
import com.example.usersassessment.domain.usecase.FetchUsersUseCase
import com.example.usersassessment.domain.usecase.GetUsersByNickName
import com.example.usersassessment.domain.usecase.SaveAllUsersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val saveAllUsersUseCase: SaveAllUsersUseCase,
    private val getUsersByNickName: GetUsersByNickName
) : ViewModel() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _viewState: MutableStateFlow<MainViewState> =
        MutableStateFlow(
            MainViewState(
                users = emptyList(),
                searchQuery = "",
                onSearchByNickName = { nickName ->
                    onSearchByNickName(nickName)
                })
        )
    val viewState: StateFlow<MainViewState> get() = _viewState.asStateFlow()

    init {
        coroutineScope.launch {
            fetchUsersUseCase().onSuccess { list ->
                emitViewState { copy(users = list) }
                saveAllUsersUseCase(list)
            }
        }
    }

    private fun onSearchByNickName(nickName: String) {
        coroutineScope.launch {
            val list = getUsersByNickName(nickName)
            emitViewState { copy(searchQuery = nickName, users = list) }
        }
    }

    private fun emitViewState(
        reducer: MainViewState.() -> MainViewState
    ) = coroutineScope.launch(Dispatchers.Main) {
        val newState = _viewState.value.reducer()
        _viewState.value = newState
    }
}

data class MainViewState(
    val users: List<User>,
    val searchQuery: String,
    val onSearchByNickName: (String) -> Unit
)