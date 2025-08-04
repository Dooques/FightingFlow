package com.example.fightingflow.ui.viewmodels

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
        val profanityData = profanityData
        if (profanityData.isNotEmpty()) {
            profanityData.forEach { profanityObject ->
                profanityObject.dictionary.forEach { wordObject ->
                    val profaneWord = wordObject.match.replace("*", "")
                    if (profaneWord in username) {
                        val exceptionsList = wordObject.exceptions
                        exceptionsList?.forEach { exception ->
                            if (exception.first() == '*') {
                                val exceptionWord = "$profaneWord${exception.replace("*", "")}"
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
                        return true
                    }
                }
            }
        } else {
            return true
        }
        return false
    }
}