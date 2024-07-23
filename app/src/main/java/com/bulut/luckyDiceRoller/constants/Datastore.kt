package com.bulut.luckyDiceRoller.constants

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "results")

class DataStoreHelper(private val context: Context) {
    companion object {
        val RESULTS_KEY = stringPreferencesKey("results")
        val LANGUAGE_KEY = stringPreferencesKey("language")
    }

    val resultsFlow: Flow<List<List<Int>>> =
        context.dataStore.data
            .map { preferences ->
                preferences[RESULTS_KEY]?.let { storedString ->
                    storedString.split(";").map { it.split(",").map { num -> num.toInt() } }
                } ?: emptyList()
            }

    val languageFlow: Flow<String?> =
        context.dataStore.data
            .map { preferences ->
                preferences[LANGUAGE_KEY]
            }

    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    suspend fun saveResults(newResults: List<Int>) {
        context.dataStore.edit { preferences ->
            val currentResults = preferences[RESULTS_KEY]?.split(";")?.map { it } ?: emptyList()
            val updatedResults = currentResults + newResults.joinToString(",")
            preferences[RESULTS_KEY] = updatedResults.joinToString(";")
        }
    }

    suspend fun clearResults() {
        context.dataStore.edit { preferences ->
            preferences.remove(RESULTS_KEY)
        }
    }
}
