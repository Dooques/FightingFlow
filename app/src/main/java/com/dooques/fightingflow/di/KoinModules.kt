package com.dooques.fightingflow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.dooques.fightingflow.data.database.FlowDataRepository
import com.dooques.fightingflow.data.database.FlowDatabase
import com.dooques.fightingflow.data.database.FlowRepository
import com.dooques.fightingflow.data.database.dao.CharacterDao
import com.dooques.fightingflow.data.database.dao.ComboDao
import com.dooques.fightingflow.data.database.dao.MoveDao
import com.dooques.fightingflow.data.datastore.CharacterDatastoreRepository
import com.dooques.fightingflow.data.datastore.CharacterDsRepository
import com.dooques.fightingflow.data.datastore.ComboDatastoreRepository
import com.dooques.fightingflow.data.datastore.ComboDsRepository
import com.dooques.fightingflow.data.datastore.ProfanityDatastoreRepository
import com.dooques.fightingflow.data.datastore.ProfanityDsRepository
import com.dooques.fightingflow.data.datastore.ProfileDatastoreRepository
import com.dooques.fightingflow.data.datastore.UserDsRepository
import com.dooques.fightingflow.data.datastore.SettingsDatastoreRepository
import com.dooques.fightingflow.data.datastore.SettingsDsRepository
import com.dooques.fightingflow.data.firebase.FirebaseRepository
import com.dooques.fightingflow.data.firebase.GoogleAuthRepository
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.data.mediastore.MediaStoreUtil
import com.example.fightingflow.ui.InitViewModel
import com.dooques.fightingflow.ui.viewmodels.AddCharacterViewModel
import com.dooques.fightingflow.ui.viewmodels.CharacterViewModel
import com.dooques.fightingflow.ui.viewmodels.ComboCreationViewModel
import com.dooques.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.dooques.fightingflow.ui.comboItem.ComboItemViewModel
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.ProfanityViewModel
import com.dooques.fightingflow.ui.viewmodels.SearchFilterViewModel
import com.dooques.fightingflow.ui.viewmodels.UserViewModel
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
    viewModelOf(::SearchFilterViewModel)
    viewModelOf(::ProfanityViewModel)
}