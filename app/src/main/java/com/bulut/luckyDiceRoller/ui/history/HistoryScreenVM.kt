package com.bulut.luckyDiceRoller.ui.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bulut.luckyDiceRoller.ui.utils.createEvent
import com.bulut.luckyDiceRoller.ui.utils.triggered

class HistoryScreenVM : ViewModel() {
    val onBack = createEvent()
    val onClear = createEvent()
    val onPopup = createEvent()

    var showDialog by mutableStateOf(false)

    fun onBack()  {
        onBack.value = triggered
    }

    fun onDelete()  {
        onPopup.value = triggered
    }

    fun delete()  {
        onClear.value = triggered
    }
}
