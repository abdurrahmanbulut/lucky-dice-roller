package com.bulut.luckyDiceRoller.constants

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.firstOrNull
import java.util.Locale

data object LocalizationKeys {
    const val ROLL_DICE = "Roll Dice"
    const val ROLLING = "Rolling..."
    const val DELETE = "Delete"
    const val ROLL_AGAIN = "Roll Again"
    const val NUM_OF_DICE = "Choose Number Of Dice"
    const val DICE_HISTORY = "Dice History"
    const val LAST_DICE = "Last Dice"
    const val DELETE_POPUP_INFO = "Are you sure you want to delete all?"
    const val DELETE_POPUP_APPROVE_BUTTON = "Approve"
    const val DELETE_POPUP_CANCEL_BUTTON = "Cancel"
}

val englishLocalization =
    mapOf(
        LocalizationKeys.ROLL_DICE to "Roll Dice",
        LocalizationKeys.ROLLING to "Rolling...",
        LocalizationKeys.DELETE to "Delete",
        LocalizationKeys.ROLL_AGAIN to "Roll Again",
        LocalizationKeys.NUM_OF_DICE to "Choose Number Of Dice",
        LocalizationKeys.DICE_HISTORY to "Dice History",
        LocalizationKeys.LAST_DICE to "Last Dice",
        LocalizationKeys.DELETE_POPUP_INFO to "Are you sure you want to delete all?",
        LocalizationKeys.DELETE_POPUP_APPROVE_BUTTON to "Approve",
        LocalizationKeys.DELETE_POPUP_CANCEL_BUTTON to "Cancel",
    )

val turkishLocalization =
    mapOf(
        LocalizationKeys.ROLL_DICE to "Zar At",
        LocalizationKeys.ROLLING to "Zar Atılıyor...",
        LocalizationKeys.DELETE to "Sil",
        LocalizationKeys.ROLL_AGAIN to "Tekrar Zar At",
        LocalizationKeys.NUM_OF_DICE to "Zar Sayısını Seçin",
        LocalizationKeys.DICE_HISTORY to "Zar Geçmişi",
        LocalizationKeys.LAST_DICE to "Son Zar",
        LocalizationKeys.DELETE_POPUP_INFO to "Tümünü silmek istediğinizden emin misiniz?",
        LocalizationKeys.DELETE_POPUP_APPROVE_BUTTON to "Onayla",
        LocalizationKeys.DELETE_POPUP_CANCEL_BUTTON to "Vazgeç",
    )

var localizationStrings by mutableStateOf<Map<String, String>>(englishLocalization)
var language by mutableStateOf("en")

fun get(key: String): String {
    return localizationStrings[key] ?: key
}

suspend fun switchLanguage(
    language1: String,
    dataStoreHelper: DataStoreHelper,
) {
    when (language1) {
        "en" -> {
            localizationStrings = englishLocalization
            dataStoreHelper.saveLanguage("en")
            language = "en"
        }

        "tr" -> {
            localizationStrings = turkishLocalization
            dataStoreHelper.saveLanguage("tr")
            language = "tr"
        }

        else -> {
            localizationStrings = englishLocalization
            dataStoreHelper.saveLanguage("en")
            language = "en"
        }
    }
}

fun getDeviceLanguage(): String {
    return Locale.getDefault().language
}

suspend fun getInitialLocalizationStrings(dataStoreHelper: DataStoreHelper): Map<String, String> {
    val savedLanguage = dataStoreHelper.languageFlow.firstOrNull() ?: getDeviceLanguage()
    var temp: Map<String, String>? = null
    when (savedLanguage) {
        "en" -> {
            temp = englishLocalization
            language = "en"
        }

        "tr" -> {
            temp = turkishLocalization
            language = "tr"
        }

        else -> {
            temp = englishLocalization
            language = "en"
        }
    }
    return temp
}
