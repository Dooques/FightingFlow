package com.example.fightingflow.di

import android.content.Context
import androidx.room.Room
import com.example.fightingflow.data.database.initData.InitViewModel
import com.example.fightingflow.data.database.dao.UserDao
import com.example.fightingflow.data.database.FlowDatabase
import com.example.fightingflow.data.database.OfflineTekkenDataRepository
import com.example.fightingflow.data.database.OfflineUserDataRepository
import com.example.fightingflow.data.database.TekkenDataRepository
import com.example.fightingflow.data.database.UserDataRepository
import com.example.fightingflow.data.database.dao.CharacterDao
import com.example.fightingflow.data.database.dao.ComboDao
import com.example.fightingflow.data.database.dao.MoveDao
import com.example.fightingflow.ui.comboAddScreen.AddComboViewModel
import com.example.fightingflow.ui.comboViewScreen.ComboViewModel
import com.example.fightingflow.ui.userInputForms.InputViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun provideDatabase(context: Context) =
    Room.databaseBuilder(
        context,
        FlowDatabase::class.java,
        "fighting_flow"
    )
        .fallbackToDestructiveMigration()
        .build()

fun provideUserDao(flowDatabase: FlowDatabase): UserDao = flowDatabase.getUserDao()
fun provideCharacterDao(flowDatabase: FlowDatabase): CharacterDao = flowDatabase.getCharacterDao()
fun provideMoveDao(flowDatabase: FlowDatabase): MoveDao = flowDatabase.getMoveDao()
fun provideComboDao(flowDatabase: FlowDatabase): ComboDao = flowDatabase.getComboDao()


val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideUserDao(get()) }
    single { provideCharacterDao(get()) }
    single { provideComboDao(get()) }
    single { provideMoveDao(get()) }
}

val repositoryModule = module {
    single<UserDataRepository> { OfflineUserDataRepository(get()) }
    single<TekkenDataRepository> { OfflineTekkenDataRepository(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { InputViewModel(get()) }
    viewModel { InitViewModel(get()) }
    viewModel { ComboViewModel(get()) }
    viewModel { AddComboViewModel(get()) }
}