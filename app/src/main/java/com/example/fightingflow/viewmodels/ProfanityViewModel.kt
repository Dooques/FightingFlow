package com.example.fightingflow.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.fightingflow.data.datastore.ProfanityDsRepository
import com.example.fightingflow.model.ProfanityData
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.IOException
import java.nio.charset.Charset

class ProfanityViewModel(): ViewModel() {

    var profanityData: List<ProfanityData> = emptyList()

    fun readJsonFromAssets(context: Context, fileName: String) {
        Timber.d("--Serializing $fileName data into object--")
        val jsonString: String
        try {
            Timber.d("Attempting to serialize data...")
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            jsonString = String(buffer, Charset.defaultCharset())
            Timber.d("Json Data: $jsonString")
        } catch (e: IOException) {
            Timber.e(e, "Error reading assets file.")
            return
        }

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        try {
            Timber.d("Json object parsed to ProfanityData object.")
            profanityData = json.decodeFromString<List<ProfanityData>>(jsonString)
        } catch (e: SerializationException) {
            Timber.e(e, "Error parsing JSON string into ProfanityDataObject.")
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Illegal argument during JSON parsing.")
        }
    }

    fun checkForUsernameInProfanityFilter(username: String): Boolean {
        Timber.d("Checking username for profanity")
        val profanityData = profanityData
        if (profanityData.isNotEmpty()) {
            Timber.d("Data: $profanityData")
            profanityData.forEach { profanityObject ->
                Timber.d("Profanity Object: $profanityObject")
                profanityObject.dictionary.forEach { wordObject ->
                    Timber.d("Word to check: ${wordObject.match}")
                    val profaneWord = wordObject.match.replace("*", "")
                    if (profaneWord in username) {
                        val exceptionsList = wordObject.exceptions
                        exceptionsList?.forEach { exception ->
                            Timber.d("Exception: $exception")
                            if (exception.first() == '*') {
                                val exceptionWord = "$profaneWord${exception.replace("*", "")}"
                                Timber.d("")
                                if (exceptionWord in username) {
                                    Timber.d("Profanity word is an exception, returning false")
                                    return false
                                }
                            }
                            if (exception.last() == '*') {
                                val exceptionWord = "${exception.replace("*", "")}$profaneWord"
                                if (exceptionWord in username) {
                                    Timber.d("Profanity word is an exception, returning false")
                                    return false
                                }
                            }
                            if ('*' in exception) {
                                val wordSplit = exception.split("*")
                                "${wordSplit[0]}$profaneWord${wordSplit[1]}"
                            }
                        }
                        Timber.d("No exceptions, profane word found, returning true")
                        return true
                    } else {
                        Timber.d("No profane words found, returning false")
                    }
                }
            }
        } else {
            Timber.d("Profanity filter not found, returning true")
            return true
        }
        Timber.d("No problems found, username is ok.")
        return false
    }
}