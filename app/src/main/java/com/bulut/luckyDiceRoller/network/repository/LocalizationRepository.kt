package com.bulut.luckyDiceRoller.network.repository

import com.bulut.luckyDiceRoller.constants.LocalizationKeys
import com.bulut.luckyDiceRoller.network.api.LocalizationApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalizationRepository(private val api: LocalizationApi) {
    suspend fun fetchLocalizationStrings(lang: String): List<LocalizationKeys> {
        return withContext(Dispatchers.IO) {
            api.getLocalizationStrings(lang)
        }
    }
}
