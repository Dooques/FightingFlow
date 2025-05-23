package com.example.fightingflow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.fightingflow.data.database.FlowDatabase
import com.example.fightingflow.data.database.TekkenDatabaseRepository
import com.example.fightingflow.data.database.ProfileDatabaseRepository
import com.example.fightingflow.data.database.TekkenDbRepository
import com.example.fightingflow.data.database.ProfileDbRepository
import com.example.fightingflow.data.database.dao.CharacterDao
import com.example.fightingflow.data.database.dao.ComboDao
import com.example.fightingflow.data.database.dao.MoveDao
import com.example.fightingflow.data.database.dao.ProfileDao
import com.example.fightingflow.ui.InitViewModel
import com.example.fightingflow.data.datastore.ComboDatastoreRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.ProfileDatastoreRepository
import com.example.fightingflow.data.datastore.ProfileDsRepository
import com.example.fightingflow.data.datastore.SelectedCharacterDatastoreRepository
import com.example.fightingflow.data.datastore.SelectedCharacterDsRepository
import com.example.fightingflow.ui.comboAddScreen.AddComboViewModel
import com.example.fightingflow.ui.comboScreen.ComboViewModel
import com.example.fightingflow.ui.profileScreen.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private const val USER_SETTINGS = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

fun provideUserDao(flowDatabase: FlowDatabase): ProfileDao = flowDatabase.getUserDao()
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
    single<ProfileDbRepository> { ProfileDatabaseRepository(get()) }
    single<TekkenDbRepository> { TekkenDatabaseRepository(get(), get(), get()) }

    // DataStore
    single<ProfileDsRepository> { ProfileDatastoreRepository(androidContext().dataStore) }
    single<SelectedCharacterDsRepository> { SelectedCharacterDatastoreRepository(androidContext().dataStore) }
    single<ComboDsRepository> { ComboDatastoreRepository(androidContext().dataStore) }
}

val viewModelModule = module {
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { InitViewModel(get()) }
    viewModel { ComboViewModel(get(), get()) }
    viewModel { AddComboViewModel(get(), get()) }
}