package com.example.usersassessment.ui.screen.main.vm

import com.example.usersassessment.domain.model.User
import com.example.usersassessment.domain.usecase.FetchUsersUseCase
import com.example.usersassessment.domain.usecase.GetAllUsersUseCase
import com.example.usersassessment.domain.usecase.GetUsersByNickName
import com.example.usersassessment.domain.usecase.SaveAllUsersUseCase
import com.example.usersassessment.utils.MainDispatcherRule
import com.example.usersassessment.utils.NetworkMonitor
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MainViewModel

    private var mockUserList: List<User> = listOf(
        User(
            id = 1,
            nickName = "User1",
            imageUrl = "url1"
        ),
        User(
            id = 2,
            nickName = "User2",
            imageUrl = "url2"
        ), User(
            id = 3,
            nickName = "User3",
            imageUrl = "url3"
        ),
        User(
            id = 4,
            nickName = "User4",
            imageUrl = "url4"
        )
    )

    private val fetchUsersUseCase: FetchUsersUseCase = mockk(relaxed = true)
    private val getAllUsersUseCase: GetAllUsersUseCase = mockk(relaxed = true)
    private val saveAllUsersUseCase: SaveAllUsersUseCase = mockk(relaxed = true)
    private val getUsersByNickName: GetUsersByNickName = mockk(relaxed = true)
    private val networkMonitor: NetworkMonitor = mockk(relaxed = true)

    @Test
    fun `fetch users when network is connected`() = runTest {
        every { networkMonitor.isNetworkConnected } returns MutableStateFlow(true)
        coEvery { fetchUsersUseCase() } returns Result.success(mockUserList)
        initViewModel()

        advanceUntilIdle()

        coVerify {
            fetchUsersUseCase()
            saveAllUsersUseCase(mockUserList)
        }
        coVerify { getAllUsersUseCase wasNot Called }

        assertEquals(mockUserList, viewModel.viewState.value.users)
    }

    private fun initViewModel() {
        viewModel = MainViewModel(
            networkMonitor = networkMonitor,
            fetchUsersUseCase = fetchUsersUseCase,
            getAllUsersUseCase = getAllUsersUseCase,
            saveAllUsersUseCase = saveAllUsersUseCase,
            getUsersByNickName = getUsersByNickName
        )
    }
}