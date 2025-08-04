package com.example.fightingflow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.fightingflow.data.database.FlowDataRepository
import com.example.fightingflow.data.database.FlowDatabase
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.data.database.dao.CharacterDao
import com.example.fightingflow.data.database.dao.ComboDao
import com.example.fightingflow.data.database.dao.MoveDao
import com.example.fightingflow.data.datastore.CharacterDatastoreRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.data.datastore.ComboDatastoreRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.ProfanityDatastoreRepository
import com.example.fightingflow.data.datastore.ProfanityDsRepository
import com.example.fightingflow.data.datastore.ProfileDatastoreRepository
import com.example.fightingflow.data.datastore.UserDsRepository
import com.example.fightingflow.data.datastore.SettingsDatastoreRepository
import com.example.fightingflow.data.datastore.SettingsDsRepository
import com.example.fightingflow.data.firebase.FirebaseRepository
import com.example.fightingflow.data.firebase.GoogleAuthRepository
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.data.mediastore.MediaStoreUtil
import com.example.fightingflow.ui.InitViewModel
import com.example.fightingflow.ui.viewmodels.AddCharacterViewModel
import com.example.fightingflow.ui.viewmodels.CharacterViewModel
import com.example.fightingflow.ui.viewmodels.ComboCreationViewModel
import com.example.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.example.fightingflow.ui.comboItem.ComboItemViewModel
import com.example.fightingflow.ui.viewmodels.AuthViewModel
import com.example.fightingflow.ui.viewmodels.ProfanityViewModel
import com.example.fightingflow.ui.viewmodels.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private const val USER_SETTINGS = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

fun provideCharacterDao(flowDatabase: FlowDatabase): CharacterDao = flowDatabase.getCharacterDao()
fun provideMoveDao(flowDatabase: FlowDatabase): MoveDao = flowDatabase.getMoveDao()
fun provideComboDao(flowDatabase: FlowDatabase): ComboDao = flowDatabase.getComboDao()

fun provideDatabase(context: Context) =
    Room.databaseBuilder(context,
        FlowDatabase::class.java,
        "fighting_flow"
    )
        .fallbackToDestructiveMigration(false)
        .build()

val databaseModule = module {
    singleOf(::provideDatabase)
    singleOf(::provideCharacterDao)
    singleOf(::provideComboDao)
    singleOf(::provideMoveDao)
}

val repositoryModule = module {
    // Database
    single<FlowRepository> { FlowDataRepository(get(), get(), get()) }

    // DataStore
    single<UserDsRepository> { ProfileDatastoreRepository(androidContext().dataStore) }
    single<CharacterDsRepository> { CharacterDatastoreRepository(androidContext().dataStore) }
    single<ComboDsRepository> { ComboDatastoreRepository(androidContext().dataStore) }
    single<SettingsDsRepository> { SettingsDatastoreRepository(androidContext().dataStore) }
    single<ProfanityDsRepository> { ProfanityDatastoreRepository(androidContext().dataStore) }

    // MediaStore
    singleOf(::MediaStoreUtil)

    // Firebase
    singleOf(::FirebaseRepository)
    single<GoogleAuthService> { GoogleAuthRepository(get()) }

}

val viewModelModule = module {
    viewModelOf(::UserViewModel)
    viewModelOf(::InitViewModel)
    viewModelOf(::ComboDisplayViewModel)
    viewModelOf(::ComboCreationViewModel)
    viewModelOf(::CharacterViewModel)
    viewModelOf(::ComboItemViewModel)
    viewModelOf(::AddCharacterViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::ProfanityViewModel)
}