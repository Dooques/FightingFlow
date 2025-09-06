package com.dooques.fightingflow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dooques.fightingflow.data.database.dao.CharacterDao
import com.dooques.fightingflow.data.database.dao.ComboDao
import com.dooques.fightingflow.data.database.dao.MoveDao
import com.dooques.fightingflow.model.CharacterConverter
import com.dooques.fightingflow.model.CharacterEntry
import com.dooques.fightingflow.model.ComboConverter
import com.dooques.fightingflow.model.ComboEntry
import com.dooques.fightingflow.model.MoveEntry

@Database(
    entities = [CharacterEntry::class, MoveEntry::class, ComboEntry::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(ComboConverter::class, CharacterConverter::class)
abstract class FlowDatabase: RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
    abstract fun getMoveDao(): MoveDao
    abstract fun getComboDao(): ComboDao
}

