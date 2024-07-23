package com.bulut.luckyDiceRoller.ui.main

import androidx.lifecycle.ViewModel
import com.abdurrahmanbulut.sherlock.navigation.Navigator
import com.bulut.luckyDiceRoller.ui.utils.createEvent
import com.bulut.luckyDiceRoller.ui.utils.triggered

class MainScreenVM : ViewModel() {
    val onFirst = createEvent()
    val onSecond = createEvent()
    lateinit var navigator: Navigator

    fun onClickFirst() {
        onFirst.value = triggered
    }

    fun onClickSecond() {
        onSecond.value = triggered
    }
}
