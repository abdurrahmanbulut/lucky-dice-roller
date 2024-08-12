package com.bulut.luckyDiceRoller.ui.main.home

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulut.luckyDiceRoller.network.repository.StockRepository
import com.bulut.luckyDiceRoller.ui.theme.drawables
import com.bulut.luckyDiceRoller.ui.utils.EventHandler

class HomeScreenVM(private val stockRepository: StockRepository) : ViewModel() {
    val onButtonClicked = EventHandler<Unit>()
    val navigateToHistory = EventHandler<Unit>()
    val test = "Home Screen"

    var started by mutableStateOf(false)
    var isRolling by mutableStateOf(false)
    var diceSides by mutableIntStateOf(6)
    var diceCount by mutableIntStateOf(2)
    var diceResults by mutableStateOf(List(diceCount) { 1 })
    var rotation by mutableStateOf(Animatable(0f))
    var openMenu by mutableStateOf(false)

    val dices =
        listOf(
            drawables.dice1,
            drawables.dice2,
            drawables.dice3,
            drawables.dice4,
            drawables.dice5,
            drawables.dice6,
        )

    init {
        getStock()
    }

    private fun getStock() {
        stockRepository.getStockPrice(viewModelScope, "IBM")
            .success {
                println(it)
                println(it.globalQuote.symbol)
                println(it.globalQuote.price)
            }
            .failure {
                println(it)
            }
    }

    fun onButtonClicked() {
        onButtonClicked.trigger()
        openMenu = false
    }

    fun chooseNumberOfDice(number: Int) {
        diceCount = number
        started = false
    }

    fun onDelete() {
        started = false
        isRolling = false
        diceResults = List(diceCount) { 1 }
        rotation = Animatable(0f)
    }

    fun onHistory()  {
        navigateToHistory.trigger()
    }
}
