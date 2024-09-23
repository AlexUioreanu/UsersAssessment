package com.example.usersassessment.di

import androidx.room.Room
import com.example.usersassessment.data.db.AppDatabase
import com.example.usersassessment.data.repository.UserRepository
import com.example.usersassessment.data.repository.UserRepositoryImpl
import com.example.usersassessment.domain.usecase.FetchUsersUseCase
import com.example.usersassessment.domain.usecase.GetAllUsersUseCase
import com.example.usersassessment.domain.usecase.GetUsersByNickName
import com.example.usersassessment.domain.usecase.SaveAllUsersUseCase
import com.example.usersassessment.ui.screen.main.vm.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
}

val dataModule = module {
    single<UserRepository> {
        UserRepositoryImpl(
            api = get(),
            dao = get()
        )
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "user_db"
        ).build()
    }
    single {
        get<AppDatabase>().userDao()
    }

    singleOf(::UserRepositoryImpl)
}

val useCasesModule = module {
    factoryOf(::FetchUsersUseCase)
    factoryOf(::SaveAllUsersUseCase)
    factoryOf(::GetUsersByNickName)
    factoryOf(::GetAllUsersUseCase)
}