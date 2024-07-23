package com.bulut.luckyDiceRoller.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

class InsetsViewModel : ViewModel() {
    var statusBarHeight by mutableStateOf(0.dp)
    var navigationBarHeight by mutableStateOf(0.dp)
}
