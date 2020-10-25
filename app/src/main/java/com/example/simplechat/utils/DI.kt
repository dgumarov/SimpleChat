package com.example.simplechat.utils

import androidx.room.Room
import com.example.simplechat.entity.Database
import com.example.simplechat.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Preferences(get()) }
    single { Room.databaseBuilder(get(), Database::class.java, "database").build() }
    single { get<Database>().messageDao() }
    viewModel { MainViewModel(get(), get()) }
}