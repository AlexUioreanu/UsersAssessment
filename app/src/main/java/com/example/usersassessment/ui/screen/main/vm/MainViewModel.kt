package com.example.usersassessment.ui.screen.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersassessment.domain.model.User
import com.example.usersassessment.domain.usecase.FetchUsersUseCase
import com.example.usersassessment.domain.usecase.GetAllUsersUseCase
import com.example.usersassessment.domain.usecase.GetUsersByNickName
import com.example.usersassessment.domain.usecase.SaveAllUsersUseCase
import com.example.usersassessment.utils.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class MainViewModel(
    private val networkMonitor: NetworkMonitor,
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val saveAllUsersUseCase: SaveAllUsersUseCase,
    private val getUsersByNickName: GetUsersByNickName
) : ViewModel() {

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

    private val _effects: MutableSharedFlow<MainViewEffect> = MutableSharedFlow(replay = 1)
    val effects: SharedFlow<MainViewEffect> get() = _effects.asSharedFlow()

    init {
        subscribeToNetworkConnectivity()
    }

    private fun subscribeToNetworkConnectivity() {
        viewModelScope.launch(Dispatchers.IO) {
            networkMonitor.isNetworkConnected.collectLatest { isConnected ->
                if (isConnected) {
                    if (_viewState.value.users.isEmpty()) {
                        launch(Dispatchers.IO) {
                            fetchUsersUseCase()
                                .onSuccess { list ->
                                    emitViewState { copy(users = list) }
                                    saveAllUsersUseCase(list)
                                }
                        }
                    }
                } else {
                    if (_viewState.value.users.isEmpty()) {
                        launch(Dispatchers.IO) {
                            val list = getAllUsersUseCase()
                            if (list.isEmpty()) {
                                setViewEffect { MainViewEffect.Dialog.Error(title = "Search will not work") }
                            } else {
                                emitViewState { copy(users = list) }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onSearchByNickName(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getUsersByNickName(nickName)
            emitViewState { copy(searchQuery = nickName, users = list) }
        }
    }

    private fun emitViewState(
        reducer: MainViewState.() -> MainViewState
    ) = viewModelScope.launch(Dispatchers.Main) {
        val newState = _viewState.value.reducer()
        _viewState.value = newState
    }

    private fun setViewEffect(builder: () -> MainViewEffect) {
        val effectValue = builder()
        viewModelScope.launch(Dispatchers.IO) { _effects.emit(effectValue) }
    }
}

data class MainViewState(
    val users: List<User>,
    val searchQuery: String,
    val onSearchByNickName: (String) -> Unit
)