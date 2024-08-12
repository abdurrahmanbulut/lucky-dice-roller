package com.bulut.luckyDiceRoller.ui.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bulut.luckyDiceRoller.ui.utils.EventHandler

class HistoryScreenVM : ViewModel() {
    val onBack = EventHandler<Unit>()
    val onPopup = EventHandler<Unit>()
    val onClear = EventHandler<Unit>()

    var showDialog by mutableStateOf(false)

    fun onBack()  {
        onBack.trigger()
    }

    fun onDelete()  {
        onPopup.trigger()
    }

    fun delete()  {
        onClear.trigger()
    }
}
