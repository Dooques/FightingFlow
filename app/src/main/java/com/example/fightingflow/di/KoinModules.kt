package com.example.fightingflow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.fightingflow.data.database.FlowDatabase
import com.example.fightingflow.data.database.OfflineTekkenDataRepository
import com.example.fightingflow.data.database.OfflineUserDataRepository
import com.example.fightingflow.data.database.TekkenDataRepository
import com.example.fightingflow.data.database.UserDataRepository
import com.example.fightingflow.data.database.dao.CharacterDao
import com.example.fightingflow.data.database.dao.ComboDao
import com.example.fightingflow.data.database.dao.MoveDao
import com.example.fightingflow.data.database.dao.UserDao
import com.example.fightingflow.data.database.initData.InitViewModel
import com.example.fightingflow.data.datastore.ComboDataRepository
import com.example.fightingflow.data.datastore.ComboRepository
import com.example.fightingflow.data.datastore.FlowPreferencesRepository
import com.example.fightingflow.data.datastore.PreferencesRepository
import com.example.fightingflow.data.datastore.SelectedCharacterDataRepository
import com.example.fightingflow.data.datastore.SelectedCharacterRepository
import com.example.fightingflow.ui.comboAddScreen.AddComboViewModel
import com.example.fightingflow.ui.comboViewScreen.ComboViewModel
import com.example.fightingflow.ui.userInputForms.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private const val USER_SETTINGS = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

fun provideUserDao(flowDatabase: FlowDatabase): UserDao = flowDatabase.getUserDao()
fun provideCharacterDao(flowDatabase: FlowDatabase): CharacterDao = flowDatabase.getCharacterDao()
fun provideMoveDao(flowDatabase: FlowDatabase): MoveDao = flowDatabase.getMoveDao()
fun provideComboDao(flowDatabase: FlowDatabase): ComboDao = flowDatabase.getComboDao()


fun provideDatabase(context: Context) =
    Room.databaseBuilder(
        context,
        FlowDatabase::class.java,
        "fighting_flow"
    )
        .fallbackToDestructiveMigration()
        .build()

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideUserDao(get()) }
    single { provideCharacterDao(get()) }
    single { provideComboDao(get()) }
    single { provideMoveDao(get()) }
}

val repositoryModule = module {
    // Database
    single<UserDataRepository> { OfflineUserDataRepository(get()) }
    single<TekkenDataRepository> { OfflineTekkenDataRepository(get(), get(), get()) }

    // DataStore
    single<PreferencesRepository> { FlowPreferencesRepository(androidContext().dataStore) }
    single<SelectedCharacterRepository> { SelectedCharacterDataRepository(androidContext().dataStore) }
    single<ComboRepository> { ComboDataRepository(androidContext().dataStore) }
}

val viewModelModule = module {
    viewModel { UserViewModel(get(), get()) }
    viewModel { InitViewModel(get()) }
    viewModel { ComboViewModel(get(), get()) }
    viewModel { AddComboViewModel(get(), get()) }
}