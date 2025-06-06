package com.example.fightingflow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.fightingflow.data.database.FlowDataRepository
import com.example.fightingflow.data.database.FlowDatabase
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.data.database.ProfileDatabaseRepository
import com.example.fightingflow.data.database.ProfileDbRepository
import com.example.fightingflow.data.database.dao.CharacterDao
import com.example.fightingflow.data.database.dao.ComboDao
import com.example.fightingflow.data.database.dao.MoveDao
import com.example.fightingflow.data.database.dao.ProfileDao
import com.example.fightingflow.data.datastore.CharacterDatastoreRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.data.datastore.ComboDatastoreRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.GameDatastoreRepository
import com.example.fightingflow.data.datastore.GameDsRepository
import com.example.fightingflow.data.datastore.ProfileDatastoreRepository
import com.example.fightingflow.data.datastore.ProfileDsRepository
import com.example.fightingflow.data.mediastore.MediaStoreUtil
import com.example.fightingflow.ui.InitViewModel
import com.example.fightingflow.ui.characterScreen.CharacterScreenViewModel
import com.example.fightingflow.ui.comboCreationScreen.ComboCreationViewModel
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayViewModel
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
        .fallbackToDestructiveMigration(false)
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
    single<FlowRepository> { FlowDataRepository(get(), get(), get()) }

    // DataStore
    single<ProfileDsRepository> { ProfileDatastoreRepository(androidContext().dataStore) }
    single<CharacterDsRepository> { CharacterDatastoreRepository(androidContext().dataStore) }
    single<ComboDsRepository> { ComboDatastoreRepository(androidContext().dataStore) }
    single<GameDsRepository> { GameDatastoreRepository(androidContext().dataStore) }

    // MediaStore
    single<MediaStoreUtil> { MediaStoreUtil(androidContext())}
}

val viewModelModule = module {
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { InitViewModel(get()) }
    viewModel { ComboDisplayViewModel(get(), get(), get()) }
    viewModel { ComboCreationViewModel(get(), get(), get(), get()) }
    viewModel { CharacterScreenViewModel(get()) }
}