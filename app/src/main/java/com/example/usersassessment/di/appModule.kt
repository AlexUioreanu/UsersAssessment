package com.example.usersassessment.di

import com.example.usersassessment.ui.screen.main.vm.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
}