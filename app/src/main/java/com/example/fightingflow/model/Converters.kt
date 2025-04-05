package com.example.fightingflow.model

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ComboConverter {
    private val jsonAdapter = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
        .adapter(ComboEntry::class.java)

    @TypeConverter
    fun comboToString(combo: ComboEntry?) =
        combo?.let { jsonAdapter.toJson(it) } ?: ""

    @TypeConverter
    fun stringToCombo(json: kotlin.String?) =
        json?.let { jsonAdapter.fromJson(json) }
}

class ListConverter {
    private val jsonAdapter = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
        .adapter(ComboEntry::class.java)

    @TypeConverter
    fun comboToString(combo: ComboEntry?) =
        combo?.let { jsonAdapter.toJson(it) } ?: ""

    @TypeConverter
    fun stringToCombo(json: kotlin.String?) =
        json?.let { jsonAdapter.fromJson(json) }
}

class UserConverter {
    private val jsonAdapter = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
        .adapter(ProfileEntry::class.java)

    @TypeConverter
    fun userToString(user: ProfileEntry?) =
        user?.let { jsonAdapter.toJson(it) } ?: ""

    @TypeConverter
    fun stringToUser(json: kotlin.String?) =
        json?.let { jsonAdapter.fromJson(json) }
}