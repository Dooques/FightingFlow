package com.example.fightingflow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fightingflow.data.database.dao.CharacterDao
import com.example.fightingflow.data.database.dao.ComboDao
import com.example.fightingflow.data.database.dao.MoveDao
import com.example.fightingflow.data.database.dao.ProfileDao
import com.example.fightingflow.model.CharacterConverter
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboConverter
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.ProfileEntry

@Database(
    entities = [ProfileEntry::class, CharacterEntry::class, MoveEntry::class, ComboEntry::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(ComboConverter::class, CharacterConverter::class)
abstract class FlowDatabase: RoomDatabase() {
    abstract fun getUserDao(): ProfileDao
    abstract fun getCharacterDao(): CharacterDao
    abstract fun getMoveDao(): MoveDao
    abstract fun getComboDao(): ComboDao
}

